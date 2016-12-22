package iit.android.swarachakra;

import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.PopupWindow;

import java.util.HashMap;
import java.util.List;

import iit.android.language.ExceptionHandler;
import iit.android.language.Language;
import iit.android.language.MainLanguageExceptionHandler;

public class MainKeyboardView extends CustomKeyboardView {
	public PopupWindow mChakraPopup;
	public View mPopupParent;
	public SwaraChakra mSwaraChakra;
	//private KeyLogger mKeyLogger;
	private MainKeyboardActionListener mActionListener;
	private boolean isPassword;
    private Context mContext;


	public MainKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
		// TODO Auto-generated constructor stub
	}

	public MainKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
		// TODO Auto-generated constructor stub
	}

	private void initialize(Context context) {
		super.setPreviewEnabled(false);
        mContext = context;

		setLayerType(View.LAYER_TYPE_HARDWARE, null);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.chakra_layout, null);
		mSwaraChakra = (SwaraChakra) v.findViewById(R.id.swarachakra);
		// TO BE DONE
		int mode = 0;
		if(isTablet(context)){mode = 1;}
		mSwaraChakra.setMetrics(mode);
		mSwaraChakra.setVisibility(View.GONE);

		mChakraPopup = new PopupWindow(context);
		mChakraPopup.setContentView(v);
		mChakraPopup.setTouchable(false);
		mChakraPopup.setClippingEnabled(false);
		mChakraPopup.setBackgroundDrawable(null);
		mPopupParent = this;

		//Moved from MainKeyboardActionListener
		mSwaraChakra.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

		width = mSwaraChakra.getMeasuredWidth();
		height = mSwaraChakra.getMeasuredHeight();


		//keys = this.getKeyboard().getKeys();
	}
	
	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	@Override
	public void init(SoftKeyboard sk, Language lang,
			HashMap<Integer, KeyAttr> keys) {
		mActionListener = new MainKeyboardActionListener();
		this.setOnKeyboardActionListener(mActionListener);
		mActionListener.initialize(this);
		this.setOnTouchListener(mActionListener);
		mActionListener.setKeysMap(keys);
		mActionListener.setHalantEnd(lang.halantEnd);
		mActionListener.setSoftKeyboard(sk);
		InputConnection mInputConnection = sk.getCurrentInputConnection();
		mActionListener.setInputConnection(mInputConnection);
		//mActionListener.setKeyLogger(sk.getKeyLogger());

		//mKeyLogger = sk.getKeyLogger();
		isPassword = sk.isPassword();

		String[] swaras = lang.defaultChakra;
		boolean halantExists = lang.halantExists;
		SwaraChakra.setHalantExists(halantExists);
		SwaraChakra.setDefaultChakra(swaras);

		ExceptionHandler exceptionHandler = new MainLanguageExceptionHandler(lang,mContext,mInputConnection);
		mActionListener.setExceptionHandler(exceptionHandler);

		MainLanguageExceptionHandler exceptionLangHandler = new MainLanguageExceptionHandler(lang,mContext,mInputConnection);
		mActionListener.setExceptionLangHandler(exceptionLangHandler);

	}

	@Override
	public void resetInputConnection(InputConnection ic) {
		mActionListener.setInputConnection(ic);
		/*if (!isPassword) {
			mKeyLogger.writeToLocalStorage();
		}
		mKeyLogger.extractedText="";*/
	}

	@Override
	protected boolean onLongPress(Key key) {
		mActionListener.onLongPress(key);
		return super.onLongPress(key);
	}

	@Override
	public void configChanged() {
		// TODO Auto-generated method stub
		mChakraPopup.dismiss();
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return super.onTouchEvent(me);
	}

}
