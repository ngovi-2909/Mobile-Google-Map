package tdtu.edu.vn.musicapp.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayList implements Serializable {

@SerializedName("IdPlaylist")
@Expose
private Integer idPlaylist;
@SerializedName("Ten")
@Expose
private String ten;
@SerializedName("HinhPlaylist")
@Expose
private String hinhPlaylist;
@SerializedName("Icon")
@Expose
private String icon;

public Integer getIdPlaylist() {
return idPlaylist;
}

public void setIdPlaylist(Integer idPlaylist) {
this.idPlaylist = idPlaylist;
}

public String getTen() {
return ten;
}

public void setTen(String ten) {
this.ten = ten;
}

public String getHinhPlaylist() {
return hinhPlaylist;
}

public void setHinhPlaylist(String hinhPlaylist) {
this.hinhPlaylist = hinhPlaylist;
}

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

}