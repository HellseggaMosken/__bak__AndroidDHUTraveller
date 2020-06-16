package dhu.cst.zhangcheng171010220.dhutraveller;

import android.graphics.PointF;

import java.io.Serializable;

public class DhuBuilding implements Serializable {
    float leftTopX;
    float leftTopY;
    float rightBottomX;
    float rightBottomY;
    float scale;
    String name;
    String msg;
    int[] pictures;
    String fullViewLocation;

    public DhuBuilding(float leftTopX, float leftTopY, float rightBottomX, float rightBottomY,
                       float mapScale, String name, String msg, String fullViewLocation, int ...pictureResId) {
        this.leftTopX = leftTopX;
        this.leftTopY = leftTopY;
        this.rightBottomX = rightBottomX;
        this.rightBottomY = rightBottomY;
        this.scale = mapScale;
        this.name = name;
        this.msg = msg;
        this.fullViewLocation = fullViewLocation;
        this.pictures = pictureResId;
    }

    public boolean isInBuilding(PointF pointF) {
        return pointF.x > leftTopX && pointF.x < rightBottomX
                && pointF.y > leftTopY && pointF.y < rightBottomY;
    }

    public PointF getCenter() {
        return new PointF((leftTopX + rightBottomX) / 2, (leftTopY + rightBottomY) / 2);
    }
}
