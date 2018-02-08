package com.alberto.reproductoryoutube;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyCwRc-CvHRgYd6zQZ3xGmYq4qgv7jPTOxc";
    private static final String YOUTUBE_VIDEOS_ID = "PLA_I2ay5YcUUr8_rE-DlVknObkJ6-Rn3q";

    public ListView mListaVideos;
    public ImageView mImagenCabecera;
    private String mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListaVideos = findViewById(R.id.listaVideos);
        mImagenCabecera = findViewById(R.id.imagenCabecera);

        mImagenCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Parseador parseador = new Parseador(mContent);
                parseador.process();

                final Adaptador adaptador = new Adaptador(
                        MainActivity.this,
                        parseador.getVideos()
                );

                mListaVideos.setAdapter(adaptador);

                mListaVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                        Intent video = YouTubeStandalonePlayer.createPlaylistIntent(
                                MainActivity.this,
                                YOUTUBE_API_KEY,
                                YOUTUBE_VIDEOS_ID,
                                position,
                                1000,
                                true,
                                false
                        );
                        startActivity(video);
                    }
                });
            }
        });

        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://www.youtube.com/feeds/videos.xml?playlist_id=PLA_I2ay5YcUUr8_rE-DlVknObkJ6-Rn3q");
    }

    private class DownloadData extends AsyncTask<String, Void, String>{
        private static final String TAG = "DownloadData";

        @Override
        protected String doInBackground(String... strings){
            mContent = downloadXmlFile(strings[0]);

            if (mContent == null){
                Log.d(TAG, "Problema al descargar el xml");
            }
            return mContent;
        }

        public String downloadXmlFile(String urlPath){
            StringBuilder tempBuffer = new StringBuilder();

            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Code status: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charsRead;
                char[] inputBuffer = new  char[500];

                while (true){
                    charsRead = isr.read(inputBuffer);

                    if (charsRead <= 0){
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }
                return tempBuffer.toString();
            }catch (IOException e){
                Log.e(TAG, "No se ha podido descargar el Rss - " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }
}