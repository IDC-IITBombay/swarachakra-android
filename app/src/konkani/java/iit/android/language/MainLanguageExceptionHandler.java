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

	private static final String RA = "\u0930";
	private static final String HALANT = "\u094D";
	private static final String EYELASHRA = "\u0930";
	private static final String ZWJ = "\u200D";

	private static final int RAFARCODE = 53;
	private static final int TRAKARCODE = 52;
	private static final int EYELASHRACODE = 51;

	public MainLanguageExceptionHandler(Language lang){
		main = lang;
		initializeKeyArray();
	}

	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, KeyAttr> handleException(int keyCode){
		HashMap<Integer, KeyAttr> sKeys = new HashMap<Integer, KeyAttr>();
		switch(keyCode){
			case EYELASHRACODE:
				handleEyelashRa(sKeys);
				break;
			case TRAKARCODE:
				handleTrakar(sKeys);
				break;
			case RAFARCODE:
				handleRafar(sKeys);
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

	private void handleRafar(HashMap<Integer, KeyAttr> sKeys) {
		for(KeyAttr key : keyArray){
			String newLabel = RA + HALANT + mKeys.get(key.code).label;
			key.label = newLabel;
			key.showChakra = true;
			sKeys.put(key.code, key);
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

	private void handleEyelashRa(HashMap<Integer, KeyAttr> sKeys) {
		for(KeyAttr key : keyArray){
			String newLabel = EYELASHRA + HALANT + ZWJ + mKeys.get(key.code).label;
			key.label = newLabel;
			key.showChakra = true;
			sKeys.put(key.code, key);
		}
	}

}
