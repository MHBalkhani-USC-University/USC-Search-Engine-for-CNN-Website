package ir.project.usc;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.*;
import java.util.*;



public class Main {

    private static void printMainMenu(){

        String menu = "1) Add document with File\n";
        menu += "2) Add document with Url\n";
        menu += "3) Add document \n";

        menu += "4) Normal query\n";
        menu += "5) Boolean query\n";
        menu += "6) Set Structure File\n";

        menu += "0) Exit\n";

        System.out.println(menu);

    }

    public static void main(String args[]){

        /*

           User Interface

        */

//        UI
//        System.out.println("fasf");
//        UI ui = new UI();
//        ui.run(args);




        Crawler crawler = new Crawler("https://edition.cnn.com/","cnn.com",10);
        final int CRAWLER_MAX_FAILURE_ATTEMPTS = 10;
        Set<CNNDoc> documentToBeIndexed = new HashSet<>();

        try {

            Set<String> links = crawler.crawl(CRAWLER_MAX_FAILURE_ATTEMPTS).getLinks();


            System.out.println(links.size());
            int  prog = 1;

            for(String link : links){

                System.out.println("Fetching ("+prog+"/"+links.size()+") :\t"+link+" ... \n---------------------------------------------\n");

                try {

                    CNNDoc newDoc = new HTML(link).parse().extractCNNDoc();
                    documentToBeIndexed.add(newDoc);
                    System.out.println(newDoc);

                    if(prog==5)
                        break;

                } catch (IOException e){

                    System.out.println(e);

                }


                prog++;
            }

        } catch (IOException e){

            System.out.println(e);

        }

        try{

            new JSON(documentToBeIndexed).writeToFile("INDEX.json");

        } catch (IOException e){

            System.out.println(e);

        }


        StructureDoc [] structureDoc = {};

        try{

            structureDoc = new JSON("Structure.json").readFromFileStructures();


        } catch (IOException e){

            System.out.println(e);

        }


        Retriever retriver = new Retriever().addDocuments(structureDoc,documentToBeIndexed);
        retriver.commitChanges();


        //Main

        Scanner scanner = new Scanner(System.in);
        int mainSelection = -1;

        while (mainSelection!=0){

            printMainMenu();
            mainSelection = scanner.nextInt();

            switch (mainSelection){

                case 1:
                    System.out.println("Path : ");
                    String path = new Scanner(System.in).nextLine();
                    break;

                case 2:
                    System.out.println("Url : ");
                    String url = new Scanner(System.in).nextLine();
                    break;

                case 3:

                    break;

                case 4:
                    System.out.println("Search with Query : ");
                    String query = new Scanner(System.in).nextLine();
                    System.out.println("Title : ");
                    String title = new Scanner(System.in).nextLine();
                    TopDocs docs =  retriver.search(title,query);


                    for (ScoreDoc sdocs : docs.scoreDocs){

                        System.out.println(query);

                        try {

                            Document doc = retriver.indexReader.document(sdocs.doc);
                            System.out.println(doc.getField("title").stringValue());
                            System.out.println("--------------------");

                        }catch (IOException e){
                            System.out.println(e);
                        }

                    }

                    break;

                case 5:
                    System.out.println("Search with Boolean Query ( type exit for quit ) : \n");
                    String q;
                    String condition;

                    System.out.print("Query : ");
                    q = new Scanner(System.in).nextLine();

                    while(q!="exit"){

                        System.out.print("Query : ");
                        q = new Scanner(System.in).nextLine();

                        System.out.print("Search with Boolean Query ( type exit for quit ) : ");
                        condition = new Scanner(System.in).nextLine();

                        if(q!="exit")
                            break;



                    }

                    break;


            }

            System.out.println(mainSelection);

        }




    }

}
