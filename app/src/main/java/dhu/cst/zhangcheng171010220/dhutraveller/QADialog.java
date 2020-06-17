package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

public class QADialog {
    private Context context;
    private Dialog dialog;

    private TextView textViewTitle;
    private TextView textViewRes;
    private Button buttonAgain;
    private ProgressBar progressBar;

    private IatHandler iatHandler;
    private QAAction qaAction;


    public QADialog(Context context, ViewGroup layout,
                    DhuView dhuView, NavBar navBar, IatHandler iatHandler) {
        this.context = context;
        this.iatHandler = iatHandler;
        initLayout(layout);
        initDialog(layout);
        this.qaAction = new QAAction(context, dhuView, navBar, textViewTitle, textViewRes, progressBar, buttonAgain);
    }

    private void initLayout(ViewGroup layout) {
        this.textViewTitle = layout.findViewById(R.id.qa_title);
        this.textViewRes = layout.findViewById(R.id.qa_res);
        this.progressBar = layout.findViewById(R.id.qa_progressBar);
        this.buttonAgain = layout.findViewById(R.id.qa_again);
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setWindowAnimations(R.style.DialogPop);
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
