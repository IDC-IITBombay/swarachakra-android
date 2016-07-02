package iit.android.settings;

import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class GIFView extends View{

    Movie movie;
    long moviestart;

    WindowManager wm;
    Display display;
    Point size = new Point();
    int screenwidth;
    int screenheight;
    int gifheight = 858;
    int gifwidth = 482;
    float scaleFactor = (float) 0.7;
    float scaleWidth;
    float scaleHeight;
    
    public GIFView(Context context) throws IOException { 
        super(context);
    }
    public GIFView(Context context, AttributeSet attrs) throws IOException{
        super(context, attrs);
    }
    public GIFView(Context context, AttributeSet attrs, int defStyle) throws IOException {
        super(context, attrs, defStyle);
    }

    @SuppressLint("NewApi") public void loadGIFResource(Context context, int id, int containHeight, int containWidth)
    {
    	wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	display = wm.getDefaultDisplay();
    	display.getSize(size);
    	screenwidth = size.x;
    	screenheight = size.y;
        scaleWidth = (float) ((scaleFactor*screenwidth / (1f*gifwidth)));
        scaleHeight = (float) ((scaleFactor*screenheight / (1f*gifheight)));
    	
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        InputStream is=context.getResources().openRawResource(id);
        movie = Movie.decodeStream(is);
    }

    public void loadGIFAsset(Context context, String filename)
    {
        InputStream is;
        try {
            is = context.getResources().getAssets().open(filename);
            movie = Movie.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getMarginLeft() {
    	int margin = (int) (screenwidth * (1-scaleFactor) * 0.5);
    	return margin;
    }
    
    public int getMarginTop() {
    	int margin = (int) (screenheight * 0.02);
    	return margin;
    }
    
    public int getMarginBottom() {
    	int margin = screenheight - getMeasuredHeight() - getMarginTop();
    	return margin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (movie == null) {
            return;
        }

        long now=android.os.SystemClock.uptimeMillis();

        if (moviestart == 0) moviestart = now;

        int relTime;
        relTime = (int)((now - moviestart) % movie.duration());
        movie.setTime(relTime);
        canvas.scale(scaleWidth, scaleHeight);
        movie.draw(canvas, 0, 0);
        this.invalidate();
    }
}