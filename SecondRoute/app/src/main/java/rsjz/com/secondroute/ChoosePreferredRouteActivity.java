package rsjz.com.secondroute;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class ChoosePreferredRouteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_preferred_route);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> routes = BingMapsAPI.getListOfPossibleRoutes()
            }
        }).start();
    }


}
