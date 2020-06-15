package dhu.cst.zhangcheng171010220.dhutraveller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class DhuDialog {
    private ViewGroup layout;
    private DhuBuilding building;
    private TextView textViewTitle;
    private TextView textViewDetails;
    private ImageView imageViewCover;
    private View backgroundView;

    private Animation animationShow;
    private Animation animationClose;
    private Animation animationBgNormal;
    private Animation animationBgDark;


    public DhuDialog(Context context, ViewGroup layout, View backgroundView) {
        this.layout = layout;
        this.textViewDetails = layout.findViewById(R.id.dhu_dialog_details);
        this.textViewTitle = layout.findViewById(R.id.dhu_dialog_title);
        this.imageViewCover = layout.findViewById(R.id.dhu_dialog_cover);
        this.backgroundView = backgroundView;

        this.animationShow = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_in);
        this.animationClose = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_out);
        this.animationBgNormal = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_bg_normal);
        this.animationBgDark = AnimationUtils.loadAnimation(context, R.anim.dhu_dialog_bg_dark);

        layout.setOnClickListener(null); // 覆盖其他的点击事件

        ((View) layout.findViewById(R.id.dhu_dialog_toGallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToGallery();
            }
        });
        ((View) layout.findViewById(R.id.dhu_dialog_toFullView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToFullView();
            }
        });
    }

    private boolean onClickToGallery() {
        if (building == null) return false;
        //close();
        return true;
    }

    private boolean onClickToFullView() {
        if (building == null) return false;
        //close();
        return true;
    }

    public boolean isShowing() {
        return layout.getVisibility() == View.VISIBLE;
    }

    public void show() {
        if (building == null) return;
        layout.startAnimation(animationShow);
        layout.setVisibility(View.VISIBLE);
        backgroundView.startAnimation(animationBgDark);
        backgroundView.setVisibility(View.VISIBLE);
    }

    public void close() {
        layout.startAnimation(animationClose);
        layout.setVisibility(View.INVISIBLE);
        backgroundView.startAnimation(animationBgNormal);
        backgroundView.setVisibility(View.INVISIBLE);
    }

    public void reset(DhuBuilding building) {
        if (this.isShowing()) {
            this.close();
        }
        this.building = building;
        imageViewCover.setImageResource(building.pictures[0]);
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
