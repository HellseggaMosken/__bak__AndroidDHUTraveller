package dhu.cst.zhangcheng171010220.dhutraveller;

public interface IatAction {
    public void onStart();
    public void onResult(StringBuffer buffer);
    public void onFinish();
    public String blockTimeAfterSpeak(); //毫秒
}
