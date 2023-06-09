package com.example.routetaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectStopsActivity extends AppCompatActivity {

    private ImageButton back;
    private Button see_routes;
    private Spinner stop1;
    private Spinner stop2;
    private String start_stop = "";
    private String end_stop = "";
    private List<String> all_stops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_select_stops);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String client_url = "http://192.168.100.23:8080/users/add-user?";
        String get_stops_url = "http://192.168.100.23:8080/stops/get-all";
        all_stops = getStops(get_stops_url);
        System.out.println("all_stops = " + all_stops);

        stop1 = (Spinner) findViewById(R.id.stop1);
        stop2 = (Spinner) findViewById(R.id.stop2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, all_stops);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stop1.setAdapter(adapter);
        stop2.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Intent intent = getIntent();
        String start_ost = intent.getStringExtra("start_stop");
        System.out.println("start_ost = " + start_ost);

        ArrayAdapter find_adapter = (ArrayAdapter) stop1.getAdapter();
        int position = adapter.getPosition(start_ost);
        System.out.println("position = " + position);

        int id_start_stop = all_stops.indexOf(start_ost);
        System.out.println("id_start_stop = " + id_start_stop);

        if(start_ost == null){
            stop1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                    start_stop = all_stops.get(selectedItemPosition);
                    System.out.println("start_stop select in num = " + start_stop);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    start_stop = all_stops.get(0);
                    System.out.println("start_stop nothing click in num = " + start_stop);
                }
            });

            stop2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                    end_stop = all_stops.get(selectedItemPosition);
                    System.out.println("end_stop select in num = " + end_stop);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    end_stop = all_stops.get(0);
                    System.out.println("end_stop nothing click in num = " + end_stop);
                }
            });
        }
        else{
            stop1.setSelection(position);
            start_stop = all_stops.get(position);
            System.out.println("start stop in qr = " + start_stop);

            stop2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                    end_stop = all_stops.get(selectedItemPosition);
                    System.out.println("end_stop select in qr = " + end_stop);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                    end_stop = all_stops.get(0);
                    System.out.println("end_stop nothing click in qr = " + end_stop);
                }
            });
        }

        String selected = stop1.getSelectedItem().toString();
        System.out.println("selected in stop1 = " + selected);
        int item_id = stop1.getSelectedItemPosition();
        System.out.println("item_id in stop1 = " + item_id);

        see_routes = (Button) findViewById(R.id.see_routes);
        this.see_routes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String create_client_url = client_url + "start_stop=" + start_stop + "&end_stop=" + end_stop;
                System.out.println("url user = " + create_client_url);
                String id = createUser(create_client_url);
                System.out.println("created user_id = " + id);
                System.out.println("start send = " + start_stop);
                System.out.println("end send = " + end_stop);
                Intent intent = new Intent(SelectStopsActivity.this, OrderRouteTaxiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("start", start_stop);
                intent.putExtra("end", end_stop);
                intent.putExtra("user_id", id);
                startActivity(intent);
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectStopsActivity.this, OrderSelectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectStopsActivity.this, OrderSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static List<String> getStops(String url){
        List<String> stops = new ArrayList<>();
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        GetMethod get = new GetMethod(url);
        try {
            System.out.println("--------------- START INFO ---------------\n");
            client.executeMethod(get);
            System.out.println("Get status line = " + get.getStatusLine());
            System.out.println("--------------- END INFO ---------------\n");

            String[] list_stops = get.getResponseBodyAsString().split("\n");
            stops.addAll(Arrays.asList(list_stops));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            get.releaseConnection();
        }

        return stops;
    }

    public String createUser(String url){
        String user_id = "";
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        PostMethod post = new PostMethod(url);
        try {
            System.out.println("--------------- START INFO ---------------\n");
            client.executeMethod(post);
            System.out.println("Get status line = " + post.getStatusLine());
            System.out.println("--------------- END INFO ---------------\n");

            String id = post.getResponseBodyAsString();
            user_id = id;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }

        return user_id;
    }
}