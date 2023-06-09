package com.example.routetaxi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutesStopsActivity extends AppCompatActivity {

    private ListView route_list;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_routes_stops);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String get_route_url = "http://192.168.100.23:8080/route/get-all";
        List<String> routes = new ArrayList<>();
        route_list = (ListView) findViewById(R.id.route_list);
        routes = getRoutes(get_route_url);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes);
        route_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        route_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String str = route_list.getItemAtPosition(index).toString();
                String[] s = str.split(" ");
                String num = s[0];
                String name = s[1];

                Intent intent = new Intent(RoutesStopsActivity.this, StopsActivity.class);
                intent.putExtra("route_number", num);
                startActivity(intent);
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoutesStopsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RoutesStopsActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static List<String> getRoutes(String url){
        List<String> routes = new ArrayList<>();
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        GetMethod get = new GetMethod(url);
        try {
            System.out.println("--------------- START INFO ---------------\n");
            client.executeMethod(get);
            System.out.println("Get status line = " + get.getStatusLine());
            System.out.println("--------------- END INFO ---------------\n");

            String[] list_routes = get.getResponseBodyAsString().split("\n");
            routes.addAll(Arrays.asList(list_routes));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            get.releaseConnection();
        }

        return routes;
    }
}