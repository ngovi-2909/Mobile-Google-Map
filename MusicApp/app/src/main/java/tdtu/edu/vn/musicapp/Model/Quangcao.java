package tdtu.edu.vn.musicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Quangcao implements Serializable {

@SerializedName("idQuangCao")
@Expose
private Integer idQuangCao;
@SerializedName("hinhanh")
@Expose
private String hinhanh;
@SerializedName("noidung")
@Expose
private String noidung;
@SerializedName("idBaiHat")
@Expose
private Integer idBaiHat;
@SerializedName("tenBaiHat")
@Expose
private String tenBaiHat;
@SerializedName("hinhBaiHat")
@Expose
private String hinhBaiHat;

public Integer getIdQuangCao() {
return idQuangCao;
}

public void setIdQuangCao(Integer idQuangCao) {
this.idQuangCao = idQuangCao;
}

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

public String getNoidung() {
return noidung;
}

public void setNoidung(String noidung) {
this.noidung = noidung;
}

public Integer getIdBaiHat() {
return idBaiHat;
}

public void setIdBaiHat(Integer idBaiHat) {
this.idBaiHat = idBaiHat;
}

public String getTenBaiHat() {
return tenBaiHat;
}

public void setTenBaiHat(String tenBaiHat) {
this.tenBaiHat = tenBaiHat;
}

public String getHinhBaiHat() {
return hinhBaiHat;
}

public void setHinhBaiHat(String hinhBaiHat) {
this.hinhBaiHat = hinhBaiHat;
}

}