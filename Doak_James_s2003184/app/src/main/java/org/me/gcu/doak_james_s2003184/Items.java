//James Alexander Doak
//S2003184

package org.me.gcu.doak_james_s2003184;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Items {

    //variables
    private String title;
    private String description;
//    private String link;
    private String georss;
//    private String author;
//    private String comments;
    private String pubDate;
    private String startDate;
    private String endDate;
    private String delayInfo;
    private ArrayList<Items> item = new ArrayList<Items>();

//    public void setArray(ArrayList<Items> arr){
//        item = arr;
//    }
//    public ArrayList<Items> getArr(){
//        return item;
//    }

    //getters and setters
    public String getTitle(){
        return title;
    }
    public void setTitle(String titleIn){
        title = titleIn;
    }

//    public String getDescription(){
//        return desFormat(description);
//    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String descriptionIn){
        description = removeBRTags(descriptionIn);
    }

//    public String getLink(){
//        return link;
//    }
//    public void setLink(String linkIn){
//        link = linkIn;
//    }
//
    public String getGeorss(){
        return georss;
    }
    public void setGeorss(String georssIn){
        georss = georssIn;
    }
//
//    public String getAuthor(){
//        return author;
//    }
//    public void setAuthor(String authorIn){
//        author = authorIn;
//    }
//
//    public String getComments(){
//        return comments;
//    }
//    public void setComments(String commentIn){
//        comments = commentIn;
//    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDateIn){
        pubDate = pubDateIn;
    }

    public String getItem(String item){
        String output;
        output = "• Road: "+title
                + "\n• Description: "
                + removeBRTags(description)
//                + "\n• Link: " + link
                + "\n• geoRSS: " + georss
                + "\n• Published: " + pubDate;
        return output;
    }

    //constructor
    public Items() {
        title = "";
        description = "";
//        link = "";
        georss = "";
//        author = "";
//        comments = "";
        pubDate = "";
    }

    public Items(String titleIn, String descriptionIn, String linkIn, String georssIn, String authorIn,
                     String commentsIn, String pubdateIn){
        this.title = titleIn;
        this.description = descriptionIn;
//        link = linkIn;
        georss = georssIn;
//        author = authorIn;
//        comments = commentsIn;
        this.pubDate = pubdateIn;
    }

    public Items(String titleIn, String pubdateIn){
        this.title = titleIn;
        this.pubDate = pubdateIn;
    }

    public String toString()
    {
        String result;
        result = "• Road: "+title
                + "\n• Description: "
                + removeBRTags(description)
//                + "\n• Link: " + link
                + "\n• geoRSS: " + georss
//                + " " + author
//                + " " + comments
                + "\n• Published: " + pubDate;
        return result;
    }


    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDateIn){
        startDate = startDateIn;
    }

    public String getEndtDate(){
        return endDate;
    }
    public void setEndDate(String endDateIn){
        endDate = endDateIn;
    }

    public String getDelayInfo(){
        return delayInfo;
    }
    public void setDelayInfo(String delayInfoIn){
        delayInfo = delayInfoIn;
    }

    public String desFormat(String des){
        String desc = des;
        String descSDsFin;
        int tempSDs = desc.indexOf("Start Date: ");
        int tempSDe = desc.indexOf(" - 00:00");

        descSDsFin = desc.substring(tempSDs, tempSDe);
        setStartDate(descSDsFin);

        return descSDsFin;
    }


    public String removeBRTags(String des)
    {
        String[] stringArray = des.split("<br />");
        String result = "";
        for (int i = 0; i < stringArray.length; i++){
            result = result + "\n" + stringArray[i] + " ";
        }
        return result;
    }





}

