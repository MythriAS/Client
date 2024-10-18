package com.example.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private TextView txtReceiveData;
    private EditText edtServerIP, edtServerPort;
    private Button btnClientConnect;
    private String serverIp;
    private int serverPort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtReceiveData = findViewById(R.id.tvRecTxt);
        edtServerIP = findViewById(R.id.edt_server_name);
        edtServerPort = findViewById(R.id.edt_server_port);
        btnClientConnect = findViewById(R.id.btn_client_connect);
        btnClientConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickConnect(view);
            }
        });
    }
    public void onClickConnect(View view) {
        serverIp = edtServerIP.getText().toString();
        serverPort = Integer.valueOf(edtServerPort.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket=new Socket(serverIp,serverPort);
                    BufferedReader br_input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String txtFromServer=br_input.readLine();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtReceiveData.setText(txtFromServer);
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}