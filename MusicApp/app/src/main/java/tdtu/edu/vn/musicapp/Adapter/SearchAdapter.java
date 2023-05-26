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

import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.R;
import tdtu.edu.vn.musicapp.Service.APIService;
import tdtu.edu.vn.musicapp.Service.DataService;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> baiHats;

    public SearchAdapter(Context context, ArrayList<BaiHat> baiHats) {
        this.context = context;
        this.baiHats = baiHats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dong_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = baiHats.get(position);
        holder.tvTenBaiHat.setText(baiHat.getTenBaiHat());
        holder.tvTenCasi.setText(baiHat.getCaSi());
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.ivBaiHat);
    }

    @Override
    public int getItemCount() {
        return baiHats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenBaiHat, tvTenCasi;
        ImageView ivBaiHat, ivLuotThich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBaiHat = itemView.findViewById(R.id.tvSearchTenBaiHat);
            tvTenCasi = itemView.findViewById(R.id.tvSearchTenCasi);
            ivBaiHat = itemView.findViewById(R.id.ivSearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc", baiHats.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}