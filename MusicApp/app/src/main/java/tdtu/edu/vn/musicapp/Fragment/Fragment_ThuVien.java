package tdtu.edu.vn.musicapp.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

import tdtu.edu.vn.musicapp.Activity.Homepage;
import tdtu.edu.vn.musicapp.Adapter.ViewPagerThuVien;
import tdtu.edu.vn.musicapp.R;

public class Fragment_ThuVien extends Fragment{
    TabLayout tabLayout;
    ViewPager viewPager;
    View view;
    private Homepage hm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thuvien, container, false);
        AnhXa();
        init();
        return  view;
    }
    private void init() {
        ViewPagerThuVien viewPagerThuVien = new ViewPagerThuVien(getChildFragmentManager());
        viewPagerThuVien.addFragment(new Fragment_ThuVien_YeuThich(), "Yêu thích");
        viewPager.setAdapter(viewPagerThuVien);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void AnhXa() {
        hm = (Homepage) getActivity();
        tabLayout = view.findViewById(R.id.tabLayouttv);
        viewPager = view.findViewById(R.id.viewPagertv);
    }


}
