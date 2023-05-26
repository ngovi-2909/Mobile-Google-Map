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

import tdtu.edu.vn.musicapp.Activity.DanhSachBaiHatActivity;
import tdtu.edu.vn.musicapp.Activity.DanhSachCacPlayListActivity;
import tdtu.edu.vn.musicapp.Model.PlayList;
import tdtu.edu.vn.musicapp.R;

public class DanhSachCacPlayListAdapter extends RecyclerView.Adapter<DanhSachCacPlayListAdapter.ViewHolder> {
    Context context;
    ArrayList<PlayList> playLists;

    public DanhSachCacPlayListAdapter(Context context, ArrayList<PlayList> playLists) {
        this.context = context;
        this.playLists = playLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danhsachcacplaylist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playList = playLists.get(position);
        Picasso.with(context).load(playList.getIcon()).into(holder.img);
        holder.tvTenPL.setText(playList.getTen());
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenPL;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ivDSCPL);
            tvTenPL = itemView.findViewById(R.id.tvDSCPL);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("itemPlayList", playLists.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}
