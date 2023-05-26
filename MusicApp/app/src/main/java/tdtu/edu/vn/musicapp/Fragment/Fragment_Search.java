package tdtu.edu.vn.musicapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.SearchAdapter;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class Fragment_Search extends Fragment {

    private View view;
    Toolbar toolbar;
    RecyclerView recyclerViewSearch;
    TextView tvNoData;
    SearchAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //       dung de gan layout cho fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = view.findViewById(R.id.toolbarSearch);
        recyclerViewSearch = view.findViewById(R.id.recyclerviewSearch);
        tvNoData = view.findViewById(R.id.alertSearch);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchBaiHat(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchBaiHat(String query){
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getSearchBaiHat(query);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baiHats = (ArrayList<BaiHat>) response.body();
                if(baiHats.size() > 0){
                    searchAdapter = new SearchAdapter(getActivity(), baiHats);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewSearch.setLayoutManager(linearLayoutManager);
                    recyclerViewSearch.setAdapter(searchAdapter);
                    tvNoData.setVisibility(View.GONE);
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerViewSearch.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}