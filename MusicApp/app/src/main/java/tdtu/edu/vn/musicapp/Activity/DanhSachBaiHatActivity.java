package tdtu.edu.vn.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.DanhSachBaiHatAdapter;
import tdtu.edu.vn.musicapp.Model.Album;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.Model.PlayList;
import tdtu.edu.vn.musicapp.Model.Quangcao;
import tdtu.edu.vn.musicapp.Model.TheLoai;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class DanhSachBaiHatActivity extends AppCompatActivity {

    Quangcao quangcao;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDSBH;
    FloatingActionButton floatingActionButton;

    ImageView imageViewBH, ivLuotThichDSBH;

    ArrayList<BaiHat> baiHats;

    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    PlayList playList;
    TheLoai theLoai;
    Album album;
    private int dem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_bai_hat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        anhxa();
        DataIntent();
        init();

        if (quangcao != null && !quangcao.getTenBaiHat().equals("")) {
            new LoadImageTask().execute(quangcao.getHinhBaiHat());
            collapsingToolbarLayout.setTitle(quangcao.getTenBaiHat());
            getDataQC(quangcao.getIdQuangCao());
        }
        if (playList != null && !playList.getTen().equals("")) {
            new LoadImageTask().execute(playList.getHinhPlaylist());
            collapsingToolbarLayout.setTitle(playList.getTen());
            getDataPL(playList.getIdPlaylist());
        }

        if (theLoai != null && !theLoai.getTenTheLoai().equals("")){
            new LoadImageTask().execute(theLoai.getHinhTheLoai());
            collapsingToolbarLayout.setTitle(theLoai.getTenTheLoai());
            getDataTL(theLoai.getIdTheLoai());
        }

        if (album != null && !album.getTenAlbum().equals("")){
            new LoadImageTask().execute(album.getHinhAlbum());
            collapsingToolbarLayout.setTitle(album.getTenAlbum());
            getDataA(album.getIdAlbum());
        }

    }

    private void getDataA(Integer idAlbum) {
        DataService dataService =APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDSBHFollowAlbum(idAlbum);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHats = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, baiHats);
                recyclerViewDSBH.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDSBH.setAdapter(danhSachBaiHatAdapter);
                danhSachBaiHatAdapter.notifyDataSetChanged();
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataTL(Integer idtheloai) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDSBHFollowTheLoai(idtheloai);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHats = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, baiHats);
                recyclerViewDSBH.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDSBH.setAdapter(danhSachBaiHatAdapter);
                danhSachBaiHatAdapter.notifyDataSetChanged();
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });

    }

    private void getDataPL(Integer idPlaylist) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDataBaiHatFollowPlayList(idPlaylist);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHats = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, baiHats);
                recyclerViewDSBH.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDSBH.setAdapter(danhSachBaiHatAdapter);
                danhSachBaiHatAdapter.notifyDataSetChanged();
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private class LoadImageTask extends AsyncTask<String, Void, BitmapDrawable> {

        @Override
        protected BitmapDrawable doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                return drawable;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            if (drawable != null) {
                collapsingToolbarLayout.setBackground(drawable);
                imageViewBH.setImageDrawable(drawable);
            }
        }
    }

    private void getDataQC(Integer idquangcao) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDataBaiHatFollowBanner(idquangcao);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHats = (ArrayList<BaiHat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhSachBaiHatActivity.this, baiHats);
                recyclerViewDSBH.setLayoutManager(new LinearLayoutManager(DanhSachBaiHatActivity.this));
                recyclerViewDSBH.setAdapter(danhSachBaiHatAdapter);
                danhSachBaiHatAdapter.notifyDataSetChanged();
                eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    public void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void anhxa() {
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        coordinatorLayout = findViewById(R.id.coordinatorLayoutDSBH);
        toolbar = findViewById(R.id.toolbarDSBH);
        recyclerViewDSBH = findViewById(R.id.recyclerviewDSBH);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        imageViewBH = findViewById(R.id.ivDanhSachCaKhuc);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("banner")) {
                quangcao = (Quangcao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemPlayList")) {
                playList = (PlayList) intent.getSerializableExtra("itemPlayList");
            }
            if (intent.hasExtra("idtheloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("album")){
                album = (Album) intent.getSerializableExtra("album");
                Toast.makeText(this, album.getTenAlbum(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eventClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachBaiHatActivity.this, PlayNhacActivity.class);
                intent.putExtra("cacbaihat", baiHats);
                startActivity(intent);
            }
        });
    }

}