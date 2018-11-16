package ir.project.usc;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class HTML {

    /*
        Variables
    */

    //File Config
    private File file;
    private String baseUrl;

    //Url
    private String url;


    //Jsoup
    private Document document;
    private String documentCharset;


    /*
        Constructor
    */

    public HTML(String path,String baseUrl,String documentCharset) {

        //nulling variables
        this.url = null;

        //initializing variables
        file = new File(path);
        this.documentCharset = documentCharset;
        this.baseUrl = baseUrl;

    }

    public HTML(String url) {

        //nulling variables
        file = null;
        this.documentCharset = null;
        this.baseUrl = null;

        //initializing variables
        this.url = url;

    }

    /*
        Parser
    */

    public HTML parse() throws IOException {

        if(file != null){

            //IOException
            document = Jsoup.parse(file, documentCharset);

        }else{

            //IOException
            Connection connection = Jsoup
                                        .connect(url)
                                        .followRedirects(true)
                                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                                        .timeout(600000)
                                        .maxBodySize(0);

            connection.execute();

            System.out.println("Status Code :"+connection.response().statusCode());
//            System.out.println("Status Code :"+connection.response().body());

            if(connection.response().statusCode() == 200)
                document = connection.get();
            else
                document = null;



        }

        return this;

    }


    /*
        Extractors Helper
    */

    public String extractFirstOnlyTextByTag(String tag){

        Elements elements = document.select(tag);

        if(elements==null || elements.size()<1)
            return null;

        return elements.first().text();

    }

    public Set<String> extractTextByTag(String tag){

        Set<String> list = new HashSet<>();

        Elements elements = document.getElementsByTag(tag);

        if(elements==null || elements.size()<1)
            return list;

        for( Element el : elements)
            list.add(el.text());

        return list;

    }

    private Set<String> extractTextByTags(String [] tags){

        Set<String> list = new HashSet<>();

        for(String tag : tags){

            list.addAll(extractTextByTag(tag));

        }

        return list;

    }



    public Set<String> extractAbosluteHrefByTag(String tag){

        Set<String> list = new HashSet<>();

        Elements elements = document.getElementsByTag(tag);

        if(elements==null || elements.size()<1)
            return list;

        for( Element el : elements)
            list.add(el.attr("abs:href"));

        return list;

    }

    public String extractFirstOnlyTextByClass(String className){

        Elements elements = document.getElementsByClass(className);

        if(elements==null || elements.size()<1)
            return null;

        return elements.first().text();

    }

    public String extractFirstOnlyAbosluteHrefByClass(String className){

        Elements elements = document.getElementsByClass(className);

        if(elements==null || elements.size()<1)
            return null;

        return elements.first().attr("abs:href");

    }

    public Set<String> extractTextsByClass(String className){

        Set<String> list = new HashSet<>();

        Elements elements = document.getElementsByClass(className);

        if(elements==null || elements.size()<1)
            return list;

        for( Element el : elements)
            list.add(el.text());

        return list;

    }

    public Set<String> extractTextsBySelect(String select){

        Set<String> list = new HashSet<>();

        Elements elements = document.select(select);

        if(elements==null || elements.size()<1)
            return list;

        for( Element el : elements)
            list.add(el.text());

        return list;

    }

    public String extractFirstOnlyTextBySelect(String select){


        Elements elements = document.select(select);

        if(elements==null || elements.size()<1)
            return null;

        return elements.first().text();

    }

    public String extractFirstOnlyAbosluteHrefBySelect(String select){

        Elements elements = document.select(select);

        if(elements==null || elements.size()<1)
            return null;

        return elements.first().attr("abs:href");

    }


    /*
        Extractor
    */

    public CNNDoc extractCNNDoc() {

        if(document==null)
            return null;


        String title            = getPageTitle();
        String url              = this.url;

        Set<String> headlines   = extractTextsByClass("cd__headline-text");

        Set<String> socialRefrences=extractAbosluteHrefByTag("a");
        System.out.println(socialRefrences.size());
        if(!socialRefrences.isEmpty()){

            Set<String> socialRefrencesTemp = new HashSet<>();
            Set<String> socialRefrencesTempFinal = new HashSet<>();



            for(String social : CNNDoc.getSocialsSet()) {

                socialRefrencesTemp.clear();
                socialRefrencesTemp.addAll(socialRefrences);

                socialRefrencesTemp.removeIf(ref -> !ref.contains(social));

                socialRefrencesTempFinal.addAll(socialRefrencesTemp);

            }

            socialRefrences = socialRefrencesTempFinal;

        }

        System.out.println(socialRefrences.size());

        Set<String> paragraphs = extractTextsByClass("zn-body__paragraph");
        Set<String> paragraphHeaders =extractTextsBySelect(".zn-body__paragraph > h3");


        Set<String> moreInfoLinks = extractAbosluteHrefByTag("a");
        if(!moreInfoLinks.isEmpty())
            moreInfoLinks.removeIf(ref -> ref.contains("www.cnn.com") || ref.contains("cnn.com"));

        Set<String> videoLinks = extractAbosluteHrefByTag("a");
        if(!videoLinks.isEmpty())
            videoLinks.removeIf(ref -> !ref.contains("/videos/"));

        String author = extractFirstOnlyTextBySelect(".metadata__byline__author > a");
        String authorProfile = extractFirstOnlyAbosluteHrefBySelect(".metadata__byline__author > a");

        String updateDateTime = extractFirstOnlyTextByClass("update-time");
        Set<String> twitterHashTags = extractTextsByClass("update-time");
        if(!twitterHashTags.isEmpty())
            twitterHashTags.removeIf(ref -> !ref.startsWith("#"));


        CNNDoc doc = new CNNDoc(

                title,
                url,

                headlines.toArray(new String[0]),

                socialRefrences.toArray(new String[0]),

                paragraphs.toArray(new String[0]),
                paragraphHeaders.toArray(new String[0]),

                moreInfoLinks.toArray(new String[0]),
                videoLinks.toArray(new String[0]),

                author,
                authorProfile,

                updateDateTime,
                twitterHashTags.toArray(new String[0])

        );

        return doc;

    }

    /*
        Data Getter
    */

    public String getPageTitle(){

        return document.head().getElementsByTag("title").text();

    }


}
