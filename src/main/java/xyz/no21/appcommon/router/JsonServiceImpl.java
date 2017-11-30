//package xyz.no21.appcommon.router;
//
//import android.content.Context;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.alibaba.android.arouter.facade.service.SerializationService;
//import com.google.gson.Gson;
//
///**
// * Created by lin on 2017/8/3.
// * Email: L427942145@gmail.com
// * desc:
// */
//@Route(path = "/app/CanvasActivity")
//public class JsonServiceImpl implements SerializationService {
//
//    private Gson gson;
//
//    @Override
//    public <T> T json2Object(String json, Class<T> clazz) {
//        return gson.fromJson(json, clazz);
//    }
//
//    @Override
//    public String object2Json(Object instance) {
//        return gson.toJson(instance);
//    }
//
//    @Override
//    public void init(Context context) {
//        gson = new Gson();
//    }
//}
