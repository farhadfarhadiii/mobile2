package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editUrl;
    private Button btnAdd;
    private ListView listView;
    private ArrayList<String> urlList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUrl = findViewById(R.id.editUrl);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);

        urlList = new ArrayList<>();
        //noinspection rawtypes
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, urlList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editUrl.getText().toString().trim();

                if (url.isEmpty()) {
                    Toast.makeText(MainActivity.this, "آدرس را وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                urlList.add(url);
                adapter.notifyDataSetChanged();
                editUrl.setText("");
                Toast.makeText( MainActivity.this, "اضافه شد", Toast.LENGTH_SHORT).show();
            }
        });
    }
}