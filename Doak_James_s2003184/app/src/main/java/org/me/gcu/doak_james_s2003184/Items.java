package org.me.gcu.doak_james_s2003184;

public class Items {

    //variables
    private String title;
    private String description;
    private String link;
    private String georss;
    private String author;
    private String comments;
    private String pubDate;

    //getters and setters
    public String getTitle(){
        return title;
    }
    public void setTitle(String titleIn){
        title = titleIn;
    }

    public String getDescription(){
        return title;
    }
    public void setDescription(String descriptionIn){
        description = descriptionIn;
    }

    public String getLink(){
        return link;
    }
    public void setLink(String linkIn){
        link = linkIn;
    }

    public String getGeorss(){
        return georss;
    }
    public void setGeorss(String georssIn){
        georss = georssIn;
    }

    public String getAuthor(){
        return author;
    }
    public void setAuthor(String authorIn){
        author = authorIn;
    }

    public String getComments(){
        return comments;
    }
    public void setComments(String commentIn){
        comments = commentIn;
    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDateIn){
        pubDate = pubDateIn;
    }

    //constructor
    public Items() {
        title = "";
        description = "";
        link = "";
        georss = "";
        author = "";
        comments = "";
        pubDate = "";
    }

    public Items(String titleIn, String descriptionIn, String linkIn, String georssIn, String authorIn,
                     String commentsIn, String pubdateIn){
        title = titleIn;
        description = descriptionIn;
        link = linkIn;
        georss = georssIn;
        author = authorIn;
        comments = commentsIn;
        pubDate = pubdateIn;
    }

    public String toString(){

        String output;
        output = "• Road: "+title
//                + "\n• Description: "
//                + removeTags(description)
//                + "\n• Link: " + link
//                + "\ngeoRSS " + georss
//                + " " + author
//                + " " + comments
                + "\n• Published: " + pubDate;
        return output;
    }

    public String DetailedString(Object o){
        String output;
        output = "• Road: "+title
                + "\n• Description: "
                + removeTags(description)
                + "\n• Link: " + link
                + "\n• geoRSS: " + georss
                + " " + author
                + " " + comments
                + "\n• Published: " + pubDate;
        return output;
    }

    public String removeTags(String description) {
        String[] arrOfStr = description.split("<br />", 10);
        String output="";

        for (int i=0;i<arrOfStr.length;i++){
            output = output+ " " +arrOfStr[i]+ " ";
        }
        return output;
    }

}

