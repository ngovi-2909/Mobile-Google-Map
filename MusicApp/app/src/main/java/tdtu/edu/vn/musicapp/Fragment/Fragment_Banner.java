package tdtu.edu.vn.musicapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.BannerAdapter;
import tdtu.edu.vn.musicapp.Model.Quangcao;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;


public class Fragment_Banner extends Fragment {

    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        anhxa();
        getData();
        // Inflate t he layout for this fragment
        return view;
    }

    private void anhxa() {
        viewPager = view.findViewById(R.id.viewPagerBanner);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Quangcao>> listCallBack = dataService.getDataBanner();
        listCallBack.enqueue(new Callback<List<Quangcao>>() {
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response) {
                ArrayList<Quangcao> quangcaoList = (ArrayList<Quangcao>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(), quangcaoList);
                viewPager.setAdapter(bannerAdapter);
//               keo truot qua cac banner
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                          currentItem = viewPager.getCurrentItem();
                          currentItem++;
                          if (currentItem >= viewPager.getAdapter().getCount()){
                              currentItem = 0;
                          }
                          viewPager.setCurrentItem(currentItem, true);
                          handler.postDelayed(runnable, 8000);
                    }
                };
                handler.postDelayed(runnable, 8000);

            }

            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t) {

            }
        });


    }

}