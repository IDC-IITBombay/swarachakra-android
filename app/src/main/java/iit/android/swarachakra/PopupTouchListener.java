package iit.android.swarachakra;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PopupTouchListener implements OnTouchListener {
	SwaraChakra mSwaraChakra;
	private static int MOVE_THRESHOLD = 0;
	
	@Override
	public boolean onTouch(View view, MotionEvent me) {
		mSwaraChakra = (SwaraChakra) view.findViewById(R.id.swarachakra);
		
		int centerX = (mSwaraChakra.getLeft() + mSwaraChakra.getRight())/2;
		int centerY = (mSwaraChakra.getTop() + mSwaraChakra.getBottom())/2;
		
		int action = me.getAction();
		
		if(action == MotionEvent.ACTION_MOVE){
			int touchMovementX = (int)me.getX() - centerX;
			int touchMovementY = (int)me.getY() - centerY;
			
			int radius = (int) Math.sqrt((touchMovementX*touchMovementX) + (touchMovementY*touchMovementY));
			int theta = (int)Math.toDegrees(Math.atan2(touchMovementY, touchMovementX));
			if(radius>MOVE_THRESHOLD){
				int arc = findArc(theta);
				mSwaraChakra.setArc(arc);
			}
			else{
				mSwaraChakra.desetArc();
			}
			
			return true;
		}
		//Log.d("chakra", "touch action = " + action);
		
		
		return false;
	}
	
	private int findArc(int theta) {
		int nArcs = SwaraChakra.getNArcs();
		int relAngle = theta;
		if(theta<0){
			relAngle = 360 + theta;
		}
		int region = (int)(relAngle*nArcs/360);
		return region;
	}

}
