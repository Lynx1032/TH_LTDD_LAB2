/*
*** Lab 2 - Nhóm 2A ***
* Hồ Hoàng Nghiệo - 20200277
* Nguyễn Tiến Trung - 20200382
*
* App demo listview với dữ liệu là các môn thể thao
*
* 2 Ngôn ngữ: Anh, Việt
* Xem danh sách, click vào xem chi tiết, xóa đối tượng: Hoạt động tốt
* Chỉnh sửa đối tượng: mở được dialouge để chỉnh sửa, nhưng bấm lưu lại sẽ bị crash
 */

package com.example.listview_lab2;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Integer[] nameIDArray;
    private List<Integer> sportNames;
    private List<Integer> imageIDs;
    private ListView listView;
    private CustomSportListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_list_main_activity);

        nameIDArray = new Integer[] {
                R.string.american_football_listName,
                R.string.badminton_listName,
                R.string.baseball_listName,
                R.string.basketball_listName,
                R.string.bowling_listName,
                R.string.football_listName,
                R.string.golf_listName,
                R.string.ping_pong_listName,
                R.string.tennis_listName
        };

        sportNames = new ArrayList<>(Arrays.asList(nameIDArray));

        imageIDs = new ArrayList<>(Arrays.asList(
                R.drawable.american_football_icon,
                R.drawable.badminton_icon,
                R.drawable.baseball_icon,
                R.drawable.basketball_icon,
                R.drawable.bowling_icon,
                R.drawable.football_icon,
                R.drawable.golf_icon,
                R.drawable.ping_pong_icon,
                R.drawable.tennis_icon
        ));

        adapter = new CustomSportListAdapter(this, sportNames, imageIDs);
        listView = findViewById(R.id.sportListID);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Handle item click as before
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Resources res = getResources();
            String message = res.getString(sportNames.get(position));
            intent.putExtra("sport", message);
            startActivity(intent);
        });
    }
}
