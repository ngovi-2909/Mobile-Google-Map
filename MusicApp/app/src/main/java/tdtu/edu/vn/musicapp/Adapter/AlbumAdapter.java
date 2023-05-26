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
import tdtu.edu.vn.musicapp.Model.Album;
import tdtu.edu.vn.musicapp.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        holder.tvTenCasiAlbum.setText(album.getTenCaSiAlbum());
        holder.tvTenAlbum.setText(album.getTenAlbum());
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imgHinhAlbum);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhAlbum;
        TextView tvTenAlbum, tvTenCasiAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAlbum = itemView.findViewById(R.id.ivAlbum);
            tvTenAlbum = itemView.findViewById(R.id.tvTenAlbum);
            tvTenCasiAlbum = itemView.findViewById(R.id.tvTenCasiAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("album", albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }

}
