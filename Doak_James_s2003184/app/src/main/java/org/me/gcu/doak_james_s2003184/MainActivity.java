//James Alexander Doak
//S2003184

package org.me.gcu.doak_james_s2003184;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawFeedDataDisplay;
    private EditText roadSearch;
    private EditText dateSearch;
    private EditText roadSearchEntry;
    private EditText dateSearchEntry;
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
    private ProgressBar progressBar;
    private DatePickerDialog picker;
    private CustomArrayAdaptor cAdaptor;


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

//        roadSearch = (EditText)findViewById(R.id.roadSearchEntry);
//        roadSearch.setOnClickListener(this);
//        dateSearch = (EditText)findViewById(R.id.dateSearchEntry);
//        dateSearch.setOnClickListener(this);

        Log.e("MyTag","after feedAButton");
        // More Code goes here
        label = (TextView) findViewById(R.id.label);
        itemListView = findViewById(R.id.ListView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(false);

        roadSearchEntry = findViewById(R.id.roadSearchEntry);
        roadSearchEntry.setOnClickListener(this);
        dateSearchEntry = findViewById(R.id.dateSearchEntry);
        dateSearchEntry.setOnClickListener(this);

        roadSearchEntry.setVisibility(View.GONE);
        dateSearchEntry.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);
        rawFeedDataDisplay.setVisibility(View.GONE);


        dateSearchEntry.setEnabled(true);

//        ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
//        ArrayAdapter adapter2 = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, itemList);

//        itemListView.setAdapter(adapter);
        cAdaptor = new CustomArrayAdaptor(MainActivity.this, itemsArrayList);
        itemListView.setAdapter(cAdaptor);

        dateSearchEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String dayFinal = "" + dayOfMonth;
                                    switch(dayFinal){
                                        case "1":{dayFinal = "01";break;}
                                            case "2":{dayFinal = "02";break;}
                                                case "3":{dayFinal = "03";break;}
                                                    case "4":{dayFinal = "04";break;}
                                                        case "5":{dayFinal = "05";break;}
                                                            case "6":{dayFinal = "06";break;}
                                                                case "7":{dayFinal = "07";break;}
                                                                    case "8":{dayFinal = "08";break;}
                                                                        case "9":{dayFinal = "09";break;}
                                }
                                String monthFinal = "";
                                    switch(monthOfYear){
                                        case 0:{monthFinal = "January"; break;}
                                            case 1:{monthFinal = "February";break;}
                                                case 2:{monthFinal = "March";break;}
                                                    case 3:{monthFinal = "April";break;}
                                                        case 4:{monthFinal = "May";break;}
                                                            case 5:{monthFinal = "June";break;}
                                                                case 6:{monthFinal = "July";break;}
                                                                    case 7:{monthFinal = "August";break;}
                                                                        case 8:{monthFinal = "September";break;}
                                                                            case 9:{monthFinal = "October";break;}
                                                                                case 10:{monthFinal = "November";break;}
                                                                                    case 11:{monthFinal = "December";break;}
                                    }

                                Log.e("Day:", dayFinal);
                                dateSearchEntry.setText(dayFinal + " " + monthFinal + " " + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        dateSearchEntry.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        String userSEntry = String.format(dateSearchEntry.getText().toString());
                        Log.d("User Input: ", userSEntry); //works - returns user entry

                        if(userSEntry.isEmpty()){
                            rawFeedDataDisplay.setText("Please enter a search term");
                        }
                        else {

                            ArrayList<Items> matched = new ArrayList<Items>();

                            for (Items item : itemsArrayList)
                            {
                                if (item.getDescription().contains(userSEntry)) {
                                    matched.add(item);
                                }

                            }
                            Log.e("Matched: ", matched.toString());

                            if(matched.isEmpty())
                            {
//                                ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
//                                itemListView.setAdapter(adapter);
                                cAdaptor = new CustomArrayAdaptor(MainActivity.this, itemsArrayList);
                                itemListView.setAdapter(cAdaptor);
                                hideKeyboard(MainActivity.this);
                                rawFeedDataDisplay.setText("Date not found");
                            }
                            else
                            {
//                                ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, matched);
//                                itemListView.setAdapter(adapter);
                                cAdaptor = new CustomArrayAdaptor(MainActivity.this, matched);
                                itemListView.setAdapter(cAdaptor);
                                hideKeyboard(MainActivity.this);
                                rawFeedDataDisplay.setText("Displaying search results: " + dateSearchEntry.getText().toString());

                            }
                        }

                        return true;
                    }
                return false;
            }
        });
    }



    public void startProgressMain(int chosenButton){
        //feedChoice is new - code further below
        switch(chosenButton){
            case 1:
                new Thread(new Task(urlSourceA, 1)).start();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                break;
            case 2:
                new Thread(new Task(urlSourceB, 2)).start();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                break;
            case 3:
                new Thread(new Task(urlSourceC, 3)).start();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                break;
        }
    }

    //remove the listview when loading new items
    public void removeList(){
        this.itemListView.setAdapter(null);
    }

    public void loadingButtons(){
        plannedRoadworksButton.setEnabled(false);
        roadworksButton.setEnabled(false);
        currentIncidentsButton.setEnabled(false);
        clearButton.setEnabled(false);
        roadSearchEntry.setEnabled(false);
    }

    public void dataButtonsParsed()
    {
        plannedRoadworksButton.setEnabled(true);
        roadworksButton.setEnabled(true);
        currentIncidentsButton.setEnabled(true);
        clearButton.setEnabled(true);
        roadSearchEntry.setEnabled(true);
    }


    public void activeButton(Boolean click, View view){
        Boolean isClicked = click;

        int tealBackground = ContextCompat.getColor(MainActivity.this, R.color.teal_200);
        int blackBackground = ContextCompat.getColor(MainActivity.this, R.color.black);
//        int whiteText = ContextCompat.getColor(MainActivity.this, R.color.white);
//        int blackText = ContextCompat.getColor(MainActivity.this, R.color.black);

        if(isClicked){
            view.setBackgroundColor(tealBackground);
        }

    }
    public void setDefaultButtonStyle(View view){
        int blackBackground = ContextCompat.getColor(MainActivity.this, R.color.black);
        view.setBackgroundColor(blackBackground);

    }


