package iit.android.language;

import iit.android.language.ExceptionHandler;
import iit.android.language.Language;
import iit.android.swarachakra.KeyAttr;
import iit.android.swarachakra.MainKeyboardActionListener;
import iit.android.swarachakra.R;
import iit.android.swarachakra.SoftKeyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

public class MainLanguageExceptionHandler implements ExceptionHandler {

    private ArrayList<KeyAttr> keyArray;
    private HashMap<Integer, KeyAttr> mKeys;
    public ArrayList<Character> languageConsonants;
    private ArrayList<Character> languageCharacterSet;
    public ArrayList<Character> vowelModifiers;
    public ArrayList<Character> chakraVowelModifiers;
    public ArrayList<Character> chakraWholeVowels;
    private static char CHARACTER_SET_START;
    private static char CHARACTER_SET_END;

    private static char CONSONANT_START;
    private static char CONSONANT_END;

    private static HashMap<Character, Character> omitConsonantInRange;
    private Map<Character, List<String>> conjuncts;
    public ArrayList<Character> specialCases;

    private Language main;
    private InputConnection mInputConnection;
    private MainKeyboardActionListener mKeyAction;
    private SoftKeyboard sk;
    private static final String RA = "\u0930";
    private static final String HALANT = "\u094D";
    private static final String EYELASHRA = "\u0930\u094d\u200d";
    private static final String NUKTA = "\u093C";


	private static final int RAFARCODE = 53;
	private static final int TRAKARCODE = 52;
	private static final int EYELASHRACODE = 51;
	private static final int NUKTACODE = 121;

    public static String PACKAGE_NAME;
    Context mContext;

    public MainLanguageExceptionHandler(Language lang, Context context, InputConnection ic) {

        mContext = context;
        main = lang;
        mInputConnection = ic;
        this.setInputConnection(ic);
        PACKAGE_NAME = mContext.getPackageName();

        initializeKeyArray();
        initializeLanguageCharacterSet();
        //initialize all the consonants in the language.
        initializeLanguageConsonants();
        //initialize special cases where deletin to take place unicde by unicode
        initializeLanguageSpecialCases();
        //initialize conjuncts in the language
        initializeConjuncts();
        //initialize Vowel modifiers
        initializeVowelModifiers();
        //initialize whole Vowel
        initializeWholeVowels();
    }

    public void setInputConnection(InputConnection ic) {
        mInputConnection = ic;
    }

