package iit.android.swarachakra;

import iit.android.language.Language;

import java.util.HashMap;

import android.content.Context;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.PopupWindow;
import android.widget.TextView;

public class EnglishKeyboardView extends CustomKeyboardView {
	
	private HashMap<Integer, KeyAttr> mKeys;
	private EnglishKeyboardActionListener mActionListener;
	public PopupWindow mPreviewPopup;
	public TextView mPreviewTextView;

	public EnglishKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		initialize(context);
	}

	public EnglishKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initialize(context);
	}

	private void initialize(Context context) {
		this.setPreviewEnabled(false);
		mPreviewPopup = new PopupWindow();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.preview_layout, null);
		mPreviewTextView = (TextView) v.findViewById(R.id.preview_text_view);
		mPreviewTextView.setVisibility(View.GONE);
		
		mPreviewPopup.setContentView(v);
		mPreviewPopup.setTouchable(false);
		mPreviewPopup.setBackgroundDrawable(null);
		mPreviewPopup.setClippingEnabled(false);
	}

	@Override
	public void init(SoftKeyboard sk, Language lang, HashMap<Integer, KeyAttr> keys){
		mKeys = keys;
		
		mActionListener = new EnglishKeyboardActionListener();
		this.setOnKeyboardActionListener(mActionListener);
		this.setOnTouchListener(mActionListener);
		mActionListener.initialize(this);
		mActionListener.setSoftKeyboard(sk);
		mActionListener.setKeysMap(mKeys);
		InputConnection mInputConnection = sk.getCurrentInputConnection();
		mActionListener.setInputConnection(mInputConnection);
		mActionListener.changeLayout();
	}
	
	@Override
	protected boolean onLongPress(Key key) {
	    mActionListener.onLongPress(key);
		return super.onLongPress(key);
	}
	
	@Override
	public void resetInputConnection(InputConnection ic){
		mActionListener.setInputConnection(ic);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me){
		return super.onTouchEvent(me);
	}
	
	@Override
	public void configChanged(){
		mPreviewPopup.dismiss();
	}
}
