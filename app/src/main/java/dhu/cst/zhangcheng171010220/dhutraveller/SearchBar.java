package dhu.cst.zhangcheng171010220.dhutraveller;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SearchBar {
    DhuView dhuView;
    SearchView searchView;
    View searchVoice;
    ProgressBar searchVoiceProgressBar;
    ViewGroup searchResList;
    IatHandler iatHandler;
    Activity activity;

    private HashMap<DhuBuilding, Boolean> searchMap;

    public SearchBar(Activity activity, DhuView dhuView, ViewGroup layout, final IatHandler iatHandler) {
        this.activity = activity;
        this.dhuView = dhuView;
        this.searchView = layout.findViewById(R.id.search_bar_view);
        this.iatHandler = iatHandler;
        this.searchVoice = layout.findViewById(R.id.search_bar_voice);
        this.searchResList = layout.findViewById(R.id.search_bar_list);
        this.searchVoiceProgressBar = layout.findViewById(R.id.search_bar_voice_progressBar);
        this.searchMap = new HashMap<>(dhuView.getBuildings().length);
        layout.setOnClickListener(null);
        initSearchView();
        initIat();
    }

    private void initIat() {
        final IatAction iatAction = new IatAction() {
            @Override
            public String blockTimeAfterSpeak() {
                return "1500"; // 1.5 second
            }

            @Override
            public void onStart() {
                searchView.setQuery("", false);
                searchView.setQueryHint("正在聆听...");
                searchVoice.setVisibility(View.INVISIBLE);
                searchVoiceProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(activity, "请说出地名", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResult(StringBuffer buffer) {
                searchView.setQuery(buffer, false);
            }

            @Override
            public void onFinish() {
                searchView.setQueryHint(activity.getResources().getString(R.string.search_hint));
                //Toast.makeText(activity, "语音结束", Toast.LENGTH_SHORT).show();
                searchVoiceProgressBar.setVisibility(View.INVISIBLE);
                searchVoice.setVisibility(View.VISIBLE);
            }
        };
        searchVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iatHandler.setIatAction(iatAction);
                iatHandler.startListen();
            }
        });
    }

    public void setSearchStr(String str) {
        this.searchView.setQuery(str, false);
    }

    private View makeResItem(final DhuBuilding b) {
        TextView view = (TextView) activity.getLayoutInflater().inflate(R.layout.search_res_item, null);
        view.setText(b.name);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchView.setQuery("", false);
                dhuView.moveToBuilding(b);
                dhuView.showDialog(b);
            }
        });
        return view;
    }

    private void initSearchView() {
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.clearFocus();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("__d", "onQueryTextSubmit   "+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchResList.removeAllViews();
                searchMap.clear();
                if (newText == null || newText.length() == 0) {
                    return false;
                }
                for (DhuBuilding b : dhuView.getBuildings()) {
                    if (b.name.contains(newText)) {
                        searchResList.addView(makeResItem(b));
                        searchMap.put(b, true);
                    }
                }
                DhuBuilding building = dhuView.fuzzySearch(newText);
                if (building != null) {
                    if (!searchMap.containsKey(building)) {
                        searchResList.addView(makeResItem(building));
                        searchMap.put(building, true);
                    }
                }
                return false;
            }
        });
    }
}
