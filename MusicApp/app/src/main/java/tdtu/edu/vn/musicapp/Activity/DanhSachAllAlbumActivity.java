package tdtu.edu.vn.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.AllAlbumAdapter;
import tdtu.edu.vn.musicapp.Adapter.DanhSachAllChuDeAdapter;
import tdtu.edu.vn.musicapp.Model.Album;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class DanhSachAllAlbumActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    AllAlbumAdapter allAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_all_album);
        anhxa();
        GetData();
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.getAllAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albums = (ArrayList<Album>) response.body();
                allAlbumAdapter = new AllAlbumAdapter(DanhSachAllAlbumActivity.this, albums);
                recyclerView.setLayoutManager(new GridLayoutManager(DanhSachAllAlbumActivity.this,2));
                recyclerView.setAdapter(allAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.recyclerviewAllAlbum);
        toolbar = findViewById(R.id.toolbarAllAlbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả Album");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}