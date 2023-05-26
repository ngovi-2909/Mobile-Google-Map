package tdtu.edu.vn.musicapp.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tdtu.edu.vn.musicapp.Model.Album;
import tdtu.edu.vn.musicapp.Model.BaiHat;
import tdtu.edu.vn.musicapp.Model.ChuDe;
import tdtu.edu.vn.musicapp.Model.PlayList;
import tdtu.edu.vn.musicapp.Model.Quangcao;
import tdtu.edu.vn.musicapp.Model.TheLoai;
import tdtu.edu.vn.musicapp.Model.TheloaiChuDe;

public interface DataService{

    @GET("songbanner.php")
    Call<List<Quangcao>> getDataBanner();

    @GET("playlistforcurrentday.php")
    Call<List<PlayList>> getDataPlayListCurrentDay();

    @GET("chudeandtheloaicurrentday.php")
    Call<TheloaiChuDe> getDataChuDeTheLoai();
    @GET("album.php")
    Call<List<Album>> getDataAlbumHot();

    @GET("baihatduocthich.php")
    Call<List<BaiHat>> getDataBaiHatHot();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatFollowBanner(@Field("idquangcao") Integer idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDataBaiHatFollowPlayList(@Field("idplaylist") Integer idquangcao);

    @GET("danhsachcacplaylist.php")
    Call<List<PlayList>> getAllPlayList();

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> getSearchBaiHat(@Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("danhsachbaihatyeuthich.php")
    Call<List<BaiHat>> getFavoriteList(@Field("username") String username);

    @FormUrlEncoded
    @POST("insertbaihatyeuthich.php")
    Call<Void> insertyeuthich(@Field("username") String username, @Field("idbaihat") int idbaihat);
    @FormUrlEncoded
    @POST("deletebaihatyeuthich.php")
    Call<Void> deleteyeuthich(@Field("username") String username, @Field("idbaihat") int idbaihat);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDSBHFollowTheLoai(@Field("idtheloai") Integer idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>> getAllChuDe();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> getTLFollowCD(@Field("idchude") Integer idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> getAllAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> getDSBHFollowAlbum(@Field("idalbum") Integer idalbum);

}
