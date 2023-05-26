package tdtu.edu.vn.musicapp.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tdtu.edu.vn.musicapp.Activity.PlayNhacActivity;
import tdtu.edu.vn.musicapp.Adapter.PlaynhacAdapter;
import tdtu.edu.vn.musicapp.R;

public class Fragment_Play_Danh_Sach_Cac_Bai_Hat extends Fragment {
    View view;
    RecyclerView recyclerViewplaynhac;
    PlaynhacAdapter playnhacAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fraggment_play_danh_sach_cac_bai_hat, container, false);
        recyclerViewplaynhac = view.findViewById(R.id.recyclerviewPlaybaihat);
        if(PlayNhacActivity.mangbaihat.size() > 0)
        {
            playnhacAdapter = new PlaynhacAdapter(getActivity(), PlayNhacActivity.mangbaihat);
            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaynhac.setAdapter(playnhacAdapter);
        }

        return view;
    }
}
