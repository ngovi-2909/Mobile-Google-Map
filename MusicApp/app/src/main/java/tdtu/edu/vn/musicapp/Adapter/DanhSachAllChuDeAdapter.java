package tdtu.edu.vn.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tdtu.edu.vn.musicapp.Activity.DanhSachTheLoaiTheoChuDeActivity;
import tdtu.edu.vn.musicapp.Model.ChuDe;
import tdtu.edu.vn.musicapp.R;

public class DanhSachAllChuDeAdapter extends RecyclerView.Adapter<DanhSachAllChuDeAdapter.ViewHolder> {

    Context context;
    ArrayList<ChuDe> chuDes;

    public DanhSachAllChuDeAdapter(Context context, ArrayList<ChuDe> chuDes) {
        this.context = context;
        this.chuDes = chuDes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_cac_chu_de, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chuDe = chuDes.get(position);
        Picasso.with(context).load(chuDe.getHinhChuDe()).into(holder.imgChuDe);
    }

    @Override
    public int getItemCount() {
        return chuDes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgChuDe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChuDe = itemView.findViewById(R.id.ivDongCacChuDe);
            imgChuDe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachTheLoaiTheoChuDeActivity.class);
                    intent.putExtra("chude", chuDes.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
