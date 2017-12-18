package xyz.no21.appcommon;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by cookie on 2017/11/7.
 * Email:l437943145@gmail.com
 * Desc
 */

public class SpUtils {

    private static final String SP_DEFAULT = "sp_default";
    private static final String TOKEN = "token";
    private static final String USER_INFO = "userInfo";
    private static SharedPreferences preferences;

    private static Object userInfo;
    private static String token;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(SP_DEFAULT, Context.MODE_PRIVATE);
    }


    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = preferences.getString(TOKEN, null);
        }
        return token;
    }

    public static void setToken(String token) {
        preferences.edit().putString(TOKEN, token).apply();
        SpUtils.token = token;
    }

    public static <T> T getUserInfo(Class<T> type) {
        if (userInfo == null) {
            String string = preferences.getString(USER_INFO, null);
            userInfo = new Gson().fromJson(string, type);
        }
        //noinspection unchecked
        return (T) userInfo;
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken()) && getUserInfo(Object.class) != null;
    }

    public static void setUserInfo(Object userInfo) {
        SpUtils.userInfo = userInfo;
        preferences.edit().putString(USER_INFO, new Gson().toJson(userInfo)).apply();
    }

    public static void clearData() {
        preferences.edit().clear().apply();
    }
}
