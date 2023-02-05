package com.github.martinfrank.games.chess.chessclient;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.martinfrank.tcpclientserver.ClientMessageReceiver;
import com.github.martinfrank.tcpclientserver.TcpClient;

public class MainActivity extends AppCompatActivity implements ClientMessageReceiver {

    private static final String LOGTAG = "CHESSCLIENT";
    private TcpClient tcpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tcpClient = new TcpClient("192.168.0.11", 8100, this);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                tcpClient.start();
            }
        };
        new Thread(r).start();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        tcpClient.send("test");
                    }
                };
                new Thread(r).start();
            }
        });

    }

    @Override
    public void receive(String s) {
        Log.d(LOGTAG, "received :-) "+s);
    }

    @Override
    public void notifyDisconnect() {

    }
}