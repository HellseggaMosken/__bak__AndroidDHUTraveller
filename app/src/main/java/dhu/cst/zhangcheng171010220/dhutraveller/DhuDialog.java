package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DhuDialog {
    private Context context;
    private ViewGroup layout;
    private DhuBuilding building;
    private TextView textViewTitle;
    private TextView textViewDetails;
    private ImageView imageViewCover;
    private View backgroundView;

    private View viewToGallery;
    private View viewToFullView;

    private Animation animationShow;
    private Animation animationClose;
    private Animation animationBgNormal;
    private Animation animationBgDark;

    private NavBar navBar;

    private TtsHandler ttsHandler;

    public DhuDialog(Context context, ViewGroup layout, View backgroundView, final TtsHandler ttsHandler) {
        this.context = context;
        this.layout = layout;
        this.textViewDetails = layout.findViewById(R.id.dhu_dialog_details);
        this.textViewTitle = layout.findViewById(R.id.dhu_dialog_title);
        this.imageViewCover = layout.findViewById(R.id.dhu_dialog_cover);
        this.backgroundView = backgroundView;
        this.viewToGallery = layout.findViewById(R.id.dhu_dialog_toGallery);
        this.viewToFullView = layout.findViewById(R.id.dhu_dialog_toFullView);
        this.ttsHandler = ttsHandler;

        this.animationShow = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_in);
        this.animationClose = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_out);
        this.animationBgNormal = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_bg_normal);
        this.animationBgDark = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_bg_dark);

        layout.setOnClickListener(null); // 覆盖其他的点击事件

        viewToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToGallery();
            }
        });
        viewToFullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToFullView();
            }
        });
        ((View) layout.findViewById(R.id.dhu_dialog_speak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakDetails();
            }
        });
    }

    public boolean speakDetails() {
        if (isShowing()) {
            ttsHandler.speak(building.msg);
            return true;
        }
        return false;
    }

    private boolean onClickToGallery() {
        if (building == null) return false;
        if (building.pictures == null || building.pictures.length == 0) {
            Toast.makeText(context, "该地区暂无图片!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Intent intent = new Intent(context, GalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("building", building);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, 0);
        return true;
    }

    private boolean onClickToFullView() {
        if (building == null) return false;
        if (building.fullViewLocation == null) {
            Toast.makeText(context, "该地区暂无全景图!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!getPermission()) {
            Toast.makeText(context, "权限获取失败，请允许权限再试！", Toast.LENGTH_SHORT).show();
            return false;
        }

        Intent intent = new Intent(context, FullViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("building", building);
        intent.putExtras(bundle);
        ((Activity) context).startActivityForResult(intent, 0);
        return true;
    }


    // 动态获取权限
    private boolean getPermission() {
        String[] permissions = new String[] {
                "android.permission.INTERNET",
                "android.permission.ACCESS_WIFI_STATE",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
        };
        for (String p : permissions) {
            int status = ((Activity) context).checkSelfPermission(p);
            if (status != PackageManager.PERMISSION_GRANTED) {
                ((Activity) context).requestPermissions(permissions, 1);
                break;
            }
        }
        for (String permission : permissions) {
            int status = ((Activity) context).checkSelfPermission(permission);
            if (status != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void setNavBar(NavBar navBar) {
        this.navBar = navBar;
    }

    public boolean isShowing() {
        return layout.getVisibility() == View.VISIBLE;
    }

    public DhuBuilding getShowingBuilding() {
        if (isShowing())
            return building;
        return null;
    }

    public void show() {
        if (building == null) return;
        layout.startAnimation(animationShow);
        layout.setVisibility(View.VISIBLE);
        backgroundView.startAnimation(animationBgDark);
        backgroundView.setVisibility(View.VISIBLE);
        if (navBar != null) {
            navBar.resetLast();
        }
    }

    public void close() {
        layout.startAnimation(animationClose);
        layout.setVisibility(View.INVISIBLE);
        backgroundView.startAnimation(animationBgNormal);
        backgroundView.setVisibility(View.INVISIBLE);
        if (navBar != null) {
            navBar.disableLast();
            navBar.resetNext();
        }
    }

    public void reset(DhuBuilding building) {
        if (this.isShowing()) {
            this.close();
        }
        this.building = building;
        if (building == null) return;
        if (building.fullViewLocation == null) {
            this.viewToFullView.setAlpha(0.3f);
        } else {
            this.viewToFullView.setAlpha(1);
        }
        if (building.pictures == null || building.pictures.length == 0) {
            this.viewToGallery.setAlpha(0.3f);
            imageViewCover.setImageResource(R.drawable.dhu_full_real);
        } else {
            this.viewToGallery.setAlpha(1);
            imageViewCover.setImageResource(building.pictures[0]);
        }
        textViewTitle.setText(building.name);
        textViewDetails.setText(building.msg);

    }

    public boolean showGallery() {
        if (this.isShowing()) {
            return onClickToGallery();
        }
        return false;
    }

    public boolean showFullView() {
        if (this.isShowing()) {
            return onClickToFullView();
        }
        return false;
    }

}
