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

    protected Context mContext;
    protected String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        RetrofitUtils.remove(TAG);
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