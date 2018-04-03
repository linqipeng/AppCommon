package xyz.no21.appcommon.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lin on 2017/7/8.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class BaseActivity extends AppCompatActivity {

    protected BaseActivity context;


    protected String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(context, activity));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
