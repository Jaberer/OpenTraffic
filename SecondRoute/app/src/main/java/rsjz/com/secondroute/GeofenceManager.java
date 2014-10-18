package rsjz.com.secondroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.gms.location.Geofence;

/**
 * Created by Joseph on 10/17/2014.
 */
public class GeofenceManager
{
    private final float MILES_TO_METERS = (float) 1609.34;
    private final float RADIUS_IN_MILES = (float) 0.5;

    /**
     * Creates Home and Destination Geofence
     * @param context
     */
    public GeofenceManager(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Geofence.Builder HomeBuilder = new Geofence.Builder();
        Geofence.Builder DestinationBuilder = new Geofence.Builder();

        HomeBuilder.setCircularRegion(prefs.getFloat("homelat", 0), prefs.getFloat("homelng", 0),
                (float) (RADIUS_IN_MILES*MILES_TO_METERS));     // set homeGeofence Area
        HomeBuilder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        HomeBuilder.setNotificationResponsiveness(0);
        Geofence Home = HomeBuilder.build();

        DestinationBuilder.setCircularRegion(prefs.getFloat("worklat", 0), prefs.getFloat("worklng", 0),
                (float) (RADIUS_IN_MILES*MILES_TO_METERS));     // set workGeofence Area
        DestinationBuilder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        DestinationBuilder.setNotificationResponsiveness(0);
        Geofence Destination = DestinationBuilder.build();
    }
}
