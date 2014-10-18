package rsjz.com.secondroute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ryan on 9/20/2014.
 */
public class BingMapsAPI {
    private static final String MAPS_API_BASE = "http://dev.virtualearth.net/REST/V1";
    private static final String TYPE_ROUTES = "/Routes";
    private static final String MODE_DRIVING = "/Driving";

    private static final String API_KEY = "AmXC0roDXBSoAn6AUz9ScsUWbYrvoqCvjerGZ-Q4O1KxFfea9AHCi3cZ8Prl5aIM";

    public enum TRANSIT_MODE {driving, transit, walking}
    public static ArrayList<String> getPreferredDirectionsList()
    {
        
    }
    public static ArrayList<String> getDirectionsList (float lat1, float lng1, float lat2, float lng2)
    {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(MAPS_API_BASE + TYPE_ROUTES + MODE_DRIVING);
            sb.append("?o=json");
            sb.append("&key=" + API_KEY);
            sb.append("&wp.0=" + lat1 + "," + lng1);
            sb.append("&wp.1=" + lat2 + "," + lng2);
            sb.append("&optmz=timeWithTraffic");
            sb.append("&rpo=none");

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            ArrayList<String> directionList = new ArrayList<String>();
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONObject resourceObj = jsonObj.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0);
            JSONArray itineraryItems = resourceObj.getJSONArray("routeLegs").getJSONObject(0).getJSONArray("itineraryItems");
            for (int index = 0; index < itineraryItems.length(); index++)
            {
                JSONObject itinerary = itineraryItems.getJSONObject(index);
                directionList.add(itinerary.getJSONObject("instruction").getString("text"));
            }
            return directionList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
