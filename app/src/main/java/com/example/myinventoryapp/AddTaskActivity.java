package com.example.myinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextName, editTextPrice, editTextQuantity, editTextSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_activity);

        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextSupplier= findViewById(R.id.editTextSupplier);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
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

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Task task = new Task();
                task.setName(sName);
                task.setPrice(sPrice);
                task.setQuantity(sQuantity);
                task.setSupplier(sSupplier);
                //task.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}
