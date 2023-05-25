package tdtu.edu.mymap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.Manifest;
import android.provider.Settings;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap myMap;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    final int REQUEST_CODE = 52430;
    final int LOCATION_REQUEST_CODE = 52000;
    private SearchView searchView = null;
    private LatLng myLocation;
    private LatLng searchLocation;
    private Marker markerSearchLocation = null;
    private Polyline polyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if(markerSearchLocation != null)
                {
                    markerSearchLocation.remove();
                    polyLine.remove();
                }
                if(location != null || !location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    }catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                   if(!addressList.isEmpty())
                   {
                       Address address = addressList.get(0);
                       searchLocation = new LatLng(address.getLatitude(), address.getLongitude());
                       markerSearchLocation = myMap.addMarker(new MarkerOptions().position(searchLocation).title(location));
                       myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchLocation, 6));
                       polyLine = myMap.addPolyline(new PolylineOptions()
                               .add(searchLocation, myLocation).color(Color.RED));
                   }else {
                       Toast.makeText(MapsActivity.this, "Cannot Find Location", Toast.LENGTH_SHORT).show();
                   }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //Initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if(ActivityCompat.checkSelfPermission(MapsActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //when permission granted
            //call method to get location
            getCurrentLocation();
        }else
        {
            //when permission denied
            //request permission
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
        mapFragment.getMapAsync(this);
    }

    private void getCurrentLocation() {
        //Initialize task location
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null)
                {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //Initialize lat lng
                            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            //Create maker options
                            MarkerOptions markerOptions = new MarkerOptions().position(myLocation).title("I'm here");
                            // Zoom map
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
                            //Add maker on map
                            googleMap.addMarker(markerOptions);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED)
            {
                //when permission granted
                //call method to get location
                getCurrentLocation();
            }else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Permission")
                        .setMessage("Location permission is required to launch this app")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, LOCATION_REQUEST_CODE);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOCATION_REQUEST_CODE)
        {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isEnable)
            {
                getCurrentLocation();
                Toast.makeText(this, "Location is enable", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "Location is unable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
    }
}