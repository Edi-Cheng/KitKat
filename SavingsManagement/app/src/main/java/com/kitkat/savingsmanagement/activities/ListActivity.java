package com.kitkat.savingsmanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kitkat.savingsmanagement.R;
import com.kitkat.savingsmanagement.services.DataFetchingService;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends android.app.ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = new Intent(this, DataFetchingService.class);
        startService(intent);

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        HashMap<String, String> map2 = new HashMap<String, String>();
        HashMap<String, String> map3 = new HashMap<String, String>();

        map1.put("user_name", "steven zhang");
        map1.put("user_age", "30");

        map2.put("user_name", "ethen xu");
        map2.put("user_age", "34");

        map3.put("user_name", "apple shen");
        map3.put("user_age", "26");
        list.add(map1);
        list.add(map2);
        list.add(map3);

        SimpleAdapter listAdapter = new SimpleAdapter(this, list, R.layout.user,
                new String[]{"user_name", "user_age"},
                new int[]{R.id.user_name, R.id.user_age});

        setListAdapter(listAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        System.out.println("id --------------" + id);
        System.out.println("position --------------" + position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(this, DataFetchingService.class);
        stopService(intent);
    }
}
