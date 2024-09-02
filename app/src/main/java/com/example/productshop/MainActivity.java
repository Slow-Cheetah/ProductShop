package com.example.productshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbarMain;
    private Button addBTN;
//    private ListView shopLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbarMain);
        addBTN = findViewById(R.id.addBTN);
//        shopLV = findViewById(R.id.shopLV);

        setSupportActionBar(toolbarMain);

        addBTN.setOnClickListener(v -> {
            Intent intent = new Intent(this, activity_createProduct.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exitMenuMain) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
}