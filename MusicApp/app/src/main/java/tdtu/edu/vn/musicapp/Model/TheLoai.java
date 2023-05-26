package tdtu.edu.vn.musicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TheLoai implements Serializable {

@SerializedName("IdTheLoai")
@Expose
private Integer idTheLoai;
@SerializedName("IdKeyChuDe")
@Expose
private String idKeyChuDe;
@SerializedName("TenTheLoai")
@Expose
private String tenTheLoai;
@SerializedName("HinhTheLoai")
@Expose
private String hinhTheLoai;

public Integer getIdTheLoai() {
return idTheLoai;
}

public void setIdTheLoai(Integer idTheLoai) {
this.idTheLoai = idTheLoai;
}

public String getIdKeyChuDe() {
return idKeyChuDe;
}

public void setIdKeyChuDe(String idKeyChuDe) {
this.idKeyChuDe = idKeyChuDe;
}

public String getTenTheLoai() {
return tenTheLoai;
}

public void setTenTheLoai(String tenTheLoai) {
this.tenTheLoai = tenTheLoai;
}

public String getHinhTheLoai() {
return hinhTheLoai;
}

public void setHinhTheLoai(String hinhTheLoai) {
this.hinhTheLoai = hinhTheLoai;
}

}