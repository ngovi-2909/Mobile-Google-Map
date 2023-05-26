package tdtu.edu.vn.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Activity.DanhSachAllAlbumActivity;
import tdtu.edu.vn.musicapp.Adapter.AlbumAdapter;
import tdtu.edu.vn.musicapp.Model.Album;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class Fragment_Album_Hot extends Fragment {

    View view;
    RecyclerView recyclerView;
    TextView tvXemThemAlbum;
    AlbumAdapter albumAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__album_hot, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewAlbum);
        tvXemThemAlbum = view.findViewById(R.id.tvXemThemAlbum);
        tvXemThemAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachAllAlbumActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> listCallBack = dataService.getDataAlbumHot();
        listCallBack.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                albumAdapter = new AlbumAdapter(getActivity(),albumArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}