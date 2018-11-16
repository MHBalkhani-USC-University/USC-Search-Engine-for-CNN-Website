package ir.project.usc;

import java.lang.reflect.Field;
import java.util.*;

public class CNNDoc {

    public CNNDoc(

            String title,
            String url,

            String [] headlines,

            String [] socialRefrences,

            String [] paragraphs,
            String [] paragraphHeaders,

            String [] moreInfoLinks,
            String [] videoLinks,

            String author,
            String authorProfile,

            String updateDateTime,

            String [] twitterHashTags
    ){

        this.title              = title;
        this.url                = url;

        this.headlines          = headlines;

        this.socialRefrences      = socialRefrences;

        this.paragraphs         = paragraphs;
        this.paragraphHeaders   = paragraphHeaders;

        this.moreInfoLinks      = moreInfoLinks;
        this.videoLinks         = videoLinks;

        this.author             = author;
        this.authorProfile      = authorProfile;

        this.updateDateTime     = updateDateTime;

        this.twitterHashTags    = twitterHashTags;


    }

    public CNNDoc(){

        this.title              = null;
        this.url                = null;

        this.headlines          = null;

        this.socialRefrences      = null;

        this.paragraphs         = null;
        this.paragraphHeaders   = null;

        this.moreInfoLinks      = null;
        this.videoLinks         = null;

        this.author             = null;
        this.authorProfile      = null;

        this.updateDateTime     = null;

        this.twitterHashTags    = null;

    }

    //Title : document.getElementsByTagName('title')[0].innerText

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //Url :
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /*

        for (d of document.getElementsByClassName("cd__headline-text")){
            console.log(d.innerText)
        }

    */

    private String [] headlines;

    public String[] getHeadlines() {
        return headlines;
    }

    public void setHeadlines(String[] headlines) {
        this.headlines = headlines;
    }

    /*

            document.getElementsByClassName("social__link social__link--instagram")[0].lastChild.href
            document.getElementsByClassName("nav-flyout-footer__social-link nav-flyout-footer__social-link--twitter")

            console.log(document.getElementsByClassName("nav-flyout-footer__social-link--twitter")[0].href)

    */

    private String [] socialRefrences; // a tag : filters

    public String[] getSocialRefrences() {
        return socialRefrences;
    }

    public void setSocialRefrences(String[] socialRefrences) {
        this.socialRefrences = socialRefrences;
    }

    /*

        zn-body__paragraph.inner
        zn-body__paragraph > h3
     */

    private String [] paragraphs;
    private String [] paragraphHeaders;

    public String[] getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(String[] paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String[] getParagraphHeaders() {
        return paragraphHeaders;
    }

    public void setParagraphHeaders(String[] paragraphHeaders) {
        this.paragraphHeaders = paragraphHeaders;
    }

    /*

     Link to outside of domain ( more info links )
     contains /videos/ > div innerHTML

     */

    private String [] moreInfoLinks;
    private String [] videoLinks;

    public String[] getMoreInfoLinks() {
        return moreInfoLinks;
    }

    public void setMoreInfoLinks(String[] moreInfoLinks) {
        this.moreInfoLinks = moreInfoLinks;
    }

    public String[] getVideoLinks() {
        return videoLinks;
    }

    public void setVideoLinks(String[] videoLinks) {
        this.videoLinks = videoLinks;
    }

    /*
     author : metadata__byline__author
     */

    private String author;
    private String authorProfile;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(String authorProfile) {
        this.authorProfile = authorProfile;
    }

    /*
        last update : update-time
     */

    private String updateDateTime;

    public String getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        this.updateDateTime = updateDateTime;
    }


    private String [] twitterHashTags;

    public String [] getTwitterHashTags() {
        return twitterHashTags;
    }

    public void setTwitterHashTags(String [] twitterHashTags) {
        this.twitterHashTags = twitterHashTags;
    }

    /*

        General Methods

    */

//    public boolean fieldExists(Object field){
//
//        Class thisClass = this.getClass();
//
//        for (Field classField : thisClass.getDeclaredFields() ){
//
//            if(classField.getName() == field)
//                return true;
//
//        }
//
//        return false;
//
//    }

//    public static Map<String,String> getFieldsDictionary(){
//
//        final Map<String,String> dictionary = new HashMap<>();
//
//        dictionary.put("title","Title");
//        dictionary.put("headlines","Headlines");
//
//
//        return dictionary;
//
//    }

    public static Set<String> getSocialsSet(){

        final Set<String> set = new HashSet<>();

        set.add("instagram.com");
        set.add("facebook.com");
        set.add("twitter.com");


        return set;

    }

    // To String Method
    public String toString(){

        String document = "";
        document += "Document URL     : "+url+"\n";
        document += "Document TITLE   : "+title+"\n\n";


        document += "Headlines:\n";
        document += Arrays.toString(headlines)+"\n\n";


        document += "Social Refrences:\n";
        document += Arrays.toString(socialRefrences)+"\n\n";


        document += "Paragraph ( Headers ) :\n";
        document += Arrays.toString(paragraphHeaders)+"\n";

        document += "Paragraphs :\n";
        document += Arrays.toString(paragraphs)+"\n\n";


        document += "More Info Links :\n";
        document += Arrays.toString(moreInfoLinks)+"\n";

        document += "Video Links :\n";
        document += Arrays.toString(videoLinks)+"\n\n";


        document += "Author             : "+author+"\n";
        document += "Author Profile     : "+authorProfile+"\n\n";

        document += "Update Date Time     : "+updateDateTime+"\n\n";

        document += "Twitter Hash tags :\n";
        document += Arrays.toString(twitterHashTags)+"\n\n";

        return document;
    }

}
