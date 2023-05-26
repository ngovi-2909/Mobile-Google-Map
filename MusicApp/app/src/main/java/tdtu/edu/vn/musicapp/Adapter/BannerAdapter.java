package tdtu.edu.vn.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tdtu.edu.vn.musicapp.Activity.DanhSachBaiHatActivity;
import tdtu.edu.vn.musicapp.Model.Quangcao;
import tdtu.edu.vn.musicapp.R;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Quangcao> quangcaoArrayList;

    public BannerAdapter(Context context, ArrayList<Quangcao> quangcaoArrayList) {
        this.context = context;
        this.quangcaoArrayList = quangcaoArrayList;
    }

    @Override
    public int getCount() {
        return quangcaoArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_banner, null);

        ImageView imgBGBanner = view.findViewById(R.id.ivbgbanner);
        ImageView imgSongBanner = view.findViewById(R.id.ivbanner);
        TextView tvSongBanner = view.findViewById(R.id.tvBanner);
        TextView tvNoiDung = view.findViewById(R.id.tvNoiDung);

        Picasso.with(context).load(quangcaoArrayList.get(position).getHinhanh()).into(imgBGBanner);
        Picasso.with(context).load(quangcaoArrayList.get(position).getHinhBaiHat()).into(imgSongBanner);
        tvSongBanner.setText(quangcaoArrayList.get(position).getTenBaiHat());
        tvNoiDung.setText(quangcaoArrayList.get(position).getNoidung());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                intent.putExtra("banner",quangcaoArrayList.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
