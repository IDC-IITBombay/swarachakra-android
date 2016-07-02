package iit.android.language;

import iit.android.language.ExceptionHandler;
import iit.android.language.Language;
import iit.android.swarachakra.KeyAttr;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.util.Log;

public class MainLanguageExceptionHandler implements ExceptionHandler {

	private ArrayList<KeyAttr> keyArray;
	private HashMap<Integer, KeyAttr> mKeys;
	private Language main;

	private static final String RA = "\u09B0";
	private static final String HALANT = "\u09CD";

	private static final int RAFARCODE = 52;
	private static final int TRAKARCODE = 53;
	private static final int NUKTACODE = 51;

	public MainLanguageExceptionHandler(Language lang){
		main = lang;
		initializeKeyArray();
	}

	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, KeyAttr> handleException(int keyCode){
		HashMap<Integer, KeyAttr> sKeys = new HashMap<Integer, KeyAttr>();
		switch(keyCode){
			case NUKTACODE:
				handleNukta(sKeys);
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
		Log.d("debug","Handle rafar");
		for(KeyAttr key : keyArray){
			String newLabel = RA + HALANT + mKeys.get(key.code).label;
			key.label = newLabel;
			key.showChakra = true;
			sKeys.put(key.code, key);
		}
	}

	private void handleTrakar(HashMap<Integer, KeyAttr> sKeys) {
		Log.d("debug","Handle trakar");
		for(KeyAttr key : keyArray){
			String newLabel = mKeys.get(key.code).label + HALANT + RA;
			key.label = newLabel;
			key.showChakra = true;
			sKeys.put(key.code, key);
		}
	}

	private void handleNukta(HashMap<Integer, KeyAttr> sKeys) {
		Log.d("debug","Handle nukta");
		for(KeyAttr key : keyArray){
			if(key.code == 13){
				String newLabel = "\u09DC";
				key.label = newLabel;
				key.showChakra = true;
			}
			else if(key.code == 14){
				String newLabel = "\u09DD";
				key.label = newLabel;
				key.showChakra = true;
			}
			else if(key.code == 26){
				String newLabel = "\u09DF";
				key.label = newLabel;
				key.showChakra = true;
			}
			sKeys.put(key.code, key);
		}
	}

}
