package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl;
    private Button btnAdd;
    private ListView listView;
    private ArrayList<String> urlList;
    private ArrayAdapter<String> adapter;
    private static final String PREF_NAME = "UrlPrefs";
    private static final String KEY_URL_LIST = "url_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = findViewById(R.id.etUrl);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);

        urlList = loadUrlList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, urlList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String url = etUrl.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(this, "لطفاً آدرس را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            if (urlList.contains(url)) {
                Toast.makeText(this, "این آدرس قبلاً ثبت شده", Toast.LENGTH_SHORT).show();
            } else {
                urlList.add(url);
                adapter.notifyDataSetChanged();
                saveUrlList();
                etUrl.setText("");
                Toast.makeText(this, "آدرس اضافه شد", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String currentUrl = urlList.get(position);
            showEditDialog(position, currentUrl);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("حذف")
                    .setMessage("آیا می‌خواهید این آدرس را حذف کنید؟")
                    .setPositiveButton("بله", (dialog, which) -> {
                        urlList.remove(position);
                        adapter.notifyDataSetChanged();
                        saveUrlList();
                        Toast.makeText(this, "آدرس حذف شد", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("خیر", null)
                    .show();
            return true;
        });
    }

    private void showEditDialog(int position, String oldUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ویرایش آدرس");

        final EditText input = new EditText(this);
        input.setText(oldUrl.replace("https://", "").replace("http://", ""));
        builder.setView(input);

        builder.setPositiveButton("ذخیره", (dialog, which) -> {
            String newUrlText = input.getText().toString().trim();
            if (newUrlText.isEmpty()) {
                Toast.makeText(this, "آدرس نمی‌تواند خالی باشد", Toast.LENGTH_SHORT).show();
                return;
            }

            String newUrl = newUrlText;
            if (!newUrl.startsWith("http://") && !newUrl.startsWith("https://")) {
                newUrl = "https://" + newUrl;
            }

            urlList.set(position, newUrl);
            adapter.notifyDataSetChanged();
            saveUrlList();
            Toast.makeText(this, "آدرس ویرایش شد", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("لغو", null);
        builder.show();
    }

    private void saveUrlList() {
        getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .edit()
                .putString(KEY_URL_LIST, new Gson().toJson(urlList))
                .apply();
    }

    private ArrayList<String> loadUrlList() {
        String json = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                .getString(KEY_URL_LIST, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}