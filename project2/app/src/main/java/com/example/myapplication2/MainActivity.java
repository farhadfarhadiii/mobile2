package com.example.myapplication2;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout rootLayout = findViewById(R.id.rootLayout);
        Button btnChangeColor = findViewById(R.id.btnChangeColor);
        boolean[] isBlue = {true};

        rootLayout.setBackgroundColor(Color.parseColor("#4FC3F7"));

        btnChangeColor.setOnClickListener(v -> {
            if (isBlue[0]) {
                rootLayout.setBackgroundColor(Color.parseColor("#FFF9C4"));
            } else {
                rootLayout.setBackgroundColor(Color.parseColor("#4FC3F7"));
            }
            isBlue[0] = !isBlue[0];
        });
    }
}