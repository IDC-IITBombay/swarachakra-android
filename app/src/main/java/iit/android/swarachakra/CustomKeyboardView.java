package iit.android.swarachakra;

import iit.android.language.Language;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputConnection;

public class CustomKeyboardView extends KeyboardView {
	
	public final int BACKSPACE = Integer.parseInt(getResources().getString(R.string.backspace));
	public final int SYMBOLS = Integer.parseInt(getResources().getString(R.string.symbols));
	public final int ENGLISH = Integer.parseInt(getResources().getString(R.string.english));
	public final int SPACE = Integer.parseInt(getResources().getString(R.string.space));
	public final int ENTER = Integer.parseInt(getResources().getString(R.string.enter));
	public final int SHIFT = Integer.parseInt(getResources().getString(R.string.shift));
	public int width;
	public int height;
	public List<Keyboard.Key> keys;

	public CustomKeyboardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomKeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void init(SoftKeyboard mSoftKeyboard, Language lang, HashMap<Integer, KeyAttr> keys){
		
	}
	
	public void configChanged(){
		
	}
	
	public int getEnterKeyCode(){
		return ENTER;
	}
	
	public void resetInputConnection(InputConnection ic){
		
	}

}
