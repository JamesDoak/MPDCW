//James Alexander Doak
//S2003184

package org.me.gcu.doak_james_s2003184;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawFeedDataDisplay;
    private EditText roadSearch;
    private Button plannedRoadworksButton;
    private Button roadworksButton;
    private Button currentIncidentsButton;
    private Button clearButton;
    private String result = "";
    private String url1="";
    private final String urlSourceA="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private final String urlSourceB="https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private final String urlSourceC="https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private ArrayList<Items> itemsArrayList = new ArrayList<>();
    private ArrayList itemList = new ArrayList<>();
    private ListView itemListView;
    private TextView label;



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
        clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);
        roadSearch = (EditText)findViewById(R.id.editText1);
        Log.e("MyTag","after feedAButton");
        // More Code goes here
        label = (TextView) findViewById(R.id.label);
        itemListView = (ListView) findViewById(R.id.ListView);


        ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, itemList);

        itemListView.setAdapter(adapter);


//        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                rawFeedDataDisplay.setText("Clicked item number: " + position);
//                Object item;
//                item = itemListView.getItemAtPosition(position);
//
//                //get current position
//                Integer pos = position;
//                Log.e("Position: ", pos.toString());
////                Log.e("ArrayList", itemsArrayList.toString());
//
//                if (!itemList.isEmpty()) {
//                    itemList.clear();
//                    ArrayAdapter adapter1 = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
//
//                    Log.d("Object", item.toString());
//                    itemListView.setAdapter(adapter1);
//                }else{
//                itemList.add(item);
//                ArrayAdapter adapter2 = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, itemList);
//
//                Log.d("Object", item.toString());
//                itemListView.setAdapter(adapter2);
//                }
//            }
//        });

    }

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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v)
    {

        if (v == plannedRoadworksButton) {
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Loading all Planned Roadworks");
            startProgressMain(1);
            itemList.clear();
            Log.e("MyTag","after startProgress");
        }
        if (v == roadworksButton) {
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Loading all Current Roadworks");
            startProgressMain(2);
            itemList.clear();
            Log.e("MyTag","after startProgress");
        }
        if (v == currentIncidentsButton) {
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Loading all Current Incidents");
            startProgressMain(3);
            itemList.clear();
            Log.e("MyTag","after startProgress");
        }

        if (v == clearButton){

                ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
                itemListView.setAdapter(adapter);
                roadSearch.setText("");

        }

        //return the search results from the text entry
        //the code will also run from pressing the enter key
        //on the virtual keyboard, and then minimise it
        roadSearch.setOnKeyListener(new AdapterView.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        String userSEntry = roadSearch.getText().toString();
                        Log.d("User Input: ", userSEntry); //works - returns user entry

                        if(userSEntry.isEmpty()){
                            rawFeedDataDisplay.setText("Please enter a search term");
                        }
                        else {

                            ArrayList<Items> matched = new ArrayList<Items>();
                            for (Items item : itemsArrayList) {
                                if (item.getTitle().toLowerCase(Locale.ROOT).contains(userSEntry)) {
                                    matched.add(item);
                                }
                            }
                            Log.e("Matched: ", matched.toString());

                            ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, matched);
                            itemListView.setAdapter(adapter);
                            hideKeyboard(MainActivity.this);

                        }

                        return true;
                    }
                return false;
            }
        });

    }

    //function to hide the virtual keyboard
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }

            catch (IOException ae) {
                Log.e("MyTag", "ioexception in run");
            }




            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    //set the parser for future use
                    Parser p = new Parser();

                    String xmlCopy = result;
                    Log.d("(XML COPY): ", xmlCopy);

                    //update the items array with the parsed data
                    itemsArrayList = p.parseData(xmlCopy);

                    //set and display the list view
                    ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
                    itemListView.setAdapter(adapter);
                    rawFeedDataDisplay.setText("Displaying results");


                }
            });
        }

    }




}