    @SuppressLint("UseSparseArrays")
    public HashMap<Integer, KeyAttr> handleException(int keyCode) {
        HashMap<Integer, KeyAttr> sKeys = new HashMap<Integer, KeyAttr>();
        switch (keyCode) {
            case EYELASHRACODE:
                handleEyelashRa(sKeys);
                break;
            case TRAKARCODE:
                handleTrakar(sKeys);
                break;
            case NUKTACODE:
                handleNukta(sKeys);
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

    //initialize Vowel Modifiers
    private void initializeVowelModifiers() {
//        char vowelStart = mContext.getText(R.string.vowel_modifier_start).charAt(0);
//        char vowelEnd = mContext.getText(R.string.vowel_modifier_end).charAt(0);
//
        vowelModifiers = new ArrayList<Character>();
        chakraVowelModifiers = new ArrayList<Character>();
//        for (int i = vowelStart; i != vowelEnd; i++) {
//            vowelModifiers.add((char) i);
//        }
//        specialCases = new ArrayList<Character>();
        for (int i = 1; ; i++) {
            String vowel_start = "vowel_modifier_" + i + "_start";
            String vowel_end = "vowel_modifier_" + i + "_end";
            if (mContext.getResources().getIdentifier(vowel_start, "string", PACKAGE_NAME) != 0) {
                int vowel_id_start = mContext.getResources().getIdentifier(vowel_start, "string", PACKAGE_NAME);
                char vowel_value_start = mContext.getText(vowel_id_start).charAt(0);
                int vowel_id_end = mContext.getResources().getIdentifier(vowel_end, "string", PACKAGE_NAME);
                char vowel_value_end = mContext.getText(vowel_id_end).charAt(0);
                int id_chakra_array = mContext.getResources().getIdentifier("in_chakra_vowel_modifiers", "array", PACKAGE_NAME);
                List<String> chakraValues = Arrays.asList(mContext.getResources().getStringArray(id_chakra_array));

                for (int j = vowel_value_start; j <= vowel_value_end; j++) {

                    if(chakraValues.contains(String.valueOf((char)j))) {

                        chakraVowelModifiers.add((char) j);
                    }

                    vowelModifiers.add((char) j);
                }

                //System.out.println("language modifiers list cvm"+chakraVowelModifiers.toString());
                //System.out.println("language modifiers list vm"+vowelModifiers.toString());
            } else {
                return;
            }
        }


    }

    //initialize conjuncts
    private void initializeConjuncts() {
        conjuncts = new HashMap<Character, List<String>>();

        for (int i = 1; ; i++) {
            String suffix_id = "conjunct_" + i;
            String conjunct_id = "conjunct_" + i + "_array";
            if (mContext.getResources().getIdentifier(suffix_id, "string", PACKAGE_NAME) != 0) {
                int id = mContext.getResources().getIdentifier(suffix_id, "string", PACKAGE_NAME);
                char suffixValue = mContext.getText(id).charAt(0);
                int id_array = mContext.getResources().getIdentifier(conjunct_id, "array", PACKAGE_NAME);
                List<String> conjunctValues = Arrays.asList(mContext.getResources().getStringArray(id_array));
//                List<String> conjunctValues = Arrays.asList(mContext.getResources().getStringArray(R.array.conjunct_1_array));
                conjuncts.put(suffixValue, conjunctValues);
            } else {
                Log.d("System", "in else in else" + i);
                return;
            }
        }
    }

    //initialize whole vowel chakra
    private void initializeWholeVowels() {
        chakraWholeVowels = new ArrayList<Character>();
        int id_array = mContext.getResources().getIdentifier("in_chakra_whole_vowel", "array", PACKAGE_NAME);
        List<CharSequence> conjunctValues = Arrays.asList(mContext.getResources().getTextArray(id_array));

        //System.out.println("language modifiers list vm"+conjunctValues.toString()+":"+conjunctValues.get(0).charAt(0));
        //chakraWholeVowels.add(conjunctValues.get(0).charAt(0));
        for(int i=0;i<conjunctValues.size();i++){
            System.out.println("Print:::"+i);
            chakraWholeVowels.add(conjunctValues.get(i).charAt(0));
        }

        // System.out.println("language modifiers list vm"+chakraWholeVowels.toString());
    }

    //initialize language special cases

    private void initializeLanguageSpecialCases() {
        specialCases = new ArrayList<Character>();
        for (int i = 1; ; i++) {
            String special_identifier_start = "special_" + i + "_start";
            String special_identifier_end = "special_" + i + "_end";
            if (mContext.getResources().getIdentifier(special_identifier_start, "string", PACKAGE_NAME) != 0) {
                int special_id_start = mContext.getResources().getIdentifier(special_identifier_start, "string", PACKAGE_NAME);
                char special_value_start = mContext.getText(special_id_start).charAt(0);
                int special_id_end = mContext.getResources().getIdentifier(special_identifier_end, "string", PACKAGE_NAME);
                char special_value_end = mContext.getText(special_id_end).charAt(0);
                for (int j = special_value_start; j <= special_value_end; j++) {
                    specialCases.add((char) j);
                }
            } else {
                return;
            }
        }

    }


    //initialize array with all characters of the script
    private void initializeLanguageCharacterSet() {
        CHARACTER_SET_START = mContext.getText(R.string.character_set_start).charAt(0);
        CHARACTER_SET_END = mContext.getText(R.string.character_set_end).charAt(0);
        languageCharacterSet = new ArrayList<Character>();
        for (int i = CHARACTER_SET_START; i != CHARACTER_SET_END; i++) {
            languageCharacterSet.add((char) i);
        }
    }

    //initialize all the consonants in the language.
    private void initializeLanguageConsonants() {
        //initialize omit range from resources
        languageConsonants = new ArrayList<Character>();
        CONSONANT_START = mContext.getText(R.string.consonant_set_start).charAt(0);
        CONSONANT_END = mContext.getText(R.string.consonant_set_end).charAt(0);

        omitConsonantInRange = new HashMap<>();

        for (int i = 1; ; i++) {
            String omit_start = "omit_" + i + "_start";
            String omit_end = "omit_" + i + "_end";
            if (mContext.getResources().getIdentifier(omit_start, "string", PACKAGE_NAME) != 0) {
                int start_id = mContext.getResources().getIdentifier(omit_start, "string", PACKAGE_NAME);
                char omit_start_value = mContext.getText(start_id).charAt(0);
                int end_id = mContext.getResources().getIdentifier(omit_end, "string", PACKAGE_NAME);
                char omit_end_value = mContext.getText(end_id).charAt(0);
                omitConsonantInRange.put(omit_start_value, omit_end_value);
            } else {
                break;
            }
        }


        // Add all letters between start and end into the language consonant array - 'languageConsonants'.
        for (int i = CONSONANT_START; i <= CONSONANT_END; i++) {
            //omit values in between the range.
            if (omitConsonantInRange.containsKey((char) i)) {
                i = omitConsonantInRange.get((char) i);
                continue;
            }
            languageConsonants.add((char) i);
        }

        //System.out.println("lang consonats"+languageConsonants.toString());
    }

    private void handleRafar(HashMap<Integer, KeyAttr> sKeys) {
        //Log.d("debug","rafar");
        for (KeyAttr key : keyArray) {
            String newLabel = RA + HALANT + mKeys.get(key.code).label;
            key.label = newLabel;
            key.showChakra = true;
            sKeys.put(key.code, key);
        }
    }

	private void handleTrakar(HashMap<Integer, KeyAttr> sKeys) {
		//Log.d("debug","trakar");
		for(KeyAttr key : keyArray){
			String newLabel = mKeys.get(key.code).label + HALANT + RA;
			key.label = newLabel;
			key.showChakra = true;
			sKeys.put(key.code, key);
		}
	}

	private void handleEyelashRa(HashMap<Integer, KeyAttr> sKeys) {
		//Log.d("debug","eyelash");

        int[] temp = {26,33};

        String[] eyelashVal = {"\u0930\u094d\u200d\u092F","\u0930\u094d\u200d\u0939"};

        //ArrayList<Integer> nuktaKeys = new ArrayList<Integer>();
        SimpleArrayMap<Integer, String> eyelashraKeyValues = new SimpleArrayMap<Integer, String>();


        for (int i = 0; i < temp.length; i++) {
            //nuktaKeys.add(temp[i]);
			eyelashraKeyValues.put(temp[i], eyelashVal[i]);

        }

		for(KeyAttr key : keyArray){
            if (eyelashraKeyValues.containsKey(key.code)) {
                String newLabel = eyelashraKeyValues.get(key.code);
                //Log.d("debug","Keep this one button "+key.code);

			key.label = newLabel;
			key.showChakra = true;
            } else {
                //Log.d("debug","Hide this button "+key.code);
                //key.showChakra = false;
                //key.label = "";
                //key.code = 0;
                //key.icon = "";
                //key.showIcon = false;
            }
			sKeys.put(key.code, key);
		}
	}

    private void commitText(String text) {
        mInputConnection.setComposingText(text, 1);
        mInputConnection.finishComposingText();
        updateExtractedText();
    }

    private void updateExtractedText() {
        try {
            ExtractedText edt = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0);

            if (edt != null) {
//                mKeyLogger.extractedText = edt.text.toString();
            } else {
//                Log.d(mKeyLogger.TAG, "handlechar(): About to hide, nothing to save" + edt);
            }
        } catch (Exception ex) {
//            Log.d(mKeyLogger.TAG, "handlechar():ex " + ex.getMessage());
        }
    }

    public void handleBackSpaceDeleteChar(InputConnection ic) {
        mInputConnection = ic;
       // Log.v("System", mContext.getPackageName().toString());
        CharSequence selection = mInputConnection.getSelectedText(0);
        if (selection == null) {
            ExtractedTextRequest etr = new ExtractedTextRequest();
            if(mInputConnection.getExtractedText(etr, 0)!= null) {
            //Finish composing text
            mInputConnection.finishComposingText();
            //Log.v("System", "language Chars" + languageCharacterSet.toString());
            //Log.v("System", "spcial Chars" + specialCases.toString());
            //Log.v("System", "Language consonants Chars" + languageConsonants.toString());
            //if(mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart>0) {

            repositionCursor(mInputConnection.getExtractedText(etr, 0).selectionStart);

            //}
            //edit function t accommodate language specific rules. Handles ra-halant, halant-ra combinations and places cursor after the CV if in between.
            CharSequence charBefore = mInputConnection.getTextBeforeCursor(1, 0);

            Log.v("System", String.valueOf(charBefore));
            if (charBefore.length() > 0) {
                //Handle space deletion, check if devnagari
                if (languageCharacterSet.contains(charBefore.charAt(0)) && !specialCases.contains(charBefore.charAt(0))) {
                    CharSequence before = mInputConnection.getTextBeforeCursor(15, 0);
                    String test = before.toString();
                    Log.v("System", test + "tesst string");

                    int i = test.length() - 1;
                    for (; i >= 0; i--) {
                        Log.v("System", test.charAt(i) + " test char string");
                        Log.d("track", "i=" + i + ",test=" + test + ", length=" + test.length());
                        if (languageCharacterSet.contains(test.charAt(i)) && !specialCases.contains(test.charAt(i))) {
                            Log.v("System", test.charAt(i) + " language characterset");
                            if (languageConsonants.contains(test.charAt(i))) {
                                Log.v("System", test.charAt(i) + " language consonant");
                                //handle conjuncts if present in the language set
                                // 4 CASES IN DEVANAGARI
                                //check if cnsonant in conjunct list ArrayList<Char,String> ie: <'sha' ,"ka+halant+sha">
                                // logic is to get the previous 3 characters and strip of zwj and nzwj.
                                // then get value and check if it is equal. if yes INCLUDE the previous two chars to delete.
                                // then compare the prev two + current to find if
                                if (conjuncts.containsKey(test.charAt(i))) {
                                    Log.v("System", test.charAt(i) + " in conjuncts");
                                    //deleting ksha with rafar code
                                    if(String.valueOf(test.charAt(i)).equals("ष")){
                                        if(test.length()>4 && i>3) {

                                            Log.d("track","i="+i+",test="+test+", length="+test.length());
                                            if (test.substring(i - 4, i).equals("र्क्")) {
                                                if (conjuncts.get(test.charAt(i)).contains(test.substring(i - 4, i))) {
                                                    i -= 4;
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    //deleting gnya with rafar
                                    if(String.valueOf(test.charAt(i)).equals("ञ")){
                                        if(test.length()>4 && i>3) {

                                            //TO DO: crashes here with the error: java.lang.StringIndexOutOfBoundsException: length=5; regionStart=-1; regionLength=4
                                            if (test.substring(i - 4, i).equals("र्ज्")) {
                                                if (conjuncts.get(test.charAt(i)).contains(test.substring(i - 4, i))) {
                                                    i -= 4;
                                                }
                                            break;
                                        }

                                        }
                                    }
                                    //deleting shra with rafar
                                    if(String.valueOf(test.charAt(i)).equals("र")){
                                        if(test.length()>4 && i>3) {

                                            if (test.substring(i - 4, i).equals("र्श्")) {
                                                if (conjuncts.get(test.charAt(i)).contains(test.substring(i - 4, i))) {
                                                    i -= 4;
                                                }
                                                break;
                                            }

                                        }
                                    }
                                    //deleting thra with rafar
                                    if(String.valueOf(test.charAt(i)).equals("र")){
                                        if(test.length()>4 && i>3) {

                                            if (test.substring(i - 4, i).equals("र्त्")) {
                                                if (conjuncts.get(test.charAt(i)).contains(test.substring(i - 4, i))) {
                                                    i -= 4;
                                                }
                                                break;
                                            }

                                        }
                                    }
                                    if(test.length()>3 && i>2) {

                                        if (test.substring(i - 3, i).equals(EYELASHRA)) {
                                            if (conjuncts.get(test.charAt(i)).contains(test.substring(i - 3, i))) {
                                                i -= 3;
                                            }
                                            break;
                                        }

                                    }
                                    if (test.length() > 2 && i > 1) {
                                            if (conjuncts.get(test.charAt(i)).contains(test.substring(i - 2, i))) {
                                                i -= 2;
                                            }
                                    }
                                }
                                break;
                            }else{
                                Log.d("System","Ignore");
                                //i--;
                            }
                        } else {
                            Log.v("System", test.charAt(i) + " out");
                            i++;
                            break;
                        }
                    }
                    Log.v("System", test.length() - i + " no. of chars to be deleted");
                    mInputConnection.deleteSurroundingText(test.length() - i, 0);
                } else {
                    mInputConnection.deleteSurroundingText(1, 0);
                }
            } else {
                mInputConnection.commitText("", 1);
            }

        }
        } else {
            //Code to capture glyph before and after the selection cursor positions.
            int selectionStart = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart;
            int selectionEnd = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionEnd;

            int setCursorStart = repositionCursor(selectionStart);
            mInputConnection.setSelection(setCursorStart, selectionEnd);
            selectionStart = setCursorStart;
            String left = mInputConnection.getTextBeforeCursor(10, 0).toString();
            String right = mInputConnection.getTextAfterCursor(10, 0).toString();
            String selected = mInputConnection.getSelectedText(0).toString();
            String edt = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).text.toString();

            //handle selection expansion towards right.
            {
//                if (left.length() > 0) {
//                    if (left.charAt(left.length() - 1) != ' ' || selected.charAt(0) != ' ') { //char just before is space or 1st is space- exit
//                        if (languageCharacterSet.contains(selected.charAt(0))) {
//                            if (vowelModifiers.contains(selected.charAt(0))) {
//                                if (languageConsonants.contains(left.charAt(left.length() - 1))) {
//                                    // remove the consonant also
//                                    int start = edt.lastIndexOf(selected);
//                                    mInputConnection.setSelection(start - 1, start + selected.length());
//                                }
//                            }
//                        }
//                    }
//                }

                if (left.length() > 0) {
                    if (selected.charAt(0) != ' ' || left.charAt(left.length() - 1) != ' ') { //char just before is space or 1st is space- exit
                        if (languageCharacterSet.contains(left.charAt(left.length() - 1))) {
                            if (languageConsonants.contains(left.charAt(left.length() - 1))) {
                                if (chakraVowelModifiers.contains(selected.charAt(0))) {
                                    // remove the consonant also
                                    int start = edt.lastIndexOf(selected);
                                    mInputConnection.setSelection(start + 1, start + selected.length());
                                }
                            }
                        }
                    }
                }

                selected = mInputConnection.getSelectedText(0).toString();


                int setCursorEnd = repositionCursor(selectionEnd);
                mInputConnection.setSelection(selectionStart, setCursorEnd);
                right = mInputConnection.getTextAfterCursor(10, 0).toString();
                if (right.length() > 0) {
                    if (right.charAt(0) != ' ' || selected.charAt(selected.length() - 1) != ' ') { //char just before is space or 1st is space- exit
                        if (languageCharacterSet.contains(right.charAt(0))) {
                            if (languageConsonants.contains(selected.charAt(selected.length() - 1))) {
                                if (chakraVowelModifiers.contains(right.charAt(0))) {
                                    // remove the consonant also
                                    int start = edt.lastIndexOf(selected);
                                    mInputConnection.setSelection(start, start + selected.length() + 1);
                                }
                            }
                        }
                    }
                }
            }
            commitText("");

            String beforeCursor = mInputConnection.getTextBeforeCursor(1, 0).toString();
            String afterCursor = mInputConnection.getTextAfterCursor(1, 0).toString();
            if (beforeCursor.equals(" ") && afterCursor.equals(" ")) {
                mInputConnection.deleteSurroundingText(0, 1);
                commitText("");
            }

        }
    }

    private void handleNukta(HashMap<Integer, KeyAttr> sKeys) {
        int[] temp = {1, 2, 3, 8, 13, 14, 20, 22, 26, 27, 28};
        String[] nuktaVal = {"\u0958", "\u0959", "\u095a", "\u095b", "\u095c", "\u095d", "\u0929", "\u095e", "\u095f", "\u0931", "\u0934"};
        //ArrayList<Integer> nuktaKeys = new ArrayList<Integer>();
        SimpleArrayMap<Integer, String> nuktaKeyValues = new SimpleArrayMap<Integer, String>();
        for (int i = 0; i < temp.length; i++) {
            //nuktaKeys.add(temp[i]);
            nuktaKeyValues.put(temp[i], nuktaVal[i]);

		}

		for (KeyAttr key : keyArray) {
			if (nuktaKeyValues.containsKey(key.code)) {
				String newLabel = nuktaKeyValues.get(key.code);
				//Log.d("debug","Keep this one button "+key.code);


				key.label = newLabel;
				key.showChakra = true;
			} else {
				//Log.d("debug","Hide this button "+key.code);
				//key.showChakra = false;
				//key.label = "";
				//key.code = 0;
				//key.icon = "";
				//key.showIcon = false;

			}
			sKeys.put(key.code, key);
		}

	}


    public int repositionCursor(int selectionEnd) {
        mInputConnection.setSelection(selectionEnd, selectionEnd);
        CharSequence charAfter = mInputConnection.getTextAfterCursor(2, 0);
        Log.v("System", String.valueOf(charAfter) + "repositionCursour");
        if (charAfter.length() == 2) {
            if (charAfter.toString().equals("्र")) {
                int selectionStart = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart;
                mInputConnection.setSelection(selectionStart + 2, selectionStart + 2);
            }
        }
        CharSequence charBefore = mInputConnection.getTextBeforeCursor(2, 0);
        Log.v("System", String.valueOf(charBefore) + "repositionCursour Char Before");
        if (charBefore.length() == 2) {
            if (charBefore.toString().equals("र्")) {
                int selectionStart = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart;
                mInputConnection.setSelection(selectionStart + 1, selectionStart + 1);
            }
        }
        if (charBefore.length() > 0 && charAfter.length() > 0) {
            if (Character.toString(charBefore.charAt(charBefore.length() - 1)).equals("र") && Character.toString(charAfter.charAt(0)).equals("्")) {
                int selectionStart = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart;
                selectionStart++;
                if (charAfter.length() > 1) {
                    if (languageConsonants.contains(charAfter.charAt(1))) {
                        selectionStart++;
                    }
                }
                mInputConnection.setSelection(selectionStart, selectionStart);
            }
        }

		int selectionStart = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart;

		charAfter = mInputConnection.getTextAfterCursor(1, 0);
		for (int i = charAfter.length() - 1; i >= 0; i--) {
			if (chakraVowelModifiers.contains(charAfter.charAt(i))) {
				selectionStart++;
			} else if (specialCases.contains(charAfter.charAt(i))) {
				selectionStart++;
			}
		}
		mInputConnection.setSelection(selectionStart, selectionStart);
		return mInputConnection.getExtractedText(new ExtractedTextRequest(), 0).selectionStart;

    }
}
