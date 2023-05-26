package tdtu.edu.vn.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.DanhSachTLTheoCDAdapter;
import tdtu.edu.vn.musicapp.Model.ChuDe;
import tdtu.edu.vn.musicapp.Model.TheLoai;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class DanhSachTheLoaiTheoChuDeActivity extends AppCompatActivity {

    ChuDe chuDe;
    RecyclerView recyclerView;
    Toolbar toolbar;
    DanhSachTLTheoCDAdapter  danhSachTLTheoCDAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_the_loai_theo_chu_de);
        GetIntent();
        anhxa();
        GetData();

    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.getTLFollowCD(chuDe.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> theLoais = (ArrayList<TheLoai>) response.body();
                danhSachTLTheoCDAdapter = new DanhSachTLTheoCDAdapter(DanhSachTheLoaiTheoChuDeActivity.this, theLoais);
                recyclerView.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiTheoChuDeActivity.this, 2));
                recyclerView.setAdapter(danhSachTLTheoCDAdapter);

                danhSachTLTheoCDAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void GetIntent(){
        Intent intent = getIntent();
        if ( (intent.hasExtra("chude"))){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
            Toast.makeText(this, chuDe.getTenChuDe(), Toast.LENGTH_SHORT).show();
        }
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.recyclerviewTLtheoCD);
        toolbar = findViewById(R.id.toolbarTLtheoCD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}