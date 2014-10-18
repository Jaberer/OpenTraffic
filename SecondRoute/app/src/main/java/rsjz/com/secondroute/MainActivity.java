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


    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ((TextView)findViewById(R.id.home_address)).setText("Home: " + prefs.getString("home_address", "unset"));
        ((TextView)findViewById(R.id.work_address)).setText("Work: " + prefs.getString("work_address", "unset"));
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
                ArrayList<String> directionsList = BingMapsAPI.getDirectionsList(prefs.getFloat("homelat", 0), prefs.getFloat("homelng", 0), prefs.getFloat("worklat", 0), prefs.getFloat("worklng", 0));
                Message message = handler.obtainMessage();
                message.obj = directionsList.toString();
                handler.sendMessage(message);

            }
        }).start();
        */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
