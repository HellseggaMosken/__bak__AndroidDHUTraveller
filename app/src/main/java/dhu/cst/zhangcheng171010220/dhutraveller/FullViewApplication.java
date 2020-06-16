package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;

public class FullViewApplication extends Application {
    private static FullViewApplication mInstance = null;
    public BMapManager mBMapManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initEngineManager(this);
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
            Log.d("__d", "FullViewApplication initEngineManager  初始化错误");
        }
    }

    public static FullViewApplication getInstance() {
        return mInstance;
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                Log.d("__d", "FullViewApplication 授权Key错误");
            }
        }
    }
}
