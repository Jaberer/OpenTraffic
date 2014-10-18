package rsjz.com.secondroute;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.set_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SetAddressActivity.class);
                i.putExtra("home", true);
                startActivity(i);

            }
        });
        findViewById(R.id.set_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SetAddressActivity.class);
                i.putExtra("home", false);
                startActivity(i);
            }
        });
        findViewById(R.id.set_preferred_route).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ChoosePreferredRouteActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ((TextView)findViewById(R.id.home_address)).setText("Home: " + prefs.getString("home_address", "unset"));
        ((TextView)findViewById(R.id.work_address)).setText("Work: " + prefs.getString("work_address", "unset"));
        ((TextView)findViewById(R.id.work_address)).setText("Preferred Route: " + prefs.getString("preferredRoute", "unset"));

/*
       final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                ((TextView)findViewById(R.id.home_address)).setText((String) message.obj);

                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> directionsList = BingMapsAPI.getListOfPossibleRoutes(prefs.getFloat("homelat", 0), prefs.getFloat("homelng", 0), prefs.getFloat("worklat", 0), prefs.getFloat("worklng", 0));
                Message message = handler.obtainMessage();
                message.obj = directionsList.toString();
                handler.sendMessage(message);

            }
        }).start();
        */



    }
}
