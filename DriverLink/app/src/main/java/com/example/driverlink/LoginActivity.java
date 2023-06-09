package com.example.driverlink;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ImageView gal1;
    ImageView gal2;
    EditText login;
    EditText pass;
    ImageButton exit;
    Button in;
    boolean isLoginChecked = false;
    boolean isPassChecked = false;
    boolean isAllFieldsChecked = false;
    String login_url = "http://192.168.100.23:8080/driver/singin?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        gal1 = (ImageView) findViewById(R.id.galochka1);
        gal2 = (ImageView) findViewById(R.id.galochka2);
        login = (EditText) findViewById(R.id.login);
        pass = (EditText) findViewById(R.id.pass);
        in = (Button) findViewById(R.id.in);

        gal1.setVisibility(View.INVISIBLE);
        gal2.setVisibility(View.INVISIBLE);

        this.in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename = login.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(CheckLogin())
                    isLoginChecked = true;
                if(CheckPaasword())
                    isPassChecked = true;
                if(isLoginChecked && isPassChecked)
                    isAllFieldsChecked = true;

                String login_driver_url = login_url + "login=" + usename + "&password=" + password;
                System.out.println("url login = " + login_driver_url);
                String inf = loginDriver(login_driver_url);
                System.out.println("info about login = " + inf);

                if(isAllFieldsChecked){
                    gal1.setVisibility(View.VISIBLE);
                    gal1.setImageResource(R.drawable.green_galochka);
                    gal2.setVisibility(View.VISIBLE);
                    gal2.setImageResource(R.drawable.green_galochka);

                    String driver_id = inf.split(" ")[0];
                    System.out.println("driver_id = " + driver_id);

                    Intent intent = new Intent(LoginActivity.this, InfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("driver_id", driver_id);
                    startActivity(intent);
                }
            }
        });

        exit = (ImageButton) findViewById(R.id.exit);
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
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(LoginActivity.this);
        quitDialog.setTitle("Вы уверены что хотите выйти?");
        quitDialog.setIcon(R.drawable.exit);
        quitDialog.setCancelable(false);
        quitDialog.setPositiveButton("Да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this, "Спасибо что пользуетесь нашим приложением. Хорошего вам дня!)"
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

    private boolean CheckLogin(){
        if (login.length() == 0) {
            login.setError("Вы не ввели логин");
            gal1.setVisibility(View.VISIBLE);
            gal1.setImageResource(R.drawable.error);
            return false;
        }
        return true;
    }

    private boolean CheckPaasword(){
        if (pass.length() == 0) {
            pass.setError("Вы не ввели пароль");
            gal2.setVisibility(View.VISIBLE);
            gal2.setImageResource(R.drawable.error);
            return false;
        }
        return true;
    }

    public static String loginDriver(String url){
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