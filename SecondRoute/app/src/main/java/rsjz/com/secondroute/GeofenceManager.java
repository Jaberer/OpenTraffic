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
    private final float METERS_TO_MILES = (float) 0.000621371;

    public GeofenceManager(Context context)
    {
        Geofence.Builder HomeBuilder = new Geofence.Builder();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        HomeBuilder.setCircularRegion(prefs.getFloat("homelat", 0), prefs.getFloat("homelng", 0),(float) (0.5*1609.34));
        Geofence Destination;
    }
}
