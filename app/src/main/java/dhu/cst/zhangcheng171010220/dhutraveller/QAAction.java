package dhu.cst.zhangcheng171010220.dhutraveller;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QAAction implements IatAction {

    private Context context;
    private DhuView dhuView;
    private NavBar navBar;
    private QADialog qaDialog;
    private SearchBar searchBar;

    private TtsHandler ttsHandler;

    private TextView textViewRes;
    private TextView textViewSpeaking;
    private View buttonAgain;
    private View buttonAgainDivider;
    private ProgressBar progressBar;
    private TextView progressBarHint;

    private StringBuffer stringBuffer;

    public QAAction(Context context, DhuView dhuView, NavBar navBar, QADialog qaDialog, SearchBar searchBar,
                    TextView textViewRes, ProgressBar progressBar, TextView progressBarHint,
                    View buttonAgain, View buttonAgainDivider, TextView textViewSpeaking,
                    TtsHandler ttsHandler) {
        this.context = context;
        this.dhuView = dhuView;
        this.qaDialog = qaDialog;
        this.searchBar = searchBar;
        this.navBar = navBar;
        this.textViewRes = textViewRes;
        this.textViewSpeaking = textViewSpeaking;
        this.progressBar = progressBar;
        this.progressBarHint = progressBarHint;
        this.buttonAgain = buttonAgain;
        this.buttonAgainDivider = buttonAgainDivider;
        this.ttsHandler = ttsHandler;
    }

    @Override
    public String blockTimeAfterSpeak() {
        return "1700"; // 1.7 second
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
        if (stringBuffer == null) {
            textViewRes.setText("您好像未说话，请尝试再次对话。");
        } else {
            handleRes(textViewRes, stringBuffer.toString());
        }
    }

    private void handleRes(TextView resView, String iatRes) {
        if (iatRes.contains("东华") && iatRes.contains("校训")) {
            resView.setText(R.string.dhu_motto);
            ttsHandler.speak(context.getResources().getString(R.string.dhu_motto));
            return;
        }
        if (iatRes.contains("东华") && (iatRes.contains("简介") || iatRes.contains("介绍") || iatRes.contains("详情"))) {
            resView.setText(R.string.dhu_details);
            ttsHandler.speak(context.getResources().getString(R.string.dhu_details));
            return;
        }
        if (iatRes.contains("下一")) {
            if (navBar.nextBuilding(false)) {
                qaDialog.close();
                showTip("已经移动到下一个位置");
            } else {
                resView.setText("已经位于最后一个游览位置");
            }
            return;
        }
        if (iatRes.contains("上一")) {
            if (navBar.lastBuilding(false)) {
                qaDialog.close();
                showTip("已经移动到上一个位置");
            } else {
                resView.setText("已经位于第一个游览位置");
            }
            return;
        }
        if (iatRes.startsWith("搜索")) {
            qaDialog.close();
            searchBar.setSearchStr(iatRes.substring(2));
            return;
        }
        if (iatRes.startsWith("开始") && (iatRes.contains("游览") || iatRes.contains("浏览"))) {
            if (dhuView.isShowingBuilding()) {
                resView.setText("您已经处于浏览状态");
            } else {
                qaDialog.close();
                dhuView.showDialog(dhuView.getBuildings()[0]);
                showTip("开始进行浏览");
            }
            return;
        }
        if (iatRes.startsWith("前往") || iatRes.startsWith("去")) {
            DhuBuilding b = dhuView.fuzzySearch(iatRes);
            if (b != null) {
                qaDialog.close();
                if (dhuView.getShowingBuilding() != b) {
                    dhuView.closeDialog();
                }
                dhuView.moveToBuilding(b);
                showTip("已经前往" + b.name);
            } else {
                resView.setText("未找到" + (iatRes.startsWith("去") ? iatRes.substring(1) : iatRes.substring(2)));
            }
            return;
        }
        if (iatRes.startsWith("打开") || iatRes.startsWith("显示")
                || iatRes.startsWith("查看") || iatRes.startsWith("游览") || iatRes.startsWith("浏览")) {
            DhuBuilding b = dhuView.fuzzySearch(iatRes);
            if (b == null) {
                resView.setText("未找到" + iatRes.substring(2));
                return;
            }
            qaDialog.close();
            dhuView.moveToBuilding(b);
            dhuView.showDialog(b);
            if (iatRes.contains("图库") || iatRes.contains("图片")) {
                dhuView.showGallery();
                return;
            } else if (iatRes.contains("全景")) {
                dhuView.showFullView();
                return;
            }
            showTip("已经打开" + b.name);
            return;
        }
        DhuBuilding b = dhuView.fuzzySearch(iatRes);
        if (b != null) {
            qaDialog.close();
            if (dhuView.getShowingBuilding() != b) {
                dhuView.closeDialog();
            }
            dhuView.moveToBuilding(b);
            showTip("已经前往" + b.name);
            return;
        }
        resView.setText("抱歉，我没有理解你的意思。");
    }

    private void showTip(String tip) {
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }
}
