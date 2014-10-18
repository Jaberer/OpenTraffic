package rsjz.com.secondroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ryan on 9/20/2014.
 */
public class BingMapsAPI {
    private static final String MAPS_API_BASE = "http://dev.virtualearth.net/REST/V1";
    private static final String TYPE_ROUTES = "/Routes";
    private static final String MODE_DRIVING = "/Driving";

    private static final String API_KEY = "AmXC0roDXBSoAn6AUz9ScsUWbYrvoqCvjerGZ-Q4O1KxFfea9AHCi3cZ8Prl5aIM";

    public enum TRANSIT_MODE {driving, transit, walking}
    public static List<String> getPreferredDirectionsListHome(Context context)
    {
        String route = PreferenceManager.getDefaultSharedPreferences(context).getString("preferredRoute", "");
        return Arrays.asList(route.split("\n"));
    }
    public static List<String> getPreferredDirectionsListWork(Context context)
    {
        String route = PreferenceManager.getDefaultSharedPreferences(context).getString("preferredRoute", "");
        return Arrays.asList(route.split("\n"));
    }=
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
                String instruction = itinerary.getJSONObject("instruction").getString("text");
                if (!instruction.toLowerCase().startsWith("road name changes"))
                {
                    directionList.add(instruction);
                }
            }
            return directionList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<String> getListOfPossibleRoutes(Context context)
    {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return BingMapsAPI.getListOfPossibleRoutes(prefs.getFloat("homelat", 0), prefs.getFloat("homelng", 0), prefs.getFloat("worklat", 0), prefs.getFloat("worklng", 0));

    }
    public static ArrayList<String> getListOfPossibleRoutes (float lat1, float lng1, float lat2, float lng2)
    {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(MAPS_API_BASE + TYPE_ROUTES + MODE_DRIVING);
            sb.append("?o=json");
            sb.append("&key=" + API_KEY);
            sb.append("&wp.0=" + lat1 + "," + lng1);
            sb.append("&wp.1=" + lat2 + "," + lng2);
            sb.append("&maxSolns=3");

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
            ArrayList<String> routesList = new ArrayList<String>();
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray resourcesArray = jsonObj.getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources");
            for (int resourceIndex = 0; resourceIndex < resourcesArray.length(); resourceIndex++) {
                JSONObject resourceObj = resourcesArray.getJSONObject(resourceIndex);
                JSONArray itineraryItems = resourceObj.getJSONArray("routeLegs").getJSONObject(0).getJSONArray("itineraryItems");
                String route = "";
                for (int index = 0; index < itineraryItems.length(); index++) {
                    JSONObject itinerary = itineraryItems.getJSONObject(index);
                    String instruction = itinerary.getJSONObject("instruction").getString("text") + "\n";
                    if (!instruction.toLowerCase().startsWith("road name changes"))
                    {
                        route += instruction;
                    }

                }
                route = route.substring(0, route.length() - 1);
                routesList.add(route);
            }
            return routesList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
