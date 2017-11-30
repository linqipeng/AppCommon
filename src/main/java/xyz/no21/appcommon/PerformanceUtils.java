package xyz.no21.appcommon;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by lin on 19/07/17.
 * Email: L427942145@gmail.com
 * desc:性能优化相关工具类
 */

public class PerformanceUtils {

    public static void fixInputMethodManagerLeak(Context context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field field;
        Object object;
        try {
            for (String param : arr) {
                field = imm.getClass().getDeclaredField(param);
                field.setAccessible(true);
                object = field.get(imm);
                if (object != null) {
                    field.set(imm, null);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
