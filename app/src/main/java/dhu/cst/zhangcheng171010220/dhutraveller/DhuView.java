package dhu.cst.zhangcheng171010220.dhutraveller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class DhuView {
    private Context context;
    private SubsamplingScaleImageView imageView;
    private DhuBuilding[] buildings;
    private DhuDialog dialog;
    private int style;

    @SuppressLint("ClickableViewAccessibility")
    public DhuView(Context context, SubsamplingScaleImageView imageView,
                   ViewGroup dhuDialogLayout, View dhuDialogBg, TtsHandler ttsHandler) {
        this.context = context;
        this.imageView = imageView;
        this.dialog = new DhuDialog(context, dhuDialogLayout, dhuDialogBg, ttsHandler);
        this.style = -1;
        this.changeStyle(0);
        this.buildings = new DhuBuildingHelper(context).create();
        final GestureDetector gestureDetector = getGestureDetector();
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dialog.isShowing()) {
                    dialog.close();
                    return true;
                }
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public DhuBuilding[] getBuildings() { return buildings; }

    public DhuBuilding getShowingBuilding() {
        return dialog.getShowingBuilding();
    }

    public boolean speakDetails() {
        return dialog.speakDetails();
    }

    public boolean moveToBuilding(String name) {
        for (DhuBuilding b : buildings) {
            if (b.name.equals(name)) {
                moveToBuilding(b);
                return true;
            }
        }
        return false;
    }

    public void moveToBuilding(DhuBuilding b) {
        SubsamplingScaleImageView.AnimationBuilder animationBuilder
                = imageView.animateScaleAndCenter(b.scale, b.getCenter());
        if (animationBuilder == null) {
            Log.d("__b", "DhuView moveToBuilding() animationBuilder nullptr");
            return;
        }
        animationBuilder
                .withDuration(500)
                .withEasing(SubsamplingScaleImageView.EASE_IN_OUT_QUAD)
                .withInterruptible(false)
                .start();
    }

    public void setNavBar(NavBar navBar) {
        this.dialog.setNavBar(navBar);
    }

    public void showDialog(DhuBuilding b) {
        dialog.reset(b);
        dialog.show();
    }

    public void closeDialog() {
        if (dialog.isShowing())
            dialog.close();
    }

    public void changeStyle(int s) {
        if (s == style) return;
        if (s >=0 && s <= 2) style = s;
        else return;
        if (s == 0) imageView.setImage(ImageSource.resource(R.drawable.dhu_full_abs));
        else if (s == 1) imageView.setImage(ImageSource.resource(R.drawable.dhu_full_real));
        else imageView.setImage(ImageSource.resource(R.drawable.dhu_full_std));
    }

    public DhuBuilding fuzzySearch(String str) {
        if (str.contains("教学楼")) {
            if (str.contains("1") || str.contains("一")) return accurateSearch("第1教学楼");
            if (str.contains("2") || str.contains("二")) return accurateSearch("第2教学楼");
        } else if (str.contains("学院楼")) {
            if (str.contains("1") || str.contains("一")) return accurateSearch("1号学院楼");
            if (str.contains("2") || str.contains("二")) return accurateSearch("2号学院楼");
            if (str.contains("3") || str.contains("三")) return accurateSearch("3号学院楼");
            if (str.contains("4") || str.contains("四")) return accurateSearch("4号学院楼");
            if (str.contains("5") || str.contains("五")) return accurateSearch("5号学院楼");
        } else if (str.contains("食堂")) {
            if (str.contains("1") || str.contains("一")) return accurateSearch("1食堂");
            if (str.contains("2") || str.contains("二")) return accurateSearch("2食堂");
        } else if (str.contains("实验楼")) {
            return accurateSearch("综合实验楼");
        } else if (str.contains("宿舍")) {
            return accurateSearch("宿舍区");
        } else if (str.contains("东门")) {
            return accurateSearch("东大门");
        }else if (str.contains("北门")) {
            return accurateSearch("北大门");
        }
        return null;
    }

    public DhuBuilding accurateSearch(String str) {
        for (DhuBuilding b: buildings) {
            if (b.name.equals(str)) {
                return b;
            }
        }
        return null;
    }

    public boolean showGallery() {
        return dialog.showGallery();
    }

    public boolean showFullView() {
        return dialog.showFullView();
    }

    public boolean isShowingBuilding() {
        return dialog.isShowing();
    }


    private GestureDetector getGestureDetector() {
        return new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (!imageView.isReady()) return true;

                // 获取触摸点相对于屏幕的坐标
                float x = e.getX();
                float y = e.getY();

                // 屏幕的坐标转化为相对于图片的坐标
                PointF pointF = imageView.viewToSourceCoord(x, y);
                if (pointF == null) return false;

                for (DhuBuilding b : buildings) {
                    if (b.isInBuilding(pointF)) {
                        moveToBuilding(b);
                        showDialog(b);
                        return true;
                    }
                }
                return false;
            }
        });
    }

}
