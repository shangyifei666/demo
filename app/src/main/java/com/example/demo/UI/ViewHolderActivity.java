package com.example.demo.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.demo.R;
import com.example.demo.Recycler.Fruit;
import com.example.demo.Recycler.FruitAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewHolderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FruitAdapter adapter;
    private List<Fruit> fList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_holder);

        initData();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new FruitAdapter(ViewHolderActivity.this,fList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.offsetChildrenHorizontal(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void initData(){
        fList = new ArrayList<>();
        Fruit cmF = new Fruit("草莓", R.mipmap.cm);
        Fruit jzF = new Fruit("橘子",R.mipmap.jz);
        Fruit mgF = new Fruit("芒果",R.mipmap.mg);
        Fruit ytF = new Fruit("樱桃",R.mipmap.yt);
        for (int i=0; i<7; i++){
            fList.add(cmF);
            fList.add(jzF);
            fList.add(mgF);
            fList.add(ytF);
        }
    }
}
