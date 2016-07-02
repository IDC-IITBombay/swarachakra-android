package iit.android.language;

import iit.android.language.ExceptionHandler;
import iit.android.language.Language;
import iit.android.swarachakra.KeyAttr;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;

public class MainLanguageExceptionHandler implements ExceptionHandler {

	private ArrayList<KeyAttr> keyArray;
	private HashMap<Integer, KeyAttr> mKeys;
	private Language main;

	private static final String RA = "\u0d30";
	private static final String HALANT = "\u0D4D";

	private static final int TRAKARCODE = 53;

	public MainLanguageExceptionHandler(Language lang){
		main = lang;
		initializeKeyArray();
	}

	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, KeyAttr> handleException(int keyCode){
		HashMap<Integer, KeyAttr> sKeys = new HashMap<Integer, KeyAttr>();
		switch(keyCode){
			case TRAKARCODE:
				handleTrakar(sKeys);
				break;
		}
		initializeKeyArray();
		return sKeys;
	}

	private void initializeKeyArray(){
		keyArray = new ArrayList<KeyAttr>();
		int halantEnd = main.halantEnd;
		for(int i = 0; i < halantEnd; i++){
			KeyAttr key = new KeyAttr();
			key.code = i+1;
			keyArray.add(key);
			mKeys = main.hashThis();
		}
	}


	private void handleTrakar(HashMap<Integer, KeyAttr> sKeys) {
		for(KeyAttr key : keyArray){
			String newLabel = mKeys.get(key.code).label + HALANT + RA;
			key.label = newLabel;
			key.showChakra = true;
			sKeys.put(key.code, key);
		}
	}

}
