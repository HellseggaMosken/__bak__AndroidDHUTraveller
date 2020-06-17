package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

public class IatHandler {
    IatAction iatAction;
    SpeechRecognizer mIat; // 语音识别模型
    StringBuffer buffer; // 识别结果
    Activity activity;

    public IatHandler(Activity activity) {
        this.activity = activity;
        buffer = new StringBuffer();
        // 初始化监听器
        InitListener mInitListener = new InitListener() {
            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    showTip("语音系统初始化错误，请稍后再试");
                    Log.d("__xf", "初始化失败，错误码：" + code);
                }
            }
        };
        mIat = SpeechRecognizer.createRecognizer(activity, mInitListener);
        setIatParams();
    }

    private void showTip(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public void setIatAction(IatAction iatAction) {
        this.iatAction = iatAction;
        if (iatAction.blockTimeAfterSpeak() != null) {
            mIat.setParameter(SpeechConstant.VAD_EOS, iatAction.blockTimeAfterSpeak());
        } else {
            mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        }
    }

    public boolean startListen() {
        if (!getPermission()) {
            showTip("获取权限失败");
            return false;
        }
        buffer.setLength(0);
        mIat.startListening(mIatListener);
        return true;
    }

    // 设置语音识别对话框参数
    private void setIatParams() {
        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null );
        mIat.setParameter(SpeechConstant.SUBJECT, null );

        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "plain");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "5000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT,"0");
    }

    // 识别监听器
    private RecognizerListener mIatListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) { }
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) { }

        @Override
        public void onBeginOfSpeech() {
            if (iatAction != null) iatAction.onStart();
        }

        @Override
        public void onEndOfSpeech() {
            if (iatAction != null) iatAction.onFinish();
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            buffer.append(recognizerResult.getResultString());
            if (iatAction != null) iatAction.onResult(buffer);
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.d("__xf", "识别失败：" + speechError.toString());
            if (iatAction != null) iatAction.onFinish();
        }
    };

    // 动态获取权限
    private boolean getPermission() {
        String[] permissions = new String[] {
                "android.permission.RECORD_AUDIO",
                "android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
        };
        for (String p : permissions) {
            int status = activity.checkSelfPermission(p);
            if (status != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(permissions, 1);
                break;
            }
        }
        for (String permission : permissions) {
            int status = activity.checkSelfPermission(permission);
            if (status != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
