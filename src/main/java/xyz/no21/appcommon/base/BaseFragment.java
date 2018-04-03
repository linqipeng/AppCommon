package xyz.no21.appcommon.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


/**
 * Created by lin on 2017/6/29.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public class BaseFragment extends Fragment {

    protected BaseActivity context;
    protected String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void startActivity(Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }
}
