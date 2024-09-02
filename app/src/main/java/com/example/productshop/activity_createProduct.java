package com.example.productshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.productshop.adapters.ListAdapter;
import com.example.productshop.model.Removable;
import com.example.productshop.model.Updatable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class activity_createProduct extends AppCompatActivity implements Removable, Updatable {
    public static final int GALLERY_REQUEST = 333;
    List<Product> productList = new ArrayList<>();
    Bitmap bitmap = null;
    private Toolbar toolbarCreate;
    private ImageView createIV;
    private EditText prodNameET;
    private EditText prodPriceET;
    private EditText prodDescripET;
    private Button confirmBTN;
    private ListView shopCopyLV;
    private ListAdapter adapter = null;
    private Integer item = null;
    private Boolean chek = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_product);

        unitUI();
        createIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST);
        });

        confirmBTN.setOnClickListener(v -> {
        String productName = prodNameET.getText().toString();
        String productPrice = prodPriceET.getText().toString();
        String productDescription = prodDescripET.getText().toString();
        Bitmap productImage = bitmap;
        Product product = new Product(productName, productPrice, productDescription, productImage);
        productList.add(product);

            adapter = new ListAdapter(this, productList);
            shopCopyLV.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            prodNameET.getText().clear();
            prodPriceET.getText().clear();
            prodDescripET.getText().clear();
            createIV.setImageResource(R.drawable.sec_basket);
            bitmap = null;

        });

        shopCopyLV.setOnItemClickListener(((parent, view, position, id) -> {
            Product product = adapter.getItem(position);
            item = position;
            MyAlertDialog dialog = new MyAlertDialog();
            Bundle args = new Bundle();
            args.putSerializable("product", product);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getIntent().getBooleanExtra("newCheck", true)) {
            chek = getIntent().getExtras().getBoolean("newCheck");
            if (!chek) {
                productList = (List<Product>) getIntent().getSerializableExtra("list");
                if (productList != null) {
                    adapter = new ListAdapter(this, productList);
                    chek = true;
                    shopCopyLV.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        createIV = findViewById(R.id.createIV);
        Bitmap bitmap = null;
        if (requestCode == GALLERY_REQUEST && requestCode == RESULT_OK) {
            Uri uriImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            createIV.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exitMenuCreateProd) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    private void unitUI() {
        toolbarCreate = findViewById(R.id.toolbarCreate);
        createIV = findViewById(R.id.createIV);
        prodNameET = findViewById(R.id.prodNameET);
        prodPriceET = findViewById(R.id.prodPriceET);
        prodDescripET = findViewById(R.id.prodDescripET);
        confirmBTN = findViewById(R.id.confirmBTN);
        shopCopyLV = findViewById(R.id.shopCopyLV);

        setSupportActionBar(toolbarCreate);
    }

    @Override
    public void remove(Product product) {
        adapter.remove(product);
    }

    @Override
    public void updateInteface(Product product) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("product", product);
        intent.putExtra("products", (ArrayList<Product>) this.productList);
        intent.putExtra("position", item);
        intent.putExtra("check", chek);
        startActivity(intent);
    }
}