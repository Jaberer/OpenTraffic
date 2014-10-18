package rsjz.com.secondroute;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

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
        ArrayList<String> pr = BingMapsAPI.getDirectionsList(this);
        ArrayList<String> cr = (ArrayList<String>) BingMapsAPI.getPreferredDirectionsList(this);
        compareRoutes(pr, cr);      // later do voice here
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
