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
        int i = 0,j = 0,k = 0,l = 0,tableIdx=0,joinIdx=0;
        String table = null;
        String[][] col = new String[2][];
        String[] colQuery = new String[4] ;
        String[] tableJoin = new String[1];
        boolean from, onusing = false, join = false, star, error = false;
        
        try {
            br = new BufferedReader(new FileReader(csvFile)); //Membuka file CSV
            while ((line = br.readLine()) != null) { //Selama isi CSV masih ada looping baca file CSV
                col[i]  = line.split(cvsSplitBy);  //Memisah kata memakai spasi dan memasukkan ke array
                System.out.print("Tabel : ");
                System.out.println(col[i][0]);
                System.out.print("List Kolom : ");
                for (j = 1; j < col[i].length; j++) {
                    System.out.print(col[i][j]+",");
                }
                System.out.println("");
                i++;
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
               
        //Input query
        System.out.println("");
        System.out.println("Input a query : ");
        Scanner baca = new Scanner(System.in);
        String input = baca.nextLine();
        
        //Convert ';' --> ' ;'
        String titikKoma = input.replace(";", " ;");
        
        //Split query berdasarkan spasi
        String[] query = titikKoma.split(" ");
        
        //Cek split
//        for(i = 0; i < query.length; i++){
//            System.out.println(query[i]);
//        }
        
        //Cek query SELECT diawal query
        if(query[0].equalsIgnoreCase("SELECT")){
            //Cek semicolon di akhir query
            if(query[query.length - 1].equals(";")){
                //Cek query FROM                   
                if(query[2].equalsIgnoreCase("FROM")){
                    // ---- Insert syntax untuk query SELECT FROM below ---- 
                    from = true;
                    i = 0;
                    table = query[3];
                    if ("buku".equalsIgnoreCase(query[3])) {
                        tableIdx = 0;
                    } else if ("penulis".equalsIgnoreCase(query[3])) {
                        tableIdx = 1;
                    } else {
                        System.out.println("SQL Error (No such table exist)");
                        error = true;
                    }   
                    if (query[1].equals("*")) { //Jika ada bintang maka memasukkan semua kolom dari tabel
                        star = true;
                        for (i = 0;  i<2 ; i++) {
                            if (table.equalsIgnoreCase(col[i][0])) {
                                l=0;
                                for (int m = 1; m < col[i].length; m++) {
                                    colQuery[l] = col[i][m]; 
                                    l++;
                                }
                            }
                        }
                    } else {
                        colQuery = query[1].split(","); //Jika tidak ada bintang maka memasukkan kolom sesuai query
                    }
                } else {
                    System.out.println("SQL Error (Syntax Error)");
                    error = true;
                }
                
                //Cek ada query JOIN
                for(i = 3; i<query.length; i++){
                    if(query[i].equalsIgnoreCase("JOIN")){
                        // ---- Insert syntax untuk query JOIN FROM below ----
                        join  = true;
                        joinIdx = i+1;
                        //Cek ada kata ON atau USING
                        for(i = 6; i < query.length; i++){
                            if(query[i].equalsIgnoreCase("ON") || query[i].equalsIgnoreCase("USING")){
                                // ---- Insert syntax untuk query JOIN ON / USING below ----
                                onusing = true;
                                tableJoin = query[joinIdx].split(","); //memasukkan nama table yang di join ke array
                            }   
                        }
                    }
                }
                if(onusing == false || (join == false && onusing == true)){
                    error = true;
                    System.out.println("SQL Error (Syntax error)");
                }
            } else {
                System.out.println("SQL Error (Missing ;)");
                error = true;
            }    
        } else {
            System.out.println("SQL Error (Syntax error)");
            error = true;
        }
        
        //Jika query tidak ada error samasekali, output tabel & kolom
        System.out.println("");
        if (error == false){
            System.out.println("Hasil Query : ");
            System.out.println("Tabel : "+col[tableIdx][0]);
            if (joinIdx!=0) {
                for (int m = 0; m < tableJoin.length; m++) {
                    System.out.println("Tabel "+(m+2)+" : "+tableJoin[m]);
                }
            }
            System.out.print("Kolom : ");
            for (int m = 0; m < colQuery.length; m++) {
                System.out.print(colQuery[m]+" ");
            }
        }
    }
}