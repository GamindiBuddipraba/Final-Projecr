package com.codebysl.greenlanka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    RadioButton user, collector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnGetStart);
        user = (RadioButton)findViewById(R.id.radioUser);
        collector = (RadioButton)findViewById(R.id.radioCollector);

        btnStart.setOnClickListener(v -> {
            if(user.isChecked()){
                Intent i = new Intent(this, User_Login.class);
                startActivity(i);
            }
            else if(collector.isChecked()){
                Intent i = new Intent(this, Collector_Login.class);
                startActivity(i);
            }
        });
    }
}