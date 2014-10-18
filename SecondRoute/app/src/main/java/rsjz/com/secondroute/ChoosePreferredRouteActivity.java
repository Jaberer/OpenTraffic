package rsjz.com.secondroute;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class ChoosePreferredRouteActivity extends Activity {
    ArrayList<String> routes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_preferred_route);
        findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRoute = ((RadioGroup) findViewById(R.id.layout)).getCheckedRadioButtonId();
                PreferenceManager.getDefaultSharedPreferences(ChoosePreferredRouteActivity.this).edit().putString("preferredRoute", routes.get(selectedRoute)).commit();
                finish();
            }
        });
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                
                RadioGroup layout = (RadioGroup) findViewById(R.id.layout);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                int index = 0;
                for (String route: routes)
                {
                    RadioButton button = new RadioButton(ChoosePreferredRouteActivity.this);
                    button.setId(index);
                    button.setText(route);
                    button.setBackgroundResource(R.drawable.custom_radiogroup_divider);
                    layout.addView(button);
                    index++;
                }
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                routes = BingMapsAPI.getListOfPossibleRoutes(ChoosePreferredRouteActivity.this);

                handler.sendEmptyMessage(0);
            }
        }).start();
    }


}
