
package tdtu.edu.vn.musicapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaiHat implements Parcelable {

@SerializedName("IdBaiHat")
@Expose
private Integer idBaiHat;
@SerializedName("TenBaiHat")
@Expose
private String tenBaiHat;
@SerializedName("HinhBaiHat")
@Expose
private String hinhBaiHat;
@SerializedName("CaSi")
@Expose
private String caSi;
@SerializedName("LinkBaiHat")
@Expose
private String linkBaiHat;
@SerializedName("LuotThich")
@Expose
private Integer luotThich;

    protected BaiHat(Parcel in) {
        if (in.readByte() == 0) {
            idBaiHat = null;
        } else {
            idBaiHat = in.readInt();
        }
        tenBaiHat = in.readString();
        hinhBaiHat = in.readString();
        caSi = in.readString();
        linkBaiHat = in.readString();
        if (in.readByte() == 0) {
            luotThich = null;
        } else {
            luotThich = in.readInt();
        }
    }

    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
        @Override
        public BaiHat createFromParcel(Parcel in) {
            return new BaiHat(in);
        }

        @Override
        public BaiHat[] newArray(int size) {
            return new BaiHat[size];
        }
    };

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

public String getCaSi() {
return caSi;
}

public void setCaSi(String caSi) {
this.caSi = caSi;
}

public String getLinkBaiHat() {
return linkBaiHat;
}

public void setLinkBaiHat(String linkBaiHat) {
this.linkBaiHat = linkBaiHat;
}

public Integer getLuotThich() {
return luotThich;
}

public void setLuotThich(Integer luotThich) {
this.luotThich = luotThich;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (idBaiHat == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(idBaiHat);
        }
        parcel.writeString(tenBaiHat);
        parcel.writeString(hinhBaiHat);
        parcel.writeString(caSi);
        parcel.writeString(linkBaiHat);
        if (luotThich == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(luotThich);
        }
    }
}