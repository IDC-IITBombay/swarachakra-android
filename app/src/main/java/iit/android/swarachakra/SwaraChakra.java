package iit.android.swarachakra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import iit.android.settings.SettingsActivity;
import iit.android.settings.UserSettings;

public class SwaraChakra extends View {
	private float mOuterRadius;
	private float mInnerRadius;
	private float mArcTextRadius;
	private float centerX, centerY;
	private Paint mBlackPaint;
	private Paint mTransparentPaint;//changes
	private Paint mWhitePaint;
	private Paint mInnerPaint;
	private Paint mInnerTextPaint;
	private Paint mArcPaint;
	private Paint mArcDividerPaint;
	private Paint mArcPrevPaint;
	private Paint mArcTextPaint;
	private float screen_width;
	private float screen_height;
	private RectF bound,boundOut;//changes
	private static String[] defaultChakra;
	private static int nArcs;
	private int arc;
	private static boolean halantExists;
	private KeyAttr currentKey;
	private String keyLabel;
	private static final int greenLight=Color.rgb(76,128,114);;
	//private static final int greenDark;
	private static final int green = Color.rgb(54,89,80);;
	private static final int greyLight =Color.rgb(108, 108, 108);
	//private static final int greyDark;
	private static final int grey = Color.rgb(48, 48, 48);
    private Paint arcPaint;
    private Paint transparentPaint;
    private float anglePerArc;
	//private float font_size;
    private Rect textBounds;
	public SwaraChakra(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	public SwaraChakra(Context context, AttributeSet attrs){
		super(context,attrs);
		init(context);
	}
	

	@SuppressLint("NewApi")
	private void init(Context context){

        //Log.d("jank","init() of Swarachakra");
		String layoutName = getResources().getString(R.string.layout);
		//SharedPreferences prefs = UserSettings.getPrefs();
		SharedPreferences prefs;//UserSettings.getPrefs();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);

		int layout = prefs.getInt(layoutName, SettingsActivity.LAYOUT_DEFAULT);

		mTransparentPaint = new Paint();//changes
		mTransparentPaint.setColor(Color.TRANSPARENT);//changes
		mTransparentPaint.setAntiAlias(true);//changes

		mBlackPaint = new Paint();
		mBlackPaint.setColor(Color.BLACK);
		mBlackPaint.setAntiAlias(true);
		mWhitePaint = new Paint();
		mWhitePaint.setColor(Color.WHITE);
		mWhitePaint.setAntiAlias(true);
		mInnerPaint = new Paint();
		mInnerPaint.setColor(Color.rgb(255, 255, 255));
		mInnerPaint.setAntiAlias(true);
		mInnerTextPaint = new Paint();
		mInnerTextPaint.setColor(Color.BLACK);
		mInnerTextPaint.setAntiAlias(true);
		mInnerTextPaint.setTextAlign(Align.CENTER);
		mArcPaint = new Paint();
		//mArcPaint.setColor(Color.rgb(61, 102, 91));
		mArcPaint.setColor(Color.rgb(54, 89, 80));//changes
		mArcPaint.setAntiAlias(true);
		mArcPaint.setStyle(Paint.Style.FILL);
		mArcDividerPaint = new Paint();
		mArcDividerPaint.setColor(Color.rgb(200, 200, 200));
		mArcDividerPaint.setAntiAlias(true);
		mArcPrevPaint = new Paint();
		mArcPrevPaint.setColor(Color.rgb(76, 128, 114));//changes
		mArcPrevPaint.setAntiAlias(true);
		mArcTextPaint = new Paint();
		mArcTextPaint.setColor(Color.rgb(255, 255, 255));
		mArcTextPaint.setAntiAlias(true);
		mArcTextPaint.setTextAlign(Align.CENTER);


		mArcPaint = new Paint();
		mArcPrevPaint = new Paint();


		switch(layout) {

			case SettingsActivity.LAYOUT_HIVE:
				mArcPaint.setColor(green);
				mArcPrevPaint.setColor(greenLight);

				break;
			case SettingsActivity.LAYOUT_DEFAULT:
				mArcPaint.setColor(grey);
				mArcPrevPaint.setColor(greyLight);
				break;
			/*default:
				mArcPaint.setColor(grey);
				mArcPrevPaint.setColor(greyLight);*/
		}
		mArcPaint.setAntiAlias(true);
		mArcPrevPaint.setAntiAlias(true);

		setLayerType(View.LAYER_TYPE_NONE, null);
		arc = -1;
		
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

		//font_size = displayMetrics.scaledDensity; // use this as fontsize multiplier.
        screen_width = displayMetrics.widthPixels;
        screen_height = displayMetrics.heightPixels;

        //Moved from ondraw
        transparentPaint = new Paint();
        transparentPaint.setColor(Color.TRANSPARENT);
        transparentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        textBounds = new Rect();
	}
	
	
	public void setMetrics(int mode){
		switch(mode){
		case 0:
                //mOuterRadius = (float) (0.25 * Math.min(screen_width, screen_height));
                mOuterRadius = (float) (0.35 * Math.min(screen_width, screen_height));
                //Log.d("radius", "0.25");
			break;
		case 1:
			mOuterRadius = (float) (0.25*Math.min(screen_width, screen_height));
                //Log.d("radius", "0.17");
		default:
			
			break;
		}
       /* mInnerRadius = (float) (0.3 * mOuterRadius);
		mArcTextPaint.setTextSize((float) 0.20*mOuterRadius);
		mInnerTextPaint.setTextSize((float) 0.25*mOuterRadius);
        mArcTextRadius = (float) (0.55 * mOuterRadius);*/

        mInnerRadius = (float) (0.18 * mOuterRadius);
        mArcTextPaint.setTextSize((float) 0.15 * mOuterRadius);
        mInnerTextPaint.setTextSize((float) 0.20 * mOuterRadius);
        mArcTextRadius = (float) (0.35 * mOuterRadius);

		bound = new RectF(mOuterRadius,mOuterRadius,3*mOuterRadius, 3*mOuterRadius);
		boundOut = new RectF(mOuterRadius-3,mOuterRadius-3,(3*mOuterRadius)+3, (3*mOuterRadius)+3);
		centerX = bound.centerX();
		centerY = bound.centerY();
	}
	
	public float getOuterRadius(){
		return mOuterRadius;
	}
	
	public float getInnerRadius(){
		return mInnerRadius;
	}
	
	public float getScreenHeight(){
		return screen_height;
	}
	
	public static void setDefaultChakra(String[] swaras){
		defaultChakra = swaras;
		nArcs = defaultChakra.length;
	}
	
	public static int getNArcs(){
		return nArcs;
	}
	
	public void setArc(int region){

        //Log.d("jank","setarc()");
		if(region != arc){
			arc = region;
            //Log.d("jank","setarc(): invalidate()");
			invalidate();
		}
	}
	
	public void setCurrentKey(KeyAttr currentKey){
		this.currentKey = currentKey;
	}
	
	public void setKeyLabel(String label){
		keyLabel = label;
	}
	
	public void desetArc(){
		arc = -1;
		invalidate();
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		//canvas.drawCircle(centerX, centerY, mOuterRadius, mArcDividerPaint);

        //Log.d("jank","Ondraw()");

		//get layout to determine the chakra drawing to use
		String layoutName = getResources().getString(R.string.layout);
        SharedPreferences prefs;//UserSettings.getPrefs();
        prefs = PreferenceManager.getDefaultSharedPreferences(SoftKeyboard.appContext());
		int layout = prefs.getInt(layoutName, SettingsActivity.LAYOUT_HIVE);

		switch(layout) {

			case SettingsActivity.LAYOUT_HIVE:
				mArcPaint.setColor(green);
				mArcPrevPaint.setColor(greenLight);

				break;
			case SettingsActivity.LAYOUT_DEFAULT:
				mArcPaint.setColor(grey);
				mArcPrevPaint.setColor(greyLight);
				break;
			default:
				mArcPaint.setColor(grey);
				mArcPrevPaint.setColor(greyLight);
		}

		switch(layout){

			case SettingsActivity.LAYOUT_HIVE:
				anglePerArc = (float) (360.0/nArcs);

				canvas.drawCircle(centerX, centerY, mOuterRadius, mArcDividerPaint);

				canvas.drawCircle(centerX, centerY, mOuterRadius, mTransparentPaint);
				for(int i =0; i< nArcs; i++){
					arcPaint = mArcPaint;
					if(i == arc){arcPaint = mArcPrevPaint;}
					//arcs
					//canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc-1, true, arcPaint);
					canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc-1, true, arcPaint);
					canvas.drawArc(boundOut, getMidAngle(i) - anglePerArc/2, anglePerArc, false, transparentPaint);
					//canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc, false, transparentPaint);

					//arc seperators
					//canvas.drawArc(bound, getMidAngle(i) + anglePerArc/2 -1, 1, true, mArcDividerPaint);
				}
				/*canvas.drawCircle(centerX, centerY, mOuterRadius, mTransparentPaint);
				for(int i =0; i< nArcs; i++){
					arcPaint = mArcPaint;
					if(i == arc){arcPaint = mArcPrevPaint;}
					//arcs
					//canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc-1, true, arcPaint);
					//canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc, true, arcPaint);
					canvas.drawArc(boundOut, getMidAngle(i) - anglePerArc/2, anglePerArc, false, transparentPaint);

					//arc seperators
					//canvas.drawArc(bound, getMidAngle(i) + anglePerArc/2 -1, 1, true, mArcDividerPaint);
				}*/

				canvas.drawCircle(centerX, centerY, mInnerRadius, mInnerPaint);

				drawLetters(canvas);

				break;
			case SettingsActivity.LAYOUT_DEFAULT:
			default:
		canvas.drawCircle(centerX, centerY, mOuterRadius, mBlackPaint);

				anglePerArc = (float) (360.0/nArcs);
				/*Paint arcPaint;
		Paint transparentPaint = new Paint();
		transparentPaint.setColor(Color.TRANSPARENT);
		transparentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));*/

				for(int i =0; i< nArcs; i++){
					arcPaint = mArcPaint;
					if(i == arc){arcPaint = mArcPrevPaint;}
					//arcs
					canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc-1, true, arcPaint);

					//arc seperators
					canvas.drawArc(bound, getMidAngle(i) + anglePerArc/2 -1, 1, true, mArcDividerPaint);
				}

				canvas.drawCircle(centerX, centerY, mInnerRadius, mInnerPaint);

				drawLetters(canvas);

		}
		/*canvas.drawCircle(centerX, centerY, mOuterRadius, mBlackPaint);

		float anglePerArc = (float) (360.0/nArcs);
		Paint arcPaint;
		for(int i =0; i< nArcs; i++){
			arcPaint = mArcPaint;
			if(i == arc){arcPaint = mArcPrevPaint;}
			//arcs
			canvas.drawArc(bound, getMidAngle(i) - anglePerArc/2, anglePerArc-1, true, arcPaint);
			
			//arc seperators
			canvas.drawArc(bound, getMidAngle(i) + anglePerArc/2 -1, 1, true, mArcDividerPaint);
		}
		
		canvas.drawCircle(centerX, centerY, mInnerRadius, mInnerPaint);
		
		drawLetters(canvas);*/

	}

	private void drawLetters(Canvas canvas) {
		float offsetY = 0;
		//textBounds = new Rect();
		mInnerTextPaint.getTextBounds(getText(), 0, getText().length(), textBounds);
		offsetY = (textBounds.bottom - textBounds.top)/2;

		//mInnerPaint.setTextSize(font_size*50);
		canvas.drawText(getText(), centerX, centerY + offsetY, mInnerTextPaint);

        float offsetX = 0;

		for(int i = 0; i<nArcs; i++){
            //PointF textPos = getArcTextPoint(i);
            PointF textPos = new PointF();
            //Rect textBounds = new Rect();
            String text = getTextForArc(i);
            mArcTextPaint.getTextBounds(text, 0, text.length(), textBounds);

            offsetY = (textBounds.bottom - textBounds.top)/2;
            float angleRad = (float) Math.toRadians(getMidAngle(i));
            textPos.x = centerX + (float) (mArcTextRadius*Math.cos(angleRad)) + offsetX;
            textPos.y = centerY + (float) (mArcTextRadius*Math.sin(angleRad)) + offsetY;

			//mInnerPaint.setTextSize(font_size*50);
            canvas.drawText(getTextForArc(i), textPos.x, textPos.y, mArcTextPaint);
		}
		
	}

	@Override
	protected void onMeasure(int measuredWidth, int measuredHeight){
		setMeasuredDimension((int)(4*mOuterRadius),(int)(4*mOuterRadius));
	}
	
	public static void setHalantExists(boolean b){
		halantExists = b;
	}

	public boolean isHalant(){
		boolean thisIsHalant = !currentKey.showCustomChakra;
		return (arc == 0) && halantExists && thisIsHalant;
	}
	
	public float getMidAngle(int region){
		float anglePerArc = (float) (360.0/nArcs);
		float offset = -90;
		float midAngle = region*anglePerArc + offset;
		return midAngle;
	}
	
	private PointF getArcTextPoint(int region){
		PointF textPos = new PointF();
		Rect textBounds = new Rect();
		String text = getTextForArc(region);
        mArcTextPaint.getTextBounds(text, 0, text.length(), textBounds);
		float offsetX = 0;
		float offsetY = 0;
		offsetY = (textBounds.bottom - textBounds.top)/2;
		float angleRad = (float) Math.toRadians(getMidAngle(region));
		textPos.x = centerX + (float) (mArcTextRadius*Math.cos(angleRad)) + offsetX;
		textPos.y = centerY + (float) (mArcTextRadius*Math.sin(angleRad)) + offsetY;
		return textPos;
	}
	
	public String getText() {
		if(arc < 0){
			return keyLabel;
		}
		return getTextForArc(arc);
	}
	
	public String getTextForArc(int region){
		String[] chakra = defaultChakra;
		if(currentKey.showCustomChakra){
			chakra = currentKey.customChakraLayout;
			return chakra[region];
		}
		if(chakra[region]!="") {
			String text = keyLabel + chakra[region];
			return text;
		}else{
			String text = chakra[region];
			return text;
		}

	}
	
}
