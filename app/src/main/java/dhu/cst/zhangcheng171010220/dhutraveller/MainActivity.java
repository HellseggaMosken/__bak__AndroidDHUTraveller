package dhu.cst.zhangcheng171010220.dhutraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {

    DhuView dhuView;
    DhuBuilding[] buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SubsamplingScaleImageView imageView = findViewById(R.id.dhu_v);

        dhuView = new DhuView(this, imageView,
                (ViewGroup) findViewById(R.id.dhu_dialog),
                findViewById(R.id.dhu_dialog_background));
        buildings = dhuView.getBuildings();

        // 创建语音配置对象
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5e023538");

        final Button button = findViewById(R.id.button);
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