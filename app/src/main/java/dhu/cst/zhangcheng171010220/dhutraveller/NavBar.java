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
    SearchBar searchBar;
    View navIconSettings;
    View navIconLast;
    View navIconNext;
    View navIconQA;
    IatHandler iatHandler;
    TtsHandler ttsHandler;
    QADialog qaDialog;

    public NavBar(Context context, ViewGroup navBarLayout, DhuView dhuView,
                  SearchBar searchBar, IatHandler iatHandler, TtsHandler ttsHandler) {
        this.context = context;
        this.iatHandler = iatHandler;
        this.ttsHandler = ttsHandler;
        this.dhuView = dhuView;
        this.searchBar = searchBar;
        this.navIconSettings = navBarLayout.findViewById(R.id.nav_bar_settings);
        this.navIconLast = navBarLayout.findViewById(R.id.nav_bar_last);
        this.navIconNext = navBarLayout.findViewById(R.id.nav_bar_next);
        this.navIconQA = navBarLayout.findViewById(R.id.nav_bar_qa);
        navBarLayout.setOnClickListener(null); // 覆盖其他的点击事件
        initSettings();
        initLastAndNext();
        initQA();
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

        rAbs.setChecked(true);

        rAbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhuView.changeStyle(0);
                rAbs.setChecked(true);
                rReal.setChecked(false);
                rStd.setChecked(false);
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

        rStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhuView.changeStyle(2);
                rStd.setChecked(true);
                rReal.setChecked(false);
                rAbs.setChecked(false);
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
        int nowIndex = findCurrentIndex(b);
        if (nowIndex == dhuView.getBuildings().length - 1) {
            return 0;
        }
        return nowIndex + 1;
    }

    private int findLastBuildingIndex(DhuBuilding b) {
        if (b == null) return 0;
        int nowIndex = findCurrentIndex(b);
        return nowIndex - 1;
    }

    private int findCurrentIndex(DhuBuilding b) {
        for (int i = 0; i < dhuView.getBuildings().length; i++) {
            if (dhuView.getBuildings()[i] == b) {
                return i;
            }
        }
        return -1;
    }

    public boolean nextBuilding(boolean showFinishTip) {
        if (!navIconNext.isClickable()) {
            return false;
        }
        DhuBuilding b = dhuView.getShowingBuilding();
        int nextIndex = findNextBuildingIndex(b);
        b = dhuView.getBuildings()[nextIndex];
        dhuView.moveToBuilding(b);
        dhuView.showDialog(b);
        if (nextIndex == dhuView.getBuildings().length - 1) {
            disableNext();
            if (showFinishTip) Toast.makeText(context, "游览结束！", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public boolean lastBuilding(boolean showFinishTip) {
        if (!navIconLast.isClickable()) {
            return false;
        }
        DhuBuilding b = dhuView.getShowingBuilding();
        int lastIndex = findLastBuildingIndex(b);
        if (lastIndex < 0) lastIndex = dhuView.getBuildings().length - 1;
        b = dhuView.getBuildings()[lastIndex];
        dhuView.moveToBuilding(b);
        dhuView.showDialog(b);
        if (lastIndex == 0) {
            disableLast();
        }
        return true;
    }

    private void initLastAndNext() {
        dhuView.setNavBar(this);
        navIconNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBuilding(true);
            }
        });
        navIconLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastBuilding(true);
            }
        });
        disableLast();
    }

    private void initQA() {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.nav_bar_qa_dialog, null);
        qaDialog = new QADialog(context, view, dhuView, this, searchBar, iatHandler, ttsHandler);
        navIconQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qaDialog.isShowing()) qaDialog.close();
                else qaDialog.show();
            }
        });
    }

}
