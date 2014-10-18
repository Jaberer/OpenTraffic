package rsjz.com.secondroute;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;

/**
 * Created by Joseph on 10/17/2014.
 */
public class GeofenceManager
{
    private final float MILES_TO_METERS = (float) 1609.34;
    private final float RADIUS_IN_MILES = (float) 0.5;
    private final float HALF_MILE = (float) (RADIUS_IN_MILES*MILES_TO_METERS);

    private Geofence Home;
    private Geofence Work;

    /**
     * Creates Home and Destination Geofence
     * @param context
     */
    public GeofenceManager(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Geofence.Builder HomeBuilder = new Geofence.Builder();
        Geofence.Builder WorkBuilder = new Geofence.Builder();

        HomeBuilder.setCircularRegion(prefs.getFloat("homelat", 0), prefs.getFloat("homelng", 0),
                HALF_MILE);     // set homeGeofence Area
        HomeBuilder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        HomeBuilder.setNotificationResponsiveness(0);
        HomeBuilder.setRequestId("Home");
        Home = HomeBuilder.build();

        WorkBuilder.setCircularRegion(prefs.getFloat("worklat", 0), prefs.getFloat("worklng", 0),
                HALF_MILE);     // set workGeofence Area
        WorkBuilder.setExpirationDuration(Geofence.NEVER_EXPIRE);
        WorkBuilder.setNotificationResponsiveness(0);
        WorkBuilder.setRequestId("Destination");
        Work = WorkBuilder.build();
    }

    public void modifyHomeGeofence()
    {
        removeGeofences(List<String>, LocationClient.OnRemoveGeofencesResultListener)
    }

}
