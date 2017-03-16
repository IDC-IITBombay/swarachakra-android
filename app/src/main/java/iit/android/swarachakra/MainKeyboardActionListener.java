package iit.android.swarachakra;

import iit.android.event.BroadcastMessage;
import iit.android.event.MessageReceivedEvent;
import iit.android.hotspot.BaseHotspotActivity;
import iit.android.hotspot.HotspotManagerActivity;
import iit.android.hotspot.HotspotManagerService;
import iit.android.hotspot.HotspotManagerServiceCommunicator;
import iit.android.language.ExceptionHandler;
import iit.android.language.MainLanguageExceptionHandler;
import iit.android.mode.TextActivity;
import iit.android.settings.SettingsActivity;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainKeyboardActionListener extends AppCompatActivity implements OnKeyboardActionListener,
        OnTouchListener, HotspotManagerServiceCommunicator.HotspotServiceCallback {
    private static final int MSG_SHOW_CHAKRA = 1;
    private static final int MSG_REMOVE_CHAKRA = 2;
    private static final int DELAY_BEFORE_SHOW = 70;
    private static MainKeyboardView mKeyboardView;
    public static PopupWindow mChakraPopup;
    private static SwaraChakra mSwaraChakra;
    private ExceptionHandler mExceptionHandler;
    //BT
    public static MainLanguageExceptionHandler mExceptionLangHandler;
    private static View mPopupParent;
    private static boolean isChakraVisible;
    private static int chakraX;
    private static int chakraY;

    //BT
    public static BroadcastMessage message;
    public static BroadcastMessage message1;
    private static HotspotManagerServiceCommunicator mServiceCommunicator;
    private EditText mKeyboardText;
    private TextView mFooter;
    private Button disconnect;
    private Context context;
    private int halantEnd;
    public static int halantendremote;
    public static String b;
    public static boolean ishalant = false;
    //static boolean messagereceived=false;
    public static boolean active = false;

    //BT
    @Override
    public void onBroadcastMessageReceived(BroadcastMessage message) {
    }
    @Override
    public void onServiceMessageReceived(int type, String data) {
        switch (type) {
            case HotspotManagerService.EVENT_LOCAL_CLIENT_ERROR:
                finish(); // our connection to the server failed - need to reconnect from manager activity
                break;
        }
    }

    //BT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialiseViewAndToolbar(R.layout.mode_keyboard, false);

        setContentView(R.layout.mode_keyboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        context = getBaseContext();
        mServiceCommunicator = new HotspotManagerServiceCommunicator(MainKeyboardActionListener.this);
        System.out.println("mservice " + mServiceCommunicator);
        mServiceCommunicator.bindService(MainKeyboardActionListener.this);
        //mServiceCommunicator.bindService(MainKeyboardActionListener.this);
        //System.out.println("service create?"+mServiceCommunicator.mIsBound);
        mKeyboardText = (EditText) findViewById(R.id.keyboard_text);
        System.out.println("contexxtt1" + context);
        mFooter = (TextView) findViewById(R.id.footer);
        disconnect=(Button) findViewById(R.id.buttondisconnect);
        mFooter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // show the keyboard whenever the text view is touched
                // TODO: can this be improved?
                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager
                        .HIDE_IMPLICIT_ONLY);
                //mKeyboardText.clearFocus();
                mKeyboardText.requestFocus();
                return true; // so that events don't pass through to the EditText
            }
        });
        disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.better_together_framework);
                builder.setMessage(R.string.confirm_disconnect);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        committextremote("",103);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
                builder.show();*/

                committextremote("",103);
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();

        active = true;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this); // register for EventBus events
        }

    }//--

    //TODO: this is no longer static for BT, does this affect the keyboard?
    private class myHandler extends Handler {
        //Using a weak reference means you won't prevent garbage collection
        private final WeakReference<MainKeyboardActionListener> weakRef;

        public myHandler(MainKeyboardActionListener myClassInstance) {
            weakRef = new WeakReference<MainKeyboardActionListener>(myClassInstance);
        }

        @Override
        public void handleMessage(Message msg) {
            MainKeyboardActionListener mkal = weakRef.get();
            if (mkal == null)
                return;

            //mLog.d("jank","handleMessage()");
            switch (msg.what) {
                case MSG_SHOW_CHAKRA:

                    if (mkal.mChakraPopup.isShowing()) {

                        mkal.mChakraPopup.update(mkal.chakraX, mkal.chakraY, mkal.mKeyboardView.width, mkal.mKeyboardView.height);
                        //mLog.d("track", "update chakra loc");

                    } else {

                        mChakraPopup.setWidth(mKeyboardView.width);
                        mChakraPopup.setHeight(mKeyboardView.height);
                        mChakraPopup.showAtLocation(mPopupParent, Gravity.NO_GRAVITY, chakraX, chakraY);
                        Log.d("track", "show chakra at loc:" + mkal.chakraX + "," + mkal.chakraY);

                    }
                    mkal.mSwaraChakra.setVisibility(View.VISIBLE);
                    //mLog.d("track", "Set visible()---");
                    //mKeyboardView.setAlpha(0.50f);
                    break;
                case MSG_REMOVE_CHAKRA:
                    //mLog.d("track", "Hide chakra()--");
                    mkal.removeChakra();

                    break;
            }
        }
    }

    ;

    private SoftKeyboard mSoftKeyboard;
    //private KeyLogger mKeyLogger;
    //private ExceptionHandler mExceptionHandler;
    private int touchDownX;
    private int touchDownY;
    //BT type changed to static
    public static boolean inHalantMode;
    private boolean isShifted;
    private boolean inSymbolMode;

    private boolean isPersistent;
    //BT added
    public static boolean inSymbolMode1;
    //BT type changed to static and scope to public
    public static boolean inExceptionMode;
    private boolean spaceHandled;
    private boolean shiftHandled;
    private int exceptionCode;
    private String preText;
    //BT change scope
    public static HashMap<Integer, KeyAttr> mKeys;
    public static HashMap<Integer, KeyAttr> sKeys;
    private int ENTER;
    public int BACKSPACE;
    private int SPACE;
    private int ENGLISH;
    private int SYMBOLS;
    private int SHIFT;
    //BT added
    private int REMOTEKEYBOARD;
    private int MOVE_THRESHOLD = 0;
    private int DIM_THRESHOLD = 0;
    private InputConnection mInputConnection;
    //private int halantEnd;
    private static Handler mHandler;

    private static void showChakraAt(int posX, int posY, int keyCode) {

        //Log.d("track","in ShowChakra()");
        final PopupWindow chakraPopup = mChakraPopup;

        //Move it to the time KBView changed, is it even needed(?)
        /*mSwaraChakra.measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));*/
        float offset = 2 * mSwaraChakra.getOuterRadius();

        //Move it to the time KBview changed
		/*int w = mSwaraChakra.getMeasuredWidth();
		int h = mSwaraChakra.getMeasuredHeight();*/

        //Move it to the time KBview changed
        //List<Key> keys = mKeyboardView.getKeyboard().getKeys();

        for (Keyboard.Key key : mKeyboardView.keys) {
            if (key.codes[0] == keyCode) {
                posX = key.x + (key.width / 2);
                posY = key.y + (key.height / 2);
                //Log.d("track","x,y"+key.x + "," + key.y);
            }
        }

        int x = (int) (posX - offset);
        int y = (int) (posY - offset);

		/*if (chakraPopup.isShowing()) {

			//chakraPopup.update(x, y, mKeyboardView.width, mKeyboardView.height);
            Log.d("track", "update chakra loc");

		}else{

			*//*chakraPopup.setWidth(mKeyboardView.width);
			chakraPopup.setHeight(mKeyboardView.height);
			chakraPopup.showAtLocation(mPopupParent, Gravity.NO_GRAVITY, x, y);*//*
            Log.d("track", "show chakra at loc:"+x+","+y);

		}*/
        chakraX = x;
        chakraY = y;
        //mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SHOW_CHAKRA),DELAY_BEFORE_SHOW);

        //Maybe use sendMessage instead, the current funct may cause some issues.
        //mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_SHOW_CHAKRA));
        mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_SHOW_CHAKRA));
        isChakraVisible = true;
    }

    private static void removeChakra() {
        mHandler.removeMessages(MSG_SHOW_CHAKRA);
        mSwaraChakra.desetArc();
        mSwaraChakra.setVisibility(View.GONE);
        isChakraVisible = false;
        //mKeyboardView.setAlpha(1);
    }

    public void initialize(MainKeyboardView mKeyboardView) {
        MainKeyboardActionListener.mKeyboardView = mKeyboardView;

        mChakraPopup = mKeyboardView.mChakraPopup;
        mSwaraChakra = mKeyboardView.mSwaraChakra;
        mPopupParent = mKeyboardView.mPopupParent;


        BACKSPACE = mKeyboardView.BACKSPACE;
        ENTER = mKeyboardView.ENTER;
        SPACE = mKeyboardView.SPACE;
        ENGLISH = mKeyboardView.ENGLISH;
        SYMBOLS = mKeyboardView.SYMBOLS;
        SHIFT = mKeyboardView.SHIFT;
        //BT
        REMOTEKEYBOARD = mKeyboardView.REMOTEKEYBOARD;

        initVariables();
        MOVE_THRESHOLD = (int) mSwaraChakra.getInnerRadius();
        DIM_THRESHOLD = (int) (mSwaraChakra.getOuterRadius() * 0.6);
        mHandler = new myHandler(this);
    }

    private void initVariables() {
        isChakraVisible = false;
        inHalantMode = false;
        inExceptionMode = false;
        exceptionCode = 0;
        preText = "";
    }

    public void setInputConnection(InputConnection ic) {
        mInputConnection = ic;
        initVariables();
        changeLayout("default");
    }

    public void setExceptionHandler(ExceptionHandler eh) {
        mExceptionHandler = eh;
    }

    public void setExceptionLangHandler(MainLanguageExceptionHandler mleh) {
        mExceptionLangHandler = mleh;
    }

    public void setKeysMap(HashMap<Integer, KeyAttr> mKeys) {
        this.mKeys = mKeys;
    }

    public void setHalantEnd(int h) {
        halantEnd = h;
        //BT
        halantendremote = halantEnd;
    }

    public void setTouchDownPoint(int x, int y) {
        touchDownX = x;
        touchDownY = y;
    }

    public void setSoftKeyboard(SoftKeyboard sk) {
        this.mSoftKeyboard = sk;
    }

    public void onLongPress(Key key) {
        if (key.codes[0] == SHIFT) {
            if (isShifted && isPersistent) {
                changeLayout("default");
                isPersistent = false;
                isShifted = false;
            } else {
                changeLayout("shift");
                isPersistent = true;
                isShifted = true;
            }
            shiftHandled = true;

        } else if (key.codes[0] == SPACE) {
            InputMethodManager imm = (InputMethodManager) mSoftKeyboard
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showInputMethodPicker();
            spaceHandled = true;
        }
    }

    @Override
    public void onKey(int arg0, int[] arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPress(int keyCode) {
        spaceHandled = false;
        shiftHandled = false;
        KeyAttr key = null;
        boolean showChakra = false;
        if (inExceptionMode && sKeys.containsKey(keyCode)) {
            showChakra = sKeys.get(keyCode).showChakra && !(isChakraVisible);
            key = sKeys.get(keyCode);
        } else if (mKeys.containsKey(keyCode)) {
            showChakra = mKeys.get(keyCode).showChakra && !(isChakraVisible);
            key = mKeys.get(keyCode);
        }
        if (showChakra) {
            mSwaraChakra.setCurrentKey(key);
            mSwaraChakra.setKeyLabel(getKeyLabel(keyCode));

            //Could probab be better handled
            if (mKeyboardView.keys == null) {

                mKeyboardView.keys = mKeyboardView.getKeyboard().getKeys();

            }

            showChakraAt(touchDownX, touchDownY, keyCode);
        }
    }

    @Override
    public void onRelease(int keyCode) {

        //Handle shift
        if (mKeys.containsKey(keyCode)) {
            if (isShifted && !(isPersistent)) {
                Log.d("debug", "Change to default layout");
                changeLayout("default");
                isShifted = false;
            }
        }

        //If the keyboard is already in exception mode
        if (inExceptionMode) {
            inExceptionMode = false;
            Log.d("flow", "mouse release n exception mode");
            updateKeyLabels();
            if (mKeys.containsKey(keyCode)) {
                if (mKeys.get(keyCode).isException
                        && (exceptionCode == keyCode)) {
                    exceptionCode = 0;
                    return;
                }
            }
            exceptionCode = 0;
        }

        String text = mSwaraChakra.getText();
        if (isChakraVisible) {

            Log.d("flow", "mouse release n chakra visible");
            System.out.println("in isCharavisi" + mExceptionLangHandler.chakraWholeVowels.toString());
            //String text = mSwaraChakra.getText();

            if (!preText.equals("") && (mExceptionLangHandler.chakraWholeVowels.contains(text.charAt(0))
                    || mExceptionLangHandler.specialCases.contains(text.charAt(0)) || mExceptionLangHandler.chakraVowelModifiers.contains(text.charAt(0)))) {
                System.out.println(text + "in chakra whole vowels");
                text = preText + text;
            }

            if (mSwaraChakra.isHalant()) {
                Log.d("debug", "Its halant");
                setHalant(text);
                if (active == false) {
                    //May be comment this call?
                    handlePreview();
                }
                if (active == true) {
                    committextremote(text, 99);
                }
            } else {
                Log.d("flow", "Not halant, remove halant mode");
                removeHalantMode();
                //BT
                if (active == true) {
                    committextremote(text, 1);
                } else {
                    Log.d("debug", "Commit:" + text + "-");
                    commitText(text);
                }
            }
            removeChakra();
        } else if (mKeys.containsKey(keyCode)) {

            KeyAttr key = mKeys.get(keyCode);
            if (key.isException) {
                Log.d("debug", "handle exception key:" + keyCode);
                handleException(keyCode);
            } else if (key.changeLayout) {
                Log.d("debug", "Change layout");
                changeLayout(key.layout);
            } else {
                if (active == true) {
                    System.out.println("contu onRElease text " + this);

                    //TODO:This one should initiate finish composing and commit
                    //committextremote(key.label, 1); //TODO: generalise all call codes to committextremote
                    message1 = new BroadcastMessage(BroadcastMessage.Type.STR, key.label);
                    int[] extras = new int[]{3};
                    message1.setExtras(extras);
                    //System.out.println("this will go" + message);
                    MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message1);
                    removeHalantMode();
                } else {
                    mInputConnection.finishComposingText();
                    commitText(key.label);
                    Log.d("debug", "Finish composing and commit:" + key.label + "-");
                }
            }
        } else {
            handleSpecialInput(keyCode);
            Log.d("flow", "Handle special input");
            removeHalantMode();
            if (active == true) {
                committextremote("", 99);
            }
        }

        if (active == false) {
            Log.d("flow", "HandlePreview()");
            handlePreview();
        }
		/*if(active==false){
			handlePreview();
		}
		if (active == true) {
			committextremote(text, 99);
		}*/
    }

    //TODO: generalise all the codes passed to the function
    public static void committextremote(String text, int from) {

        if (from == 987) {
            System.out.println("beforcomposing");
            message = new BroadcastMessage(BroadcastMessage.Type.FINISHCOMPOSINGTEXT, text);
            int[] extras = new int[]{from};
            message.setExtras(extras);
            System.out.println("committextremote987: this will go" + message.mMessage);
            MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message);

        } else if (from == 99) {
            message = new BroadcastMessage(BroadcastMessage.Type.HANDLEPREVIEW, text);
            int[] extras = new int[]{from};
            message.setExtras(extras);
            System.out.println("committextremote99: this will go" + message.mMessage);
            MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message);
        } else if(from==103){
            BroadcastMessage message=new BroadcastMessage(BroadcastMessage.Type.BLUETOOTH,text);
            int[] extras=new int[]{from};
            message.setExtras(extras);
            MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message);
        }else {
            message = new BroadcastMessage(BroadcastMessage.Type.KEYBOARD, text);
            int[] extras = new int[]{from};
            message.setExtras(extras);
            System.out.println("committextremote KEYBOARD type: this will go" + message.mMessage);
            MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message);
        }
    }

    private void handleException(int keyCode) {
        inExceptionMode = true;
        exceptionCode = keyCode;
        sKeys = mExceptionHandler.handleException(keyCode);
        Log.d("flow", "handle exception");
        updateKeyLabels();
    }

    private void removeHalantMode() {
        if (inHalantMode) {
            inHalantMode = false;
            preText = "";
            Log.d("flow", "removeHalant  mode");
            updateKeyLabels();
        }
    }

    /**
     * Sets halant mode and changes the labels of the keyboard accrding to the
     * halant text
     *
     * @param text The string to be added to the labels of the keyboard when
     *             halant is selected in swarachakra
     */
    private void setHalant(String text) {
        if (inHalantMode) {
            preText = text;
            Log.d("flow", "setHalant mode false");
            updateKeyLabels();
        } else {
            preText = text;
            inHalantMode = true;
            Log.d("flow", "setHalant mode true");
            updateKeyLabels();
        }
    }

    /**
     * Changes the layout of the keyboard
     *
     * @param layout name string of the layout resource
     */
    private void changeLayout(String layout) {
        mSoftKeyboard.changeKeyboard(layout);
    }

    /**
     * Changes the language of the keyboard
     *
     * @param name string of the layout resource
     */
    private void changeLanguage() {
        mSoftKeyboard.changeLanguage();
    }

    /**
     * Handles what to be done when keys like ENTER, SPACE, BACKSPACE, EN,
     * SHIFT, SYM
     *
     * @param keyCode key code of the key whose functionality is to be handled
     */
    private void handleSpecialInput(int keyCode) {
        Log.d("debug", "handle special input");
        //BT
        inSymbolMode1 = inSymbolMode;

        if (keyCode == REMOTEKEYBOARD) {
            System.out.println("handle remote keyboard");
            mSoftKeyboard.changeKeyboardremote("remote");
			/*Intent dialogIntent = new Intent(this, SettingsActivity.class);
			dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(dialogIntent);*/
        }

        if (keyCode == SHIFT && !shiftHandled) {
            if (!inSymbolMode) {
                if (isShifted) {
                    isShifted = false;
                    isPersistent = false;
                    changeLayout("default");
                } else {
                    isShifted = true;
                    changeLayout("shift");
                }
            }
        } else if (keyCode == BACKSPACE) {
            if (active == true) {
                //System.out.println("exceptionn1 +"+mExceptionLangHandler1);
                //System.out.println("exceptionn +"+mExceptionLangHandler);
                committextremote(String.valueOf(keyCode), 1);
                ishalant = true;
            }
            if (inSymbolMode) {
                Log.d("debug", "Symbol mode");
                CharSequence selection = mInputConnection.getSelectedText(0);
                commitText("");

                if (selection == null) {
                    mInputConnection.deleteSurroundingText(1, 0);
                }
            } else {
                //mExceptionLangHandler.setInputConnection(mInputConnection);
                mExceptionLangHandler.handleBackSpaceDeleteChar(mInputConnection);
            }


        } else if (keyCode == SPACE) {
            if (!spaceHandled) {
                mInputConnection.finishComposingText();
                if (active == true) {
                    //TODO: Similarly initiate finishcomposing and commit
                    //committextremote(" ", 1);
                    message1 = new BroadcastMessage(BroadcastMessage.Type.STR, " ");
                    int[] extras = new int[]{3};
                    message1.setExtras(extras);
                    //System.out.println("this will go" + message);
                    MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message1);
                }
                commitText(" ");
            }
        } else if (keyCode == SYMBOLS) {
            if (inSymbolMode) {
                inSymbolMode = !(inSymbolMode);
                if (isPersistent && isShifted) {
                    changeLayout("shift");
                } else {
                    changeLayout("default");
                }
            } else {
                inSymbolMode = !(inSymbolMode);
                changeLayout("symbols");
            }
        } else if (keyCode == ENGLISH) {
            changeLanguage();
        } else if (keyCode == ENTER) {
            handleEnter();
            if (active == true)
                committextremote(String.valueOf(keyCode), 1);
        }

    }

    private void handlePreview() {
        Log.d("system", "insidePreview");
        CharSequence before;
        //BT condition
        if (active == true) {
            before = b;
            System.out.println("beforr" + before);
        } else {
            before = mInputConnection.getTextBeforeCursor(15, 0);
        }
        System.out.println("beforeo" + before);

        Log.d("flow", "Before value::" + before + "-");
        //Something has been typed
        if (before != null) {

            Log.d("flow", "before not null");
            String test = before.toString();
            int i = test.length() - 1; //Start with the last char before cursor
            //Log.v("system","charAt i:"+test.charAt(i));
            //Log.v("system","charAt i-1:"+test.charAt(i-1));

            //Not a consonant
            if (i > 0 && !mExceptionLangHandler.languageConsonants.contains(test.charAt(i))) {
                //mLog.v("system","insideLangConsonants");

                //Is a chakra vowel modifier
                if ((i) > 0 && mExceptionLangHandler.chakraVowelModifiers.contains(test.charAt(i))) {

                    //If it is halant
                    if (String.valueOf(test.charAt(i)).equals(MainLanguageExceptionHandler.HALANT)) {

                        /*if(active == true){
                            if(!inHalantMode)
                                inHalantMode=true;
                        }*/
                        Log.d("debug", "insideHalant check");
                        //Character c = test.charAt(i-1);
                        String str = String.valueOf(test.charAt(i));
                        int consonantCount = 1;
                        //int vowelModCount = 1;
                        for (int j = i - 1; ; j--) {

                            if (j >= 0 && mExceptionLangHandler.languageConsonants.contains(test.charAt(j))) {

                                str = String.valueOf(test.charAt(j)) + str;
                                if ((j - 1) >= 0 && mExceptionLangHandler.languageConsonants.contains(test.charAt(j - 1))) {
                                    break;
                                }

                            } else if (j >= 0 && mExceptionLangHandler.chakraVowelModifiers.contains(test.charAt(j))) {
                                //vowelModCount = vowelModCount+1;
                                if (String.valueOf(test.charAt(j)).equals(MainLanguageExceptionHandler.HALANT)) {
                                    str = String.valueOf(test.charAt(j)) + str;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                            //mLog.v("system","J="+j+",i="+i);
                        }
                        Log.d("system", "halant string:" + str + "-");
                        if (str != null || !str.equals("")) {

                            if (active == true) {
								/*message1 = new BroadcastMessage(BroadcastMessage.Type.STR, str);
								int[] extras = new int[]{3};
								message1.setExtras(extras);
								//System.out.println("this will go" + message);
								MainKeyboardActionListener.mServiceCommunicator.sendBroadcastMessage(message1);*/
                            } else {
                                ExtractedText edt = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0);
                                int lastChar = mInputConnection.getTextBeforeCursor(edt.text.length(), 0).length();// The whole text in text box is considered to be less than 1000 characters
                                System.out.println("str keyboard "+str);
                                System.out.println("lastcharkeyboard "+lastChar);
                                //mLog.v("system","stringLength:"+edt.text.length()+", lastC="+lastChar+", firstC="+(lastChar-str.length()));
                                mInputConnection.setComposingRegion(lastChar - (str.length()), lastChar);
                                mInputConnection.setComposingText(str, 1);
                            }
                        }
                        preText = str;
                        //SwaraChakra.setHalantExists(true);
                        System.out.println("beforstr now" + preText);
                        Log.d("flow", "handle preview 1:" + preText + "-");
                        updateKeyLabels();

                        //inHalantMode = false;
                    }
                    //non-halant chakra vowel modifier
                    else {
                        preText = "";
                        Log.d("flow", "handle preview 2::" + preText + "-");
                        updateKeyLabels();
                    }
                } else {
                    preText = "";
                    Log.d("flow", "handle preview 3::" + preText + "-");
                    updateKeyLabels();
                }
            } else {
                preText = "";
                Log.d("flow", "handle preview 4::" + preText + "-");
                updateKeyLabels();
            }
        } else {
            //Nothing typed yet
            preText = "";
            Log.d("flow", "handle preview 5::" + preText + "-");
            Log.d("flow", "before null");
            updateKeyLabels();
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
     * @param text string to be sent to EditText
     */
    private void commitText(String text) {
        mInputConnection.setComposingText(text, 1);
        mInputConnection.finishComposingText();
        updateExtractedText();
    }

    private void updateExtractedText() {
        try {
            ExtractedText edt = mInputConnection.getExtractedText(new ExtractedTextRequest(), 0);

            /*if (edt != null) {
                mKeyLogger.extractedText = edt.text.toString();
            } else {
                //mLog.d(mKeyLogger.TAG,"handlechar(): About to hide, nothing to save" + edt);
            }*/
        } catch (Exception ex) {
            //mLog.d(mKeyLogger.TAG,"handlechar():ex "+ex.getMessage());
        }
    }

    //TODO: BT changed scope of the function
    public void updateKeyLabels() {
        Log.d("flow", "UpdateKeyLabels called");
        List<Key> keys = mKeyboardView.getKeyboard().getKeys();
        for (Key key : keys) {
            int code = key.codes[0];
            //System.out.println("beforhalantend"+halantendremote);
            if (code <= halantendremote) {
                String nextLabel = "";
                if (inExceptionMode && sKeys.containsKey(code)) {
                    nextLabel = sKeys.get(code).label;
                    Log.d("exhandle", "inexceptionMode: nextLabel = " + nextLabel);
                } else {
                    nextLabel = preText + mKeys.get(code).label;
                    Log.d("exhandle", "not in exceptionMode: nextLabel = " + nextLabel);
                }
                key.label = nextLabel;
            }
        }
        mKeyboardView.invalidateAllKeys();
    }

    private String getKeyLabel(int keyCode) {
        if (inExceptionMode) {
            if (sKeys.containsKey(keyCode)) {
                String label = sKeys.get(keyCode).label;
                return label;
            }
        }
        String label = preText + mKeys.get(keyCode).label;
        return label;
    }

    @Override
    public void onText(CharSequence arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeDown() {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeLeft() {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeRight() {
        // TODO Auto-generated method stub

    }

    @Override
    public void swipeUp() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onTouch(View view, MotionEvent me) {
        // TODO Auto-generated method stub
        MainKeyboardView mKeyboardView = (MainKeyboardView) view;
        int action = me.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int) me.getX();
            int y = (int) me.getY();
            setTouchDownPoint(x, y);
        } else if (action == MotionEvent.ACTION_UP
                || (action == MotionEvent.ACTION_OUTSIDE)) {
        } else if (action == MotionEvent.ACTION_MOVE) {
            int x = (int) me.getX();
            int y = (int) me.getY();
            handleMove(x, y);

            return true;
        } else if (action == MotionEvent.ACTION_CANCEL) {
            mSwaraChakra.desetArc();
        }

        return mKeyboardView.onTouchEvent(me);
    }

    private void handleMove(int x, int y) {

        int touchMovementX = (int) x - touchDownX;
        int touchMovementY = (int) y - touchDownY;

        if (y == 0 && touchMovementX < mSwaraChakra.getOuterRadius()
                && touchMovementY < mSwaraChakra.getOuterRadius()) {
            float outerRadius = (float) (1.2 * mSwaraChakra.getOuterRadius());
            touchMovementY = -(int) Math.sqrt(outerRadius * outerRadius
                    - touchMovementX * touchMovementX);
        }

        int radius = (int) Math.sqrt((touchMovementX * touchMovementX)
                + (touchMovementY * touchMovementY));

        float theta = (float) Math.toDegrees(Math.atan2(touchMovementY,
                touchMovementX));

        if (radius > MOVE_THRESHOLD) {
            int arc = findArc(theta);
            if (isChakraVisible) {
                mSwaraChakra.setArc(arc);
                String text = mSwaraChakra.getText();
                if (!preText.equals("") && (mExceptionLangHandler.chakraWholeVowels.contains(text.charAt(0))
                        || mExceptionLangHandler.specialCases.contains(text.charAt(0)) || mExceptionLangHandler.chakraVowelModifiers.contains(text.charAt(0)))) {
                    System.out.println(text + "in chakra whole vowels");
                    text = preText + text;
                }
                mInputConnection.setComposingText(text, 1);
            }
        } else {
            if (isChakraVisible) {
                mSwaraChakra.desetArc();
                String text = mSwaraChakra.getText();
                if (!preText.equals("") && (mExceptionLangHandler.chakraWholeVowels.contains(text.charAt(0))
                        || mExceptionLangHandler.specialCases.contains(text.charAt(0)) || mExceptionLangHandler.chakraVowelModifiers.contains(text.charAt(0)))) {
                    System.out.println(text + "in chakra whole vowels");
                    text = preText + text;
                }
                mInputConnection.setComposingText(text, 1);

            }
        }

		/*if (radius > DIM_THRESHOLD) {
			if (mSwaraChakra.getVisibility() == View.VISIBLE) {
				mKeyboardView.setAlpha(0.35f);
			}
		} else {
			if (mSwaraChakra.getVisibility() == View.VISIBLE) {
				float a = 0;
				double k = (-0.45) / DIM_THRESHOLD;
				a = (float) (0.80 + k * radius);
				mKeyboardView.setAlpha(a);
			}
		}*/
    }

    private int findArc(float theta) {
        int nArcs = SwaraChakra.getNArcs();
        float offset = (float) -(90.0 + 360.0 / (2 * nArcs));
        float relAngle = theta - offset;
        if (relAngle < 0) {
            relAngle = 360 + relAngle;
        }
        int region = (int) (relAngle * nArcs / 360);
        return region;
    }

    /*public void setKeyLogger(KeyLogger keyLogger) {
        mKeyLogger = keyLogger;
    }*/

    @Override
    public void onStop() {
        super.onStop();
        active = false;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        active = false;
        Intent i = new Intent(MainKeyboardActionListener.this, SettingsActivity.class);
        startActivity(i);
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mServiceCommunicator != null) {
            mServiceCommunicator.sendServiceMessage(HotspotManagerService.MSG_DISABLE_HOTSPOT, null);
            mServiceCommunicator.unbindService(MainKeyboardActionListener.this);
        }
    }

    public  void setbefore(String b){
        this.b=b;
    }
    public String getbefore(){
        return b;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageReceivedEvent event) {

        Log.d("debug", "OnMessage");
        BroadcastMessage message = event.mMessage;
        if(message.mType== BroadcastMessage.Type.BLUETOOTH){

            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }
            Log.d("debug", "Disconnect pressed in remote KB");
            this.onBackPressed();

        } else {
            System.out.println("beforaya" + message.mMessage);
            b = message.mMessage;
            Log.d("debug", "Value of before received:" + b);
            handlePreview();

        }
    }
}

