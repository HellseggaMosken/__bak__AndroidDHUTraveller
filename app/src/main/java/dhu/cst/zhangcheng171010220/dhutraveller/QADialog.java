package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QADialog {
    private Context context;
    private Dialog dialog;
    ViewGroup layout;

    private TextView textViewSpeaking;
    private TextView textViewRes;
    private View buttonAgain;
    private View buttonAgainDivider;
    private ProgressBar progressBar;
    private TextView progressBarHint;

    private IatHandler iatHandler;
    private QAAction qaAction;


    public QADialog(Context context, ViewGroup layout,
                    DhuView dhuView, NavBar navBar,
                    SearchBar searchBar,
                    IatHandler iatHandler) {
        this.context = context;
        this.iatHandler = iatHandler;
        this.layout = layout;
        initLayout(layout);
        initDialog(layout);
        this.qaAction = new QAAction(context, dhuView, navBar, this, searchBar,
                textViewRes, progressBar, progressBarHint, buttonAgain, buttonAgainDivider,
                textViewSpeaking);
    }

    private void initLayout(ViewGroup layout) {
        this.textViewRes = layout.findViewById(R.id.qa_res);
        this.textViewSpeaking = layout.findViewById(R.id.qa_speaking);
        this.progressBar = layout.findViewById(R.id.qa_progressBar);
        this.progressBarHint = layout.findViewById(R.id.qa_progressBar_hint);
        this.buttonAgain = layout.findViewById(R.id.qa_again);
        this.buttonAgainDivider = layout.findViewById(R.id.qa_divider);
        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListen();
            }
        });
    }

    private void startListen() {
        iatHandler.setIatAction(qaAction);
        iatHandler.startListen();
    }

    private void initDialog(ViewGroup layout) {
        this.dialog = new Dialog(context);
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setWindowAnimations(R.style.DialogPop);

        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        float margin = context.getResources().getDimension(R.dimen.dialog_horizontal_margin);
        window.setLayout(width - (int)(2 * margin), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void show() {
        dialog.show();
        startListen();
    }

    public void close() {
        dialog.dismiss();
    }
}
