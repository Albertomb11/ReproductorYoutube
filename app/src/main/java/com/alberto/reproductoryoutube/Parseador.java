package com.alberto.reproductoryoutube;


import android.util.Log;

import com.alberto.reproductoryoutube.model.Video;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Parseador {
    private static final String TAG="Parseador";

    private String xmlData;
    public ArrayList<Video> videos;

    public Parseador(String xmlData){
        this.xmlData = xmlData;
        videos = new ArrayList<>();
    }

    public ArrayList<Video> getVideos(){
        return  videos;
    }

    public boolean process(){
        boolean status = true;

        Video currentRecord = null;

        boolean inEntry = false;

        String valText ="";
         try {
             XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
             factory.setNamespaceAware(true);
             XmlPullParser xpp = factory.newPullParser();
             xpp.setInput(new StringReader(this.xmlData));
             int eventType = xpp.getEventType();

             while (eventType != XmlPullParser.END_DOCUMENT) {
                 String tagName = xpp.getName();

                 switch (eventType) {
                     case XmlPullParser.START_TAG:
                         if (tagName.equalsIgnoreCase("entry")) {
                             inEntry = true;
                             currentRecord = new Video();
                         }
                         break;
                     case XmlPullParser.TEXT:
                         valText = xpp.getText();
                         break;
                     case XmlPullParser.END_TAG:
                         if (inEntry) {
                             if (tagName.equalsIgnoreCase("entry")) {
                                 videos.add(currentRecord);
                                 inEntry = false;
                             } else if (tagName.equalsIgnoreCase("title")) {
                                 currentRecord.setTitulo(valText);
                             } else if (tagName.equalsIgnoreCase("videoId")) {
                                 currentRecord.setId(valText);
                             } else if (tagName.equalsIgnoreCase("name")) {
                                 currentRecord.setAutor(valText);
                             } else if (tagName.equalsIgnoreCase("thumbnail")) {
                                 String url = xpp.getAttributeValue(null, "url");
                                 currentRecord.setImagen(url);
                             }
                         }
                         break;
                     default:
                 }
                 eventType = xpp.next();
             }
         }catch (Exception e){
             Log.d(TAG,"Problemas al parsear el RSS"+e.getMessage());
             e.printStackTrace();
             status = false;
         }
         return status;
    }

    public Video getVideo(int x){
        return videos.get(x);
    }
}
