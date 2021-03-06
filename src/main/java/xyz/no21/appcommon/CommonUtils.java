package xyz.no21.appcommon;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lin on 2017/4/29.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class CommonUtils {
    public static final String FORMAT_MM_DD_HH_MM_SS = "MM月dd日 HH:mm:ss";

    public static final String FORMAT_STYLE_1 = "yyyy_MM_dd HH:mm:ss";
    public static final String FORMAT_STYLE_2 = "HH:mm";
    public static final String FORMAT_STYLE_3 = "yyyy-MM-dd";
    public static final String FORMAT_DEFAULT = "yyyy年MM月dd日";
    private static final File PHOTO_SAVE_DIR = new File(Environment.getExternalStorageDirectory(), "fastsale");

    public static File getPhotoSaveDir(String fileName) {
        return new File(PHOTO_SAVE_DIR, fileName);
    }

    public static String formatDate(long date) {
        return formatDate(date, FORMAT_DEFAULT);
    }

    public static String formatDate(long date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(date));
    }

    public static boolean dateEqualsDay(long date, long date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        calendar.get(Calendar.DAY_OF_YEAR);
        return false;
    }

    public static long getLongDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static int calculateInSampleSize(int imageWidth, int imageHeight, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int inSampleSize = 1;

        if (imageHeight > reqHeight || imageWidth > reqWidth) {

            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        if (inSampleSize > 8)
            inSampleSize = 8;//fresco最大支持到8
        return inSampleSize;
    }

//    public static void screenshot(Context context, View view, File output) {
//        new Thread(() -> {
//            Bitmap bitmap = null;
//            FileOutputStream outputStream = null;
//            try {
//                if (!output.exists() && output.createNewFile() || output.exists()) {
//                    bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(bitmap);
//                    view.draw(canvas);
//                    outputStream = new FileOutputStream(output);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    scanFile(context, output);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (bitmap != null) {
//                    bitmap.recycle();
//                }
//                closeOutputStrem(outputStream);
//            }
//        }).start();
//    }

    public static void scanFile(Context context, File file) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }


    public static void closeOutputStrem(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean equals(Object o1, Object o2) { // NOPMD
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    public static
    @NonNull
    String md5(@NonNull String data) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(data.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            StringBuilder md5StrBuff = new StringBuilder();

            for (byte item : byteArray) {
                if (Integer.toHexString(0xFF & item).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & item));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & item));
                }
            }
            return md5StrBuff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void hideInputMethod(View view) {
        InputMethodManager methodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);

    }

    public static void call(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static void displayImage(final ImageView imageView, String path, int width, int height) {
        imageView.setImageResource(R.color.white);
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(path))
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(false)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();

        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(request, imageView);

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                 @Override
                                 public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                     if (bitmap != null && bitmap.isRecycled())
                                         return;
                                     imageView.setImageBitmap(bitmap);
                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                 }
                             },
                UiThreadImmediateExecutorService.getInstance());
    }

    public static String Bitmap2StrByBase64(String localPath) {

        return Base64.encodeToString(getBytes(localPath), Base64.NO_WRAP);
    }

    public static byte[] Bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long downLoadApk(Context context, String title, String path) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(path));
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "版本更新");
        req.setTitle(title);
        req.setDescription("下载完后请点击打开");
        req.setMimeType("application/vnd.android.package-archive");
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (dm != null) {
            return dm.enqueue(req);
        }
        return -1;
    }

    /**
     * 连接字符串
     *
     * @param segmentation 分隔符
     */
    public static String stringAdd(List<String> input, String segmentation) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        for (String item : input) {
            buffer.append(item);
            buffer.append(segmentation);
        }
        buffer.delete(buffer.length() - segmentation.length(), buffer.length());
        return buffer.toString();
    }

    public static void destroyWebView(WebView webView) {
        webView.setWebViewClient(null);
        webView.setWebChromeClient(null);
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webView.clearHistory();
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
    }

    public static long getCacheSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getCacheSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                return file.length();
            }
        } else {
            return 0;
        }

    }

    public static void DeleteFolder(File file) {

        if (file == null || !file.exists())
            return;

        if (file.isDirectory()) {
            for (File item : file.listFiles()) {
                DeleteFolder(item);
            }
            file.delete();
        } else {
            file.delete();
        }
    }

    public static boolean joinQqGroup(Context context, String key) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    /**
     * 高精度相乘
     */
    public static double mul(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 相加
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 减法
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 除法
     *
     * @param scale 四舍五入 小数点位数
     */
    public static double div(double d1, double d2, int scale) {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理

        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isWifi(Context context) {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = null;
        if (manager != null) {
            networkInfo = manager.getActiveNetworkInfo();
        }
        //判断NetworkInfo对象是否为空 并且类型是否为WIFI
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isAvailable();

    }

    public static int getDistance(float x1, float y1, float x2, float y2) {
        return (int) Math.sqrt(Math.pow((double) (x1 - x2), 2.0D) + Math.pow((double) (y1 - y2), 2.0D));
    }

    /**
     * 弧度
     *
     * @return
     */
    public static float getRadian(PointF A, PointF B) {
        float lenA = B.x - A.x;
        float lenB = B.y - A.y;
        float lenC = (float) Math.sqrt((double) (lenA * lenA + lenB * lenB));
        float radian = (float) Math.acos((double) (lenA / lenC));
        radian *= (float) (B.y < A.y ? -1 : 1);
        return radian;
    }

    /**
     * 手机号号段校验，
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8}任意数字；
     * 第3—11位：0—9任意数字
     */
    public static boolean isMobileNum(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /**
     * 验证座机号码
     */
    public static boolean isFixedPhone(String fixedPhone) {
        String reg = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }

    public static byte[] getBytes(String filePath) {
        File file = new File(filePath);
        ByteArrayOutputStream out = null;
        try {
            FileInputStream in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                out.write(b, 0, b.length);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (out != null) {
            return out.toByteArray();
        }
        return null;
    }

    public static String runShellCommand(String apkPath) {


        Process exec;
        try {
            exec = Runtime.getRuntime().exec("pm install  -i  com.hhtc.appstore  --user 0 " + apkPath);

            InputStream stream = exec.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            StringBuilder resulttt = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                resulttt.append(line);
            }

            Log.e("ff", "runShellCommand: " + resulttt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
