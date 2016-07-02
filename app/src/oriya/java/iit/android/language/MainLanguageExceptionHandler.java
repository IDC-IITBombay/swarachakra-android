package iit.android.language;

import iit.android.language.ExceptionHandler;
import iit.android.language.Language;
import iit.android.swarachakra.KeyAttr;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.support.v4.util.SimpleArrayMap;

public class MainLanguageExceptionHandler implements ExceptionHandler {

	private ArrayList<KeyAttr> keyArray;
	private HashMap<Integer, KeyAttr> mKeys;
	private Language main;

	private static final String RA = "\u0B30";
	private static final String HALANT = "\u0B4D";
	//private static final String NUKTA = "\u093C";
	private static final String [] nuktaCharacters = {"\u0B5c","\u0B5d"};

	private static final int RAFARCODE = 53;
	private static final int TRAKARCODE = 52;
	private static final int NUKTACODE = 51;

	public MainLanguageExceptionHandler(Language lang)
	{
		main = lang;
		initializeKeyArray();
	}

	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, KeyAttr> handleException(int keyCode)
	{
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

	private void initializeKeyArray()
	{
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

	private void handleNukta(HashMap<Integer, KeyAttr> sKeys) {
		int[] temp = {13,14};
		//ArrayList<Integer> nuktaKeys = new ArrayList<Integer>();
		SimpleArrayMap<Integer, String> nuktaKeyValues= new SimpleArrayMap<Integer, String>();

		for(int i = 0; i < temp.length; i++){
			//nuktaKeys.add(temp[i]);
			nuktaKeyValues.put(temp[i], nuktaCharacters[i]);

		}

		for(KeyAttr key : keyArray){
			if(nuktaKeyValues.containsKey(key.code)){
				//String newLabel = mKeys.get(key.code).label + NUKTA;
				String newLabel = nuktaKeyValues.get(key.code);
				key.label = newLabel;
				key.showChakra = true;
			}
			sKeys.put(key.code, key);
		}
	}

}
