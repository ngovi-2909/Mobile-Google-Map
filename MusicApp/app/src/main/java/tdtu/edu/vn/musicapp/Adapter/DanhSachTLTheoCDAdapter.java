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
import tdtu.edu.vn.musicapp.Model.TheLoai;
import tdtu.edu.vn.musicapp.R;


public class DanhSachTLTheoCDAdapter extends RecyclerView.Adapter<DanhSachTLTheoCDAdapter.ViewHolder>{

    Context context;
    ArrayList<TheLoai> theLoais;

    public DanhSachTLTheoCDAdapter(Context context, ArrayList<TheLoai> theLoais) {
        this.context = context;
        this.theLoais = theLoais;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_theloai_theo_chude,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = theLoais.get(position);
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(holder.imageView);
        holder.tvTenTheLoai.setText(theLoai.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return theLoais.size()  ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvTenTheLoai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivTLtheoCD);
            tvTenTheLoai = itemView.findViewById(R.id.tvTenTLtheoCD);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("idtheloai", theLoais.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
