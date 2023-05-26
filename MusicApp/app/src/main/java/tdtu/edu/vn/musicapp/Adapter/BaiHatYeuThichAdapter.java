package tdtu.edu.vn.musicapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tdtu.edu.vn.musicapp.Activity.Homepage;
import tdtu.edu.vn.musicapp.Activity.MainActivity;
import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class BaiHatYeuThichAdapter extends RecyclerView.Adapter<BaiHatYeuThichAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> baihatyeuthiches;
    boolean checklove = true;
    String username;


    public BaiHatYeuThichAdapter(Context context, ArrayList<BaiHat> baihatyeuthiches) {
        this.context = context;
        this.baihatyeuthiches = baihatyeuthiches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_baihatyeuthich, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = baihatyeuthiches.get(position);
        holder.tvTen.setText(baihat.getTenBaiHat());
        holder.tvCasi.setText(baihat.getCaSi());
        Picasso.with(context).load(baihat.getHinhBaiHat()).into(holder.imgHinh);
    }

    @Override
    public int getItemCount() {
        return baihatyeuthiches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTen, tvCasi;
        ImageView imgHinh, imgLuotThich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenBaiHatYT);
            tvCasi = itemView.findViewById(R.id.tvTenCasiBaiHatYT);
            imgHinh = itemView.findViewById(R.id.ivBaiHatYT);
            imgLuotThich = itemView.findViewById(R.id.ivLuotThichBHYT);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("baihatyeuthich", baihatyeuthiches.get(getPosition()));
                    context.startActivity(intent);

                }
            });

            imgLuotThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(checklove == true){
                        checklove = false;
                        imgLuotThich.setImageResource(R.drawable.iconlove1);
                        Integer idbaihat = getIdBaiHat(tvTen.getText().toString());
                        username = Homepage.getUserName();
                        removeFromFavoritePlaylist(username,idbaihat);
                    }
                }
            });

        }
    }
    public void removeFromFavoritePlaylist(String username, Integer id){
        DataService dataService = APIService.getService();
        Call<Void> callBack = dataService.deleteyeuthich(username, id);
        callBack.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private Integer getIdBaiHat(String name){
        for(BaiHat i: baihatyeuthiches){
            if(i.getTenBaiHat().equals(name)){
                return i.getIdBaiHat();
            }
        }
        return null;
    }

}
