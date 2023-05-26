package tdtu.edu.vn.musicapp.LocalService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;

public class BroadcastReceiverAction extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionMusic = intent.getIntExtra("action_music", 0);
        Intent intentService = new Intent(context, ForegroundService.class);
        intentService.putExtra("action_music_service", actionMusic);

        Intent intentLargeIcon = new Intent(context, PlayNhacActivity.class);
        int idBaiHat = intentService.getIntExtra("iconId",0);
        if(intentLargeIcon != null){

            intentLargeIcon.putExtra("idBaiHatFromNotification", idBaiHat);
            context.startActivity(intentLargeIcon);
            Log.d("Icon", "aaaa");
        }

        context.startService(intentService);
    }
}
