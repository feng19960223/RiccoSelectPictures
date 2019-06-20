package lib.ricco.photo.crop.gestures;

import android.content.Context;

public final class VersionedGestureDetector {
    public static GestureDetector newInstance(Context context, OnGestureListener listener) {
        GestureDetector detector = new FroyoGestureDetector(context);
        detector.setOnGestureListener(listener);
        return detector;
    }
}