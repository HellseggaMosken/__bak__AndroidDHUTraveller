package dhu.cst.zhangcheng171010220.dhutraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity {

    DhuView dhuView;
    SearchBar searchBar;
    NavBar navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建语音配置对象
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5e023538");
        IatHandler iatHandler = new IatHandler(MainActivity.this);

        final SubsamplingScaleImageView imageView = findViewById(R.id.dhu_view);

        dhuView = new DhuView(this, imageView,
                (ViewGroup) findViewById(R.id.dhu_dialog),
                findViewById(R.id.dhu_dialog_background));

        searchBar = new SearchBar(MainActivity.this,
                dhuView, (ViewGroup) findViewById(R.id.search_bar), iatHandler);

        navBar = new NavBar(MainActivity.this,
                (ViewGroup) findViewById(R.id.nav_bar), dhuView, searchBar, iatHandler);
    }
}