package com.danhtran12797.thd.app_game2019.game2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game2_Activity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "game2";
    public static ArrayList<String> arrayName;
    ImageView imgGoc, imgNhan;
    TextView txtTotal;
    int REQUEST_CODE_IMAGE = 123;
    String tenHinhGoc;
    int total = 100;

    Toolbar toolbar_game2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2_activity);

        toolbar_game2 = findViewById(R.id.toolbar_game2);
        setSupportActionBar(toolbar_game2);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains("infor")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String infor = "Game chọn hình\n\"Không thích không chọn\"";
            editor.putString("infor", infor);
            editor.apply();
        }

        imgGoc = findViewById(R.id.imgGoc);
        imgNhan = findViewById(R.id.imgNhan);
        txtTotal = findViewById(R.id.txtTotal);

        String[] mangTen = getResources().getStringArray(R.array.list);
        arrayName = new ArrayList<>(Arrays.asList(mangTen));

        //trộn mảng
        Collections.shuffle(arrayName);

        tenHinhGoc = arrayName.get(5);
        int idHinh = getResources().getIdentifier(arrayName.get(5), "drawable", getPackageName());

        imgGoc.setImageResource(idHinh);

        imgNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Game2_Activity.this, Game2_Activity1.class), REQUEST_CODE_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            String tenHinhNhan = data.getStringExtra("tenhinhchon");
            int idHinhNhan = getResources().getIdentifier(tenHinhNhan, "drawable", getPackageName());
            imgNhan.setImageResource(idHinhNhan);

            //so sánh theo tên hình
            if (tenHinhGoc.equals(tenHinhNhan)) {
                Toast.makeText(this, "Chính xác\nBạn được cộng 10 điểm", Toast.LENGTH_SHORT).show();
                total += 10;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Collections.shuffle(arrayName);
                        tenHinhGoc = arrayName.get(5);
                        int idHinh = getResources().getIdentifier(arrayName.get(5), "drawable", getPackageName());
                        imgGoc.setImageResource(idHinh);
                    }
                }, 1500);
            } else {
                total -= 10;
                Toast.makeText(this, "Sai rồi!\nBạn bị trừ 10 điểm", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Collections.shuffle(arrayName);
                        tenHinhGoc = arrayName.get(5);
                        int idHinh = getResources().getIdentifier(arrayName.get(5), "drawable", getPackageName());
                        imgGoc.setImageResource(idHinh);
                    }
                }, 1500);
            }
        } else {
            Toast.makeText(this, "Bạn chưa chọn hình :)\nBạn bị trừ 15 điểm", Toast.LENGTH_SHORT).show();
            total -= 15;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Collections.shuffle(arrayName);
                    tenHinhGoc = arrayName.get(5);
                    int idHinh = getResources().getIdentifier(arrayName.get(5), "drawable", getPackageName());
                    imgGoc.setImageResource(idHinh);
                }
            }, 1500);
        }
        txtTotal.setText(total + "");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                imgNhan.setImageResource(R.drawable.quesion);
            }
        }, 1500);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game2_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_game2_reload:
                Collections.shuffle(arrayName);

                tenHinhGoc = arrayName.get(5);
                int idHinh = getResources().getIdentifier(arrayName.get(5), "drawable", getPackageName());
                imgGoc.setImageResource(idHinh);
                break;
            case R.id.menu_game2_my_home:
                finish();
                break;
            case R.id.menu_game2_infor:
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game2_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
