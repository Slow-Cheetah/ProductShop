package com.example.productshop;

import static com.example.productshop.activity_createProduct.GALLERY_REQUEST;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    Bitmap bitmap = null;
    private Toolbar toolbarEdit;
    private ImageView editIV;
    private EditText editNameET;
    private EditText editPriceET;
    private EditText editDescripET;
    private Button editConfirmBTN;
    Boolean check = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        toolbarEdit = findViewById(R.id.toolbarEdit);
        editIV = findViewById(R.id.editIV);
        editNameET = findViewById(R.id.editNameET);
        editPriceET = findViewById(R.id.editPriceET);
        editDescripET =findViewById(R.id.editDescripET);
        editConfirmBTN= findViewById(R.id.editConfirmBTN);
        setSupportActionBar(toolbarEdit);

        Product product = (Product) getIntent().getSerializableExtra("product");
        List<Product> products = (List<Product>) getIntent().getSerializableExtra("products");
        Integer item = getIntent().getIntExtra("position", 0);
        check = getIntent().getBooleanExtra("check", false);

        String name = product.getName();
        String price = product.getPrice();
        String description = product.getDescription();
        Bitmap bitmap = product.getImage();

        editNameET.setText(name);
        editPriceET.setText(price);
        editDescripET.setText(description);
        editIV.setImageBitmap(bitmap);

        editIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST);
        });

        editConfirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Product newProduct = new Product(
                       editNameET.getText().toString(),
                       editPriceET.getText().toString(),
                       editDescripET.getText().toString(),
                       product.getImage()
               );
               List<Product> list = products;
               if (item != null) {
                   swap(item, newProduct, products);
               }

               check = false;
               Intent comeBack = new Intent(EditActivity.this, activity_createProduct.class);
               comeBack.putExtra("list", new ArrayList<Product>(list));
               comeBack.putExtra("newCheck", check);
               startActivity(comeBack);
               finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       editIV = findViewById(R.id.editIV);
        if (requestCode == GALLERY_REQUEST && requestCode == RESULT_OK) {
            Uri uriImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            editIV.setImageBitmap(bitmap);
        }
    }

    private void swap (int item, Product product, List<Product> products) {
        products.add(item+1, product);
        products.remove(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exitMenuEditActivity) {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
}