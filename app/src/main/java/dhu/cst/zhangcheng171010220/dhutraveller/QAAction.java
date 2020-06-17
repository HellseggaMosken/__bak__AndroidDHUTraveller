package dhu.cst.zhangcheng171010220.dhutraveller;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QAAction implements IatAction {

    private Context context;
    private DhuView dhuView;
    private NavBar navBar;
    private TextView textViewRes;
    private TextView textViewSpeaking;
    private View buttonAgain;
    private View buttonAgainDivider;
    private ProgressBar progressBar;
    private TextView progressBarHint;

    private StringBuffer stringBuffer;

    public QAAction(Context context, DhuView dhuView, NavBar navBar,
                    TextView textViewRes, ProgressBar progressBar, TextView progressBarHint,
                    View buttonAgain, View buttonAgainDivider, TextView textViewSpeaking) {
        this.context = context;
        this.dhuView = dhuView;
        this.navBar = navBar;
        this.textViewRes = textViewRes;
        this.textViewSpeaking = textViewSpeaking;
        this.progressBar = progressBar;
        this.progressBarHint = progressBarHint;
        this.buttonAgain = buttonAgain;
        this.buttonAgainDivider = buttonAgainDivider;
    }

    @Override
    public String blockTimeAfterSpeak() {
        return "3000"; // 3 second
    }

    @Override
    public void onStart() {
        this.stringBuffer = null;
        textViewSpeaking.setText(null);
        textViewRes.setText(null);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHint.setVisibility(View.VISIBLE);
        buttonAgain.setVisibility(View.INVISIBLE);
        buttonAgainDivider.setVisibility(View.INVISIBLE);
        textViewRes.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResult(StringBuffer buffer) {
        this.stringBuffer = buffer;
        textViewSpeaking.setText(buffer);
    }

    @Override
    public void onFinish() {
        progressBar.setVisibility(View.INVISIBLE);
        progressBarHint.setVisibility(View.INVISIBLE);
        textViewRes.setVisibility(View.VISIBLE);
        buttonAgain.setVisibility(View.VISIBLE);
        buttonAgainDivider.setVisibility(View.VISIBLE);
        handleRes(textViewRes, stringBuffer);
    }

    private void handleRes(TextView resView, StringBuffer iatRes) {

    }
}
