package xyz.no21.appcommon;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

/**
 * Created by lin on 2017/8/10.
 * Email: L427942145@gmail.com
 * desc:
 */

public class LanguraUtils {

    private static LanguraUtils languraUtils;
    private Locale locale;

    private LanguraUtils(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
    }

    public static void init(Context context) {
        languraUtils = new LanguraUtils(context);
    }

    public static LanguraUtils get() {
        return languraUtils;
    }

    public String formatLocalString(Context context, int formatId, Object... data) {
        return String.format(locale, context.getResources().getString(formatId), data);
    }
}
