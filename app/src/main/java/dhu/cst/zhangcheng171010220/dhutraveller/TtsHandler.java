package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

public class TtsHandler {
    private Activity activity;
    private SpeechSynthesizer speechSynthesizer;
    public TtsHandler(Activity activity) {
        this.activity = activity;
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(activity, new InitListener() {
            @Override
            public void onInit(int i) {
                Log.d("__d", "tts on init");
            }
        });
        setIatParams();
    }

    private void setIatParams() {

    }

    public void stopSpeaking() {
        if (speechSynthesizer.isSpeaking())
            speechSynthesizer.stopSpeaking();
    }

    public boolean speak(String text) {
        if (speechSynthesizer.isSpeaking()) {
            speechSynthesizer.stopSpeaking();
        }
        if (!getPermission()) {
            Toast.makeText(activity, "获取权限失败", Toast.LENGTH_SHORT).show();
            return false;
        }
        speechSynthesizer.startSpeaking(text, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() { }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) { }

            @Override
            public void onSpeakPaused() { }

            @Override
            public void onSpeakResumed() { }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) { }

            @Override
            public void onCompleted(SpeechError speechError) { }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) { }
        });
        return false;
    }


    // 动态获取权限
    private boolean getPermission() {
        String[] permissions = new String[] {
                "android.permission.RECORD_AUDIO",
                "android.permission.INTERNET",
                "android.permission.ACCESS_WIFI_STATE",
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
