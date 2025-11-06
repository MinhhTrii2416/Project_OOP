import Person.*;

import java.util.*;
import java.io.*;
import java.util.ArrayList;

import Person.Librarian;
public class Main{
    public static void main( String[] args){
        ArrayList<Librarian> list = new ArrayList<>();
        list = loadListLib("./data/Librarian.csv");
        for( int i=0; i<list.size(); i++){
            list.get(i).showINFO();
            System.out.println();
        }
    }

    public static ArrayList<Librarian> loadListLib (String filename){
        ArrayList<Librarian> list = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            br.readLine();
            String line;
            while( (line = br.readLine()) != null ){
                String[] data = line.split(",");
                Librarian l = new Librarian( data[0], data[1], data[2], data[3], data[4], data[5], data[6], Double.parseDouble(data[7]), data[8]);
                list.add(l);
            }
        }
        catch( IOException e){
            e.printStackTrace();
        }
        return list;
    }
}


