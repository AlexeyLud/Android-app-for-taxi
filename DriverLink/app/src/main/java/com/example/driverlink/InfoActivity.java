package com.example.driverlink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class InfoActivity extends AppCompatActivity {

    String dr_id = "";
    TextView driver_info;
    TextView your_route;
    TextView route_number;
    TextView your_bus;
    TextView bus_count;
    Button go;
    ImageButton back;
    String get_info = "http://192.168.100.23:8080/route/get-route-by-driver?";
    String driver = "";
    String route_name = "";
    String number_route = "";
    String bus = "";
    String count = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_info);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        driver_info = (TextView) findViewById(R.id.driver_info);
        your_route = (TextView) findViewById(R.id.your_route);
        route_number = (TextView) findViewById(R.id.route_number);
        your_bus = (TextView) findViewById(R.id.your_bus);
        bus_count = (TextView) findViewById(R.id.bus_count);
        go = (Button) findViewById(R.id.go);

        Intent intent = getIntent();
        dr_id = intent.getStringExtra("driver_id");
        System.out.println("dr_id = " + dr_id);

        String get_info_url = get_info + "driver=" + dr_id;
        System.out.println("get_info_url = " + get_info_url);
        String inf = getInformation(get_info_url);
        System.out.println("info about information = " + inf);

        String[] info = inf.split(" ");
        driver = info[3] + " " + info[4];
        driver_info.setText(driver);
        route_name = info[2];
        your_route.setText(route_name);
        number_route = info[1];
        route_number.setText(number_route);
        bus = info[5] + " " + info[6] + " " + info[7] + " " + info[8];
        your_bus.setText(bus);
        count = info[9];
        bus_count.setText(count);

        this.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, DriveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("route_number", number_route);
                intent.putExtra("count_bus", count);
                startActivity(intent);
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static String getInformation(String url){
        String response = "";
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        GetMethod get = new GetMethod(url);
        try {
            System.out.println("--------------- START INFO ---------------\n");
            client.executeMethod(get);
            System.out.println("Get status line = " + get.getStatusLine());
            System.out.println("--------------- END INFO ---------------\n");

            response = get.getResponseBodyAsString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            get.releaseConnection();
        }

        return response;
    }
}