package tdtu.edu.vn.musicapp.LocalService;

import static tdtu.edu.vn.musicapp.LocalService.ChannelNotification.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;

public class ForegroundService extends Service {
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_NEXT = 3;
    public static final int ACTION_PREVIOUS = 4;
    public static final int ACTION_DURATION = 5;
    public static final int ACTION_REPEAT = 6;
    public static final int ACTION_RANDOM = 7;
    public static final int ACTION_STOP = 8;
    public static final int ACTION_CLEAR = 9;
    public static final int ACTION_SPEED = 10;
    public static final int ACTION_LARGE_ICON_CLICK = 11;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying, isRepeat, isRandom;
    private String urlImage;
    private ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    private int positionPlayer = 0, duration = 0, seekToTime = 0, curentime = 0;
    private float speedMusic;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            if (intent.hasExtra("obj_song_baihat")){
                clearArray();
                mangbaihat = intent.getParcelableArrayListExtra("obj_song_baihat");
            }
        }
        assert intent != null;
        if (!intent.hasExtra("action_music_service")){
            CompleteAndStart();
        }
        int actionMusic = intent.getIntExtra("action_music_service", 0);
        seekToTime = intent.getIntExtra("duration", 0);
        isRepeat = intent.getBooleanExtra("repeat_music", false);
        isRandom = intent.getBooleanExtra("random_music", false);
        speedMusic = intent.getFloatExtra("speed_music", 0f);
        handleActionMusic(actionMusic);
        return START_NOT_STICKY;
    }
    private void clearArray() {
        positionPlayer = 0;
        mangbaihat.clear();
    }
    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    pauseMusic(mangbaihat.get(positionPlayer).getTenBaiHat(), mangbaihat.get(positionPlayer).getCaSi());
                }
                break;
            case ACTION_RESUME:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    resumeMusic(mangbaihat.get(positionPlayer).getTenBaiHat(), mangbaihat.get(positionPlayer).getCaSi());
                }
                break;
            case ACTION_NEXT:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    nextMusic(mangbaihat.size());
                }
                CompleteAndStart();
                break;
            case ACTION_PREVIOUS:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    previousMusic(mangbaihat.size());
                }
                CompleteAndStart();
                break;
            case ACTION_DURATION:
                mediaPlayer.seekTo(seekToTime);
                break;
            case ACTION_CLEAR:
                mediaPlayer.stop();
                stopForeground(true);
                break;
            case ACTION_SPEED:
                speedUp(speedMusic);
                break;
        }
    }
    private void speedUp(float speed){
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
    }
    private void startMusic(String linkBaiHat) {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        new playMP3().onPostExecute(linkBaiHat);
        isPlaying = true;
        duration = mediaPlayer.getDuration();
        sendActionToPlayNhacActivity(ACTION_RESUME);
        sendTimeCurrent();
    }
    private void resumeMusic(String tenBaiHat, String tenCaSi) {
        if (mediaPlayer != null && !isPlaying){
            mediaPlayer.start();
            isPlaying = true;
            sendNotificationMedia(tenBaiHat, tenCaSi);
            sendActionToPlayNhacActivity(ACTION_RESUME);
        }
    }
    private void pauseMusic(String tenBaiHat, String tenCaSi) {
        if (mediaPlayer != null && isPlaying){
            mediaPlayer.pause();
            isPlaying = false;
            sendNotificationMedia(tenBaiHat, tenCaSi);
            sendActionToPlayNhacActivity(ACTION_PAUSE);
        }
    }
    private void nextMusic(int sizeArray){
        positionPlayer++;
        if (isRepeat){
            positionPlayer -= 1;
        }else if (isRandom){
            Random random = new Random();
            positionPlayer = random.nextInt(sizeArray);
        }
        if (positionPlayer >= sizeArray){
            positionPlayer = 0;
        }
        sendActionToPlayNhacActivity(ACTION_NEXT);
    }
    private void previousMusic(int sizeArray){
        positionPlayer--;
        if (isRepeat){
            positionPlayer += 1;
        }else if (isRandom){
            Random random = new Random();
            positionPlayer = random.nextInt(sizeArray);
        }
        if (positionPlayer < 0){
            positionPlayer = sizeArray-1;
        }
        sendActionToPlayNhacActivity(ACTION_PREVIOUS);
    }
    private void CompleteAndStart(){
        if (mangbaihat != null && mangbaihat.size() > 0){
            startMusic(mangbaihat.get(positionPlayer).getLinkBaiHat());
            urlImage = mangbaihat.get(positionPlayer).getHinhBaiHat();
            sendNotificationMedia(mangbaihat.get(positionPlayer).getTenBaiHat(), mangbaihat.get(positionPlayer).getCaSi());
        }
    }
    @SuppressLint("NotificationTrampoline")
    private void sendNotificationMedia(String tenBaiHat, String tenCaSi){
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");
        NotificationCompat.Builder notificationBuilder;
        Intent myIntent = new Intent(this, PlayNhacActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_ONE_SHOT);
            notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.iconlogo)
                    .setContentText("H V")
                    .setContentTitle(tenBaiHat)
                    .setContentIntent(pendingIntent)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0,1,2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .addAction(R.drawable.previous, "Previous", getPendingIntent(this, ACTION_PREVIOUS));
        if (isPlaying){
            notificationBuilder.addAction(R.drawable.pause, "Pause", getPendingIntent(this, ACTION_PAUSE));
        }
        else {
            notificationBuilder.addAction(R.drawable.playbuttonarrowhead, "Pause", getPendingIntent(this, ACTION_RESUME));
        }

        notificationBuilder.addAction(R.drawable.next, "Next", getPendingIntent(this, ACTION_NEXT));
        Picasso.with(getApplicationContext()).load(urlImage)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        notificationBuilder.setLargeIcon(bitmap);

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

        Notification notification = notificationBuilder.build();

        startForeground(1, notification);
    }
    private PendingIntent getPendingMusicPlayer(Context context, int action){
        Intent intent = new Intent(this, BroadcastReceiverAction.class);
        intent.putExtra("icon_noti_hinh", mangbaihat.get(positionPlayer).getHinhBaiHat());
        intent.putExtra("icon_noti_tenbaihat", mangbaihat.get(positionPlayer).getTenBaiHat());
        intent.putExtra("icon_noti_tencasi", mangbaihat.get(positionPlayer).getCaSi());
        return  PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(this, BroadcastReceiverAction.class);
        intent.putExtra("action_music", action);
        Log.d("aa","ac");
        return  PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void sendActionToPlayNhacActivity(int action){
        if (mangbaihat != null){
            Intent intent = new Intent("send_data_to_activity");
            intent.putExtra("status_player", isPlaying);
            intent.putExtra("action_music", action);
            intent.putExtra("position_music", positionPlayer);
            intent.putExtra("duration_music", duration);
            intent.putExtra("seektomusic", curentime);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }
    @SuppressWarnings("deprecation")
    private void sendTimeCurrent(){
        if (mediaPlayer != null){
            curentime = mediaPlayer.getCurrentPosition();
            sendActionToPlayNhacActivity(ACTION_DURATION);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        sendTimeCurrent();
                    }
                }
            }, 1000);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    class playMP3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mangbaihat != null && mangbaihat.size() > 0){
                            nextMusic(mangbaihat.size());
                        }
                        CompleteAndStart();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();


        }
    }
}

