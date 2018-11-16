package ir.project.usc;

//Analyzers
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

//Directory
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

//Java IO
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Retriever {

    private IndexWriter indexWriter;
    private Analyzer analyzer;
    private Directory directory;
    public  IndexReader indexReader;


    private void initialize() throws IOException {


//        Analyzer analyzer = new Analyzer() {
//            @Override
//            protected TokenStreamComponents createComponents(String s) {
//
//                Tokenizer source = new StandardTokenizer();
//                TokenStream filter = new StopFilter(new TokenStream(),);
//                filter = new LowerCaseFilter(filter);
//                filter = new StandardFilter(filter);
//                return new TokenStreamComponents(source, filter);
//
//            }
//        };


        analyzer =  new StandardAnalyzer();
        directory = new RAMDirectory();
        indexWriter = new IndexWriter(directory,new IndexWriterConfig(analyzer));


    }


    public Retriever() {

        try {

            initialize();

        } catch (IOException e){

        }


    }

    public void commitChanges(){

        try {

            indexWriter.commit();
            indexWriter.close();

        } catch (IOException e){

            System.out.println(e);

        }


    }

    public Retriever addDocument(StructureDoc [] fields,CNNDoc page){

        Document document = new Document();

        for(StructureDoc field : fields){

            System.out.println(field.getName());


            switch (field.getName()){

                case "title":
                    document.add(new TextField("title",page.getTitle(),field.isStore()?Field.Store.YES:Field.Store.NO));
                    break;

                case "headlines":
                    for(String headline : page.getHeadlines())
                        document.add(new TextField("headline",headline,field.isStore()?Field.Store.YES:Field.Store.NO));
                    break;


            }


        }


        try {

            System.out.println(document);
            indexWriter.addDocument(document);

        } catch (IOException e){

            System.out.println(e);

        }

        return this;

    }

    public Retriever addDocuments(StructureDoc [] fields,Set<CNNDoc> pages){

        for( CNNDoc page : pages )
            this.addDocument(fields,page);

        return this;
    }

    public TopDocs search(String title,String query){

        try {

            indexReader =  DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            QueryParser parser = new QueryParser(title,analyzer);

            try {

                Query queryParsed = parser.parse(query);

                try {

                    System.out.println(queryParsed);
                    return indexSearcher.search(queryParsed,10);

                }catch (IOException e){

                    System.out.println(e);

                }


            }catch (ParseException ex){

                System.out.println(ex);

            }

        }catch (IOException e){

            System.out.println(e);

        }

    return null;

    }

//    public void search(Map<String,String> queryOccurance){
//
////        IndexReader indexReader =  DirectoryReader.open(directory);
////        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        //indexSearcher.search();
//
//        Query query1 = new BooleanQuery.Builder()
//                .add(new TermQuery(new Term("title","java")),BooleanClause.Occur.MUST)
//                .add(new TermQuery(new Term("title","persist")),BooleanClause.Occur.SHOULD)
//                .add(new TermQuery(new Term("title","web")),BooleanClause.Occur.MUST_NOT)
//                .build();
//
//
//        QueryParser parser = new QueryParser("tyytfuy",analyzer);
//
//        try {
//            Query query3 = parser.parse("+Java and persist or -web");
//            Query query4 = parser.parse("jave~");
//            Query query5 = parser.parse("F*work");
//            System.out.println(query3);
//            System.out.println(query4.toString());
//            System.out.println(query5.toString());
//        }catch (ParseException ex){
//            System.out.println(ex);
//        }
//
//
//
//    }

}
