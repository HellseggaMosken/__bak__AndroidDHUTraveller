package dhu.cst.zhangcheng171010220.dhutraveller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchBar {
    DhuView dhuView;
    SearchView searchView;
    View searchVoice;
    IatHandler iatHandler;
    Context context;

    public SearchBar(Context context, DhuView dhuView, ViewGroup layout, final IatHandler iatHandler) {
        this.context = context;
        this.dhuView = dhuView;
        this.searchView = layout.findViewById(R.id.search_bar_view);
        this.iatHandler = iatHandler;
        this.searchVoice = layout.findViewById(R.id.search_bar_voice);
        initSearchView();
        initIat();
    }

    private void initIat() {
        final IatAction iatAction = new IatAction() {
            @Override
            public void onStart() {
                searchView.setQuery("", false);
                searchView.setQueryHint("正在聆听...");
                Toast.makeText(context, "请说出地名", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResult(StringBuffer buffer) {
                searchView.setQuery(buffer, false);
            }

            @Override
            public void onFinish() {
                searchView.setQueryHint(context.getResources().getString(R.string.search_hint));
                Toast.makeText(context, "语音结束", Toast.LENGTH_SHORT).show();
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
                Log.d("__d", "onQueryTextChange   "+newText);
//                if (newText == null || newText.length() == 0) {
//                    //
//                }

                return false;
            }
        });
    }
}
