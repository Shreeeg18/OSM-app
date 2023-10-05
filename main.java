import android.content.Context;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;

        import org.osmdroid.api.IMapController;
        import org.osmdroid.config.Configuration;
        import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
        import org.osmdroid.util.GeoPoint;
        import org.osmdroid.views.MapView;
        import android.Manifest;


public class MainActivity extends AppCompatActivity {
    private MapView map;
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the osmdroid library
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));

        // Create and configure the MapView
        MapView map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

            // Initialize LocationManager
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Initialize LocationListener
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Update map with current location
                    if (map != null) {
                        IMapController mapController = map.getController();
                        GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                        mapController.setCenter(geoPoint);
                    }
                }

                // Implement other LocationListener methods (if needed)
                // onStatusChanged, onProviderEnabled, onProviderDisabled
            };

            // Request location updates (you may need to handle permissions)
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                // Handle the case where permissions are not granted
                // You can request permissions here or handle it in a user-friendly way
            }
        }

    @Override
    protected void onResume() {
        super.onResume();
        MapView map = findViewById(R.id.mapview);
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MapView map = findViewById(R.id.mapview);
        map.onPause();
    }
}
