package sbd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CSVReader {
    public boolean basicQueryCheck(String[] query, String[][] tabel){
        //Fungsi akan RETURN nilai TRUE jika ada error
        int i;
        boolean found = false;
        
        //Memeriksa semicolon
        if(query[query.length - 1].equals(";")){
            //Memeriksa perintah SELECT
            if(query[0].equalsIgnoreCase("SELECT")){
                //Memeriksa perintah FROM
                if(query[2].equalsIgnoreCase("FROM")){
                    //Memeriksa apakah tabel yang dimaksud terdaftar
                    for(i = 0; i<=1; i++){
                        if(query[3].equalsIgnoreCase(tabel[i][0])){
                            found = true;
                        }
                    } 
                    if (found != true){
                        System.out.println("No table exist");
                        return true; //Error
                    }
                } else {
                    System.out.println("SQL Error (Syntax error)");
                    return true; //Error
                } 
            } else {
                System.out.println("SQL Error (Syntax error)");
                return true; //Error
            }
        } else {
            System.out.println("SQL Error (Missing ;)");
            return true; //Error
        }
        
        //Memeriksa apakah kolom yang dimaksud terdaftar
        if(query[1].equals("*")){
            return false;
        } else {
            String[] colQuery = query[1].split(",");
            for(int j = 0; j<colQuery.length-1; j++){
                if(tabel[i][j] != colQuery[j]){
                    return true; //Error
                }
            }
            return false;
        }
    }
    
    
    
    public static void main(String[] args) {
        String csvFile = "Tabel Entity.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int i = 0,j = 0,k = 0,l = 0,tableIdx=0,joinIdx=0;
        String table = null;
        String[][] col = new String[3][];
        String[] colQuery = new String[4];
        String[] tableJoin = new String[1];
        boolean from, on = false, join = false, star, error = false;
        
        //Membaca file .CSV
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
                    } else if ("menulis".equalsIgnoreCase(query[3])) {
                        tableIdx = 2;
                    } else {
                        System.out.println("SQL Error (No such table exist)");
                        error = true;
                    }   
                    if(query[1].equals("*")) { //Jika ada bintang maka memasukkan semua kolom dari tabel
                        star = true;
                        for (i = 0; i<3 ; i++) {
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
                    //Cek ada join atau tidak
                    i = 0;
                    while(!query[i].equalsIgnoreCase("JOIN")){
                       if(query[i].equalsIgnoreCase("JOIN")){
                            // ---- Insert syntax untuk query JOIN FROM below ----
                            join  = true;
                            joinIdx = i+1;
                            //Cek ada kata ON atau USING
                            for(i = 6; i < query.length; i++){
                                if(query[i].equalsIgnoreCase("USING") || query[i].equalsIgnoreCase("ON")){
                                    // ---- Insert syntax untuk query JOIN ON / USING below ----
                                    on = true;
                                    tableJoin = query[joinIdx].split(","); //memasukkan nama table yang di join ke array
                                }   
                            }
                        }
                        int indexTabelJoin = i + 1;
                        if((query[indexTabelJoin].equalsIgnoreCase("Penulis")) || (query[indexTabelJoin].equalsIgnoreCase("buku"))){
                            error = true;
                            System.out.println("SQL Error (Invalid table for join)");
                        }
                        i++;
                    }
                      //Old code
//                    for(i = 3; i<query.length; i++){
//                        if(query[i].equalsIgnoreCase("JOIN")){
//                            // ---- Insert syntax untuk query JOIN FROM below ----
//                            join  = true;
//                            joinIdx = i+1;
//                            //Cek ada kata ON atau USING
//                            for(i = 6; i < query.length; i++){
//                                if(query[i].equalsIgnoreCase("USING") || query[i].equalsIgnoreCase("ON")){
//                                    // ---- Insert syntax untuk query JOIN ON / USING below ----
//                                    on = true;
//                                    tableJoin = query[joinIdx].split(","); //memasukkan nama table yang di join ke array
//                                }   
//                            }    
////                            for(i = 0; i < tableJoin.length-1;i++){
////                                if(tableJoin[i].equalsIgnoreCase(col[]))
////                                for(j = 0; j < col[i].length; j++){
////                     
////                                }
////                            } 
//                        }
//                    }
                    if((join == true && on == false) || (join == false && on == true)){
                        error = true;
                        System.out.println("SQL Error (JOIN Error)");
                    }

                    } else {
                        System.out.println("SQL Error (Syntax Error)");
                        error = true;
                    }
            } else {
                System.out.println("SQL Error (Missing ;)");
                error = true;
            }    
        } else {
            System.out.println("SQL Error (Syntax error)");
            error = true;
        }
        
        //Jika query tidak ada error, output tabel & kolom
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