package xyz.no21.appcommon;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by keep on 2017/9/12.
 * Email: L437943145@gmail.com
 * desc:
 */

public class RetrofitUtils {

    static {
        requsets = new HashMap<>();
        referenceQueue = new ReferenceQueue<>();
    }

    private static HashMap<Object, ArrayList<WeakReference<Call>>> requsets;
    private static ReferenceQueue<Call> referenceQueue;


    public static void Request(Call<ResponseBody> call, Object tag, Callback<ResponseBody> Callback) {
        addRequset(tag, call);
        call.enqueue(Callback);
    }

    private static void addRequset(Object key, Call call) {

        ArrayList<WeakReference<Call>> references = requsets.get(key);
        if (references == null) {
            references = new ArrayList<>();
            requsets.put(key, references);
        }
        references.add(new WeakReference<>(call, referenceQueue));
    }

    public static void remove(Object key) {
        ArrayList<WeakReference<Call>> references = requsets.get(key);
        if (references != null) {
            for (WeakReference<Call> item : references) {
                Call call = item.get();
                if (call != null) {
                    call.cancel();
                }
            }
        }
        Reference<? extends Call> reference;
        while ((reference = referenceQueue.poll()) != null) {
            reference.clear();
        }
    }
}
