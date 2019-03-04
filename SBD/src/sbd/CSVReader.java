/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        int i = 0,j = 0,k = 0,l = 0,tableIdx=0;
        String table = null;
        String[][] col = new String[2][];
        String[] colQuery = new String[4] ;
        boolean from, on, join, star = false;
        
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
        
        Scanner baca = new Scanner(System.in);
        System.out.print("Masukkan query : ");
        String input = baca.nextLine();
        String titikKoma = input.replace(";", " ; "); //Memisahkan titik koma menggunakan spasi untuk masuk ke array
        String query[] = titikKoma.split(" ");  //Memisah kata memakai spasi dan memasukkan ke array
        
        if ("select".equalsIgnoreCase(query[0])) { //Mengecek apakah kata pertama merupakan select
            for (j = 0; j < query.length; j++) {
                if ("from".equalsIgnoreCase(query[j])) { //Mengecek lokasi kata "from"
                    i=0;
                    from = true;
                    table = query[j+1];
                    if ("buku".equalsIgnoreCase(query[j+1])) {
                        tableIdx=0;
                    }else tableIdx=1;
                }
                if ("*".equalsIgnoreCase(query[j])) { //Mengecek kata "*"
                    star=true;
                }
                if ("join".equalsIgnoreCase(query[j])) { //Mengecek kata "join"
                    join=true;
                }
            }
        }
        
        if (star) { //Jika ada bintang maka memasukkan semua kolom dari tabel
            for (i = 0;  i<2 ; i++) {
                if (table.equalsIgnoreCase(col[i][0])) {
                    l=0;
                    for (int m = 1; m < col[i].length; m++) {
                        colQuery[l]=col[i][m]; 
                        l++;
                    }
                }
            }
        }
        else {
            colQuery = query[1].split(","); //Jika tidak ada bintang maka memasukkan kolom sesuai query
        }
        
        System.out.println("Hasil Query : ");
        System.out.println("Tabel : "+col[tableIdx][0]);
        System.out.print("Kolom : ");
        for (int m = 0; m < colQuery.length; m++) {
            System.out.print(colQuery[m]+" ");
        }
    }
}
