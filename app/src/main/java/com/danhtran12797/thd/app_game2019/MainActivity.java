package com.danhtran12797.thd.app_game2019;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.game1.Game1_Activity;
import com.danhtran12797.thd.app_game2019.game10.Game10_Activity;
import com.danhtran12797.thd.app_game2019.game11.Game11_Activity;
import com.danhtran12797.thd.app_game2019.game2.Game2_Activity;
import com.danhtran12797.thd.app_game2019.game3.Game3_Activity;
import com.danhtran12797.thd.app_game2019.game4.Game4_Activity;
import com.danhtran12797.thd.app_game2019.game5.Game5_Activity;
import com.danhtran12797.thd.app_game2019.game6.Game6_Activity;
import com.danhtran12797.thd.app_game2019.game7.Game7_Activity;
import com.danhtran12797.thd.app_game2019.game8.Game8_Activity;
import com.danhtran12797.thd.app_game2019.game9.Game9_Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "main";
    Intent intent;
    Toolbar toolbar_main;
    RecyclerView recyclerView;
    ArrayList<Game> arrGame;
    AdapterGame adapterGame;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar_main = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);

        String arrNameGame[] = getResources().getStringArray(R.array.name_games);

        List<Integer> idHinh = new ArrayList<>();
        idHinh.add(R.drawable.icon_game1);
        idHinh.add(R.drawable.icon_game2);
        idHinh.add(R.drawable.icon_game3);
        idHinh.add(R.drawable.icon_game4);
        idHinh.add(R.drawable.icon_game5);
        idHinh.add(R.drawable.icon_game6);
        idHinh.add(R.drawable.icon_chim7);
        idHinh.add(R.drawable.icon_chim8);
        idHinh.add(R.drawable.icon_game9);
        idHinh.add(R.drawable.icon_game10);
        idHinh.add(R.drawable.icon_game11);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrGame = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Game game = new Game(idHinh.get(i), arrNameGame[i]);
            arrGame.add(game);
        }
        adapterGame = new AdapterGame(arrGame, this);
        recyclerView.setAdapter(adapterGame);

        adapterGame.setOnItemClickListener(new AdapterGame.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 9:
                        intent = new Intent(MainActivity.this, Game10_Activity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(MainActivity.this, Game9_Activity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this, Game8_Activity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, Game7_Activity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, Game6_Activity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, Game5_Activity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, Game4_Activity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, Game3_Activity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, Game2_Activity.class);
                        startActivity(intent);
                        break;
                    case 0:
                        intent = new Intent(MainActivity.this, Game1_Activity.class);
                        startActivity(intent);
                        break;
                    case 10:
                        intent = new Intent(MainActivity.this, Game11_Activity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    public void Dialog_Infor1(String s, String title, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setIcon(icon);
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_infor_game:
                String infor_game = "Tên: THD Games\nLĩnh vực:\n-IQ\n-Cờ bạc\n-Nhanh tay\n-May rủi\n-Tính toán";
                Dialog_Infor1(infor_game,"Thông tin ứng dụng",R.mipmap.ic_launcher_round);
                break;
            case R.id.menu_main_infor:
                String infor = "Name: Trần Hùng Danh\nEmail: danhtran12797@gmail.com";
                Dialog_Infor1(infor,"Developer",R.drawable.ic_person);
                break;
            case R.id.menu_main_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Nhấn lại lần nữa để thoát", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}
