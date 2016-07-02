package iit.android.swarachakra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;

public class EnglishKeyboardActionListener implements OnKeyboardActionListener,
		OnTouchListener {

	private int ENTER;
	private int BACKSPACE;
	private int SPACE;
	private int ENGLISH;
	private int SYMBOLS;
	private int SHIFT;
	private static EnglishKeyboardView mKeyboardView;
	private SoftKeyboard mSoftKeyboard;
	private HashMap<Integer, KeyAttr> mKeys;
	private InputConnection mInputConnection;
	private List<Integer> mSpecialKeys;
	private static HashMap<Integer, Key> mKeyboardKeys;

	private static final int MSG_SHOW_PREVIEW = 1;
	private static final int MSG_REMOVE_PREVIEW = 2;
	private static final int MSG_STOP_DOUBLE_TAP = 3;
	private static final int DELAY_BEFORE_PREVIEW = 0;
	private static final int DELAY_AFTER_PREVIEW = 100;
	private static final int DELAY_SHIFT_DOUBLE_TAP = 200;

	private static PopupWindow mPreviewPopup;
	private static TextView mPreviewTextView;

	private boolean isShifted;
	private boolean inSymbolMode;
	private boolean inMoreSymbolMode;
	private boolean isPersistent;
	private boolean spaceHandled;
	private boolean shiftHandled;
	private boolean inQuickSymbolMode;
	private boolean isDoubleTapReady;

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SHOW_PREVIEW:
				showPreview(msg.arg1);
				break;
			case MSG_REMOVE_PREVIEW:
				removePreview();
				break;
			case MSG_STOP_DOUBLE_TAP:
				isDoubleTapReady = false;
				break;
			}
		}
	};

	public void initialize(EnglishKeyboardView kv) {
		mKeyboardView = kv;

		BACKSPACE = mKeyboardView.BACKSPACE;
		ENTER = mKeyboardView.ENTER;
		SPACE = mKeyboardView.SPACE;
		ENGLISH = mKeyboardView.ENGLISH;
		SYMBOLS = mKeyboardView.SYMBOLS;
		SHIFT = mKeyboardView.SHIFT;
		mSpecialKeys = new ArrayList<Integer>();
		mSpecialKeys = Arrays.asList(BACKSPACE, ENTER, SPACE, ENGLISH, SYMBOLS,
				SHIFT);
		buildKeyboardKeys();

		mPreviewPopup = mKeyboardView.mPreviewPopup;
		mPreviewTextView = mKeyboardView.mPreviewTextView;

		initBooleans();
	}
	
	private void initBooleans(){
		isShifted = false;
		inSymbolMode = false;
		inMoreSymbolMode = false;
		isPersistent = false;
		inQuickSymbolMode = false;
		isDoubleTapReady = false;
	}

	@SuppressLint("UseSparseArrays")
	private static void buildKeyboardKeys() {
		mKeyboardKeys = new HashMap<Integer, Key>();
		List<Key> keys = mKeyboardView.getKeyboard().getKeys();
		for (Key key : keys) {
			mKeyboardKeys.put(key.codes[0], key);
		}
	}

	public void setSoftKeyboard(SoftKeyboard sk) {
		this.mSoftKeyboard = sk;
	}

	public void setKeysMap(HashMap<Integer, KeyAttr> mKeys) {
		this.mKeys = mKeys;
	}

	public void setInputConnection(InputConnection ic) {
		mInputConnection = ic;
		initBooleans();
		changeLayout();
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		int action = me.getActionMasked();
		if (action == MotionEvent.ACTION_UP
				|| action == MotionEvent.ACTION_POINTER_UP) {
			mHandler.sendMessageDelayed(
					mHandler.obtainMessage(MSG_REMOVE_PREVIEW),
					DELAY_AFTER_PREVIEW);
		} else if (action == MotionEvent.ACTION_MOVE) {
			List<Key> keys = mKeyboardView.getKeyboard().getKeys();
			for (Key key : keys) {
				if (key.pressed == true
						&& !(mSpecialKeys.contains(key.codes[0]))) {
					mHandler.removeMessages(MSG_REMOVE_PREVIEW);
					mHandler.sendMessageDelayed(mHandler.obtainMessage(
							MSG_SHOW_PREVIEW, key.codes[0], 0),
							DELAY_BEFORE_PREVIEW);
				} else if (key.pressed == true
						&& (mSpecialKeys.contains(key.codes[0]))) {
					mHandler.sendMessageDelayed(
							mHandler.obtainMessage(MSG_REMOVE_PREVIEW),
							DELAY_AFTER_PREVIEW);
				}
			}
		}
		return mKeyboardView.onTouchEvent(me);
	}

	@Override
	public void onKey(int arg0, int[] arg1) {

	}

	@Override
	public void onPress(int keyCode) {
		spaceHandled = false;
		shiftHandled = false;
		if (keyCode == SYMBOLS) {
			inQuickSymbolMode = true;
			handleSpecialInput(SYMBOLS);
			Log.d("testing", "on press shift");
		}
		if (!(mSpecialKeys.contains(keyCode))) {
			mHandler.removeMessages(MSG_REMOVE_PREVIEW);
			mHandler.sendMessageDelayed(
					mHandler.obtainMessage(MSG_SHOW_PREVIEW, keyCode, 0),
					DELAY_BEFORE_PREVIEW);
		}
	}

	@Override
	public void onRelease(int keyCode) {
		String label = getLabel(keyCode);
		if (mKeys.containsKey(keyCode)) {
			if (isShifted && !(isPersistent)) {
				isShifted = false;
				changeLayout();
			}
			commitText(label);
			if (inQuickSymbolMode) {
				handleSpecialInput(SYMBOLS);
			}
		} else {
			if (keyCode != SYMBOLS) {
				handleSpecialInput(keyCode);
			}
		}
		inQuickSymbolMode = false;

	}

	private void showPreview(int keyCode) {
		if (keyCode != 0) {

			Key key = mKeyboardKeys.get(keyCode);
			int w = 0;
			int h = 0;
			int x = 0;
			int y = 0;
			w = key.width * 5 / 3;
			h = key.height*5/3;
			x = key.x - w / 5;
			y = key.y - h + (int)(0.02*h);

			mPreviewTextView.setText(getLabel(keyCode));

			if (mPreviewPopup.isShowing()) {
				mPreviewPopup.update(x, y, w, h);
			} else {
				mPreviewPopup.setWidth(w);
				mPreviewPopup.setHeight(h);
				mPreviewPopup.showAtLocation(mKeyboardView, Gravity.NO_GRAVITY,
						x, y);
			}
			mPreviewTextView.setVisibility(View.VISIBLE);
		}
	}

	private void removePreview() {
		mPreviewTextView.setVisibility(View.GONE);
	}

	@Override
	public void onText(CharSequence arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeDown() {
		removePreview();

	}

	@Override
	public void swipeLeft() {
		removePreview();

	}

	@Override
	public void swipeRight() {
		removePreview();

	}

	@Override
	public void swipeUp() {
		removePreview();

	}

	public void onLongPress(Key key) {
		if (key.codes[0] == SHIFT) {
			if(!inSymbolMode){
				if (isShifted && isPersistent) {
					isPersistent = false;
					isShifted = false;
					changeLayout();
				} else {
					isPersistent = true;
					isShifted = true;
					changeLayout();
				}
				shiftHandled = true;
			}

		} else if (key.codes[0] == SPACE) {
			InputMethodManager imm = (InputMethodManager) mSoftKeyboard
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showInputMethodPicker();
			spaceHandled = true;
		}
	}

	private String getLabel(int keyCode) {
		if (mKeys.containsKey(keyCode)) {
			if (inSymbolMode) {
				if (keyCode == 53) {
					return ".";
				}
				if(inMoreSymbolMode){
					return mKeys.get(keyCode + 80).label;
				}
				return mKeys.get(keyCode + 54).label;
			} else if (isShifted) {
				if (keyCode == 53) {
					return ".";
				}
				return mKeys.get(keyCode + 26).label;
			}
			return mKeys.get(keyCode).label;
		}
		return "";
	}

	
	public void changeLayout() {
		List<Key> keys = mKeyboardView.getKeyboard().getKeys();
		for (Key key : keys) {
			if (mKeys.containsKey(key.codes[0])) {
				key.label = getLabel(key.codes[0]);
			} else {
				int code = key.codes[0];
				if (code == SHIFT) {

					if (!(inSymbolMode)) {

						if (!isPersistent) {
							Drawable normalShift = mSoftKeyboard.getResources()
									.getDrawable(R.drawable.shift);
							key.icon = normalShift;
							key.label = null;
						} else {
							Drawable activeShift = mSoftKeyboard.getResources()
									.getDrawable(R.drawable.shift_active);
							key.icon = activeShift;
							key.label = null;
						}

					} else {
						if (inMoreSymbolMode) {
							key.label = "@#$";
							key.icon = null;
						} else {
							key.label = "<~+";
							key.icon = null;
						}
					}
				} else if (code == SYMBOLS) {
					if (inSymbolMode) {
						key.label = "ABC";
					} else {
						key.label = "123";
					}
				} else if (code == ENGLISH){
					key.label = mSoftKeyboard.mainLanguageSymbol;
				}
			}
		}
		mKeyboardView.invalidateAllKeys();
	}

	/**
	 * Changes the language of the keyboard
	 * 
	 * @param layout
	 *            name string of the layout resource
	 */
	private void changeLanguage() {
		mSoftKeyboard.changeLanguage();
	}

	/**
	 * Handles what to be done when keys like ENTER, SPACE, BACKSPACE, EN,
	 * SHIFT, SYM
	 * 
	 * @param keyCode
	 *            key code of the key whose functionality is to be handled
	 */
	private void handleSpecialInput(int keyCode) {
		if (keyCode == SHIFT && !shiftHandled) {
			if (!inSymbolMode) {
				if(isDoubleTapReady){
					isShifted = true;
					isPersistent = true;
					isDoubleTapReady = false;
					changeLayout();
				}else{
					if (isShifted) {
						isShifted = false;
						isPersistent = false;
						changeLayout();
					} else {
						isShifted = true;
						changeLayout();
					}
					isDoubleTapReady = true;
					mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_DOUBLE_TAP), DELAY_SHIFT_DOUBLE_TAP);
				}
			} else {
				inMoreSymbolMode = !(inMoreSymbolMode);
				changeLayout();
			}
		}

		else if (keyCode == BACKSPACE) {
			CharSequence selection = mInputConnection.getSelectedText(0);
			commitText("");

			if (selection == null) {
				mInputConnection.deleteSurroundingText(1, 0);
			}
		}

		else if (keyCode == SPACE) {
			if (!spaceHandled) {
				mInputConnection.finishComposingText();
				commitText(" ");
			}
		}

		else if (keyCode == SYMBOLS) {
			if (inSymbolMode) {
				inSymbolMode = !(inSymbolMode);
				inMoreSymbolMode = false;
				if (isPersistent && isShifted) {
					changeLayout();
				} else {
					changeLayout();
				}
			} else {
				inSymbolMode = !(inSymbolMode);
				inMoreSymbolMode = false;
				changeLayout();
			}
		}

		else if (keyCode == ENGLISH) {
			changeLanguage();
		}

		else if (keyCode == ENTER) {

			handleEnter();
		}

	}

	private void handleEnter() {
		// TODO Auto-generated method stub
		mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
				KeyEvent.KEYCODE_ENTER));
		mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
				KeyEvent.KEYCODE_ENTER));
	}

	/**
	 * Send text to the EditText
	 * 
	 * @param text
	 *            string to be sent to EditText
	 */
	private void commitText(String text) {
		mInputConnection.setComposingText(text, 1);
		mInputConnection.finishComposingText();
	}

}
