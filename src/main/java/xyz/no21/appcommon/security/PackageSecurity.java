package xyz.no21.appcommon.security;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lin on 2017/8/7.
 * Email: L427942145@gmail.com
 * desc:
 */

public class PackageSecurity {

    // 白名单列表
    private static List<String> safePackages;

    static {
        safePackages = new ArrayList<>();
    }

    /**
     * 检测当前Activity是否安全
     */
    public static boolean checkActivity(Context context) {
        boolean safe = false;
        PackageManager pm = context.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序

        for (ApplicationInfo app : listAppcations) {//这个排序必须有.
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                safePackages.add(app.packageName);
            }
        }
        //得到所有的系统程序包名放进白名单里面.
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivityPackageName;

        if (Build.VERSION.SDK_INT >= 21) {//获取系统api版本号,如果是5x系统就用这个方法获取当前运行的包名
            runningActivityPackageName = getTopActivityPkgName(context);
        } else {
            //如果是4x及以下,用这个方法.
            runningActivityPackageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        if (runningActivityPackageName != null) {//有些情况下在5x的手机中可能获取不到当前运行的包名，所以要非空判断。
            if (runningActivityPackageName.equals(context.getPackageName())) {
                safe = true;
            }
            // 白名单比对
            for (String safePack : safePackages) {
                if (safePack.equals(runningActivityPackageName)) {
                    return true;
                }
            }
        }
        return safe;
    }

    /**
     * 5x系统以后利用反射获取当前栈顶activity的包名.
     */
    public static String getTopActivityPkgName(Context context) {

        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field;
        int START_TASK_TO_FRONT = 2;
        String pkgName = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");//通过反射获取进程状态字段.

            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo app : appList) {

                if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {//表示前台运行进程.
                    Integer state;
                    state = field.getInt(app);//反射调用字段值的方法,获取该进程的状态.

                    if (state == START_TASK_TO_FRONT) {//根据这个判断条件从前台中获取当前切换的进程对象.
                        currentInfo = app;
                        break;
                    }
                }
            }
            if (currentInfo != null) {
                pkgName = currentInfo.processName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pkgName;
    }

}
