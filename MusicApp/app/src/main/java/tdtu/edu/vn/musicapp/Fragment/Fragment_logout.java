package tdtu.edu.vn.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import tdtu.edu.vn.musicapp.Activity.Homepage;
import tdtu.edu.vn.musicapp.Activity.login;
import tdtu.edu.vn.musicapp.Adapter.BaiHatYeuThichAdapter;
import tdtu.edu.vn.musicapp.R;

public class Fragment_logout extends Fragment {
    View view;
    ImageView imageView;
    Button btnLogout;
    String username;
    BaiHatYeuThichAdapter baiHatYeuThichAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        imageView = view.findViewById(R.id.imageProfile);
        btnLogout = view.findViewById(R.id.btndangxuat);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), login.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
