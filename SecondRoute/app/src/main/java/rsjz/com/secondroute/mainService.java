/**
 *
 *
 */
public class mainService extends IntentService
{


    @Override
    protected void onHandleIntent(Intent workIntent)
    {
        /*
         * Creates a new Intent to start IntentService.
         * Gets Current Location
         */
        ServiceIntent = new Intent(getActivity(), RSSPullService.class);
        ServiceIntent.setData(Uri.parse(dataUrl));

        // Gets data from the incoming Intent

    }

    private double compareRoutes()
    {
        double matchpercentage = 0.0;
        for(int i = 0; i < min(PR.size, CR.size); i++) // i starts at 0, ends at smaller number
        {
            if(pr.size - i == cr.size - i)      // begins at end of list and minus i to iterate
            {
                matchpercentage++;
            }
            else
            {
                return matchpercentage / Math.min(pr.size, cr.size);
            }
        }
    }
}