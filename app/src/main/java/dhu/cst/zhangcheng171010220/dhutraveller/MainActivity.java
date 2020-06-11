package dhu.cst.zhangcheng171010220.dhutraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DhuView dhuView = new DhuView(this, (SubsamplingScaleImageView) findViewById(R.id.dhu_v));

        // 创建语音配置对象
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5e023538");

        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editTextTextPersonName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IatHandler iatHandler = new IatHandler(MainActivity.this, editText);
                iatHandler.startListen();
            }
        });

    }
}