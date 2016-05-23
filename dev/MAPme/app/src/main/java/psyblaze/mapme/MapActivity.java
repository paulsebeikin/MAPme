package psyblaze.mapme;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity {

    GoogleMap gmap;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map.getView().setClickable(true);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        location = latLng;
                        gmap.addMarker(new MarkerOptions().position(latLng).title("Location"));
                    }
                });
            }
        });
    }

    public void onSave(View view){
        Intent saveInt = new Intent(this, NewRecordActivity.class);
        saveInt.putExtra("location", new Double[]{location.longitude, location.latitude});
        //return getParentActivityIntent().set
    }
}
