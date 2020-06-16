package dhu.cst.zhangcheng171010220.dhutraveller;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.lbsapi.panoramaview.*;
import com.baidu.lbsapi.BMapManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FullViewActivity extends AppCompatActivity {
    DhuBuilding building;
    PanoramaView fullView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        final Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        this.building = (DhuBuilding) bundle.getSerializable("building");
        TextView textViewHead = findViewById(R.id.activity_full_view_head_textView);
        textViewHead.setText(building.name);
        View viewBack = findViewById(R.id.activity_full_view_head_back);
        viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initFullView();
    }

    private void initFullView() {
        FullViewApplication app = (FullViewApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);

            app.mBMapManager.init(new FullViewApplication.MyGeneralListener());
        }
        fullView = findViewById(R.id.fullView);
        fullView.setPanorama(building.fullViewLocation);
        fullView.setShowTopoLink(false);
        fullView. setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionMiddle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fullView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullView.onResume();
    }

    @Override
    protected void onDestroy() {
        fullView.destroy();
        super.onDestroy();
    }
}