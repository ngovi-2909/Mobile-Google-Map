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
import tdtu.edu.vn.musicapp.Adapter.DanhSachAllChuDeAdapter;
import tdtu.edu.vn.musicapp.Model.ChuDe;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class DanhSachAllChuDeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    DanhSachAllChuDeAdapter danhSachAllChuDeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_all_chu_de);
        anhxa();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.getAllChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> chuDes = (ArrayList<ChuDe>) response.body();
                danhSachAllChuDeAdapter = new DanhSachAllChuDeAdapter(DanhSachAllChuDeActivity.this, chuDes);
                recyclerView.setLayoutManager(new GridLayoutManager(DanhSachAllChuDeActivity.this, 1));
                recyclerView.setAdapter(danhSachAllChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.recyclerviewAllChuDe);
        toolbar = findViewById(R.id.toolbarAllChuDe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả Chủ đề");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}