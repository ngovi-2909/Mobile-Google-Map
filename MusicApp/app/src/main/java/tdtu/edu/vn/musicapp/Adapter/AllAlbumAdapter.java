package tdtu.edu.vn.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
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

public class AllAlbumAdapter extends RecyclerView.Adapter<AllAlbumAdapter.ViewHolder> {

    Context context;
    ArrayList<Album> albums;

    public AllAlbumAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_all_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albums.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.ivAllAlbum);
        holder.tvTenAllAlbum.setText(album.getTenAlbum());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivAllAlbum;
        TextView tvTenAllAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAllAlbum = itemView.findViewById(R.id.ivDSAllAlbum);
            tvTenAllAlbum = itemView.findViewById(R.id.tvDSAllAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("album", albums.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
