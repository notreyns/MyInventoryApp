package com.example.myinventoryapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddProduct;
    private RecyclerView recyclerView;
    private ImageView wallpaper;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        wallpaper= findViewById(R.id.no_items_wallpaper);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Main Page");



        buttonAddProduct = findViewById(R.id.floating_button_add);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        getProducts();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    private void getProducts() {
        class GetProducts extends AsyncTask<Void, Void, List<Product>> {

            @Override
            protected List<Product> doInBackground(Void... voids) {
                List<Product> productList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getAll();
                return productList;
            }

            @Override
            protected void onPostExecute(List<Product> products) {
                super.onPostExecute(products);
                ProductAdapter adapter = new ProductAdapter(MainActivity.this, products);
                recyclerView.setAdapter(adapter);
                if (adapter.getItemCount()<1){
                    wallpaper.setVisibility(View.VISIBLE);
                }else{
                    wallpaper.setVisibility(View.GONE);
                }
            }
        }

        GetProducts gp = new GetProducts();
        gp.execute();
    }
}
