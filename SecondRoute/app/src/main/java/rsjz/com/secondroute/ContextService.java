package rsjz.com.secondroute;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


/**
 * Handles Geofencing and running of BackgroundService
 */
public class ContextService extends Service implements LocationListener
{
    public static boolean isHeadingHome;
    LocationManager locationManager;
    float currentLat = 0;
    float currentLng = 0;
    public ContextService()
    {
        super();

    }

    @Override
    public void onDestroy() {
        stopActiveTracking();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        return super.onStartCommand(intent, flags, startId);
    }
    public void startActiveTracking()
    {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    public void stopActiveTracking()
    {
        locationManager.removeUpdates(this);
    }
    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startIntentService()
    {
        if (currentLat != 0 && currentLng != 0) {
            Intent compareResults = new Intent(this, BackgroundService.class);
            compareResults.putExtra("lat", currentLat);
            compareResults.putExtra("lng", currentLng);
        }
    }
    public void onLocationChanged(Location location) {
        currentLat = (float) location.getLatitude();
        currentLng = (float) location.getLongitude();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public void onProviderEnabled(String provider) {}

    public void onProviderDisabled(String provider) {}
}
