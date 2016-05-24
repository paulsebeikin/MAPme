package psyblaze.mapme;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    GoogleMap gmap;
    LatLng location;
    String snippet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // get the intent so that the startMarker can be extracted
        Intent intent = getIntent();
        location = intent.getParcelableExtra("startMarker");
        snippet = String.valueOf(location.latitude) + ", " + String.valueOf(location.longitude);

        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map.getView().setClickable(true);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                gmap.animateCamera(CameraUpdateFactory.newLatLng(location));
                gmap.animateCamera(CameraUpdateFactory.zoomIn());
                gmap.addMarker(new MarkerOptions().position(location).title("Location").snippet(snippet));
                gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        gmap.clear();
                        location = latLng;
                        snippet = String.valueOf(latLng.latitude) + ", " + String.valueOf(latLng.longitude);
                        gmap.addMarker(new MarkerOptions().position(latLng).title("Location").snippet(snippet));
                        gmap.animateCamera(CameraUpdateFactory.newLatLng(location));
                        gmap.animateCamera(CameraUpdateFactory.zoomIn());
                    }
                });
            }
        });
    }

    public void onSave(View view){
        Intent resultInt = new Intent(this, NewRecordActivity.class);
        resultInt.putExtra("location", location);
        setResult(Activity.RESULT_OK, resultInt);
        finish();
    }
}
