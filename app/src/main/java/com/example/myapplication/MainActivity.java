package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button add,delete,update,view;
    EditText id,name,price,qty;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        update=(Button)findViewById(R.id.update);
        view=(Button)findViewById(R.id.view);

        id=(EditText)findViewById(R.id.id);
        name=(EditText)findViewById(R.id.name);
        price=(EditText)findViewById(R.id.price);
        qty=(EditText)findViewById(R.id.quantity);

        db = new DatabaseHelper(this,"",null,0);

        add_product();
        update_product();
        view_product();
        delete_product();


    }

    private void delete_product() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rows = db.deleteProduct(id.getText().toString());
                if (rows>0)
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void view_product() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.viewALlProduct();
                if(res.getCount() == 0){
                    showMessage("error","No product found");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (res.moveToNext()){
                    stringBuffer.append("id," +res.getString(0) + "\n");
                    stringBuffer.append("name,"+res.getString(1) + "\n");
                    stringBuffer.append("price,"+res.getString(2) + "\n");
                    stringBuffer.append("quantity,"+res.getString(3) + "\n");
                }
                showMessage("data",stringBuffer.toString());

            }
        });

    }

    private void showMessage(String error, String no_product_found) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(error);
        builder.setMessage(no_product_found);
        builder.show();

    }

    private void update_product() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = db.updateItems(id.getText().toString(),name.getText().toString(),price.getText().toString(),qty.getText().toString());
                if (isUpdated){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void add_product(){

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = db.addItems(name.getText().toString(),price.getText().toString(),qty.getText().toString());
                if (isInserted){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }
}
