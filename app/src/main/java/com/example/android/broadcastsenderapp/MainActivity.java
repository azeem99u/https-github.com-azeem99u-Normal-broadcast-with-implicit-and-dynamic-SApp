package com.example.android.broadcastsenderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String MESSAGE_KEY = "message_for_receiverApp";
    public static final String ACTION_BR = "com.example.android.broadcastsenderapp.SEND_BR_STRING";
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText et = findViewById(R.id.et1);
        textView2 = findViewById(R.id.textView2);
        findViewById(R.id.button).setOnClickListener(view -> {
            String s  = et.getText().toString();
            Intent intent= new Intent(ACTION_BR);
            intent.putExtra(MESSAGE_KEY,s);
            sendBroadcast(intent);
        });
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_BR.equals(intent.getAction())){
                if(intent.hasExtra(MESSAGE_KEY)){
                    String s = intent.getStringExtra(MESSAGE_KEY);
                    textView2.setText("Message Delivered to other Apps: "+ s);
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver,new IntentFilter(ACTION_BR));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}