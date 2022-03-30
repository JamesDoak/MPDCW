package org.me.gcu.doak_james_s2003184;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawFeedDataDisplay;
    private Button plannedRoadworksButton;
    private Button roadworksButton;
    private Button currentIncidentsButton;
    private String result = "";
    private String url1="";
    // Traffic Scotland Planned Roadworks XML link
    private final String urlSourceA="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private final String urlSourceB="https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private final String urlSourceC="https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private ArrayList<Items> itemsArrayList = new ArrayList<>();
    private String[] items;
    private ArrayAdapter<Items> itemsArrayAdapter;
    private ListView itemListView;
    private TextView label2;
    private Handler handler;
    private ProgressBar pBar;


    //new
//    private ArrayList<Items> roadworks = new ArrayList<>();
//    private ArrayList<Items> currentIncidents = new ArrayList<>();
//    private ArrayList<Items> plannedWorks = new ArrayList<>();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        rawFeedDataDisplay = (TextView)findViewById(R.id.rawFeedDataDisplay);
        plannedRoadworksButton = (Button)findViewById(R.id.plannedRoadworksButton);
        plannedRoadworksButton.setOnClickListener(this);
        roadworksButton = (Button)findViewById(R.id.roadworksButton);
        roadworksButton.setOnClickListener(this);
        currentIncidentsButton = (Button)findViewById(R.id.currentIncidentsButton);
        currentIncidentsButton.setOnClickListener(this);
        Log.e("MyTag","after feedAButton");
        // More Code goes here
//        label2 = (TextView) findViewById(R.id.label2);
        itemListView = (ListView) findViewById(R.id.ListView);



            }

//    public void progressBar(){
//        //progress bar
//        result = "";
//        handler = new Handler();
//        pBar = findViewById(R.id.bar1);
//        pBar.setMax(itemsArrayList.size());
//
//        new Thread(new Runnable(){
//            @Override
//            public void run(){
//
//                int counter = itemsArrayList.size();
//                for (int i = 0; i <= counter; i++)
//                {
//                    final int currentProgressCount = i;
//                    try
//                    {
//                        Thread.sleep(50);
//                    }
//                    catch (InterruptedException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    handler.post(new Runnable()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            pBar.setProgress(currentProgressCount);
//                        }
//                    });
//                }
//
//            }
//
//        }).start();
//    }

    public void startProgressMain(int chosenButton){
        //feedChoice is new - code further below
        switch(chosenButton){
            case 1:
                new Thread(new Task(urlSourceA, 1)).start();
//                progressBar();
                break;
            case 2:
                new Thread(new Task(urlSourceB, 2)).start();
//                progressBar();
                break;
            case 3:
                new Thread(new Task(urlSourceC, 3)).start();
//                progressBar();
                break;
        }
    }

    //remove the listview when loading new items
    public void removeList(){
        this.itemListView.setAdapter(null);
    }


    @Override
    public void onClick(View v)
    {

        if (v == plannedRoadworksButton) {
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Showing all Planned Roadworks");
            startProgressMain(1);
            Log.e("MyTag","after startProgress");
        }
        if (v == roadworksButton) {
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Showing all Current Roadworks");
            startProgressMain(2);
            Log.e("MyTag","after startProgress");
        }
        if (v == currentIncidentsButton) {
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Showing all Current Incidents");
            startProgressMain(3);
            Log.e("MyTag","after startProgress");
        }

    }


    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        //new
        private String url;
        private int chosenFeed;

        public Task(String aurl, int feedChoice)
        {
            url = aurl;
            //new - int above is new too
            chosenFeed = feedChoice;
        }
        @Override
        public void run()
        {
            //new
            int choice = chosenFeed;

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            result = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();

            }

            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //
            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    //display raw data - redundant.
//                    rawFeedDataDisplay.setText(result);

                    //set the parser for future use
                    Parser p = new Parser();


                    //run the data through the parser
                    p.parseData(result);
                    //update the items array with the parsed data
                    itemsArrayList = p.parseData(result);
                    //set and display the list view
                    ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
                    itemListView.setAdapter(adapter);


                }
            });
        }

    }


}
