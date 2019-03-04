package sbd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CSVReader {
    
    public static void main(String[] args) {
        String csvFile = "Tabel Entity.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int i = 0;
              
        //Input query
        System.out.println("Input a query : ");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        
        //Convert ';' --> ' ;'
        String inputan = s.replace(";", " ;");
        
        //Split query berdasarkan spasi
        String[] pisahSpasi = inputan.split(" ");
        
        //Cek split
        for(i = 0; i < pisahSpasi.length; i++){
            System.out.println(pisahSpasi[i]);
        }
        
        //Baca file CSV
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] col = line.split(cvsSplitBy);
                System.out.print("Tabel : ");
                System.out.println(col[0]);
                System.out.print("List Kolom : ");
                for (i = 1; i < col.length; i++) {
                    System.out.print(col[i]+",");
                }
                System.out.println("");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        //Cek query SELECT diawal query
        if(pisahSpasi[0].equalsIgnoreCase("SELECT")){
            //Cek semicolon di akhir query
            if(pisahSpasi[pisahSpasi.length - 1].equals(";")){
                //Cek query FROM
                if(pisahSpasi[2].equalsIgnoreCase("FROM")){
                    // ---- Insert syntax untuk query SELECT FROM below ---- 
                    
                    //Cek ada query JOIN
                    for(i = 3; i<pisahSpasi.length; i++){
                        if(pisahSpasi[i].equalsIgnoreCase("JOIN")){
                            //Cek ada kata ON atau USING
                            for(i = 6; i < pisahSpasi.length; i++){
                                if(pisahSpasi[i].equalsIgnoreCase("ON") || pisahSpasi[i].equalsIgnoreCase("USING")){
                                    // ---- Insert syntax untuk query JOIN ON / USING below ----
                                } else {
                                    System.out.println("SQL Error (Syntax Error)");
                                }   
                            }
                        }
                    }
                } else {
                    System.out.println("SQL Error (Syntax Error)");
                }
            } else {
                System.out.println("SQL Error (Missing ;)");
            }
        } else {
            System.out.println("SQL Error (Syntax Error)");
        }
    }   
}
