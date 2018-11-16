package ir.project.usc;

import com.google.gson.*;

import java.io.*;
import java.util.Set;

public class JSON {

//    public static int DOCUMENT_STRUCTURE = 0x1;
//    public static int HTML_STRUCTURE = 0x2;

    private String      path;
    private CNNDoc      object;
    private Set<CNNDoc> objects;

    public JSON(String path){

        this.objects = null;
        this.object = null;
        this.path = path;

    }

    public JSON(CNNDoc object){

        this.path = null;
        this.objects = null;
        this.object = object;

    }

    public JSON(Set<CNNDoc> objects){

        this.path = null;
        this.object = null;
        this.objects = objects;

    }


    public CNNDoc readFromFileIndex() throws FileNotFoundException {

        BufferedReader jsonFile = new BufferedReader(new FileReader(path));
        CNNDoc jsonObj = new GsonBuilder().setPrettyPrinting().create().fromJson(jsonFile,CNNDoc.class);

        return jsonObj;

    }

    public StructureDoc readFromFileStructure() throws FileNotFoundException {

        BufferedReader jsonFile = new BufferedReader(new FileReader(path));
        StructureDoc jsonObj = new GsonBuilder().setPrettyPrinting().create().fromJson(jsonFile,StructureDoc.class);

        return jsonObj;

    }

    public StructureDoc [] readFromFileStructures() throws FileNotFoundException {

        BufferedReader jsonFile = new BufferedReader(new FileReader(path));
        StructureDoc [] jsonObj = new GsonBuilder().setPrettyPrinting().create().fromJson(jsonFile,StructureDoc[].class);

        return jsonObj;

    }


    public void writeToFile(String path) throws FileNotFoundException {

        try  {

            Writer writer = new FileWriter(path,true);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            if(object!=null)
                gson.toJson(object, writer);
            else
                gson.toJson(objects,writer);

            writer.close();

        }catch (IOException e){

            System.out.println(e);

        }

    }



    public String toString() {

        String output = new GsonBuilder().setPrettyPrinting().create().toJson(object);

        return output;

    }

}
