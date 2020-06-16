package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Objects;

public class NavBar {
    Context context;
    DhuView dhuView;
    View navIconSettings;
    View navIconLast;
    View navIconNext;
    View navIconHelp;
    IatHandler iatHandler;
    public NavBar(Context context, ViewGroup navBarLayout, DhuView dhuView, IatHandler iatHandler) {
        this.context = context;
        this.iatHandler = iatHandler;
        this.dhuView = dhuView;
        this.navIconSettings = navBarLayout.findViewById(R.id.nav_bar_settings);
        this.navIconLast = navBarLayout.findViewById(R.id.nav_bar_last);
        this.navIconNext = navBarLayout.findViewById(R.id.nav_bar_next);
        this.navIconHelp = navBarLayout.findViewById(R.id.nav_bar_help);
        navBarLayout.setOnClickListener(null); // 覆盖其他的点击事件
        initSettings();
        initLastAndNext();
        initHelp();
    }

    public void resetLast() {
        navIconLast.setClickable(true);
        navIconLast.setAlpha(1);
    }

    public void resetNext() {
        navIconNext.setClickable(true);
        navIconNext.setAlpha(1);
    }

    public void disableLast() {
        navIconLast.setClickable(false);
        navIconLast.setAlpha(0.3f);
    }

    public void disableNext() {
        navIconNext.setClickable(false);
        navIconNext.setAlpha(0.3f);
    }

    private void initSettings() {
        View view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.nav_bar_settings_dialog, null);
        final RadioButton rStd = view.findViewById(R.id.radio_style_std);
        final RadioButton rReal = view.findViewById(R.id.radio_style_real);
        final RadioButton rAbs = view.findViewById(R.id.radio_style_abs);

        rStd.setChecked(true);

        rStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhuView.changeStyle(0);
                rStd.setChecked(true);
                rReal.setChecked(false);
                rAbs.setChecked(false);
            }
        });

        rReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhuView.changeStyle(1);
                rReal.setChecked(true);
                rStd.setChecked(false);
                rAbs.setChecked(false);
            }
        });

        rAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhuView.changeStyle(2);
                rAbs.setChecked(true);
                rReal.setChecked(false);
                rStd.setChecked(false);
            }
        });

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setWindowAnimations(R.style.DialogPop);
        navIconSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dhuView.closeDialog();
                    dialog.show();
                }
            }
        });
    }

    private int findNextBuildingIndex(DhuBuilding b) {
        if (b == null) return 0;
        int nowIndex = -1;
        for (int i = 0; i < dhuView.getBuildings().length; i++) {
            if (dhuView.getBuildings()[i] == b) {
                nowIndex = i;
                break;
            }
        }
        if (nowIndex == dhuView.getBuildings().length - 1) {
            return 0;
        }
        return nowIndex + 1;
    }

    private int findLastBuildingIndex(DhuBuilding b) {
        if (b == null) return 0;
        int nowIndex = -1;
        for (int i = 0; i < dhuView.getBuildings().length; i++) {
            if (dhuView.getBuildings()[i] == b) {
                nowIndex = i;
                break;
            }
        }
        return nowIndex - 1;
    }

    private void initLastAndNext() {
        dhuView.setNavBar(this);
        navIconNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DhuBuilding b = dhuView.getShowingBuilding();
                int nextIndex = findNextBuildingIndex(b);
                b = dhuView.getBuildings()[nextIndex];
                dhuView.moveToBuilding(b);
                dhuView.showDialog(b);
                if (nextIndex == dhuView.getBuildings().length - 1) {
                    disableNext();
                    Toast.makeText(context, "游览结束！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        navIconLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DhuBuilding b = dhuView.getShowingBuilding();
                int lastIndex = findLastBuildingIndex(b);
                if (lastIndex < 0) lastIndex = dhuView.getBuildings().length - 1;
                b = dhuView.getBuildings()[lastIndex];
                dhuView.moveToBuilding(b);
                dhuView.showDialog(b);
                if (lastIndex == 0) {
                    disableLast();
                }
            }
        });
        disableLast();
    }

    private void initHelp() {

    }

}
