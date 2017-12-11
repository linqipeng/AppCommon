package xyz.no21.appcommon;

import android.content.Context;
import android.content.SharedPreferences;

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


    public static void init(Context context) {
        preferences = context.getSharedPreferences(SP_DEFAULT, Context.MODE_PRIVATE);
    }


    public static String getToken() {
        return preferences.getString(TOKEN, null);
    }

    public static void setToken(String token) {
        preferences.edit().putString(TOKEN, token).apply();
    }

    public static <T> T getUserInfo(Class<T> type) {
        String string = preferences.getString(USER_INFO, null);
        return new Gson().fromJson(string, type);

    }


    public static void setUserInfo(Object userInfo) {
        preferences.edit().putString(USER_INFO, new Gson().toJson(userInfo)).apply();
    }

    public static void clearData() {
        preferences.edit().clear().apply();
    }
}
