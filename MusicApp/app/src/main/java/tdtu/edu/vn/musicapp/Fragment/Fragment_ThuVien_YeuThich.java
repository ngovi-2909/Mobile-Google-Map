package tdtu.edu.vn.musicapp.Fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Activity.Homepage;
import tdtu.edu.vn.musicapp.Adapter.BaiHatYeuThichAdapter;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class Fragment_ThuVien_YeuThich extends Fragment {
    View view;
    private BaiHatYeuThichAdapter baiHatYeuThichAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thuvien_yeuthich, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewyeuthich);
        GetData();
        swipeRefreshLayout = view.findViewById(R.id.swipeyeuthich);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetData();
                baiHatYeuThichAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        String username = Homepage.getUserName();
        Call<List<BaiHat>> callBack = dataService.getFavoriteList(username);
        callBack.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baihatyeuthiches = (ArrayList<BaiHat>) response.body();
                baiHatYeuThichAdapter = new BaiHatYeuThichAdapter(getActivity(), baihatyeuthiches);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(baiHatYeuThichAdapter);
                baiHatYeuThichAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

}
