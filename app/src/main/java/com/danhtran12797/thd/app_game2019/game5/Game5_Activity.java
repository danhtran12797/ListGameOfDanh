package com.danhtran12797.thd.app_game2019.game5;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Collections;

public class Game5_Activity extends BaseActivity implements View.OnClickListener {

    private static final String SHARED_PREFERENCES_NAME = "game5";
    TextView txtTime, txtDiem;
    Button btnNewGame;

    ArrayList<Button> arrButtonMain;
    ArrayList<Button> arrSelected;
    ArrayList<Button> arrUser;
    int dem = 1;
    int time = 61, max;

    LinearLayout layout;

    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer2500;
    Toolbar toolbar_game5;

    private ArrayList<Integer> arrIdColor;
    private int idColorSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game5_activity);

        arrIdColor = new ArrayList<>();
        arrIdColor.add(Color.WHITE);
        arrIdColor.add(Color.RED);
        arrIdColor.add(Color.GREEN);
        arrIdColor.add(Color.YELLOW);
        arrIdColor.add(Color.MAGENTA);

        toolbar_game5 = findViewById(R.id.toolbar_game5);
        setSupportActionBar(toolbar_game5);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor") && sharedPreferences.contains("core"))) {
            String infor = "Game nhớ màu\n\"Coi chừng mù màu\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.putInt("core", 0);
            editor.apply();
        }
        max = sharedPreferences.getInt("core", 0);

        layout = findViewById(R.id.layout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) layout.getLayoutParams();
        //param.width = width;
        param.height = width;
        layout.setLayoutParams(param);

        txtTime = findViewById(R.id.txtTest);
        txtDiem = findViewById(R.id.txtDiem);
        btnNewGame = findViewById(R.id.btnGO);

        arrUser = new ArrayList<>();

        arrButtonMain = new ArrayList<>();
        arrButtonMain.add((Button) findViewById(R.id.button1));
        arrButtonMain.add((Button) findViewById(R.id.button2));
        arrButtonMain.add((Button) findViewById(R.id.button3));
        arrButtonMain.add((Button) findViewById(R.id.button4));
        arrButtonMain.add((Button) findViewById(R.id.button5));
        arrButtonMain.add((Button) findViewById(R.id.button6));
        arrButtonMain.add((Button) findViewById(R.id.button7));
        arrButtonMain.add((Button) findViewById(R.id.button8));
        arrButtonMain.add((Button) findViewById(R.id.button9));
        arrButtonMain.add((Button) findViewById(R.id.button10));
        arrButtonMain.add((Button) findViewById(R.id.button11));
        arrButtonMain.add((Button) findViewById(R.id.button12));
        arrButtonMain.add((Button) findViewById(R.id.button13));
        arrButtonMain.add((Button) findViewById(R.id.button14));
        arrButtonMain.add((Button) findViewById(R.id.button15));
        arrButtonMain.add((Button) findViewById(R.id.button16));
        arrButtonMain.add((Button) findViewById(R.id.button17));
        arrButtonMain.add((Button) findViewById(R.id.button18));
        arrButtonMain.add((Button) findViewById(R.id.button19));
        arrButtonMain.add((Button) findViewById(R.id.button20));
        arrButtonMain.add((Button) findViewById(R.id.button21));
        arrButtonMain.add((Button) findViewById(R.id.button22));
        arrButtonMain.add((Button) findViewById(R.id.button23));
        arrButtonMain.add((Button) findViewById(R.id.button24));
        arrButtonMain.add((Button) findViewById(R.id.button25));

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnNewGame.setEnabled(false);
                txtTime.setText((--time) + "");
                if (dem == 25)
                    this.cancel();
            }

            @Override
            public void onFinish() {
                Close_Button();
                txtTime.setText("0");
                if (dem > max) {
                    max = dem - 1;
                    Toast.makeText(Game5_Activity.this, "Kỉ lục mới, chúc mừng bạn", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences1 = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putInt("core", max);
                    editor.apply();
                }
                btnNewGame.setEnabled(true);
                Toast.makeText(Game5_Activity.this, "Bạn không hoàn thành.\nĐiểm của bạn là: " + (dem - 1), Toast.LENGTH_SHORT).show();
            }
        };

        Close_Button();

        for (Button btn : arrButtonMain) {
            btn.setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game5_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_game5_reload:
                btnNewGame.setEnabled(true);
                Close_Button();
                countDownTimer.cancel();
                countDownTimer2500.cancel();
                for (Button b : arrButtonMain)
                    b.setBackgroundColor(Color.parseColor("#ff00ddff"));
                time = 60;
                dem = 1;
                txtTime.setText("60");
                txtDiem.setText("0");
                break;
            case R.id.menu_game5_my_home:
                finish();
                break;
            case R.id.menu_game5_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game5_max_core:
                Dialog_Infor(max + "");
                break;
            case R.id.menu_game5_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    private void Open_Button() {
        for (Button b : arrButtonMain)
            b.setEnabled(true);
    }

    private void Close_Button() {
        for (Button b : arrButtonMain)
            b.setEnabled(false);
    }

    public void bntNewGame(View view) {

        Collections.shuffle(arrIdColor);
        //idColorSelected=Color.RED;
        idColorSelected = arrIdColor.get(0);

        time = 60;
        dem = 1;
        txtTime.setText("60");
        txtDiem.setText("0");
        Open_Button();
        Random_button(dem);
        countDownTimer.start();
    }

    private void Random_button(final int so) {
        if (dem > 25) {
            Toast.makeText(this, "Chúc mừng bạn đã hoàn thành!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Collections.shuffle(arrButtonMain);
        arrSelected = new ArrayList<>();
        for (int i = 0; i < so; i++) {
            arrButtonMain.get(i).setBackgroundColor(idColorSelected);
            arrSelected.add(arrButtonMain.get(i));
        }
        for (Button b : arrButtonMain)
            b.setEnabled(false);
        countDownTimer2500 = new CountDownTimer(2500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Open_Button();

                for (int i = 0; i < so; i++)
                    arrButtonMain.get(i).setBackgroundColor(Color.parseColor("#ff00ddff"));
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        Button btnUser = (Button) v;
        btnUser.setBackgroundColor(idColorSelected);
        arrUser.add(btnUser);

        if (arrSelected.contains(btnUser)) {
            if (arrUser.size() == arrSelected.size()) {
                txtDiem.setText(dem + "");
                for (Button b : arrUser)
                    b.setBackgroundColor(Color.parseColor("#ff00ddff"));
                arrUser.clear();
                Random_button(++dem);
            }
        } else {
            Toast.makeText(this, "SAI", Toast.LENGTH_SHORT).show();
            for (Button b : arrUser)
                b.setBackgroundColor(Color.parseColor("#ff00ddff"));
            arrUser.clear();
            Random_button(dem);
        }
    }
}
