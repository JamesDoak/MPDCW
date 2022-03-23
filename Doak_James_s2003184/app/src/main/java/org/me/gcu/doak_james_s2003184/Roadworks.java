package org.me.gcu.doak_james_s2003184;

public class Roadworks {

    //variables
    private String title;
    private String description;
    private String link;
    private int georss;
    private String author;
    private String comments;
    private String pubdate;

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

    public int getGeorss(){
        return georss;
    }
    public void setGeorss(int georssIn){
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

    public String getPubdate(){
        return pubdate;
    }
    public void setPubdate(String pubdateIn){
        pubdate = pubdateIn;
    }

    //constructor
    public Roadworks(){
        title = "";
        description = "";
        link = "";
        georss = 0;
        author = "";
        comments = "";
        pubdate = "";
    }

    public Roadworks(String titleIn, String descriptionIn, String linkIn, int georssIn, String authorIn,
                     String commentsIn, String pubdateIn){
        title = titleIn;
        description = descriptionIn;
        link = linkIn;
        georss = georssIn;
        author = authorIn;
        comments = commentsIn;
        pubdate = pubdateIn;
    }

}