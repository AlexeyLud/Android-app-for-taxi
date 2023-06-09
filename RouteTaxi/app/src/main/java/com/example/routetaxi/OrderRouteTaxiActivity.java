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
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRouteTaxiActivity extends AppCompatActivity {

    private ImageButton back;
    private Button make_trip;
    private Spinner route;
    private String start_stop = "";
    private String end_stop = "";
    private String user_id = "";
    private String route_id = "";
    private String number_route = "";
    private String r = "";
    private List<String> all_routes = new ArrayList<>();
    String del_url = "http://192.168.100.23:8080/users/delete-user/";
    String trip_url = "http://192.168.100.23:8080/trip/add-trip?";
    String get_routes_url = "http://192.168.100.23:8080/route/get-routes-by-stops?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_order_route_taxi);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        start_stop = intent.getStringExtra("start");
        end_stop = intent.getStringExtra("end");
        user_id = intent.getStringExtra("user_id");
        int us_id = Integer.parseInt(user_id);
        System.out.println("us_id = " + us_id);
        System.out.println("start_stop = " + start_stop);
        System.out.println("end_stop = " + end_stop);

        String routes_url = get_routes_url + "startStop=" + start_stop + "&endStop=" + end_stop;
        all_routes = getRoutesByStops(routes_url);
        System.out.println("all_routes = " + all_routes);

        route = (Spinner) findViewById(R.id.route);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, all_routes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        route.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                r = all_routes.get(selectedItemPosition);
                System.out.println("route select = " + r);
                String[] routess = r.split(" ");
                System.out.println(Arrays.toString(routess));
                route_id = routess[0];
                number_route = routess[1];
            }
            public void onNothingSelected(AdapterView<?> parent) {
                r = all_routes.get(0);
                System.out.println("route select = " + r);
                String[] routess = r.split(" ");
                System.out.println(Arrays.toString(routess));
                route_id = routess[0];
                number_route = routess[1];
            }
        });

        make_trip = (Button) findViewById(R.id.make_trip);
        this.make_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("route_id = " + route_id);
                System.out.println("user_id = " + user_id);

                String create_trip_url = trip_url + "route=" + route_id + "&user=" + user_id;
                System.out.println("url trip = " + create_trip_url);
                String inf = createTrip(create_trip_url);
                System.out.println("created trip info = " + inf);

                Intent intent = new Intent(OrderRouteTaxiActivity.this, TripActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String del_user_url = del_url + user_id;
                String inf = deleteUser(del_user_url);
                System.out.println(" info about delete user = " + inf);
                Intent intent = new Intent(OrderRouteTaxiActivity.this, SelectStopsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        String del_user_url = del_url + user_id;
        String inf = deleteUser(del_user_url);
        System.out.println(" info about delete user = " + inf);
        Intent intent = new Intent(OrderRouteTaxiActivity.this, SelectStopsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static List<String> getRoutesByStops(String url){
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

    public String createTrip(String url){
        String info = "";
        MultiThreadedHttpConnectionManager connectionManager =
                new MultiThreadedHttpConnectionManager();
        HttpClient client = new HttpClient(connectionManager);
        PostMethod post = new PostMethod(url);
        try {
            System.out.println("--------------- START INFO ---------------\n");
            client.executeMethod(post);
            System.out.println("Get status line = " + post.getStatusLine());
            System.out.println("--------------- END INFO ---------------\n");

            info = post.getResponseBodyAsString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            post.releaseConnection();
        }

        return info;
    }

    public String deleteUser(String url){
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