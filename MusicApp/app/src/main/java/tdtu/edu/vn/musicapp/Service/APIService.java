package tdtu.edu.vn.musicapp.Service;
// ket hop DataService and API client
public class APIService {
    private static String base_url = "https://musicapphv.000webhostapp.com/Server/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class); // khoi tao methods of DataService va tra du lieu den no
    }
}
