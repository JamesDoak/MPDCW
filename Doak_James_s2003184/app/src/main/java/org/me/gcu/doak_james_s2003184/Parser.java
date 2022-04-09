//James Alexander Doak
//S2003184

package org.me.gcu.doak_james_s2003184;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.ArrayList;

public class Parser {

    ArrayList<Items> parseData(String dataToParse)
    {
        Items item = null;
        int itemFound = 0;
        ArrayList <Items> itemsArrayList = null;
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        itemsArrayList  = new ArrayList<>();
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        itemFound = 1;
//                        Log.e("MyTag","Item Start Tag found");
                        item = new Items();
                    }
                    if(itemFound == 1){
                            if (xpp.getName().equalsIgnoreCase("title"))
                            {
                                // Now just get the associated text
                                String title = xpp.nextText();
                                // Do something with text
//                                Log.e("MyTag","Title is " + title);
                                item.setTitle(title);
                            }
                            else
                            if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String description = xpp.nextText();
                                // Do something with text
//                                Log.e("MyTag","description is " + description);
                                item.setDescription(description);
                            }
//                            else
//                            if (xpp.getName().equalsIgnoreCase("link"))
//                            {
//                                // Now just get the associated text
//                                String link = xpp.nextText();
//                                // Do something with text
//                                Log.e("MyTag","link is " + link);
//                                item.setLink(link);
//                            }
//                            else
                            if (xpp.getName().equalsIgnoreCase("point"))
                            {
                                // Now just get the associated text
                                String georss = xpp.nextText();
                                // Do something with text
//                                Log.e("MyTag","georss is " + georss);
                                item.setGeorss(georss);
                            }
//                            else
//                            if (xpp.getName().equalsIgnoreCase("author"))
//                            {
//                                // Now just get the associated text
//                                String author = xpp.nextText();
//                                // Do something with text
//                                Log.e("MyTag","author is " + author);
//                                item.setAuthor(author);
//                            }
//                            else
//                            if (xpp.getName().equalsIgnoreCase("comments"))
//                            {
//                                // Now just get the associated text
//                                String comments = xpp.nextText();
//                                // Do something with text
//                                Log.e("MyTag","comments is " + comments);
//                                item.setComments(comments);
//                            }
                            else
                            if (xpp.getName().equalsIgnoreCase("pubDate"))
                            {
                                // Now just get the associated text
                                String pubDate = xpp.nextText();
                                // Do something with text
//                                Log.e("MyTag","pubDate is " + pubDate);
                                item.setPubDate(pubDate);
                            }
                        }
                    }

                else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
//                        Log.e("MyTag","item is " + item.toString());
                        itemsArrayList.add(item);
                    }
                    else
                    if (xpp.getName().equalsIgnoreCase("channel"))
                    {
                        int size;
                        size = itemsArrayList.size();
//                        Log.e("MyTag","itemsCollection size is " + size);
                    }
                }


                // Get the next event
                eventType = xpp.next();

            } // End of while

            //return alist;
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

//        Log.e("MyTag","End document");

        return itemsArrayList;

    }

}
