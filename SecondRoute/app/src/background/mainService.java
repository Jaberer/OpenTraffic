public class mainService extends IntentService
{
    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        /*
         * Creates a new Intent to start IntentService.
         * Gets Current Location
         */
        mServiceIntent = new Intent(getActivity(), RSSPullService.class);
        mServiceIntent.setData(Uri.parse(dataUrl));

        // Gets data from the incoming Intent


        String dataString = workIntent.getDataString();
        ...
        // Do work here, based on the contents of dataString
        ...
    }
}