package com.example.routetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;

import java.io.IOException;

public class TripActivity extends AppCompatActivity {

    static int progress;
    ProgressBar progressBar;
    Button close_trip;
    int progressStatus = 0;
    int MaxValue = 100;
    Handler handler = new Handler();
    ImageButton exit;
    String id_route = "";
    String id_user = "";
    String number_route = "";
    String del_url = "http://192.168.100.23:8080/trip/delete-trip-by-user?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_trip);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        id_user = intent.getStringExtra("user_id");

        progress = 0;
        exit = (ImageButton) findViewById(R.id.exit);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setMax(MaxValue);
        close_trip = (Button) findViewById(R.id.close_trip);
        if(progressStatus != 100){
            close_trip.setVisibility(View.INVISIBLE);
            exit.setVisibility(View.INVISIBLE);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus <= MaxValue) {
                    progressStatus = doSomeWork();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if(progressStatus == 100){
                                close_trip.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
            private int doSomeWork(){
                try{
                    Thread.sleep(300);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                return ++progress;
            }
        }).start();

        this.close_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete_trip_url = del_url + "user=" + id_user;
                System.out.println("url del trip = " + delete_trip_url);
                String info = deleteTrip(delete_trip_url);
                System.out.println("delete trip info = " + info);
                Toast.makeText(TripActivity.this, "Поездка окончена, вы можете выйти из приложения", Toast.LENGTH_SHORT).show();
                exit.setVisibility(View.VISIBLE);
            }
        });


        this.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuitDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(TripActivity.this);
        quitDialog.setTitle("Вы уверены что хотите выйти?");
        quitDialog.setIcon(R.drawable.exit);
        quitDialog.setCancelable(false);
        quitDialog.setPositiveButton("Да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TripActivity.this, "Спасибо что пользуетесь нашим приложением. Хорошего вам дня!)"
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        quitDialog.show();
    }

    public String deleteTrip(String url){
        String info = "";
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        DeleteMethod delete = new DeleteMethod(url);
        try {
            System.out.println("--------------- START INFO ---------------\n");
            client.executeMethod(delete);
            System.out.println("Get status line = " + delete.getStatusLine());
            System.out.println("--------------- END INFO ---------------\n");

            info = delete.getResponseBodyAsString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            delete.releaseConnection();
        }

        return info;
    }
}