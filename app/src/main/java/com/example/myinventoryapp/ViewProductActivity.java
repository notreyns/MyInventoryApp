package com.example.myinventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
        textViewQuantity = findViewById(R.id.textViewQuantity);
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.one_item_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_delete_one_item:
                final Product product = (Product) getIntent().getSerializableExtra("product");
                new AlertDialog.Builder(this)
                        .setTitle("Delete entries")
                        .setMessage("Are you sure you want do delete item?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteProduct(product);
                            }
                        })
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadProduct(Product product) {
        textViewName.setText(product.getName());
        textViewPrice.setText(product.getPrice());
        textViewQuantity.setText(product.getQuantity());
        textViewSupplier.setText(product.getSupplier());
        productImg.setImageBitmap(BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length));
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
