package de.ostfalia.amexer;

/**
 * Created by Lina on 01.05.2016.
 */
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
public class Gestures implements View.OnTouchListener {
    private GestureDetector gestureDetector = null;
    private Context cxt = null;

    public Gestures(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        cxt = context;
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean res = gestureDetector.onTouchEvent(event);
        return res;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            result = onSwipeRight();
                        } else {
                            result = onSwipeLeft();
                        }
                    }else{
                        result = nichts();
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            result = onSwipeBottom();
                        } else {
                            result = onSwipeTop();
                        }
                    }else{
                        result = nichts();
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    /**
     * Swipe to right
     * @return
     */
    public boolean onSwipeRight() {
        return false;
    }

    /**
     * Swipe to left
     * @return
     */
    public boolean onSwipeLeft() {
        return false;
    }

    /**
     * No swipe
     * @return
     */
    public boolean nichts(){
        return false;
    }

    /**
     * Swipe to top
     * @return
     */
    public boolean onSwipeTop() {
        return false;
    }

    /**
     * Swipe to bottom
     * @return
     */
    public boolean onSwipeBottom() {

        return false;
    }
}