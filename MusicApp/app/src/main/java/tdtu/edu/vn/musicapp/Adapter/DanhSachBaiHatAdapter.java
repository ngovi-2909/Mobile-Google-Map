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

import java.util.ArrayList;

import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;

public class DanhSachBaiHatAdapter extends RecyclerView.Adapter<DanhSachBaiHatAdapter.ViewHolder> {

    Context context;
    ArrayList<BaiHat> baiHats;

    public DanhSachBaiHatAdapter(Context context, ArrayList<BaiHat> baiHats) {
        this.context = context;
        this.baiHats = baiHats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danhsachbaihat, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = baiHats.get(position);
        holder.tvTenCasi.setText(baiHat.getCaSi());
        holder.tvTenBaiHat.setText(baiHat.getTenBaiHat());
        holder.tvIndex.setText(position+ 1 +"");
    }

    @Override
    public int getItemCount() {
        return baiHats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvIndex, tvTenBaiHat, tvTenCasi;
        ImageView imgLuotThich;
        boolean checklove = false;
        String username;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvDSBHIndex);
            tvTenBaiHat = itemView.findViewById(R.id.tvTenBaiHat);
            tvTenCasi = itemView.findViewById(R.id.tvTenCaSiBaiHat);

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
//    private Integer getIdBaiHat(String name){
//        for(BaiHat i: baiHats){
//            if(i.getTenBaiHat().equals(name)){
//                return i.getIdBaiHat();
//            }
//        }
//        return null;
      }
}
