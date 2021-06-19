package com.example.myinventoryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText editTextName, editTextPrice, editTextQuantity, editTextSupplier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_item_activity);


        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextSupplier= findViewById(R.id.editTextSupplier);


        final Product product = (Product) getIntent().getSerializableExtra("product");

        loadProduct(product);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateProduct(product);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProductActivity.this);
                builder.setTitle("Are you sure?");
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
        });
    }

    private void loadProduct(Product product) {
        editTextName.setText(product.getName());
        editTextPrice.setText(product.getPrice());
        editTextQuantity.setText(product.getQuantity());
        editTextSupplier.setText(product.getSupplier());
    }

    private void updateProduct(final Product product) {
        final String sName = editTextName.getText().toString().trim();
        final String sPrice = editTextPrice.getText().toString().trim();
        final String sQuantity = editTextQuantity.getText().toString().trim();
        final String sSupplier = editTextSupplier.getText().toString().trim();

        if (sName.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (sPrice.isEmpty()) {
            editTextPrice.setError("Price required");
            editTextPrice.requestFocus();
            return;
        }

        if (sQuantity.isEmpty()) {
            editTextQuantity.setError("Quantity required");
            editTextQuantity.requestFocus();
            return;
        }
        if (sSupplier.isEmpty()) {
            editTextSupplier.setError("Supplier required");
            editTextSupplier.requestFocus();
            return;
        }

        class UpdateProduct extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                product.setName(sName);
                product.setPrice(sPrice);
                product.setQuantity(sQuantity);
                product.setSupplier(sSupplier);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .productDao()
                        .update(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateProductActivity.this, MainActivity.class));
            }
        }

        UpdateProduct up = new UpdateProduct();
        up.execute();
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
                startActivity(new Intent(UpdateProductActivity.this, MainActivity.class));
            }
        }

        DeleteProduct dp = new DeleteProduct();
        dp.execute();

    }
}

