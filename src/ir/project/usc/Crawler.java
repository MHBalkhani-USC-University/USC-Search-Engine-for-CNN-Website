package ir.project.usc;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class Crawler {

    private int fLimit;
    private String domain;

    private Set<String> unseen_links;
    private Set<String> seen_links;

//    private String host;

    private int notSuccessFullAttempts;

    public Crawler(String url,String domain,int fLimit){

        unseen_links = new HashSet<>();
        seen_links = new HashSet<>();

        unseen_links.add(url);

        this.fLimit = fLimit;
        this.domain = domain;

    }

    private boolean isSameDomain(String url) {

        return url.contains(this.domain);


    }

    private void addLink(String link){

        if (!(unseen_links.contains(link) || seen_links.contains(link)) )
            unseen_links.add(link);


    }

    private void seenLink(String link){

        if (unseen_links.contains(link)){

            seen_links.add(link);
            unseen_links.remove(link);

        }

    }

    public Crawler crawl(int threshold) throws IOException {

        while (seen_links.size() < fLimit && notSuccessFullAttempts<threshold){

            int seen_links_size = seen_links.size();
            Set<String> new_links_temp = new HashSet<>();
            Set<String> seen_links_temp = new HashSet<>();

            new_links_temp.clear();
            seen_links_temp.clear();

            boolean enough =false;
            int x = 0;

            for(String url : unseen_links){

                x++;

                Set<String> links = new HTML(url).parse().extractAbosluteHrefByTag("a");
                links.removeIf(link -> !isSameDomain(link) || seen_links.contains(link) || unseen_links.contains(link) || new_links_temp.contains(link));

                new_links_temp.addAll(links);

                seen_links_temp.add(url);


//                System.out.println("Links from \""+url+"\" : "+links.size());
//                System.out.println("All Temp: "+new_links_temp.size());
//                System.out.println("Seen Temp: "+seen_links_temp.size());
//                System.out.println("X :"+x);
//                System.out.println("Enough :"+enough);
//                System.out.println("new_links_temp.size()+seen_links.size()+unseen_links.size()-seen_links_temp.size() :"+(new_links_temp.size()+seen_links.size()+unseen_links.size()-seen_links_temp.size()));

                if(new_links_temp.size()+seen_links.size()+unseen_links.size()-seen_links_temp.size() >= fLimit){
                    enough = true;
                    break;
                }

            }

//            System.out.println("Unseen Links : "+unseen_links.size());
//            System.out.println("Seen Links : "+seen_links.size());
//            System.out.println("Enough Before:"+enough);

            if (enough){

                unseen_links.removeAll(seen_links_temp);
//                System.out.println("\tSeen in Enough Links Before : "+seen_links.size());
                seen_links.addAll(unseen_links);
//                System.out.println("\tSeen in Enough Links After/Before : "+seen_links.size());
//                System.out.println("\tNew in Enough Links Before : "+new_links_temp.size());
                int n = 0;
                for(String l : new_links_temp){
//                    System.out.println("\t\tContains L ("+n+") : "+seen_links.contains(l));
                    seen_links.add(l);
                    n++;
//                    System.out.println("\t\tSeen in Iteration ("+n+") : "+seen_links.size());
//                    System.out.println("\t\tString ("+n+") : "+l);


                }
                seen_links.addAll(new_links_temp);
//                System.out.println("\tSeen in Enough Links After : "+seen_links.size());
                seen_links.addAll(new_links_temp);
                new_links_temp.clear();
                unseen_links.clear();


            } else {

                seen_links.addAll(unseen_links);
                unseen_links.clear();
                unseen_links.addAll(new_links_temp);

            }

//
//            System.out.println("Unseen Links : "+unseen_links.size());
//            System.out.println("Seen Links : "+seen_links.size());

            if(seen_links_size == seen_links.size())
                notSuccessFullAttempts++;

        }

//        while (seen_links.size() < fLimit && notSuccessFullAttempts<threshold){
//
//            int seen_links_size = seen_links.size();
//
//            for(String url : unseen_links){
//
//                Set<String> links = new HTML(url).parse().extractAbosluteHrefByTag("a");
//
//
//                for(String link : links){
//
//                    try{
//
//                        if(isSameDomain(link))
//                            addLink(link);
//                        else
//                            unseen_links.remove(link);
//
//                    } catch (MalformedURLException ex){
//
//                    }
//
//                    if(unseen_links.size()+seen_links.size() >= fLimit){
//
//                        seen_links.addAll(unseen_links);
//                        unseen_links.clear();
//                        break;
//
//                    }
//
//                }
//
//
//                seenLink(url);
//
//
//
//                System.out.println(seen_links.size());
//
//            }
//
//            if(seen_links_size == seen_links.size())
//                notSuccessFullAttempts++;
//
//        }

        return this;

    }

    public Set<String> getLinks(){

        return seen_links;

    }

}
