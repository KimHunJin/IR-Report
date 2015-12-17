package com.sku.irassey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    Button btnSelectQuery;
    EditText edtQuery;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectQuery = (Button)findViewById(R.id.btnQuerySelect);
        edtQuery = (EditText)findViewById(R.id.edtQuery);
        txtResult = (TextView)findViewById(R.id.txtResqult);

        btnSelectQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordStorage wordStorage = new WordStorage();
                HashSet<String> hashSet = wordStorage.getHashSetString();
                HashMap<String, Integer> hashMap = wordStorage.getHashMap();
                String str = "";
                for(String string : hashSet) {
                     str += string + " " + hashMap.get(string);
                }
                txtResult.setText(str);
            }
        });

    }
}