//    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v)
    {
        int blackBackground = ContextCompat.getColor(MainActivity.this, R.color.black);
        int whiteText = ContextCompat.getColor(MainActivity.this, R.color.white);

        if (v == plannedRoadworksButton)
        {
            roadSearchEntry.setVisibility(View.VISIBLE);
            dateSearchEntry.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.VISIBLE);
            rawFeedDataDisplay.setVisibility(View.VISIBLE);
            activeButton(true, v);
            roadSearchEntry.setText("");
            dateSearchEntry.setText("");
            setDefaultButtonStyle(roadworksButton);
            setDefaultButtonStyle(currentIncidentsButton);
            loadingButtons();
            dateSearchEntry.setEnabled(true);
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Loading all Planned Roadworks");
            startProgressMain(1);
            itemList.clear();
            Log.e("MyTag","after startProgress");
        }
        if (v == roadworksButton)
        {
            roadSearchEntry.setVisibility(View.VISIBLE);
            dateSearchEntry.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.VISIBLE);
            rawFeedDataDisplay.setVisibility(View.VISIBLE);
            activeButton(true, v);
            roadSearchEntry.setText("");
            dateSearchEntry.setText("");
            setDefaultButtonStyle(plannedRoadworksButton);
            setDefaultButtonStyle(currentIncidentsButton);
            loadingButtons();
            dateSearchEntry.setEnabled(true);
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Loading all Current Roadworks");
            startProgressMain(2);
            itemList.clear();
            Log.e("MyTag","after startProgress");
        }
        if (v == currentIncidentsButton)
        {
            roadSearchEntry.setVisibility(View.VISIBLE);
            dateSearchEntry.setVisibility(View.GONE);
            clearButton.setVisibility(View.VISIBLE);
            rawFeedDataDisplay.setVisibility(View.VISIBLE);
            activeButton(true, v);
            roadSearchEntry.setText("");
            dateSearchEntry.setText("");
            setDefaultButtonStyle(plannedRoadworksButton);
            setDefaultButtonStyle(roadworksButton);
            dateSearch.setEnabled(false);
            loadingButtons();
            removeList();
            Log.e("MyTag","in onClick");
            rawFeedDataDisplay.setText("Loading all Current Incidents");
            startProgressMain(3);
            itemList.clear();
            Log.e("MyTag","after startProgress");
        }

        if (v == clearButton){
//                ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
//                itemListView.setAdapter(adapter);
                cAdaptor = new CustomArrayAdaptor(MainActivity.this, itemsArrayList);
                itemListView.setAdapter(cAdaptor);


            roadSearchEntry.setText("");
            dateSearchEntry.setText("");
                rawFeedDataDisplay.setText("Displaying " + itemsArrayList.size() + " results");
        }

        //return the search results from the text entry
        //the code will also run from pressing the enter key
        //on the virtual keyboard, and then minimise it
        roadSearchEntry.setOnKeyListener(new AdapterView.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        String userSEntry = roadSearchEntry.getText().toString();
                        Log.d("User Input: ", userSEntry); //works - returns user entry

                        if(userSEntry.isEmpty()){
                            rawFeedDataDisplay.setText("Please enter a search term");
                        }
                        else {

                            ArrayList<Items> matched = new ArrayList<Items>();
                            for (Items item : itemsArrayList)
                            {
                                if (item.getTitle().contains(userSEntry.toUpperCase(Locale.ROOT))) {
                                    matched.add(item);
                                }
                            }
                            Log.e("Matched: ", matched.toString());

                            if(matched.isEmpty())
                            {
//                                ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
//                                itemListView.setAdapter(adapter);
                                cAdaptor = new CustomArrayAdaptor(MainActivity.this, itemsArrayList);
                                itemListView.setAdapter(cAdaptor);

                                hideKeyboard(MainActivity.this);
                                rawFeedDataDisplay.setText("Road not found");
                            }
                            else
                            {
//                                ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, matched);
//                                itemListView.setAdapter(adapter);
                                cAdaptor = new CustomArrayAdaptor(MainActivity.this, matched);
                                itemListView.setAdapter(cAdaptor);


                                hideKeyboard(MainActivity.this);
                                rawFeedDataDisplay.setText("Displaying search results: " + roadSearchEntry.getText().toString());
                            }
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
//                    ArrayAdapter<Items> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.activity_listview, itemsArrayList);
//                    itemListView.setAdapter(adapter);

                    cAdaptor = new CustomArrayAdaptor(MainActivity.this, itemsArrayList);
                    itemListView.setAdapter(cAdaptor);


                    rawFeedDataDisplay.setText("Displaying "+itemsArrayList.size()+" results");
                    progressBar.setVisibility(View.INVISIBLE);
                    dataButtonsParsed();

                    Log.d("Array Data", itemsArrayList.toString());
                    Log.e("XML Stream",result.toString());

                }
            });
        }

    }




}
