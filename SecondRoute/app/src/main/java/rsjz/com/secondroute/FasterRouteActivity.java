package rsjz.com.secondroute;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;


public class FasterRouteActivity extends Activity implements TextToSpeech.OnInitListener {
    GoogleSpeechRecognizer speechRecognizer;
    private TextToSpeech tts;
    String instruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faster_route);
        speechRecognizer = new GoogleSpeechRecognizer(this);
        instruction = getIntent().getStringExtra("instruction");
        int differenceInTime = getIntent().getIntExtra("differenceInTime", 0);
        instruction += " to save " + differenceInTime + " minutes.";
        ((TextView) findViewById(R.id.instruction)).setText(instruction);
        findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate();
            }
        });
        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void navigate()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float lat = prefs.getFloat("homelat", 0);
        float lng = prefs.getFloat("homelng", 0);
        if (!ContextService.isHeadingHome) //travelling to work
        {
            lat = prefs.getFloat("worklat", 0);
            lng = prefs.getFloat("latlng", 0);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lng));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                speakOut();
            }

        } else {
        }

    }

    private void speakOut() {


        tts.speak(instruction + " Would you like to start navigation?", TextToSpeech.QUEUE_FLUSH, null);
    }
}
