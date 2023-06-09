package com.example.driverlink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DriveActivity extends AppCompatActivity {

    private ListView stop_list;
    ImageButton back;
    Button end;
    TextView count_zan;
    TextView count_svob;
    EditText kol_in;
    EditText kol_out;
    ImageButton send_kol;
    TextView stop_view;
    List<String> stops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_drive);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        stop_view = (TextView) findViewById(R.id.stop_view);
        kol_in = (EditText) findViewById(R.id.kol_in);
        kol_out = (EditText) findViewById(R.id.kol_out);
        send_kol = (ImageButton) findViewById(R.id.send_kol);
        count_zan = (TextView) findViewById(R.id.count_zan);
        count_svob = (TextView) findViewById(R.id.count_svob);
        end = (Button) findViewById(R.id.end_trip);
        end.setVisibility(View.INVISIBLE);

        count_zan.setText("0");
        count_svob.setText("18");
        Intent intent = getIntent();
        String get_num = intent.getStringExtra("route_number");
        if(count_svob.getText().toString().equals("0")){
            Toast.makeText(DriveActivity.this, "Больше клиентов войти не может", Toast.LENGTH_LONG).show();
        }

        String get_url = "http://192.168.100.23:8080/stops/get-all-stops-by-route/";
        String get_stop_url = get_url + get_num;
        stop_list = (ListView) findViewById(R.id.stop_list);
        stops = getStops(get_stop_url);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stops);
        stop_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        stop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String stop = stop_list.getItemAtPosition(index).toString();
                System.out.println("stop = " + stop);
                stop_view.setText(stop);
                adapter.remove(stop);
                adapter.notifyDataSetChanged();
                if(adapter.getCount() == 0){
                    end.setVisibility(View.VISIBLE);
                }
            }
        });

        this.send_kol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = kol_in.getText().toString().trim();
                int voshlo = Integer.parseInt(in);
                String out = kol_out.getText().toString().trim();
                int vyshlo = Integer.parseInt(out);
                String zan = count_zan.getText().toString();
                int zan_in_drive = Integer.parseInt(zan);
                String svob = count_svob.getText().toString();
                int svob_in_drive = Integer.parseInt(svob);
                int count_zanyato = zan_in_drive + voshlo - vyshlo;
                int count_svobodno = svob_in_drive - voshlo + vyshlo;
                String change_zan = String.valueOf(count_zanyato);
                String change_svob = String.valueOf(count_svobodno);
                kol_in.getText().clear();
                kol_out.getText().clear();
                count_zan.setText(change_zan);
                count_svob.setText(change_svob);
            }
        });

        this.end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriveActivity.this, SystemOutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        back = (ImageButton) findViewById(R.id.back);
        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriveActivity.this, InfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DriveActivity.this, InfoActivity.class);
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

}