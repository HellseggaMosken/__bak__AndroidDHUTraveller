package dhu.cst.zhangcheng171010220.dhutraveller;

import android.content.Context;

public class DhuBuildingHelper {
    Context context;

    private String resToStr(int resId) {
        return context.getResources().getString(resId);
    }

    public DhuBuildingHelper(Context context) {
        this.context = context;
    }


    public DhuBuilding[] create() {
        return new DhuBuilding[] {
                new DhuBuilding(316, 1045, 581, 1174, 2.0f,
                        resToStr(R.string.dhu_department_1), resToStr(R.string.dhu_department_1_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(321, 863, 550, 939, 1.8f,
                        resToStr(R.string.dhu_department_2), resToStr(R.string.dhu_department_2_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(393, 686, 582, 770, 2.0f,
                        resToStr(R.string.dhu_department_3), resToStr(R.string.dhu_department_3_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(318, 459, 593, 572, 1.8f,
                        resToStr(R.string.dhu_department_4), resToStr(R.string.dhu_department_4_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1366, 563, 1517, 662, 2.5f,
                        resToStr(R.string.dhu_department_5), resToStr(R.string.dhu_department_5_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(326, 299, 560, 380, 1.8f,
                        resToStr(R.string.dhu_train_center), resToStr(R.string.dhu_train_center_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(784, 585, 1049, 684, 2.0f,
                        resToStr(R.string.dhu_admin), resToStr(R.string.dhu_admin_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(957, 1349, 1238, 1412, 1.6f,
                        resToStr(R.string.dhu_dining_1), resToStr(R.string.dhu_dining_1_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1188, 587, 1270, 687, 2.6f,
                        resToStr(R.string.dhu_dining_2), resToStr(R.string.dhu_dining_2_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1616, 504, 1861, 668, 1.7f,
                        resToStr(R.string.dhu_normal_lib), resToStr(R.string.dhu_normal_lib_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1680, 316, 1847, 410, 2.6f,
                        resToStr(R.string.dhu_innovation_building), resToStr(R.string.dhu_innovation_building_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1742, 1089, 2144, 1234, 1.8f,
                        resToStr(R.string.dhu_teach_building_1), resToStr(R.string.dhu_teach_building_1_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1612, 1282, 2115, 1454, 1.3f,
                        resToStr(R.string.dhu_teach_building_2), resToStr(R.string.dhu_teach_building_2_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(557, 1421, 849, 1589, 1.9f,
                        resToStr(R.string.dhu_inner_stadium), resToStr(R.string.dhu_inner_stadium_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(605, 1901, 890, 2240, 1.8f,
                        resToStr(R.string.dhu_out_stadium_1), resToStr(R.string.dhu_out_stadium_1_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(2167, 1593, 2366, 1888, 1.8f,
                        resToStr(R.string.dhu_out_stadium_2), resToStr(R.string.dhu_out_stadium_2_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1071, 1915, 1883, 2232, 1.1f,
                        resToStr(R.string.dhu_dorm), resToStr(R.string.dhu_dorm_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(953, 1451, 1219, 1557, 2.0f,
                        resToStr(R.string.dhu_activity_center), resToStr(R.string.dhu_activity_center_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(839, 841, 1078, 940, 1.9f,
                        resToStr(R.string.dhu_library), resToStr(R.string.dhu_library_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(316, 1256, 546, 1331, 2.0f,
                        resToStr(R.string.dhu_teacher_house), resToStr(R.string.dhu_teacher_house_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1234, 967, 1502, 1218, 1.0f,
                        resToStr(R.string.dhu_lake), resToStr(R.string.dhu_lake_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1335, 1404, 1362, 1461, 2.6f,
                        resToStr(R.string.dhu_bridge_j), resToStr(R.string.dhu_bridge_j_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1123, 716, 1249, 793, 1.5f,
                        resToStr(R.string.dhu_bridge_dh), resToStr(R.string.dhu_bridge_dh_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(910, 1656, 951, 1744, 2.2f,
                        resToStr(R.string.dhu_bridge_lz), resToStr(R.string.dhu_bridge_lz_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1083, 371, 1147, 400, 2.4f,
                        resToStr(R.string.dhu_bridge_y), resToStr(R.string.dhu_bridge_y_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(625, 150, 704, 268, 2.3f,
                        resToStr(R.string.dhu_gate_north), resToStr(R.string.dhu_gate_north_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(2334, 686, 2503, 790, 2.2f,
                        resToStr(R.string.dhu_gate_east), resToStr(R.string.dhu_gate_east_msg), R.drawable.dhu_full_abs),
                new DhuBuilding(1318, 723, 2333, 787, 1.2f,
                        resToStr(R.string.dhu_road_dh), resToStr(R.string.dhu_road_dh_msg), R.drawable.dhu_full_abs),
        };
    }
}
