package tdtu.edu.vn.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import tdtu.edu.vn.musicapp.Adapter.MainViewPagerAdapter;
import tdtu.edu.vn.musicapp.Fragment.Fragment_Home;
import tdtu.edu.vn.musicapp.Fragment.Fragment_Search;

import tdtu.edu.vn.musicapp.Fragment.Fragment_ThuVien;
import tdtu.edu.vn.musicapp.Fragment.Fragment_logout;
import tdtu.edu.vn.musicapp.R;


public class Homepage extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    static String username;
    Fragment_ThuVien fragmentThuVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
        username = (String) getIntent().getExtras().get("username");
    }

    private void init(){
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Home(),"Trang chu");
        mainViewPagerAdapter.addFragment(new Fragment_ThuVien(), "Thu Vien");
        mainViewPagerAdapter.addFragment(new Fragment_Search(), "Tim kiem");
        mainViewPagerAdapter.addFragment(new Fragment_logout(), "Logout");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        set icon for trang chu
        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconthuvien);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconsearch);
        tabLayout.getTabAt(3).setIcon(R.drawable.iconlogo);
    }

    private void anhxa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        fragmentThuVien = new Fragment_ThuVien();
    }

    public static String getUserName(){
        return username;
    }
}