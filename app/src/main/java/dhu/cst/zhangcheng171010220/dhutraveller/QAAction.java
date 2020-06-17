package dhu.cst.zhangcheng171010220.dhutraveller;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QAAction implements IatAction {

    private Context context;
    private DhuView dhuView;
    private NavBar navBar;
    private TextView textViewTitle;
    private TextView textViewRes;
    private Button buttonAgain;
    private ProgressBar progressBar;

    private StringBuffer stringBuffer;

    public QAAction(Context context, DhuView dhuView, NavBar navBar, TextView textViewTitle,
                    TextView textViewRes, ProgressBar progressBar, Button buttonAgain) {
        this.context = context;
        this.dhuView = dhuView;
        this.navBar = navBar;
        this.textViewTitle = textViewTitle;
        this.textViewRes = textViewRes;
        this.progressBar = progressBar;
        this.buttonAgain = buttonAgain;
    }

    @Override
    public String blockTimeAfterSpeak() {
        return "3000"; // 3 second
    }

    @Override
    public void onStart() {
        this.stringBuffer = null;
        textViewTitle.setText("正在聆听...");
        progressBar.setVisibility(View.VISIBLE);
        buttonAgain.setVisibility(View.INVISIBLE);
        textViewRes.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResult(StringBuffer buffer) {
        this.stringBuffer = buffer;
    }

    @Override
    public void onFinish() {
        textViewTitle.setText("结果");
        progressBar.setVisibility(View.INVISIBLE);
        textViewRes.setVisibility(View.VISIBLE);
        buttonAgain.setVisibility(View.VISIBLE);
        textViewRes.setText(stringBuffer);
    }
}
