package iit.android.swarachakra;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import iit.android.event.BroadcastMessage;
import iit.android.event.MessageReceivedEvent;
import iit.android.hotspot.HotspotManagerService;
import iit.android.hotspot.HotspotManagerServiceCommunicator;
import iit.android.language.Language;
import iit.android.language.MainLanguage;
import iit.android.language.english.English;
import iit.android.mode.TextActivity;
import iit.android.settings.SettingsActivity;

import static iit.android.event.BroadcastMessage.Type.BLUETOOTH;
import static iit.android.event.BroadcastMessage.Type.FINISHCOMPOSINGTEXT;
import static iit.android.event.BroadcastMessage.Type.HANDLEPREVIEW;
import static iit.android.event.BroadcastMessage.Type.KEYBOARD;
import static iit.android.event.BroadcastMessage.Type.STR;

/**
 * Input Method Service that runs when the keyboard is up and manages the whole life cycle of the keyboard
 *
 * @author Manideep Polireddi, Madhu Kiran
 */
public class SoftKeyboard extends InputMethodService implements HotspotManagerServiceCommunicator
        .HotspotServiceCallback {

    private CustomKeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private HashMap<Integer, KeyAttr> mKeys;
    private HashMap<Integer, KeyAttr> mainKeys;
    private HashMap<Integer, KeyAttr> englishKeys;
    private MainLanguage mainLanguage;
    public String mainLanguageSymbol;
    private English english;
    private Language language;
    private InputConnection mInputConnection;
    private String languageName = "";
    private Context mContext;
    private String displayMode, layoutName;
    private Key mEnterKey;
    //private KeyLogger mKeyLogger;
    private static Context appContext = null;
    private boolean isPassword;
    //BT
    private static HotspotManagerServiceCommunicator mServiceCommunicator;
    boolean ifremote = false;
    private boolean first = false;
    private InputConnection ic;
    /*public static BroadcastMessage messagesend;*/

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d("settings","onCreate Called");
        appContext = getApplicationContext();
        Installation.id(getApplicationContext());
       /* mKeyLogger = new KeyLogger(this);
        mKeyLogger.setSoftKeyboard(this);
        mKeyLogger.extractedText = "";*/

        //BT
        mServiceCommunicator = new HotspotManagerServiceCommunicator(SoftKeyboard.this);
        //System.out.println("mservice "+mServiceCommunicator);
        //mServiceCommunicator.bindService(SoftKeyboard.this);
    }

    @Override
    public void onInitializeInterface() {
        mainLanguage = new MainLanguage();
        mainKeys = mainLanguage.hashThis();
        mainLanguageSymbol = mainLanguage.symbol;

        english = new English();
        englishKeys = english.hashThis();

        if (languageName == "") {
            setLanguage("main");
        } else {
            setLanguage(languageName);
        }

    }

    @Override
    public View onCreateInputView() {

        //mLog.d("dbgm", "onCreateInputView()");

         /*  Log.d("dbgm", "Width:" +  mKeyboardView.getWidth());
        Log.d("dbgm", "Height:" + mKeyboardView.getHeight());*/
        //BT
        mServiceCommunicator.bindService(SoftKeyboard.this);
        if (ifremote == true) {
            Button inputView = (Button) getLayoutInflater().inflate(R.layout.remote_keyboard, null);
            inputView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("destroy0");
                    stopservice();
                    /*final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.better_together_framework);
                    builder.setMessage(R.string.confirm_disconnect);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("destroy0");
                            stopservice();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();*/

                    //setInputView(onCreateInputView());
                }
            });
            return inputView;
        } else {
            mContext = this;
            layoutName = "";

            if (languageName == "") {
                setLanguage("main");
            } else {
                setLanguage(languageName);
            }

            detectDisplayMode();
            int keyboardViewResourceId = getKeyboardViewResourceId();
            final RelativeLayout layout = (RelativeLayout) getLayoutInflater()
                    .inflate(keyboardViewResourceId, null);

            try {
            /*layout = (RelativeLayout) getLayoutInflater()
                    .inflate(keyboardViewResourceId, null);*/

                if (languageName == "main") {

                    mKeyboardView = (MainKeyboardView) layout.findViewById(R.id.keyboard);

                } else {
                    mKeyboardView = (EnglishKeyboardView) layout.findViewById(R.id.keyboard);
                }

                int resourceId = getResourceId("default");
                mKeyboard = new Keyboard(this, resourceId);
                mKeyboardView.setKeyboard(mKeyboard);

                mKeyboardView.init(this, language, mKeys);

                updateFullscreenMode();

                setKeys();
                mKeyboardView.invalidateAllKeys();

            } catch (Exception ex) {
                Log.d("dbgm", ex.getMessage().toString());
                //return;
                //final RelativeLayout layout = null;
            }

            return layout;
        }
    }

    //BT
    //TODO: remove unused functions
    public void stopservice() {
        Log.d("debug", "Disconnect from text activity");
        ifremote=false;
        first=false;
        if(TextActivity.activetext==true || MainKeyboardActionListener.active==true){
            Intent i=new Intent(this, SettingsActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SettingsActivity.ifremote=false;
            SettingsActivity.ifremotekeyboard=false;
            TextActivity.activetext=false;
            MainKeyboardActionListener.active=false;

            startActivity(i);
        }
        if (mServiceCommunicator != null) {
            System.out.println("destroy1");
            Log.d("debug", "Disconnect from text activity");
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }
            mServiceCommunicator.sendServiceMessage(HotspotManagerService.MSG_DISABLE_HOTSPOT, null);
            mServiceCommunicator.unbindService(SoftKeyboard.this);
        }
        /*
        Log.d("debug", "Disconnect from text activity");
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }*/
        setInputView(onCreateInputView());
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        /*if (!isPassword) {
            mKeyLogger.writeToLocalStorage();
        }
        mKeyLogger.extractedText = "";
*/
        //mLog.d("flag", "write now");
    }

    public static Context appContext() {
        return appContext;
    }

    /**
     * Generates the layout resource id for the keyboard view based on the displayMode and current language
     *
     * @return layout resource id of the keyboard view to be shown
     */
    private int getKeyboardViewResourceId() {

        SharedPreferences prefs;//UserSettings.getPrefs();
        prefs = PreferenceManager.getDefaultSharedPreferences(appContext());

        String layout = getResources().getString(
                R.string.layout);
        String screensize = getResources().getString(R.string.layout_screensize);
        //String theme = getResources().getString(R.string.layout_theme);


        String layoutName = SettingsActivity.getLayoutName(prefs.getInt(layout, SettingsActivity.LAYOUT_HIVE));
        String sizeName = SettingsActivity.getScreenSizeName(prefs.getInt(screensize, SettingsActivity.SCREENSIZE_SMALL_NORMAL));
        //String themeName =  SettingsActivity.getThemeName(prefs.getInt(theme,SettingsActivity.THEME1));
        String file = "";
        //layout_orient_screensize_lang_layer_theme.xml
        if (languageName == "english") {
            file = "kview_" + displayMode + languageName;
            //mLog.d("dbgm", file);
        } else {
            file = "kview_" + displayMode + sizeName + "_" + languageName + "_" + layoutName;
            //mLog.d("dbgm", file);
        }

        //String file = "kview_" + displayMode + languageName;
        //mLog.d("dbgm", "filename = " + file);
        int output = getResources().getIdentifier(file, "layout",
                getPackageName());

        return output;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {

        //mLog.d("dbgm", "onStartInputView()");

        mInputConnection = getCurrentInputConnection();

        if (mInputConnection == null) {
            Log.d("dbgm", "mInputConnection is null");
            return;
        }
        if (mKeyboardView == null) {
            Log.d("dbgm", "mKeyboardView is null");
            return;
        }

        mKeyboardView.resetInputConnection(mInputConnection);
        mKeyboardView.setAlpha(1);
        setImeOptions();

        String prevDisplayMode = displayMode;
        detectDisplayMode();
        //mLog.d("track", "" + displayMode);

        if (displayMode != prevDisplayMode) {
            //mLog.d("dbgm", "Display mode is not the same");

            setInputView(onCreateInputView());
        } else {
            //mLog.d("dbgm", "Display mode is the same");
        }

        mKeyboardView.keys = mKeyboardView.getKeyboard().getKeys();
        //setInputView(onCreateInputView());

        //mLog.d("dbgm", "onStartInputView()");
        //Display dm = mKeyboardView.getDisplay();


        /*Log.d("dbgm", "Width:" + mKeyboardView.getWidth());
        Log.d("dbgm", "Height:" + mKeyboardView.getHeight());*/

        //mLog.d("dbgm", "Width:" + mKeyboardView.getMeasuredWidth());
        //mLog.d("dbgm", "Height:" + mKeyboardView.getMeasuredHeight());

    }

    @Override
    public boolean onEvaluateFullscreenMode() {
        return false;
    }

    /**
     * sets the labels to the keys on the keyboard
     */
    private void setKeys() {
        List<Key> keys = mKeyboard.getKeys();
        Log.d("track", "hereout" + keys.size());
        for (Key key : keys) {
            //Log.d("track","hereout"+keys.size());
            if (mKeys.containsKey(key.codes[0])) {
                //Log.d("track","hereout2:"+key.codes[0]);

                if (key.codes[0] == 400) {
                    //Log.d("track","here1");
                    //key.gap = 500;
                }

                KeyAttr tempKey = mKeys.get(key.codes[0]);
                key.label = tempKey.label;

                if (tempKey.showIcon) {
                    int id = getDrawableId(tempKey.icon);
                    if (id != 0) {
                        key.icon = getResources().getDrawable(id);
                        key.label = null;
                        //Log.d("Location", "set icon " + key.icon);
                    }
                }
            }
        }
        setImeOptions();
    }

    /**
     * changes the keyboard in the keyboardView
     *
     * @param layoutFile layout id of the layout to be loaded into the keyboardView
     */
    public void changeKeyboard(String layoutFile) {

        //BT
        if (layoutFile == "remote") {
            //System.out.println("honey");
            ifremote = true;
            setInputView(onCreateInputView());

        }//--

        String prevDisplayMode = displayMode;
        detectDisplayMode();

        //Log.d("dbgm", "**display modes: " + prevDisplayMode + "," + displayMode);
        //Log.d("dbgm", "**layout : " + layoutName + "," + layoutFile);

        if (prevDisplayMode != displayMode) {

            //Log.d("dbgm", "change keyboard ");
            setInputView(onCreateInputView());

        } else {

            /*//mif (layoutName.equals(layoutFile))
                Log.d("dbgm", "Dont change keyboard ");
            else {
                Log.d("dbgm", "Layout changed:change keyboard ");
                //setInputView(onCreateInputView());
            }*/

            /*if(reLoadLayout){
                reLoadLayout = false;
                Log.d("dbgm","Reload flag reset:change keyboard ");
                //setInputView(onCreateInputView());
            }else
                Log.d("dbgm", "Dont change keyboard ");*/
        }
        //setInputView(onCreateInputView());

        int resourceId = getResourceId(layoutFile);
        if (resourceId != 0) {
            mKeyboard = new Keyboard(mContext, resourceId);
            setKeys();
            mKeyboardView.setKeyboard(mKeyboard);

            //mKeyboardView.setBackground(getResourceBackgroundID(layoutFile));
            if (languageName != "english")
                mKeyboardView.setBackgroundResource(getResourceBackgroundID(layoutFile));

        } else {
            //Log.d("layout", "you ");
        }

        layoutName = layoutFile;

    }

    //BT
    public void changeKeyboardremote(String layoutFile) {
        if (layoutFile == "remote") {
            //System.out.println("honey");
            Intent dialogIntent = new Intent(this, SettingsActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);
           /* TextView inputView = (TextView) getLayoutInflater().inflate(R.layout.remote_keyboard, null);
            inputView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Log.d(this, "Disconnect keyboard here if necessary");
                }
            });*/
        }

    }

    /**
     * sets the current language and keys hashmap according to the language
     *
     * @param name name of the language
     */
    public void setLanguage(String name) {
        languageName = name;
        if (name == "english") {
            language = english;
            mKeys = englishKeys;
        } else {
            language = mainLanguage;
            mKeys = mainKeys;
        }
    }

    /**
     * Changes the language of the keyboard from english to main language and vice-versa
     */
    public void changeLanguage() {
        if (languageName == "main") {
            language = english;
            languageName = "english";
            setLanguage("english");
        } else {
            language = mainLanguage;
            languageName = "main";
            setLanguage("main");
        }
        setInputView(onCreateInputView());
    }

    public boolean showTablet(Context context) {


        // SharedPreferences settings = mySharedPreferences();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(SoftKeyboard.appContext());
        SharedPreferences.Editor editor = settings.edit();
        String key = context.getResources().getString(R.string.layout_screensize);
        boolean isFirstRun = settings.getBoolean("is_first_run", true);
        int tempVal = 0;

        if (isFirstRun) {
            editor.putBoolean("is_first_run", false);

            //editor.putBoolean(key, isTablet(context));

			/*if(isTablet(context)){
                editor.putInt(key, 2);
			}else
				editor.putInt(key, 1);*/
            tempVal = isTablet(context);
            editor.putInt(key, tempVal);

            editor.commit();
        } else {

            tempVal = settings.getInt(key, SettingsActivity.SCREENSIZE_SMALL_NORMAL);
        }

        boolean showTabletLayout = (tempVal == SettingsActivity.SCREENSIZE_ATLEAST_LARGE) ? true : false;//settings.getBoolean(key, false);
        return showTabletLayout;
    }

    public static int isTablet(Context context) {
        /*return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;*/

        //Log.d("track", "istablet()");

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return SettingsActivity.SCREENSIZE_ATLEAST_LARGE;

        } else {

            return SettingsActivity.SCREENSIZE_SMALL_NORMAL;
        }
    }


    /**
     * Detects the display config(landscape or portrait) and sets the displayMode accordingly
     */
    public void detectDisplayMode() {
        int dispMode = getResources().getConfiguration().orientation;

        if (dispMode == 1) {
            displayMode = "port_";
            //if(showTablet(mContext)){displayMode = "tablet_";}
        } else {
            displayMode = "land_";
        }
    }

    /**
     * Gets the layout file resource id of the keyboard based on displayMode and languageName
     *
     * @param layoutFile layout of the keyboard whose resource id is to be returned
     * @return Resource id of the layout file of the keyboard to be shown
     */
    public int getResourceId(String layoutFile) {

        //mLog.d("dbgm", "Get resource id for:"+layoutFile);

        int resourceId = 0;
        //hex
        //resourceId = getResources().getIdentifier(languageName + "_" + displayMode + layoutFile, "layout",getPackageName());

        //SharedPreferences prefs = UserSettings.getPrefs();
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(appContext());

        String layout = getResources().getString(
                R.string.layout);
        String screensize = getResources().getString(
                R.string.layout_screensize);
        String theme = getResources().getString(
                R.string.layout_theme);
        String kbHeight = getResources().getString(
                R.string.layout_height);
        //prefs.getInt(kbHeight, getRecommendedHeight());


        String layoutName = SettingsActivity.getLayoutName(prefs.getInt(layout, SettingsActivity.LAYOUT_HIVE));
        String sizeName = SettingsActivity.getScreenSizeName(prefs.getInt(screensize, SettingsActivity.SCREENSIZE_SMALL_NORMAL));
        String themeName = SettingsActivity.getThemeName(prefs.getInt(theme, SettingsActivity.THEME1));
        String kbHeightSetting = SettingsActivity.getLayoutHeightName(prefs.getInt(layout, SettingsActivity.LAYOUT_HIVE), prefs.getInt(kbHeight, 1));

        //Log.d("dbgm","kbheightsetting:"+kbHeightSetting);
        //layout_orient_screensize_lang_layer_theme.xml
        String name = "";
        //Log.d("dbgm","language:"+languageName);

        if (languageName == "english") {

            name = displayMode + languageName + "_" + layoutFile + "_" + themeName;
        } else {
            name = layoutName + displayMode + sizeName + "_" + languageName + "_" + layoutFile + "_" + themeName + kbHeightSetting;
        }
        //String name = layoutName+displayMode+languageName+"_"+layoutFile+"_"+themeName;
        //mLog.d("dbgm", name);

        resourceId = getResources().getIdentifier(name, "layout", getPackageName());

        return resourceId;
    }

    public int getResourceBackgroundID(String layoutFile) {
        //Log.d("track","bkg image:"+layoutFile);

        int resourceId = 0;
        //hex
        //resourceId = getResources().getIdentifier(languageName + "_" + displayMode + layoutFile, "layout",getPackageName());

        SharedPreferences prefs;//UserSettings.getPrefs();
        prefs = PreferenceManager.getDefaultSharedPreferences(appContext());
        String layout = getResources().getString(
                R.string.layout);
        String screensize = getResources().getString(
                R.string.layout_screensize);
        String theme = getResources().getString(
                R.string.layout_theme);


        String layoutName = SettingsActivity.getLayoutName(prefs.getInt(layout, SettingsActivity.LAYOUT_HIVE));
        String sizeName = SettingsActivity.getScreenSizeName(prefs.getInt(screensize, SettingsActivity.SCREENSIZE_SMALL_NORMAL));
        String themeName = SettingsActivity.getThemeName(prefs.getInt(theme, SettingsActivity.THEME1));

        //layout_orient_screensize_lang_layer_theme.xml
        String name = "";
        if (languageName == "english") {

            //name = displayMode+languageName+"_"+layoutFile+"_"+themeName;
        } else {
            name = layoutName + displayMode + sizeName + "_" + languageName + "_" + layoutFile + "_" + themeName;
        }
        //String name = layoutName+displayMode+languageName+"_"+layoutFile+"_"+themeName;
        //mLog.d("track", "bkg image:" + name);

        resourceId = getResources().getIdentifier(name, "drawable", getPackageName());

        return resourceId;
    }

    /**
     * Gets the resource id of the drawable
     *
     * @param drawable drawable name of whose resource id is to be returned
     * @return Drawable id in the resources
     */
    public int getDrawableId(String drawable) {
        int resourceId = 0;
        resourceId = getResources().getIdentifier(drawable, "drawable",
                getPackageName());
        //Log.d("Location", "R id " + resourceId);
        return resourceId;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mInputConnection != null) {
            mInputConnection.setComposingText("", 1);
            mInputConnection.finishComposingText();
            mKeyboardView.configChanged();
            ;
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Changes the appearance of the enter key based on IME options
     */
    void setImeOptions() {
        Resources res = getResources();
        EditorInfo ei = getCurrentInputEditorInfo();
        int textOptions = ei.inputType;
        int options = ei.imeOptions;
        for (Key k : mKeyboard.getKeys()) {
            if (k.codes[0] == mKeyboardView.getEnterKeyCode()) {
                mEnterKey = k;
            }
        }
        if (mEnterKey == null) {
            return;
        }

        switch (options
                & (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            case EditorInfo.IME_ACTION_GO:
                mEnterKey.iconPreview = null;

                mEnterKey.label = "Go";
                // mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_return);
                break;
            case EditorInfo.IME_ACTION_NEXT:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                // mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_return);
                mEnterKey.label = "Next";
                break;
            case EditorInfo.TYPE_TEXT_VARIATION_EMAIL_SUBJECT:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = "NEXT";
                break;
            case EditorInfo.IME_ACTION_DONE:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = "Done";

                break;
            case EditorInfo.IME_ACTION_SEARCH:
                mEnterKey.icon = res.getDrawable(R.drawable.ic_action_search);
                break;
            case EditorInfo.IME_ACTION_SEND:
                mEnterKey.iconPreview = null;
                mEnterKey.label = "Send";
                break;
            default:
                mEnterKey.icon = res.getDrawable(R.drawable.sym_keyboard_return);
                mEnterKey.label = null;
                break;
        }

        switch (textOptions) {
            case EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD:
                this.setPassword(true);
                break;
            case EditorInfo.TYPE_TEXT_VARIATION_PASSWORD:
                this.setPassword(true);
                break;
            case EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
                this.setPassword(true);
                break;
            case EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD:
                this.setPassword(true);
                break;
            default:
                this.setPassword(false);
                break;
        }
        mKeyboardView.invalidateAllKeys();
    }

    /**
     * Gets the KeyLogger of this SoftKeyboard service
     *
     * @return KeyLogger of this SoftKeyboard service
     */
   /* public KeyLogger getKeyLogger() {
        return mKeyLogger;
    }*/

    public boolean isPassword() {
        return isPassword;
    }

    public void setPassword(boolean isPassword) {
        this.isPassword = isPassword;
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        // Log.d(TAG, "Starting remote keyboard input (restarting: " + restarting + ")");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this); // register for EventBus events
        }

        // TODO: forward this state to the controlling keyboard
        //// Reset our state.  We want to do this even if restarting, because
        //// the underlying state of the text editor could have changed in any way.
        //mComposing.setLength(0);
        //updateCandidates();
        //
        //if (!restarting) {
        //	// Clear shift states.
        //	mMetaState = 0;
        //}
        //
        //mPredictionOn = false;
        //mCompletionOn = false;
        //mCompletions = null;
        //
        //// We are now going to initialize our state based on the type of
        //// text being edited.
        //switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
        //	case InputType.TYPE_CLASS_NUMBER:
        //	case InputType.TYPE_CLASS_DATETIME:
        //		// Numbers and dates default to the symbols keyboard, with
        //		// no extra features.
        //		mCurKeyboard = mSymbolsKeyboard;
        //		break;
        //
        //	case InputType.TYPE_CLASS_PHONE:
        //		// Phones will also default to the symbols keyboard, though
        //		// often you will want to have a dedicated phone keyboard.
        //		mCurKeyboard = mSymbolsKeyboard;
        //		break;
        //
        //	case InputType.TYPE_CLASS_TEXT:
        //		// This is general text editing.  We will default to the
        //		// normal alphabetic keyboard, and assume that we should
        //		// be doing predictive text (showing candidates as the
        //		// user types).
        //		mCurKeyboard = mQwertyKeyboard;
        //		mPredictionOn = true;
        //
        //		// We now look for a few special variations of text that will
        //		// modify our behavior.
        //		int variation = attribute.inputType & InputType.TYPE_MASK_VARIATION;
        //		if (variation == InputType.TYPE_TEXT_VARIATION_PASSWORD || variation == InputType
        //				.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
        //			// Do not display predictions / what the user is typing
        //			// when they are entering a password.
        //			mPredictionOn = false;
        //		}
        //
        //		if (variation == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS || variation == InputType.TYPE_TEXT_VARIATION_URI
        //				|| variation == InputType.TYPE_TEXT_VARIATION_FILTER) {
        //			// Our predictions are not useful for e-mail addresses
        //			// or URIs.
        //			mPredictionOn = false;
        //		}
        //
        //		if ((attribute.inputType & InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
        //			// If this is an auto-complete text view, then our predictions
        //			// will not be shown and instead we will allow the editor
        //			// to supply their own.  We only show the editor's
        //			// candidates when in fullscreen mode, otherwise relying
        //			// own it displaying its own UI.
        //			mPredictionOn = false;
        //			mCompletionOn = isFullscreenMode();
        //		}
        //
        //		// We also want to look at the current state of the editor
        //		// to decide whether our alphabetic keyboard should start out
        //		// shifted.
        //		updateShiftKeyState(attribute);
        //		break;
        //
        //	default:
        //		// For all unknown input types, default to the alphabetic
        //		// keyboard with no special features.
        //		mCurKeyboard = mQwertyKeyboard;
        //		updateShiftKeyState(attribute);
        //}
        //
        //// Update the label on the enter key, depending on what the application
        //// says it will do.
        //mCurKeyboard.setImeOptions(getResources(), attribute.imeOptions);
    }

    // TODO: can we communicate with the keyboard another way? (e.g., handler?)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageReceivedEvent event) {
        BroadcastMessage message = event.mMessage;
        ic = getCurrentInputConnection();
        Log.d("debug","Remote keyboard: "+message.mMessage);

        if (message.mType == KEYBOARD && first == false && MainKeyboardActionListener.active == false) {
            ifremote=true;
            changeKeyboard("remote");
            first = true;
        }
        if (message.mType == KEYBOARD) {

            System.out.println("messagelo " + message.mMessage);

            if (message.mMessage.matches("\\d+") && Integer.parseInt(message.mMessage) == mKeyboardView.BACKSPACE) {

                if (MainKeyboardActionListener.inSymbolMode1) {
                    CharSequence selection = ic.getSelectedText(0);
                    //ic = getCurrentInputConnection();
                    ic.setComposingText("", 1);
                    ic.finishComposingText();
                    /*try {
                        ExtractedText edt = ic.getExtractedText(new ExtractedTextRequest(), 0);

                        if (edt != null) {
                            //mKeyLogger.extractedText = edt.text.toString();
                        } else {
                            //mLog.d(mKeyLogger.TAG,"handlechar(): About to hide, nothing to save" + edt);
                        }
                    } catch (Exception ex) {
                        //mLog.d(mKeyLogger.TAG,"handlechar():ex "+ex.getMessage());
                    }*/


                    if (selection == null) {
                        ic.deleteSurroundingText(1, 0);
                    }
                } else {
                    System.out.println("exceptionn +" + MainKeyboardActionListener.mExceptionLangHandler);
                    //System.out.println("exceptionn2 +"+mExceptionLangHandler);
                    //mExceptionLangHandler.setInputConnection(mInputConnection);
                    MainKeyboardActionListener.mExceptionLangHandler.handleBackSpaceDeleteChar(ic);

                    CharSequence before = ic.getTextBeforeCursor(15, 0);
                    Log.d("flow", "After handleBackSpaceDeleteChar()");
                    BroadcastMessage messagesend = new BroadcastMessage(BroadcastMessage.Type.KEYBOARD, before.toString());
                    int[] extras = new int[]{67};
                    messagesend.setExtras(extras);
                    //System.out.println("this will go" + message);
                    SoftKeyboard.mServiceCommunicator.sendBroadcastMessage(messagesend);

                }


            } else if (message.mMessage.matches("\\d+") && Integer.parseInt(message.mMessage) == mKeyboardView.ENTER) {
                System.out.println("enter");
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_ENTER));
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_ENTER));
            } else {
                //ic = getCurrentInputConnection();
                if (message.getExtras()[0] == 2) {
                    //ic.finishComposingText();
                    System.out.println("halant aya" + message.mMessage);
                    ic.setComposingText(message.mMessage, 1);
                    //ic.finishComposingText();
                    //ic.deleteSurroundingText(1, 0);
                } else {
                    //ic.finishComposingText();
                    ic.setComposingText(message.mMessage, 1);
                    System.out.println("halant gaya" + message.mMessage);

                    ic.finishComposingText();
                }
                /*try {
                    ExtractedText edt = ic.getExtractedText(new ExtractedTextRequest(), 0);

                    if (edt != null) {
                        //mKeyLogger.extractedText = edt.text.toString();
                    } else {
                        //mLog.d(mKeyLogger.TAG,"handlechar(): About to hide, nothing to save" + edt);
                    }
                } catch (Exception ex) {
                    //mLog.d(mKeyLogger.TAG,"handlechar():ex "+ex.getMessage());
                }*/

            }

        } else if (message.mType == HANDLEPREVIEW) {

            //ic.finishComposingText();
            ic.setComposingText(message.mMessage, 1);
            System.out.println("HANDLEPREVIEW commit this " + message.mMessage);
            //ic.finishComposingText();

            CharSequence before = ic.getTextBeforeCursor(15, 0);
            System.out.println("SK onMessage() before=" + String.valueOf(before));
            Log.d("flow", "in OnMessage handle preview code block");
            BroadcastMessage messagesend = new BroadcastMessage(BroadcastMessage.Type.KEYBOARD, before.toString());
            int[] extras = new int[]{67};
            messagesend.setExtras(extras);
            //System.out.println("this will go" + message);
            SoftKeyboard.mServiceCommunicator.sendBroadcastMessage(messagesend);
            //MainKeyboardActionListener.handlePreviewremote();
        } else if (message.mType == STR) {
            String str = message.mMessage;
            Log.d("debug", "Message type str:-"+str+"-");

            ExtractedText edt = ic.getExtractedText(new ExtractedTextRequest(), 0);
            System.out.println("edt " + edt);
            int lastChar = ic.getTextBeforeCursor(edt.text.length(), 0).length();
            System.out.println("lastcharsoftkeyboard " + lastChar);
            // The whole text in text box is considered to be less than 1000 characters
            //mLog.v("system","stringLength:"+edt.text.length()+", lastC="+lastChar+", firstC="+(lastChar-str.length()));
            //ic.finishComposingText();
            /*ic.setComposingRegion(lastChar - (str.length()), lastChar);
            ic.setComposingText(str, 1);*/

            ic.finishComposingText();
            ic.setComposingText(message.mMessage, 1);
            ic.finishComposingText();
        } else if (message.mType == FINISHCOMPOSINGTEXT) {
            Log.d("debug", "Message type finish composing text");
            ic.finishComposingText();
        }else if(message.mType==BLUETOOTH){
            stopservice();
        }
    }

    @Override
    public void onBroadcastMessageReceived(BroadcastMessage message) {

    }

    @Override
    public void onServiceMessageReceived(int type, String data) {

    }
}
