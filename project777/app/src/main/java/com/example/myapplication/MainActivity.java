package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtUserName = findViewById(R.id.edtUserName);
        EditText edtNote = findViewById(R.id.edtNote);
        Button btnSave = findViewById(R.id.btnSave);

        SharedPreferences prefs = getSharedPreferences("user_data", MODE_PRIVATE);

        String savedName = prefs.getString("username", "");
        if (!savedName.isEmpty()) {
            edtUserName.setText(savedName);
            Toast.makeText(this, "خوش آمدی " + savedName, Toast.LENGTH_SHORT).show();
        }

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent battery = registerReceiver(null, filter);
        int level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        Toast.makeText(this, "باتری: " + level + "%", Toast.LENGTH_LONG).show();

        String url = "https://jsonplaceholder.typicode.com/posts/1";
        StringRequest req = new StringRequest(Request.Method.GET, url,
                response -> Toast.makeText(this, "API OK", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "API Error", Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(this).add(req);

        btnSave.setOnClickListener(v -> {
            String userName = edtUserName.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

            if (!userName.isEmpty()) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", userName);
                editor.apply();
                Toast.makeText(this, "نام ذخیره شد", Toast.LENGTH_SHORT).show();
            }

            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            android.content.ContentValues cv = new android.content.ContentValues();
            cv.put("text", note);
            db.insert("notes", null, cv);
            db.close();
            Toast.makeText(this, "یادداشت ذخیره شد", Toast.LENGTH_SHORT).show();
        });
    }
}
