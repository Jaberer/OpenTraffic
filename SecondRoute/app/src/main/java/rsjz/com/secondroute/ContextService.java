package rsjz.com.secondroute;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Handles Geofencing and running of BackgroundService
 */
public class ContextService extends Service
{
    public static boolean direction;

    public ContextService()
    {
        super();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startIntentService()
    {
        Intent CompareRoutes = new Intent(this, BackgroundService.class);
    }
}
