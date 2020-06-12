package dhu.cst.zhangcheng171010220.dhutraveller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class DhuView {
    Context context;
    SubsamplingScaleImageView imageView;
    DhuBuilding[] buildings;

    @SuppressLint("ClickableViewAccessibility")
    public DhuView(Context context, SubsamplingScaleImageView imageView) {
        this.context = context;
        this.imageView = imageView;
        imageView.setImage(ImageSource.resource(R.drawable.dhu_full_abs));
        this.buildings = new DhuBuildingHelper(context).create();
        final GestureDetector gestureDetector = getGestureDetector();
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }


    public boolean moveToBuilding(DhuBuilding b) {
        SubsamplingScaleImageView.AnimationBuilder animationBuilder
                = imageView.animateScaleAndCenter(b.scale, b.getCenter());
        if (animationBuilder == null) return false;
        animationBuilder
                .withDuration(500)
                .withEasing(SubsamplingScaleImageView.EASE_IN_OUT_QUAD)
                .withInterruptible(false)
                .start();
        return true;
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

                //Toast.makeText(context, ""+pointF.x + ";" + pointF.y+"  "+imageView.getScale(), Toast.LENGTH_SHORT).show();

                for (DhuBuilding b : buildings) {
                    if (b.isInBuilding(pointF)) {
                        Toast.makeText(context, b.name + "  "+imageView.getScale(), Toast.LENGTH_SHORT).show();
                        return moveToBuilding(b);
                    }
                }
                return false;
            }
        });
    }

}
