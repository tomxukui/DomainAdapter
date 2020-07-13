package com.xukui.domainadapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xukui.library.domainadapter.ui.DomainsActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setView();
    }

    private void initView() {
        btn_domain = findViewById(R.id.btn_domain);
    }

    private void setView() {
        btn_domain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DomainsActivity.class);
                startActivity(intent);
            }

        });
    }

}