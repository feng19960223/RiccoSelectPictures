package lib.ricco.photo.util;

import android.view.View;

/**
 * <p>时间：2019/8/5<p>
 * <p>作者：冯国芮<p>
 * <p>描述：防止双击<p>
 */
public class ClickUtils {
    private static final long DEBOUNCING_DEFAULT_DURATION = 300;

    private static final int TAG_KEY = 0xFFFFFFFF;

    public static boolean doubleClick(View view) {
        if (view == null) return true;
        long curTime = System.currentTimeMillis();
        Object tag = view.getTag(TAG_KEY);
        if (tag instanceof Long) {
            if (curTime - (Long) tag <= DEBOUNCING_DEFAULT_DURATION) return true;
        }
        view.setTag(TAG_KEY, curTime);
        return false;
    }
}
