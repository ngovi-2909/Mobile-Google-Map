package tdtu.edu.vn.musicapp.Activity;

import static tdtu.edu.vn.musicapp.Activity.Homepage.username;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Adapter.ViewPagerPlayListnhac;
import tdtu.edu.vn.musicapp.Fragment.Fragment_Dia_Nhac;
import tdtu.edu.vn.musicapp.LocalService.ForegroundService;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class PlayNhacActivity extends AppCompatActivity {
    Toolbar toolbarplaynhac;
    TextView txtTimesong, txtTotaltimesong, txtNameSong, txtSinger;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext, imgpre, imgrandom;
    ImageView imgTim;
    ViewPager viewPagerdianhac;
    TextView speed1x, speed2x;
    float speed;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();

    public static ViewPagerPlayListnhac adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean checkLove = false;
    private int position = 0, duration = 0, timeValue = 0, durationToService = 0;
    private boolean isplaying;
    private int idBaiHat_noti=0;
    private MediaPlayer mediaPlayer;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null){
                isplaying = intent.getBooleanExtra("status_player", false);
                int action = intent.getIntExtra("action_music", 0);
                duration = intent.getIntExtra("duration_music", 0);
                timeValue = intent.getIntExtra("seektomusic", 0);
                position = intent.getIntExtra("position_music", 0);
                idBaiHat_noti = intent.getIntExtra("idBaiHatFromNotification",0);
                if(idBaiHat_noti != 0){
                    setViewStart();
                }
                sktime.setProgress(timeValue);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                txtTimesong.setText(simpleDateFormat.format(timeValue));
                handleMusic(action);
                TimeSong();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("send_data_to_activity"));
        GetDataFromIntent();
        init();
        eventClick();
        setViewStart();
        StartService();
        overridePendingTransition(R.anim.anim_intent_in, R.anim.anim_intent_out);
    }

    private void StartService() {
        Intent intent =  new Intent(this, ForegroundService.class);
        if (mangbaihat.size() > 0){
            intent.putExtra("obj_song_baihat", mangbaihat);
        }
        startService(intent);
    }

    private void eventClick() {
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isplaying){
                    sendActionToService(ForegroundService.ACTION_PAUSE);
                    imgplay.setImageResource(R.drawable.iconpause);
                }else{
                    isplaying = false;
                    sendActionToService(ForegroundService.ACTION_RESUME);
                    imgplay.setImageResource(R.drawable.iconplay);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!repeat) {
                    if (checkrandom) {
                        checkrandom = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgrandom.setImageResource(R.drawable.iconsuffle);
                    } else {
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                    }
                    repeat = true;
                } else {
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
                sendActionToService(ForegroundService.ACTION_REPEAT);
            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkrandom == false){
                    if(repeat == true){
                        repeat = false;
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgrandom.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                }else{
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                    repeat = false;
                }
                sendActionToService(ForegroundService.ACTION_RANDOM);
            }
        });

        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //float speed = 1.0f + (float) seekBar.getProgress() / 100; // Calculate speed based on seek bar progress
                //sendActionToService(ForegroundService.ACTION_SPEED);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                durationToService = seekBar.getProgress();
                sendActionToService(ForegroundService.ACTION_DURATION);
            }
        });

        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(ForegroundService.ACTION_NEXT);
            }
        });

        imgpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(ForegroundService.ACTION_PREVIOUS);
            }
        });
        imgTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLove == false){
                    checkLove = true;
                    imgTim.setImageResource(R.drawable.iconloved);
                    String username = Homepage.getUserName();
                    addToFavoritePlaylist(username, mangbaihat.get(position).getIdBaiHat());
                }
            }
        });
        speed1x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speed = 1f;
                sendActionToService(ForegroundService.ACTION_SPEED);
            }
        });
        speed2x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speed = 2f;
                sendActionToService(ForegroundService.ACTION_SPEED);
            }
        });
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(ForegroundService.ACTION_PAUSE);
//                sktime.setProgress(0);
                mangbaihat.clear();
                finish();
            }
        });
    }

    private void NextMusic(){
        imgplay.setImageResource(R.drawable.iconpause);
        timeValue = 0;
    }
    private void completeNextMusic() {
        if (mangbaihat.size() > 0){
            NextMusic();
            setView(username, mangbaihat.get(position).getIdBaiHat(),
                    mangbaihat.get(position).getHinhBaiHat(), mangbaihat.get(position).getTenBaiHat(), mangbaihat.get(position).getCaSi());
        }
    }
    private void PreviousMusic(){
        imgplay.setImageResource(R.drawable.iconpause);
        timeValue = 0;
    }
    private void completePreviousMusic() {
        if (mangbaihat.size() > 0){
            PreviousMusic();
            setView(username, mangbaihat.get(position).getIdBaiHat(),
                    mangbaihat.get(position).getHinhBaiHat(), mangbaihat.get(position).getTenBaiHat(), mangbaihat.get(position).getCaSi());
        }
    }
    @SuppressWarnings("deprecation")
    private void setViewStart(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mangbaihat.size() > 0){
                    setView(username, mangbaihat.get(position).getIdBaiHat(),
                            mangbaihat.get(position).getHinhBaiHat(), mangbaihat.get(position).getTenBaiHat(), mangbaihat.get(position).getCaSi());
                }else {
                    handler.postDelayed(this, 300);
                }
            }
        }, 500);
    }
    private void setView(String username, int idBaiHat, String hinhBaiHat, String tenBaiHat, String tenCaSi){
        fragment_dia_nhac.PlayNhac(hinhBaiHat);
        Objects.requireNonNull(getSupportActionBar()).setTitle(tenBaiHat);
        txtSinger.setText(tenCaSi);
        txtNameSong.setText(tenBaiHat);
        checkLove = false;
        imgTim.setImageResource(R.drawable.iconlove1);
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if(intent !=null){
            if(intent.hasExtra("cakhuc")){
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baiHat);
            }
            if(intent.hasExtra("baihatyeuthich")){
                BaiHat baiHat = intent.getParcelableExtra("baihatyeuthich");
                mangbaihat.add(baiHat);
            }
            if(intent.hasExtra("cacbaihat")){
                ArrayList<BaiHat> baiHats = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = baiHats;
            }

        }

    }

    private void init(){
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        txtTimesong = findViewById(R.id.textViewruntime);
        txtTotaltimesong = findViewById(R.id.textViewtimetotal);
        txtNameSong = findViewById(R.id.textViewtenbaihatplaynhac);
        txtSinger = findViewById(R.id.textViewtencasiplaynhac);
        sktime = findViewById(R.id.seekBartime);
        imgplay = findViewById(R.id.imageButtonplaypause);
        imgpre = findViewById(R.id.imageButtonpreview);
        imgnext = findViewById(R.id.imageButtonnext);
        imgrandom = findViewById(R.id.imageButtonSuffle);
        imgrepeat = findViewById(R.id.imageButtonRepeat);
        viewPagerdianhac = findViewById(R.id.viewPagerdianhac);
        imgTim = findViewById(R.id.imageViewtimplaynhac);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        adapternhac = new ViewPagerPlayListnhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerdianhac.setAdapter(adapternhac);
        speed1x = findViewById(R.id.tvSpeedUp1x);
        speed2x = findViewById(R.id.tvSpeedUp2x);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(position);
        setSupportActionBar(toolbarplaynhac);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setTitleTextColor(Color.BLACK);
    }


    public void addToFavoritePlaylist(String username,Integer id){
        DataService dataService = APIService.getService();
        Call<Void> callBack = dataService.insertyeuthich(username, id);
        callBack.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
    private void TimeSong(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(duration));
        sktime.setMax(duration);
    }
    private void handleMusic(int action){
        switch (action){
            case ForegroundService.ACTION_PAUSE:
                imgplay.setImageResource(R.drawable.iconplay);
                break;
            case ForegroundService.ACTION_RESUME:
                imgplay.setImageResource(R.drawable.iconpause);
                break;
            case ForegroundService.ACTION_NEXT:
                completeNextMusic();
                break;
            case ForegroundService.ACTION_PREVIOUS:
                completePreviousMusic();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendActionToService(ForegroundService.ACTION_CLEAR);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void sendActionToService(int action) {
        Intent intent = new Intent(this, ForegroundService.class);
        intent.putExtra("action_music_service", action);
        intent.putExtra("duration", durationToService);
        intent.putExtra("repeat_music", repeat);
        intent.putExtra("random_music", checkrandom);
        intent.putExtra("speed_music", speed);
        startService(intent);
    }
}