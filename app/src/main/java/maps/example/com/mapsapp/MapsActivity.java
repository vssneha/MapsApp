package maps.example.com.mapsapp;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Loader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    public ArrayList<Location> arrList = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bdl = getIntent().getExtras();
        arrList = bdl.getParcelableArrayList("Data");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        ArrayList<LatLng> lat = new ArrayList<LatLng>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        int arrsize = arrList.size();

        int size= 0;
        if(arrsize > 0)
        {
            for(Location loc : arrList)
            {

                if(SplashScreen.offenseType.equals(loc.offenseType))
                {
                    size = size+1;

                    double latitude = Double.parseDouble(loc.latitude);
                    double longitude = Double.parseDouble(loc.longitude);

                    LatLng address = new LatLng(latitude, longitude);
                    lat.add(address);

                    builder.include(new LatLng(latitude, longitude));

                    if(!SplashScreen.showAll && size == 10)
                    {
                        break;
                    }
                }

            }

            for(LatLng lt : lat )
            {
                drawMarker(mMap, lt);
            }

            LatLngBounds bounds = builder.build();


            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            mMap.animateCamera(cu);
        }
        else
        {
            Toast.makeText(MapsActivity.this, R.string.warningMsg, Toast.LENGTH_SHORT).show();
        }
    }


    int index = 0;
    private void drawMarker(GoogleMap googleMap,LatLng point)
    {
        index = index + 1;

        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions = getMarkerPositionAndColorBasedOnCrimeRate(markerOptions, index, point);

        // Adding marker on the Google Map
        googleMap.addMarker(markerOptions);

    }

    public MarkerOptions getMarkerPositionAndColorBasedOnCrimeRate(MarkerOptions markerOptions, int index, LatLng point)
    {
        // Setting latitude and longitude for the marker
        if(index == 1)
        {
            markerOptions.position(point);
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        else if((index % 2) == 0 )
        {
            markerOptions.position(point);
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        }
        else if((index % 3) == 0)
        {
            markerOptions.position(point);
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        }
        else if((index % 5) == 0)
        {
            markerOptions.position(point);
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        else
        {
            markerOptions.position(point);
            markerOptions.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        }

        return  markerOptions;
    }

}
