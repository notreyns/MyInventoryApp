package com.example.myinventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.function.Consumer;

public class ViewProductActivity extends AppCompatActivity {

    private TextView textViewName, textViewPrice, textViewQuantity, textViewSupplier;
    private ImageView productImg;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_activity);


        textViewName = findViewById(R.id.textViewName);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewQuantity = findViewById(R.id.textViewPrice);
        textViewSupplier= findViewById(R.id.textViewSupplier);
        productImg= findViewById(R.id.oneProductImageView);

        final Product product = (Product) getIntent().getSerializableExtra("product");

        loadProduct(product);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProductActivity.this, UpdateProductActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.one_item_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.menu_delete_all)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewProductActivity.this);
                    builder.setTitle("Are you sure you want do delete this item?\nThis action can\'t be undone");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteProduct(product);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one_item_menu, menu);
        return true;
    }

    private void loadProduct(Product product) {
        textViewName.setText(product.getName());
        textViewPrice.setText(product.getPrice());
        textViewQuantity.setText(product.getQuantity());
        textViewSupplier.setText(product.getSupplier());
        productImg.setImageBitmap(product.getImage());
    }

    private void deleteProduct(final Product product) {
        class DeleteProduct extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .delete(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(ViewProductActivity.this, MainActivity.class));
            }
        }
        DeleteProduct dp = new DeleteProduct();
        dp.execute();

    }
}
