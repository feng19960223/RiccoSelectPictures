package lib.ricco.photo.crop.gestures;

import android.view.MotionEvent;

public interface GestureDetector {
    boolean onTouchEvent(MotionEvent ev);

    boolean isScaling();

    void setOnGestureListener(OnGestureListener listener);
}
