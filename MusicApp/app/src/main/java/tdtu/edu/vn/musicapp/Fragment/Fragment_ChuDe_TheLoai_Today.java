package tdtu.edu.vn.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Activity.DanhSachAllChuDeActivity;
import tdtu.edu.vn.musicapp.Activity.DanhSachBaiHatActivity;
import tdtu.edu.vn.musicapp.Activity.DanhSachTheLoaiTheoChuDeActivity;
import tdtu.edu.vn.musicapp.Model.Album;
import tdtu.edu.vn.musicapp.Model.ChuDe;
import tdtu.edu.vn.musicapp.Model.TheLoai;
import tdtu.edu.vn.musicapp.Model.TheloaiChuDe;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class Fragment_ChuDe_TheLoai_Today extends Fragment {

    View view;
    HorizontalScrollView horizontalScrollView;
    TextView tvXemThem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__chu_de__the_loai__today, container, false);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);
        tvXemThem = view.findViewById(R.id.tvXemThem);
        tvXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachAllChuDeActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }

    private void GetData() {
        DataService dataService = APIService.getService();
        Call<TheloaiChuDe> listCallBack = dataService.getDataChuDeTheLoai();
        listCallBack.enqueue(new Callback<TheloaiChuDe>() {
            @Override
            public void onResponse(Call<TheloaiChuDe> call, Response<TheloaiChuDe> response) {
                TheloaiChuDe theloaiChuDe = response.body();

                final ArrayList<ChuDe> chudeArrayList = new ArrayList<ChuDe>();
                chudeArrayList.addAll(theloaiChuDe.getChuDe());

                final ArrayList<TheLoai> theLoaiArrayList = new ArrayList<TheLoai>();
                theLoaiArrayList.addAll(theloaiChuDe.getTheLoai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(580, 250);
                layoutParams.setMargins(10, 20,10, 30);
                for (int i = 0; i < chudeArrayList.size(); i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(chudeArrayList.get(i).getHinhChuDe() != null){
                        Picasso.with(getActivity()).load(chudeArrayList.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalI = i;
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhSachTheLoaiTheoChuDeActivity.class);
                            intent.putExtra("chude", chudeArrayList.get(finalI));
                            startActivity(intent);
                        }
                    });
                }

                for (int j = 0; j < chudeArrayList.size(); j++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(theLoaiArrayList.get(j).getHinhTheLoai() != null){
                        Picasso.with(getActivity()).load(theLoaiArrayList.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalj = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhSachBaiHatActivity.class);
                            intent.putExtra("idtheloai", theLoaiArrayList.get(finalj));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<TheloaiChuDe> call, Throwable t) {

            }
        });
    }
}