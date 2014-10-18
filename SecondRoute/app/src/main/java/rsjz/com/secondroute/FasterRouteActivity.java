package rsjz.com.secondroute;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class FasterRouteActivity extends Activity {
    GoogleSpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faster_route);
        speechRecognizer = new GoogleSpeechRecognizer(this);

    }
    public void navigate()
    {

    }


}
