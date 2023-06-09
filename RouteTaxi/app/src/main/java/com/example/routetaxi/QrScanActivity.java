package com.example.routetaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.Manifest;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    int REQUEST_CAMERA = 0;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide(); // без заголовка
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    public void handleResult(Result result){

        AlertDialog.Builder addDialog = new AlertDialog.Builder(QrScanActivity.this);
        addDialog.setTitle("Вы уверены что хотите выбрать эту остановку?");
        addDialog.setIcon(R.drawable.logo);
        addDialog.setCancelable(false);
        addDialog.setPositiveButton("Да!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = result.getText();
                System.out.println("str = " + str);
                Toast.makeText(QrScanActivity.this, "Остановка успешно выбрана как ваша начальная", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QrScanActivity.this, SelectStopsActivity.class);
                intent.putExtra("start_stop", str);
                startActivity(intent);
            }
        });

        addDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        addDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QrScanActivity.this, OrderSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}