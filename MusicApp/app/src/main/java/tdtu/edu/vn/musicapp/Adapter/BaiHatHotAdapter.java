package tdtu.edu.vn.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Activity.Homepage;
import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class BaiHatHotAdapter extends RecyclerView.Adapter<BaiHatHotAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> baiHats;
    String username;
    boolean checklove;

    public BaiHatHotAdapter(Context context, ArrayList<BaiHat> baiHats) {
        this.context = context;
        this.baiHats = baiHats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_baihat_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = baiHats.get(position);
        holder.tvTen.setText(baihat.getTenBaiHat());
        holder.tvCasi.setText(baihat.getCaSi());
        Picasso.with(context).load(baihat.getHinhBaiHat()).into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return baiHats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTen, tvCasi;
        ImageView imgHinh, imgLuotThich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenBaiHatHot);
            tvCasi = itemView.findViewById(R.id.tvTenCasiBaiHatHot);
            imgHinh = itemView.findViewById(R.id.ivBaiHatHot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", baiHats.get(getPosition()));
                    context.startActivity(intent);
                }
            });
    }

//    public void addToFavoritePlaylist(String username,Integer id){
//        DataService dataService = APIService.getService();
//        Call<Void> callBack = dataService.insertyeuthich(username, id);
//        callBack.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//            }
//        });
//    }
//
//    public void removeFromFavoritePlaylist(String username, Integer id){
//        DataService dataService = APIService.getService();
//        Call<Void> callBack = dataService.deleteyeuthich(username, id);
//        callBack.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private Integer getIdBaiHat(String name){
//        for(BaiHat i: baiHats){
//            if(i.getTenBaiHat().equals(name)){
//                return i.getIdBaiHat();
//            }
//        }
//        return null;
    }
}