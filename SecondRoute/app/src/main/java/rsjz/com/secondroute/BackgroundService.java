package rsjz.com.secondroute;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * Handles Route Comparison and TRACKING
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BackgroundService extends IntentService
{


    public BackgroundService()
    {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        //perform all code here
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float lat = prefs.getFloat("homelat", 0);
        float lng = prefs.getFloat("homelng", 0);
        if (!ContextService.isHeadingHome) //travelling to work
        {
            lat = prefs.getFloat("worklat", 0);
            lng = prefs.getFloat("latlng", 0);
        }
        ArrayList<Route> pr = BingMapsAPI.getDirectionsList(intent.getFloatExtra("lat", 0), intent.getFloatExtra("lng",0), lat, lng);
        ArrayList<String> cr = (ArrayList<String>) BingMapsAPI.getPreferredDirectionsList(this, ContextService.isHeadingHome);
        double maxConfidenceScore = 0;
        Route routeWithMaxConfidence = null;
        for (Route route : pr)
        {
            double confidenceScore = compareRoutes(route.instructions, cr);
            if (confidenceScore > maxConfidenceScore)
            {
                maxConfidenceScore = confidenceScore;
                routeWithMaxConfidence = route;
            }
        }
        if (maxConfidenceScore > .9) { //need to find match with original route, but also the fastest route can't be the original
            if (compareRoutes(pr.get(0).instructions, cr) < .9) {
                Intent i = new Intent(this, FasterRouteActivity.class);
                i.putExtra("instruction", pr.get(0).instructions.get(0));
                i.putExtra("differenceInTime", routeWithMaxConfidence.durationMinutes - pr.get(0).durationMinutes);
                startActivity(i);
            }
        }
    }



    private double compareRoutes(ArrayList<String> _pr, ArrayList<String> _cr)
    {
        double matchpercentage = 0.0;
        for(int i = 0; i < Math.min(_pr.size(), _pr.size()); i++) // i starts at 0, ends at smaller number
        {
            if(_pr.size() - i == _cr.size() - i)      // begins at end of list and minus i to iterate
            {
                matchpercentage++;
            }
            else
            {
                return matchpercentage / Math.min(_pr.size(), _cr.size());
            }
        }
        return matchpercentage / Math.min(_pr.size(), _cr.size());
    }
}
