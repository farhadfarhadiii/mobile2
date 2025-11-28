package com.example.myapplication;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private ArrayList<DataModel> dataList = new ArrayList<>();
    private boolean isGrid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.btnSwitchLayout).setOnClickListener(v -> switchLayout());

        String[] titles = {
                "آیتم ۱", "آیتم ۲", "آیتم ۳", "آیتم ۴", "آیتم ۵",
                "آیتم ۶", "آیتم ۷", "آیتم ۸", "آیتم ۹", "آیتم ۱۰"
        };

        for (int i = 0; i < 10; i++) {
            dataList.add(new DataModel(titles[i], "توضیحات " + titles[i]));
        }

        adapter = new MyAdapter(dataList);
        recyclerView.setAdapter(adapter);

        setLinearLayout();
    }

    private void switchLayout() {
        isGrid = !isGrid;
        if (isGrid) {
            setGridLayout();
        } else {
            setLinearLayout();
        }
    }

    private void setGridLayout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ((Button) findViewById(R.id.btnSwitchLayout)).setText("سوئیچ به Linear Layout");
    }

    private void setLinearLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ((Button) findViewById(R.id.btnSwitchLayout)).setText("سوئیچ به Grid Layout");
    }
}
