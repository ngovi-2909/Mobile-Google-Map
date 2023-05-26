package tdtu.edu.vn.musicapp.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import tdtu.edu.vn.musicapp.Model.PlayList;
import tdtu.edu.vn.musicapp.R;

public class PlayListAdapter extends ArrayAdapter<PlayList> {

    public PlayListAdapter(@NonNull Context context, int resource, @NonNull List<PlayList> objects) {
        super(context, resource, objects);
    }

    class ViewHolder{
        TextView tvtemPlayList;
        ImageView imgBG, imgPL;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist, null);
            viewHolder = new ViewHolder();
            viewHolder.tvtemPlayList = convertView.findViewById(R.id.tvtemPL);
            viewHolder.imgPL = convertView.findViewById(R.id.ivPL);
            viewHolder.imgBG = convertView.findViewById(R.id.imgvBGPL);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlayList playList = getItem(position);
        Picasso.with(getContext()).load(playList.getHinhPlaylist()).into(viewHolder.imgBG);
        Picasso.with(getContext()).load(playList.getIcon()).into(viewHolder.imgPL);
        viewHolder.tvtemPlayList.setText(playList.getTen());
        return convertView;
    }
}
