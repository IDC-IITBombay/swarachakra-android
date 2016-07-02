package iit.android.settings;

/***
 * To do: move the background file names for KB size preview to config on the top
 *
 *
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import iit.android.swarachakra.R;

public class SettingsActivity extends PreferenceActivity {
    public final static int SCREENSIZE_ATLEAST_LARGE = 2;
    public final static int SCREENSIZE_SMALL_NORMAL = 1;
    public final static int ORIENTATION_PORTRAIT = 1;
    public final static int ORIENTATION_LANDSCAPE = 2;
    public final static int LAYOUT_DEFAULT = 1;
    public final static int LAYOUT_HIVE = 2;
    public final static int THEME1 = 1;
    public final static int THEME2 = 2;
    public final static double layoutHeights[] = {46.61, 50.99, 56.27, 62.78, 70.98};
    public final static double smallLayoutHeight = 46.06; //layoutHeights[0];
    public final static double bigLayoutHeight = 61.41; //layoutHeights[2];
    public final static double layoutHeightsLandscape[] = {40.72, 44.55, 49.16, 54.84, 62.01};
    public final static double smallLayoutHeightLandscape = 57.59; //layoutHeights[0];
    public final static double bigLayoutHeightLandscape = 57.59; //layoutHeights[2];
    private final static float PORTRAIT_HEIGHT = 387f;
    private final static float PORTRAIT_WIDTH = 200f;
    public static int isTablet;
    public final double HEIGHT_THRESHOLD_LOWER = 3; //inches
    public final double HEIGHT_THRESHOLD_UPPER = 5; //inches
    public final double MIN_AVAILABLE_HEIGHT = 0.6; //inches, 180 mm
    /*private final static int PORTRAIT_HEIGHT = 232;
    private final static int PORTRAIT_WIDTH = 120;*/
    public final double RECO_HEIGHT = 2.65; //inches, 660 mm
    //public final double MM_TO_INCH_CONVERSION_FACTOR =  1.8; //1mm = 0.0393701 inches
    public final int HEX_LAYOUT_COUNT = 5;
    public float width, height, ydpi, xdpi, density;
    //public final static double layoutHeights[]={85.22,79.73,74.59,67.13,55.94};
    //public final static double layoutHeights[] = {46.06, 55.27, 61.41, 65.64, 70.16};
    public int currentOrientation;
    RelativeLayout.LayoutParams params;
    private boolean isDefault = false;
    private boolean isEnabled = false;
    private EditText previewEditText;
    private TextView instructionTextView, showKBSize,shortest,tallest,recommended;
    private RadioGroup radioGroup,layoutradioGroup;
    //private SeekBar sBar;
    //private RelativeLayout labels;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private boolean inEnglish = false;
    private CoordinatorLayout layout;
    private Button rateus, fblikeus, trykeyboard,fblike, tapaatap_btn;
    private TextView doYouLikeTextView;

    //hex
    public static int readPhoneSize(Context context) {

        //mLog.d("track", "istablet()");

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return SettingsActivity.SCREENSIZE_ATLEAST_LARGE;

        } else {

            return SettingsActivity.SCREENSIZE_SMALL_NORMAL;
        }
        //return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getLayoutName(int layoutNum) {
        //Log.d("track", "" + layoutNum);

        switch (layoutNum) {

            case SettingsActivity.LAYOUT_HIVE:
                return "hex_";
            case SettingsActivity.LAYOUT_DEFAULT:
            default:
                return "rect_";


        }
    }

    public static String getScreenSizeName(int screenNum) {

        switch (screenNum) {

            case SettingsActivity.SCREENSIZE_ATLEAST_LARGE:
                return "phone";
            case SettingsActivity.SCREENSIZE_SMALL_NORMAL:
                return "tablet";


        }
        return "phone";
    }

    public static String getLayoutHeightName(int layoutName, int heightNum) {

        switch (layoutName) {

            case SettingsActivity.LAYOUT_HIVE:
                //Log.d("dbgm",getLayoutName(layoutName)+"_" + Integer.toString(Math.abs(4 - heightNum) + 1));
                return "_" + Integer.toString(Math.abs(4 - heightNum) + 1);
            case SettingsActivity.LAYOUT_DEFAULT:
                return "";
        }
        return "";
        //return heightNum+1;
    }

    public static String getThemeName(int themeNum) {

        switch (themeNum) {

            case SettingsActivity.THEME1:
                return "themea";
            case SettingsActivity.THEME2:
                return "themeb";


        }
        return "themea";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            inEnglish = getIntent().getExtras().getBoolean("inEnglish", false);
        }

        layout = (CoordinatorLayout) getLayoutInflater().inflate(
                R.layout.settings_layout, null);
        /*previewEditText = (EditText) layout
                .findViewById(R.id.preview_edit_text);*/
        instructionTextView = (TextView) layout.findViewById(R.id.instruction);
        radioGroup = (RadioGroup) layout.findViewById(R.id.layoutRadioGroup);
        //sBar = (SeekBar) layout.findViewById(R.id.keyboardSize);
        //labels = (RelativeLayout) layout.findViewById(R.id.labels);
        rateus = (Button) layout.findViewById(R.id.rateus);
        doYouLikeTextView = (TextView) layout.findViewById(R.id.likeus);
        tapaatap_btn = (Button) layout.findViewById(R.id.tapaatap_button);

        fblikeus = (Button) layout.findViewById(R.id.fb_likeus);
        trykeyboard = (Button) layout.findViewById(R.id.tryit);
        showKBSize = (TextView) layout.findViewById(R.id.showKBSize);

        params = new RelativeLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        width = 0;
        height = 0;
        ydpi = 0;
        xdpi = 0;
        density = 0;

        RadioButton smallRadio = (RadioButton) layout
                .findViewById(R.id.smallRadioButton);
        RadioButton bigRadio = (RadioButton) layout
                .findViewById(R.id.bigRadioButton);
        RadioButton hexRadio = (RadioButton) layout
                .findViewById(R.id.hexRadioButton);

        /*RadioButton ht0 = (RadioButton) layout
                .findViewById(R.id.ht0);
        RadioButton ht1 = (RadioButton) layout
                .findViewById(R.id.ht1);
        RadioButton ht2 = (RadioButton) layout
                .findViewById(R.id.ht2);
        RadioButton ht3 = (RadioButton) layout
                .findViewById(R.id.ht3);
        RadioButton ht4 = (RadioButton) layout
                .findViewById(R.id.ht4);*/
        layoutradioGroup = (RadioGroup) layout
                .findViewById(R.id.layoutSizeRadioGroup);

        String smallRadioText = getStringResourceByName("settings_layout_small");
        String bigRadioText = getStringResourceByName("settings_layout_big");
        String hexRadioText = getStringResourceByName("settings_layout_hex");

        //shortest = (TextView)layout.findViewById(R.id.tvLabel1);
        //tallest = (TextView)layout.findViewById(R.id.tvLabel5);
        //recommended = (TextView)findViewById(R.id.tvLabel1);
        fblike = (Button)layout.findViewById(R.id.fb_likeus_tv2);

        FloatingActionButton langButton = (FloatingActionButton)layout.findViewById(R.id.toggleLanguage_settings);
        //String language_text = getStringResourceByName("menu_language");
        if(inEnglish) {
           /*
           //m:
           String languageName = getResources().getString(
                    R.string.language_name);
            int resId = getResources().getIdentifier(
                    languageName + "_" + "menu_language", "string",
                    getPackageName());
            language_text= getResources().getString(resId);*/
            langButton.setImageResource(R.drawable.marathi);
            //langButton.setBackgroundColor(Color.MAGENTA);
            //langButton.setBackgroundResource(R.drawable.fab_orange);
        }else{
           /* //m:
            int resId = getResources().getIdentifier(
                    "menu_language", "string",
                    getPackageName());
            language_text= getResources().getString(resId);*/
            langButton.setImageResource(R.drawable.english);
            //langButton.setBackgroundColor(Color.YELLOW);
            //langButton.setBackgroundResource(R.drawable.images);
        }
        //m:langButton.setText(language_text);


       /* String shortest_label = getStringResourceByName("height1");
        String tallest_label = getStringResourceByName("height5");*/
        String fb_label = getStringResourceByName("fb_likeus");
        //String reco_label = getStringResourceByName("heightreco");

        fblike.setText(fb_label);
        //shortest.setText(shortest_label);
        //tallest.setText(tallest_label);

        //RadioButton rb1 = (RadioButton)layoutradioGroup.getChildAt(0);
        //rb1.setText(shortest_label);

        //RadioButton rb2 = (RadioButton)layoutradioGroup.getChildAt(layoutHeights.length-1);
        //rb2.setText(tallest_label);


        smallRadio.setText(smallRadioText);
        bigRadio.setText(bigRadioText);
        hexRadio.setText(hexRadioText);

        String tryit_tv = getStringResourceByName("tryit");
        trykeyboard.setText(tryit_tv);

        String instruction = getStringResourceByName("settings_instruction");
        instructionTextView.setText(instruction);

        String doyoulikeustext = getStringResourceByName("do_you_like");
        String rateustext = getStringResourceByName("rate_us");
        String tapaatap_download = getStringResourceByName("tapaatap_download");

        doYouLikeTextView.setText(doyoulikeustext);
        rateus.setText(rateustext);
        tapaatap_btn.setText(tapaatap_download);

        layoutradioGroup.setVisibility(View.VISIBLE);

        setContentView(layout);

        //overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        checkKeyboardStatus();

        //hex:added
        //isTablet = isTablet(this);
        isTablet = readPhoneSize(this);

        prefs = UserSettings.getPrefs();

        String layout = getResources().getString(
                R.string.layout);
        String screensize = getResources().getString(
                R.string.layout_screensize);
        final String kbHeight = getResources().getString(
                R.string.layout_height);
        //Log.d("dbgm", layout + "_" + screensize);

        //Boolean isBigBool = prefs.getBoolean(key, false);
        //Log.d("settings:b",""+isBigBool);

        //hex
        //int isBig = prefs.getInt(key, 1);
        int size = prefs.getInt(screensize, SettingsActivity.SCREENSIZE_ATLEAST_LARGE);
        int layoutType = prefs.getInt(layout, SettingsActivity.LAYOUT_HIVE);

        int temp =0;
        editor = prefs.edit();
        if(!(prefs.contains(kbHeight))) {
            temp = getRecommendedHeight();
            editor.putInt(kbHeight, temp);
            //mLog.d("dbgm","Reco layout index:"+(Math.abs(4-temp)+1));
            editor.apply();
        }
        final int kbHeightSetting = prefs.getInt(kbHeight, getRecommendedHeight());

        //Log.d("settings",""+isBig);
        //Log.d("dbgm", layoutType + "_" + size);

		/*if (isBig == SettingsActivity.SCREENSIZE_SMALL_NORMAL) {
            smallRadio.setChecked(true);
			bigRadio.setChecked(false);
			hexRadio.setChecked(false);
		}else if(isBig==SettingsActivity.SCREENSIZE_ATLEAST_LARGE) {
			smallRadio.setChecked(false);
			bigRadio.setChecked(true);
			hexRadio.setChecked(false);
		} else {
			smallRadio.setChecked(false);
			bigRadio.setChecked(false);
			hexRadio.setChecked(true);
		}*/

        switch (layoutType) {

            case SettingsActivity.LAYOUT_HIVE:
                smallRadio.setChecked(false);
                bigRadio.setChecked(false);
                hexRadio.setChecked(true);

                //sBar.setVisibility(View.VISIBLE);
                //sBar.setProgress(kbHeightSetting);

                //labels.setVisibility(View.VISIBLE);

                layoutradioGroup.setVisibility(View.VISIBLE);
                ((RadioButton)layoutradioGroup.getChildAt(kbHeightSetting)).setChecked(true);

                //getDeviceSpecs();
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                //int iconHeight = (int)Math.round((metrics.heightPixels *0.9)/5);
                int iconWidth,iconHeight;

                switch(currentOrientation){

                    case ORIENTATION_PORTRAIT:
                        iconWidth = (int)Math.round((metrics.widthPixels *0.9)/5);
                        iconHeight = (int)((iconWidth / PORTRAIT_WIDTH)*PORTRAIT_HEIGHT);
                        //mLog.d("dbgm", "configchanged:width:" + iconWidth + ", height:" + iconHeight);

                        layoutradioGroup.getChildAt(0).setBackgroundResource(R.drawable.radio_size1);
                        layoutradioGroup.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(iconWidth, iconHeight));
                        layoutradioGroup.getChildAt(1).setBackgroundResource(R.drawable.radio_size2);
                        layoutradioGroup.getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(iconWidth, iconHeight));
                        layoutradioGroup.getChildAt(2).setBackgroundResource(R.drawable.radio_size3);
                        layoutradioGroup.getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(iconWidth, iconHeight));
                        layoutradioGroup.getChildAt(3).setBackgroundResource(R.drawable.radio_size4);
                        layoutradioGroup.getChildAt(3).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                        layoutradioGroup.getChildAt(4).setBackgroundResource(R.drawable.radio_size5);
                        layoutradioGroup.getChildAt(4).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                        break;
                    case ORIENTATION_LANDSCAPE:
                        iconWidth = (int)Math.round((metrics.widthPixels *0.9)/5);
                        iconHeight = (int)((iconWidth /PORTRAIT_HEIGHT)*PORTRAIT_WIDTH);
                        //mLog.d("dbgm","configchanged:width:"+iconWidth+", height:"+iconHeight);

                        layoutradioGroup.getChildAt(0).setBackgroundResource(R.drawable.radio_size1_landscape);
                        layoutradioGroup.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                        layoutradioGroup.getChildAt(1).setBackgroundResource(R.drawable.radio_size2_landscape);
                        layoutradioGroup.getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                        layoutradioGroup.getChildAt(2).setBackgroundResource(R.drawable.radio_size3_landscape);
                        layoutradioGroup.getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                        layoutradioGroup.getChildAt(3).setBackgroundResource(R.drawable.radio_size4_landscape);
                        layoutradioGroup.getChildAt(3).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                        layoutradioGroup.getChildAt(4).setBackgroundResource(R.drawable.radio_size5_landscape);
                        layoutradioGroup.getChildAt(4).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                }

                //mLog.d("dbgm", "Oncreate");
                //showKBSize(SettingsActivity.LAYOUT_HIVE, kbHeightSetting);
                break;

            case SettingsActivity.LAYOUT_DEFAULT:
            default:
                switch (size) {

                    case SettingsActivity.SCREENSIZE_ATLEAST_LARGE:
                        //sBar.setVisibility(View.GONE);
                        layoutradioGroup.setVisibility(View.GONE);
                        //labels.setVisibility(View.GONE);
                        smallRadio.setChecked(false);
                        bigRadio.setChecked(true);
                        hexRadio.setChecked(false);
                        //mLog.d("dbgm", "Oncreate");
                        //showKBSize(SettingsActivity.LAYOUT_DEFAULT, SCREENSIZE_ATLEAST_LARGE);
                        break;

                    case SettingsActivity.SCREENSIZE_SMALL_NORMAL:
                    default:
                        //sBar.setVisibility(View.GONE);
                        layoutradioGroup.setVisibility(View.GONE);
                        //labels.setVisibility(View.GONE);
                        smallRadio.setChecked(true);
                        bigRadio.setChecked(false);
                        hexRadio.setChecked(false);
                        //mLog.d("dbgm", "Oncreate");
                        //showKBSize(SettingsActivity.LAYOUT_DEFAULT, SCREENSIZE_SMALL_NORMAL);
                }

        }
        showPreview();

        rateus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent rate = new Intent(Intent.ACTION_VIEW);
                rate.setData(Uri.parse("https://play.google.com/store/apps/details?id=iit.android.swarachakraMarathi"));
                startActivity(rate);
            }
        });

        tapaatap_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent rate = new Intent(Intent.ACTION_VIEW);
                rate.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.tapaatap"));
                startActivity(rate);
            }
        });


        OnClickListener changeLanguage = new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                try {

                    if (!inEnglish) {
                        inEnglish = true;
                    } else {
                        inEnglish = false;
                    }
                    setCorrectText();

                } catch (Exception e) {

                    //mLog.d("dbgm", "Exception:"+e.getMessage());
                    e.printStackTrace();

                }
            }
        };

        OnClickListener goToFBPage = new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                try {

                    Intent rate = new Intent(Intent.ACTION_VIEW);
                    rate.setData(Uri.parse("fb://facewebmodal/f?href=swarchakra"));
                    startActivity(rate);

                } catch (Exception e) {

                    Intent rate = new Intent(Intent.ACTION_VIEW);
                    rate.setData(Uri.parse("https://www.facebook.com/swarchakra"));
                    startActivity(rate);
                    e.printStackTrace();

                }
            }
        };

        //fblikeus.setOnClickListener(goToFBPage);
        fblike.setOnClickListener(goToFBPage);

        trykeyboard.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                try {
                    //mLog.d("dbgm", "Try it");
                    //layoutradioGroup.setVisibility(View.VISIBLE);
                    Intent rate = new Intent(getApplicationContext(), iit.android.settings.PreviewActivity.class);
                    rate.putExtra("title", getStringResourceByName("back"));
                    rate.putExtra("inEnglish",inEnglish);
                    startActivity(rate);

                } catch (Exception e) {
                    //mLog.d("dbgm", "Try-it exceptioned");
                    e.printStackTrace();

                }
            }
        });

        OnCheckedChangeListener radioGroupOnCheckedChangeListener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                editor = prefs.edit();

                //hex
                String layout = getResources().getString(
                        R.string.layout);
                String screensize = getResources().getString(
                        R.string.layout_screensize);
                String kbHeight = getResources().getString(
                        R.string.layout_height);
                int kbHeightSetting = prefs.getInt(kbHeight, getRecommendedHeight());

                //Log.d("dbgm", "KB height val:" + kbHeightSetting);

                if (checkedId == R.id.bigRadioButton) {
                    //hex
                    //mLog.d("dbgm", "--BIG");
                    editor.putInt(layout, SettingsActivity.LAYOUT_DEFAULT);
                    editor.putInt(screensize, SettingsActivity.SCREENSIZE_ATLEAST_LARGE);
                    editor.commit();

                    //sBar.setVisibility(View.GONE);
                    layoutradioGroup.setVisibility(View.GONE);


                    //mLog.d("dbgm", "radiolistener");
                    //labels.setVisibility(View.GONE);
                    //showKBSize(SettingsActivity.LAYOUT_DEFAULT, SettingsActivity.SCREENSIZE_ATLEAST_LARGE);
                    showPreview();
                } else if (checkedId == R.id.smallRadioButton) {
                    //hex
                    //mLog.d("dbgm", "--SMALL");
                    editor.putInt(layout, SettingsActivity.LAYOUT_DEFAULT);
                    editor.putInt(screensize, SettingsActivity.SCREENSIZE_SMALL_NORMAL);
                    editor.commit();
                    editor.apply();

                    //sBar.setVisibility(View.GONE);

                    layoutradioGroup.setVisibility(View.GONE);

                    //labels.setVisibility(View.GONE);
                    //mLog.d("dbgm", "radiolistener");
                    //showKBSize(SettingsActivity.LAYOUT_DEFAULT, SettingsActivity.SCREENSIZE_SMALL_NORMAL);
                    showPreview();
                } else {
                    //hex
                    //mLog.d("dbgm", "--Hex");
                    editor.putInt(layout, SettingsActivity.LAYOUT_HIVE);
                    editor.putInt(screensize, SettingsActivity.SCREENSIZE_ATLEAST_LARGE);
                    editor.commit();
                    editor.apply();

                    //sBar.setVisibility(View.VISIBLE);
                    //sBar.setProgress(kbHeightSetting);

                    /*layoutradioGroup.setVisibility(View.VISIBLE);
                    ((RadioButton)layoutradioGroup.getChildAt(kbHeightSetting)).setChecked(true);
                    ScrollView scr = (ScrollView) findViewById(R.id.settings_relative);
                    scr.requestLayout();*/
                    layoutradioGroup.setVisibility(View.VISIBLE);
                    ((RadioButton)layoutradioGroup.getChildAt(kbHeightSetting)).setChecked(true);

                    //TODO: take care of the resizing issue by reloading the activity
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    //overridePendingTransition(0,0);

                    //startActivity(getIntent());
                    //labels.setVisibility(View.VISIBLE);
                    //mLog.d("dbgm", "radiolistener");
                    //showKBSize(SettingsActivity.LAYOUT_HIVE, kbHeightSetting);
                    showPreview();
                }
            }
        };

        radioGroup
                .setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);

        OnCheckedChangeListener layoutSizeChangedListener = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //editor.putInt(kbHeight, sBar.getProgress());
                //editor.putInt(kbHeight, sBar.getProgress());

                if(group.getVisibility() != View.VISIBLE) {
                    //mLog.d("dbgm", "ht options hidden");
                    return;
                }
                String kbHeight = getResources().getString(
                        R.string.layout_height);
                editor = prefs.edit();

                try {
                    //View v = (View)findViewById(checkedId);
                    RadioButton v = (RadioButton)group.findViewById(checkedId);
                    int index = group.indexOfChild(v);
                    //mLog.d("dbgm", kbHeight+":" + index);
                    editor.putInt(kbHeight, index);

                    //Log.d("dbgm", "kb ht:" + sBar.getProgress());
                    //Log.d("dbgm", "kb ht:" + index);
                    editor.commit();
                    //mLog.d("dbgm", "ht changed");
                    //showKBSize(SettingsActivity.LAYOUT_HIVE, index);
                    showPreview();
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        };

        layoutradioGroup
                .setOnCheckedChangeListener(layoutSizeChangedListener);
        langButton.setOnClickListener(changeLanguage);

       /* sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            SharedPreferences.Editor editor = prefs.edit();

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editor.putInt(kbHeight, sBar.getProgress());
                Log.d("dbgm", "kb ht:" + sBar.getProgress());
                editor.commit();
                showKBSize(SettingsActivity.LAYOUT_HIVE, sBar.getProgress());
                showPreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("tracking", "touch:");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/



        if (isDefault && isEnabled) {

            //isTablet = isTablet(this);
            isTablet = readPhoneSize(this);
            //Log.d("settings", "Defined istablet var" + isTablet);
            //previewEditText.requestFocus();
        } else {
            startMainActivity();
        }
        //getActionBar().setTitle(getStringResourceByName("title"));

        //m:comment
        //previewEditText.requestFocus();
    }

   /* public void populateLayoutHeightsArray(){

        String layout = getResources().getString(
                R.string.layout);
        String screensize = getResources().getString(
                R.string.layout_screensize);

        int currentLayout, currentLayoutSize;
        currentLayout = prefs.getInt(layout, SettingsActivity.LAYOUT_DEFAULT);
        //currentLayoutSize = prefs.getInt(screensize, SettingsActivity.SCREENSIZE_SMALL_NORMAL);

        editor.putInt(layout, SettingsActivity.LAYOUT_DEFAULT);
        //editor.putInt(screensize, SettingsActivity.SCREENSIZE_SMALL_NORMAL);
        editor.commit();
        showPreview();

        Log.d("dbgm", "Size1");


        Log.d("dbgm", "Size2");

        Log.d("dbgm", "Size3");


    }*/

    private float dpToPixels(int dpValue){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float pixels = dpValue * metrics.density;
        return pixels;
    }

    public void getDeviceSpecs(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels / metrics.xdpi;
        height = metrics.heightPixels / metrics.ydpi;
        ydpi = metrics.ydpi;
        xdpi = metrics.xdpi;
        density = metrics.density;

        //mLog.d("dbgm", "width:" + metrics.widthPixels + ", height:" + metrics.heightPixels);

    }

    public void showKBSize(int layout, int layoutHeight) {

        int tmp = 400, margin = 137;
        int backgroundResource=0;
        //String orientation="";
        int heightInPixels = (int) (height * ydpi);


        switch (layout) {

            case SettingsActivity.LAYOUT_HIVE:
                //mLog.d("dbgm", "--Hex");
                if(currentOrientation == ORIENTATION_PORTRAIT) {
                    tmp = (int) ((layoutHeights[layoutHeight] / 100) * heightInPixels);
                    margin = heightInPixels - tmp;
                    backgroundResource = R.drawable.hex_port_phone_main_default_themea;
                    //orientation = "portrait";
                }else{
                    tmp = (int) ((layoutHeightsLandscape[layoutHeight] / 100) * heightInPixels);
                    margin = heightInPixels - tmp;
                    backgroundResource = R.drawable.hex_land_phone_main_default_themea;
                    //orientation = "landscape";
                }
                break;

            case SettingsActivity.LAYOUT_DEFAULT:

                switch (layoutHeight) {

                    case SettingsActivity.SCREENSIZE_ATLEAST_LARGE:
                        //mLog.d("dbgm", "--Big");
                        if(currentOrientation == ORIENTATION_PORTRAIT) {
                            tmp = (int) ((SettingsActivity.bigLayoutHeight / 100) * heightInPixels);
                            margin = heightInPixels - tmp;
                            backgroundResource = R.drawable.rect_port_phone_main_default_themea;
                        }else{
                            tmp = (int) ((SettingsActivity.bigLayoutHeightLandscape / 100) * heightInPixels);
                            margin = heightInPixels - tmp;
                            backgroundResource = R.drawable.rect_land_phone_main_default_themea;
                        }



                        break;
                    case SettingsActivity.SCREENSIZE_SMALL_NORMAL:
                        //mLog.d("dbgm", "--Small");
                        if(currentOrientation == ORIENTATION_PORTRAIT) {
                            tmp = (int) ((SettingsActivity.smallLayoutHeight / 100) * heightInPixels);
                            margin = heightInPixels - tmp;
                            backgroundResource = R.drawable.rect_port_tablet_main_default_themea;
                        }else{
                            tmp = (int) ((SettingsActivity.smallLayoutHeightLandscape / 100) * heightInPixels);
                            margin = heightInPixels - tmp;
                            backgroundResource = R.drawable.rect_land_tablet_main_default_themea;
                        }
                        break;
                }

                //tmp = (int) layoutHeights[layoutHeight];
        }
        /*tmp = (int)(tmp/density);
        margin = (int)(margin/density);*/

        /*switch(orientation){
            case "portrait":
                layoutradioGroup.getChildAt(0).setBackgroundResource(R.drawable.radio_size1);
                layoutradioGroup.getChildAt(1).setBackgroundResource(R.drawable.radio_size2);
                layoutradioGroup.getChildAt(2).setBackgroundResource(R.drawable.radio_size3);
                layoutradioGroup.getChildAt(3).setBackgroundResource(R.drawable.radio_size4);
                layoutradioGroup.getChildAt(4).setBackgroundResource(R.drawable.radio_size5);
                break;
            case "landscape":
                layoutradioGroup.getChildAt(0).setBackgroundResource(R.drawable.radio_size1_landscape);
                layoutradioGroup.getChildAt(1).setBackgroundResource(R.drawable.radio_size2_landscape);
                layoutradioGroup.getChildAt(2).setBackgroundResource(R.drawable.radio_size3_landscape);
                layoutradioGroup.getChildAt(3).setBackgroundResource(R.drawable.radio_size4_landscape);
                layoutradioGroup.getChildAt(4).setBackgroundResource(R.drawable.radio_size5_landscape);
                break;
        }
*/

        //Log.d("dbgm", "Dimensions:" + tmp + ", margin:" + margin + ", height" + heightInPixels);
        /*showKBSize.setBackgroundResource(backgroundResource);
        showKBSize.setHeight(tmp);
        int tmp2 = (int) (width * xdpi);
        showKBSize.setWidth(tmp2);
        params.setMargins(1, margin, 1, 1);
        showKBSize.setVisibility(View.VISIBLE);

        showKBSize.setLayoutParams(params);
        showKBSize.requestLayout();
        animate(showKBSize);*/

    }
    /*private void changeLanguage(){
                if (!inEnglish) {
                    inEnglish = true;
                    *//*String languageName = getResources().getString(
                            R.string.language_name);
                    int resId = getResources().getIdentifier(
                            languageName + "_" + "menu_language", "string",
                            getPackageName());
                    String title = getResources().getString(resId);*//*
                    //item.setTitle(title);
                } else {
                    inEnglish = false;
                    //String title = getResources().getString(R.string.menu_language);
                    //item.setTitle(title);
                }
                setCorrectText();

    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //getDeviceSpecs();
        //String orientation="";
        getRecommendedHeight();
        String layout = getResources().getString(
                R.string.layout);
        String kbHeight = getResources().getString(
                R.string.layout_height);
        int layoutType = prefs.getInt(layout, SettingsActivity.LAYOUT_HIVE);
        final int kbHeightSetting = prefs.getInt(kbHeight, getRecommendedHeight());
        //mLog.d("dbgm", "Change kb preview");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //int iconHeight = (int)Math.round((metrics.heightPixels *0.9)/5);
        /*int iconWidth = (int)Math.round((metrics.widthPixels *0.9)/5);
        int iconHeight = (int)((iconWidth / PORTRAIT_WIDTH)*PORTRAIT_HEIGHT);
        Log.d("dbgm","configchanged:width:"+iconWidth+", height:"+iconHeight);*/

        int iconWidth,iconHeight;

        switch(currentOrientation){
            case ORIENTATION_PORTRAIT:
                iconWidth = (int)Math.round((metrics.widthPixels *0.9)/5);
                iconHeight = (int)((iconWidth / PORTRAIT_WIDTH)*PORTRAIT_HEIGHT);
                //mLog.d("dbgm", "configchanged:width:" + iconWidth + ", height:" + iconHeight);

                layoutradioGroup.getChildAt(0).setBackgroundResource(R.drawable.radio_size1);
                layoutradioGroup.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(iconWidth, iconHeight));
                layoutradioGroup.getChildAt(1).setBackgroundResource(R.drawable.radio_size2);
                layoutradioGroup.getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(iconWidth, iconHeight));
                layoutradioGroup.getChildAt(2).setBackgroundResource(R.drawable.radio_size3);
                layoutradioGroup.getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(iconWidth, iconHeight));
                layoutradioGroup.getChildAt(3).setBackgroundResource(R.drawable.radio_size4);
                layoutradioGroup.getChildAt(3).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                layoutradioGroup.getChildAt(4).setBackgroundResource(R.drawable.radio_size5);
                layoutradioGroup.getChildAt(4).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                break;
            case ORIENTATION_LANDSCAPE:
                iconWidth = (int)Math.round((metrics.widthPixels *0.9)/5);
                iconHeight = (int)((iconWidth /PORTRAIT_HEIGHT)*PORTRAIT_WIDTH);
                //mLog.d("dbgm","configchanged:width:"+iconWidth+", height:"+iconHeight);

                layoutradioGroup.getChildAt(0).setBackgroundResource(R.drawable.radio_size1_landscape);
                layoutradioGroup.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                layoutradioGroup.getChildAt(1).setBackgroundResource(R.drawable.radio_size2_landscape);
                layoutradioGroup.getChildAt(1).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                layoutradioGroup.getChildAt(2).setBackgroundResource(R.drawable.radio_size3_landscape);
                layoutradioGroup.getChildAt(2).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                layoutradioGroup.getChildAt(3).setBackgroundResource(R.drawable.radio_size4_landscape);
                layoutradioGroup.getChildAt(3).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
                layoutradioGroup.getChildAt(4).setBackgroundResource(R.drawable.radio_size5_landscape);
                layoutradioGroup.getChildAt(4).setLayoutParams(new LinearLayout.LayoutParams(iconWidth,iconHeight));
        }
        //showKBSize(layoutType, kbHeightSetting);
    }

    public int getRecommendedHeight() {

        //get the current device dimensions
        //get the ht of each layout on current device.
        //float layoutheights = {}; //populate this runtime
        int recoLayout = 0; //Tallest

        /*DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //Log.d("dbgm", "Width:" + metrics.widthPixels / metrics.xdpi);
        //Log.d("dbgm","Height:"+metrics.heightPixels/metrics.ydpi);

        width = metrics.widthPixels / metrics.xdpi;
        height = metrics.heightPixels / metrics.ydpi;
        ydpi = metrics.ydpi;
        xdpi = metrics.xdpi;
        density = metrics.density;*/

        getDeviceSpecs();

        double heights[] = new double[layoutHeights.length];

        if (width > height) {
            //mLog.d("dbgm", "Landscape mode");
            currentOrientation = ORIENTATION_LANDSCAPE;

        } else {
            //mLog.d("dbgm", "Portrait mode");
            currentOrientation = ORIENTATION_PORTRAIT;
        }

        if (height < HEIGHT_THRESHOLD_LOWER) {
            //mLog.d("dbgm", "Enforce min available height");

            for (int i = 0; i < layoutHeights.length; i++) {

                //Log.d("dbgm", "Layout height on the device:" + (height - ((layoutHeights[i] / 100) * height)));
                heights[i] = height - ((layoutHeights[i] / 100) * height);

            }
            recoLayout = getClosestK(heights, MIN_AVAILABLE_HEIGHT);
            //mLog.d("dbgm", "Recommended height: " + recoLayout);
        }
        /*else if(height > HEIGHT_THRESHOLD_UPPER) {

            Log.d("dbgm", "Enforce max KB height");

            for(int i=0;i<layoutHeights.length;i++){

                Log.d("dbgm", "Layout height on the device:" + ((layoutHeights[i] / 100) * height));
                heights[i]=(layoutHeights[i] / 100) * height;

            }
            recoLayout =  getClosestK(heights,);
            Log.d("dbgm","Recommended height: "+recoLayout);

        }*/
        else {

            //mLog.d("dbgm", "Enforce max KB height");

            for (int i = 0; i < layoutHeights.length; i++) {

                //Log.d("dbgm", "Layout height on the device:" + ((layoutHeights[i] / 100) * height));
                heights[i] = (layoutHeights[i] / 100) * height;

            }
            recoLayout = getClosestK(heights, RECO_HEIGHT);
            //mLog.d("dbgm", "Recommended height: " + recoLayout);

        }


        /*for(int i=0;i<layoutHeights.length;i++){

            Log.d("dbgm","Layout height on the device:"+((layoutHeights[i]/100)*height));
        }*/

        //return (HEX_LAYOUT_COUNT-1-recoLayout);
        return recoLayout;
    }

    public int getClosestK(double[] a, double x) {

        int low = 0;
        int high = a.length - 1;

        if (high < 0)
            throw new IllegalArgumentException("The array cannot be empty");

        while (low < high) {

            //Log.d("dbgm", "Low:" + low + "," + a[low] + ", high:" + high + "," + a[high]);

            int mid = (low + high) / 2;
            //Log.d("dbgm", "Mid:" + mid + "," + a[mid]);

            assert (mid < high);
            double d1 = Math.abs(a[mid] - x);
            double d2 = Math.abs(a[mid + 1] - x);
            if (d2 <= d1) {
                low = mid + 1;
            } else {
                high = mid;
            }

        }
        //return a[high];
        return high;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_language:
                if (!inEnglish) {
                    inEnglish = true;
                    String languageName = getResources().getString(
                            R.string.language_name);
                    int resId = getResources().getIdentifier(
                            languageName + "_" + "menu_language", "string",
                            getPackageName());
                    String title = getResources().getString(resId);
                    item.setTitle(title);
                } else {
                    inEnglish = false;
                    String title = getResources().getString(R.string.menu_language);
                    item.setTitle(title);
                }
                setCorrectText();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        String languageName = getResources().getString(R.string.language_name);
        int resId = getResources().getIdentifier(languageName + "_" + aString,
                "string", packageName);
        if (inEnglish)
            resId = 0;
        if (resId == 0) {
            resId = getResources()
                    .getIdentifier(aString, "string", packageName);
        }
        return getString(resId);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    //hex
    /*public static boolean isTablet(Context context) {
		Log.d("track","istablet()");
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}*/

    @Override
    public void onResume() {
        super.onResume();
        checkKeyboardStatus();
        if (isDefault && isEnabled) {

        } else {
            startMainActivity();
        }
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("inEnglish", inEnglish);
        startActivity(intent);
    }

    public void setCorrectText() {

        RadioButton smallRadio = (RadioButton) layout
                .findViewById(R.id.smallRadioButton);
        RadioButton bigRadio = (RadioButton) layout
                .findViewById(R.id.bigRadioButton);
        RadioButton hexradio = (RadioButton) layout
                .findViewById(R.id.hexRadioButton);

        FloatingActionButton langButton = (FloatingActionButton) layout
                .findViewById(R.id.toggleLanguage_settings);
        Button tryit = (Button) layout
                .findViewById(R.id.tryit);
        Button fbLikeUs = (Button) layout
                .findViewById(R.id.fb_likeus_tv2);

        String smallRadioText = getStringResourceByName("settings_layout_small");
        String bigRadioText = getStringResourceByName("settings_layout_big");
        String hexRadioText = getStringResourceByName("settings_layout_hex");

        String languageButtonText ="";//= getStringResourceByName("menu_language");
        String fbButtonText = getStringResourceByName("fb_likeus");
        String tryItButtonText = getStringResourceByName("tryit");

        smallRadio.setText(smallRadioText);
        bigRadio.setText(bigRadioText);
        hexradio.setText(hexRadioText);

        String instruction = getStringResourceByName("settings_instruction");
        instructionTextView.setText(instruction);

        String doyoulikeustext = getStringResourceByName("do_you_like");
        String rateustext = getStringResourceByName("rate_us");
        String tapaatap_down = getStringResourceByName("tapaatap_download");
        doYouLikeTextView.setText(doyoulikeustext);
        rateus.setText(rateustext);
        tapaatap_btn.setText(tapaatap_down);

        if(inEnglish) {
            /*//m:
            String languageName = getResources().getString(
                    R.string.language_name);
            int resId = getResources().getIdentifier(
                    languageName + "_" + "menu_language", "string",
                    getPackageName());
            languageButtonText= getResources().getString(resId);*/
            langButton.setImageResource(R.drawable.marathi);
            //langButton.setBackgroundColor(Color.YELLOW);
            //langButton.setBackgroundResource(R.drawable.fab_orange);
        }else{
             /*//m:
             int resId = getResources().getIdentifier(
                    "menu_language", "string",
                    getPackageName());
            languageButtonText= getResources().getString(resId);*/
            langButton.setImageResource(R.drawable.english);
            //langButton.setBackgroundColor(Color.GREEN);
            //langButton.setBackgroundResource(R.drawable.images);
        }
        //m:langButton.setText(languageButtonText);
        tryit.setText(tryItButtonText);
        fbLikeUs.setText(fbButtonText);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mLog.d("dbgm", "Pause");
        //overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
        overridePendingTransition(0,0);
    }

    public void checkKeyboardStatus() {
        //Log.d("track", "chkkbstatus()");
        InputMethodManager mgr = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> lim = mgr.getEnabledInputMethodList();
        isEnabled = false;
        isDefault = false;

        for (InputMethodInfo im : lim) {
            if (im.getPackageName().equals(getPackageName())) {
                isEnabled = true;
                final String currentImeId = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.DEFAULT_INPUT_METHOD);

                if (im != null && im.getId().equals(currentImeId)) {
                    isDefault = true;
                }
            }
        }
    }

    private void showPreview() {
        //mLog.d("track", "showpreview()");
        /*previewEditText.requestFocus();
        previewEditText.setText(null);

        InputMethodManager imm = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(previewEditText, 0);*/
    }

    public void animate(View view) {

        //Fade out
        Animation mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_anim);

        view.startAnimation(mAnimation);

        //Collapse animation
        /*final int initialHeight = view.getMeasuredHeight();
        final View finalV = view;
        finalV.setVisibility(View.VISIBLE);
        Animation mAnimation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    Log.d("dbgm", "If:interpolated");
                    finalV.setVisibility(View.GONE);
                }else{
                    Log.d("dbgm", "else:interpolated");
                    finalV.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    finalV.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        mAnimation.setDuration((int)(initialHeight / view.getContext().getResources().getDisplayMetrics().density));
        mAnimation.setStartOffset(2000);
        //mAnimation.setDuration(5000);
        finalV.startAnimation(mAnimation);*/


        //Fade out anim
        //Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_anim);
        // Now Set your animation
        //view.startAnimation(mAnimation);

        //Zoom out anim
        /*Animation mAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, -0.25f);
        mAnimation.setDuration(1500);
        //mAnimation.setRepeatCount(-1);
        mAnimation.setStartOffset(2000);
        //mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new AccelerateInterpolator());
        mAnimation.setFillEnabled(true);
        mAnimation.setFillAfter(true);*/

        mAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //view.setVisibility(View.GONE);
                //mLog.d("dbgm", "In Animation end");
                //animation.cancel();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //mLog.d("dbgm", "In Animation repeat.");


                //animation.cancel();

            }
        });
        //view.setAnimation(mAnimation);

    }

}
