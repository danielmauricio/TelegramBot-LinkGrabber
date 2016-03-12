package org.telegram.linksManager;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class LinkManager {

    private static String username;
    

    public LinkManager(String username){
    	this.username = username;
    }

    private static void GetHTMLFromWebSite(String url, int idFolder) throws IOException {
        File folderName = new File(username + "/"+idFolder);
        folderName.mkdir();
        File htmlFile = new File(username+"/"+idFolder+"/file.html");
        try {
            Document doc = Jsoup.connect(url).get();
            BufferedWriter output = new BufferedWriter(new FileWriter(htmlFile));
            output.write(doc.toString());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    };

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    public static void getLinks(String url, int folder){
        Document doc;
        Elements links;
        Elements downloadedFiles;
        File folderName = new File(username+"/");
        File idFolder = new File(username + "/"+folder);
        if(!folderName.exists()){
        	boolean bool = folderName.mkdir();
        } 
        if(!idFolder.exists()){
        	idFolder.mkdirs();
        }
        File linksFile = new File(username+"/"+folder+"/links.txt");
        
       
        try {
            doc = Jsoup.connect(url).get();
            links = doc.select("a[href]");
            downloadedFiles = doc.select(("a[href$=.(pdf|odt|doc)]"));
            BufferedWriter linksOutput = new BufferedWriter(new FileWriter(linksFile));
            for (Element link : links) {
                System.out.println(link.attr("abs:href"));
                linksOutput.write(link.attr("abs:href")+"\n");
                //print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
            }
            linksOutput.close();
            GetHTMLFromWebSite(url,folder);
            System.out.println("Debio completar el HTML");
            for( Element file: downloadedFiles){
            	
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    /*public static void main(String[] args) {
        try {
            GetHTMLFromWebSite("http://tecdigital.tec.ac.cr/register/?return_url=%2fdotlrn%2findex");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLinks("http://tecdigital.tec.ac.cr/register/?return_url=%2fdotlrn%2findex");
    }*/
}