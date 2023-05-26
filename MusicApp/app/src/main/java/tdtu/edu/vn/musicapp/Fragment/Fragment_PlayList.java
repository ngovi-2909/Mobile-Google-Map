package tdtu.edu.vn.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Activity.DanhSachBaiHatActivity;
import tdtu.edu.vn.musicapp.Activity.DanhSachCacPlayListActivity;
import tdtu.edu.vn.musicapp.Adapter.PlayListAdapter;
import tdtu.edu.vn.musicapp.Model.PlayList;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class Fragment_PlayList extends Fragment {
    View view;
    ListView lvPlayList;
    TextView tvPlayList, tvMorePL;
    PlayListAdapter playListAdapter;
    ArrayList<PlayList> playLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_play_list, container, false);
        lvPlayList = view.findViewById(R.id.lvPlayList);
        tvPlayList = view.findViewById(R.id.tvPlayList);
        tvMorePL = view.findViewById(R.id.tvMorePL);
        GetData();
        tvMorePL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachCacPlayListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<PlayList>> callBack = dataService.getDataPlayListCurrentDay();
        callBack.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                playLists = (ArrayList<PlayList>) response.body();
                playListAdapter = new PlayListAdapter(getActivity(), android.R.layout.simple_list_item_1, playLists);
                setListViewHeightBasedOnChildren(lvPlayList);
                lvPlayList.setAdapter(playListAdapter);
                lvPlayList.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(getActivity(), DanhSachBaiHatActivity.class);
                    intent.putExtra("itemPlayList",playLists.get(position));
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {
            }
        });
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                //listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}