package tdtu.edu.vn.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.DanhSachCacPlayListAdapter;
import tdtu.edu.vn.musicapp.Model.PlayList;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class DanhSachCacPlayListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<PlayList> playListArrayList;

    DanhSachCacPlayListAdapter danhSachCacPlayListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cac_play_list);
        anhxa();
        init();

        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<PlayList>> callback = dataService.getAllPlayList();
        callback.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                playListArrayList = (ArrayList<PlayList>) response.body();
                danhSachCacPlayListAdapter = new DanhSachCacPlayListAdapter(DanhSachCacPlayListActivity.this, playListArrayList);
                recyclerView.setLayoutManager(new GridLayoutManager(DanhSachCacPlayListActivity.this,2));
                recyclerView.setAdapter(danhSachCacPlayListAdapter);

                danhSachCacPlayListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarDSCPL);
        recyclerView = findViewById(R.id.recyclerviewDSCPL);

    }
    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Play Lists");
        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}