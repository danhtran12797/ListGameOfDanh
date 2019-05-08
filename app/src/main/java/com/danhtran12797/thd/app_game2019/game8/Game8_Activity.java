package com.danhtran12797.thd.app_game2019.game8;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game8_Activity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "game8";
    Spinner spinner;
    ArrayList<String> arrSelected;
    ArrayAdapter<String> adapter;

    ProgressBar progressBar;

    LinearLayout layoutMain;
    ArrayList<Integer> arrNum;
    ArrayList<Integer> arrNum1;

    Button btnNext;
    TextView txtFind, txtScore;
    int t = 101, score = 0, max1, progressMax;
    boolean check = true;
    CountDownTimer countDownTimer;
    Toolbar toolbar_game8;

    Animation scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game8_activity);

        scaleDown = AnimationUtils.loadAnimation(this, R.anim.anim_scale_game8);


        toolbar_game8 = findViewById(R.id.toolbar_game8);
        setSupportActionBar(toolbar_game8);

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };

        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor") && sharedPreferences.contains("core"))) {
            String infor = "Game tìm số\n\"Tìm ra chưa\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.putInt("core", 0);
            editor.apply();
        }
        max1 = sharedPreferences.getInt("core", 0);

        spinner = findViewById(R.id.spinner1);
        arrSelected = new ArrayList<>();
        arrSelected.addAll(Arrays.asList(new String[]{"4", "5", "6", "7", "8", "9", "10"}));
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrSelected);
        spinner.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);
        txtScore = findViewById(R.id.txtScore);

        txtFind = findViewById(R.id.txtFind);
        btnNext = findViewById(R.id.btnNext);

        layoutMain = findViewById(R.id.layoutMain);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) layoutMain.getLayoutParams();
        param.width = width;
        param.height = width;
        layoutMain.setLayoutParams(param);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTypeface(((TextView) parent.getChildAt(0)).getTypeface(), Typeface.BOLD);
                ((TextView) parent.getChildAt(0)).setTextSize(18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressMax = Integer.parseInt(spinner.getSelectedItem().toString());
                VeLayout(progressMax);

                open_view();

                score = 0;
                txtScore.setText("0");

                Collections.shuffle(arrNum1);
                t = arrNum1.get(0);
                txtFind.setText(t + "");

                int max;
                if (progressMax == 4)
                    max = 30;
                else if (progressMax == 5)
                    max = 50;
                else if (progressMax == 6)
                    max = 60;
                else if (progressMax == 7)
                    max = 90;
                else if (progressMax == 8)
                    max = 120;
                else if (progressMax == 9)
                    max = 140;
                else
                    max = 160;

                progressBar.setMax(max);
                progressBar.setProgress(0);

                countDownTimer = new CountDownTimer(max * 1000 + 1000, 1000) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

                    @Override
                    public void onTick(long millisUntilFinished) {
                        int current = progressBar.getProgress();
                        progressBar.setProgress(current + 1);
                        if (check == false) {
                            progressBar.setProgress(0);
                            this.cancel();
                            check = true;
                        }
                        if (arrNum1.size() == 0) {
                            if (score > max1) {
                                max1 = score;
                                Toast.makeText(Game8_Activity.this, "Kỉ lục mới, chúc mừng bạn.", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("core", score);
                                editor.apply();
                            }
                            this.cancel();
                            Toast.makeText(Game8_Activity.this, "Bạn đã hoàn thành cấp độ " + (spinner.getSelectedItemPosition() + 1)
                                    + "\nThời gian " + progressBar.getProgress() + "s", Toast.LENGTH_SHORT).show();

                            close_view();
                        }
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(Game8_Activity.this, "Hết giờ!\nBạn không hoàn thành cấp độ " + (spinner.getSelectedItemPosition() + 1) + ".", Toast.LENGTH_SHORT).show();

                        spinner.setEnabled(true);
                        btnNext.setVisibility(View.VISIBLE);

                        if (score > max1) {
                            max1 = score;
                            Toast.makeText(Game8_Activity.this, "Kỉ lục mới, chúc mừng bạn.", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("core", score);
                            editor.apply();
                        }
                    }
                };
                countDownTimer.start();
            }
        });
    }

    public void open_view() {
        spinner.setEnabled(false);
        btnNext.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        layoutMain.setVisibility(View.VISIBLE);
        txtFind.setVisibility(View.VISIBLE);
    }

    public void close_view() {
        spinner.setEnabled(true);
        btnNext.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        layoutMain.setVisibility(View.GONE);
        txtFind.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game8_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_game8_reload:
                if (progressBar.getProgress() < progressBar.getMax() && arrNum1.size() != 0 && progressBar.getProgress() >= 1) {
                    check = false;
                }

                //VeLayout(Integer.parseInt(spinner.getSelectedItem().toString()));
                close_view();
                break;
            case R.id.menu_game8_my_home:
                finish();
                break;
            case R.id.menu_game8_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game8_max_core:
                Dialog_Infor(max1 + "");
                break;
            case R.id.menu_game8_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addArrayList(int index) {
        arrNum = new ArrayList<>();
        arrNum1 = new ArrayList<>();

        for (int i = 0; i < index * index; i++) {
            arrNum.add(i);
            arrNum1.add(i);
        }
    }

    public void VeLayout(int index) {

        addArrayList(index);

        layoutMain.removeAllViews();
        Collections.shuffle(arrNum);

        for (int i = 1; i <= index; i++) {
            LinearLayout layout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            layout.setLayoutParams(layoutParams);
            layoutMain.addView(layout);
            for (int j = 1; j <= index; j++) {
                int vitri = index * (i - 1) + j - 1;

                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                textViewParams.setMargins(1, 1, 1, 1);
                TextView txtDanh = new TextView(this);
                txtDanh.setText(arrNum.get(vitri) + "");
                txtDanh.setGravity(Gravity.CENTER);
                txtDanh.setTextColor(Color.WHITE);
                txtDanh.setBackgroundResource(R.drawable.game8_shape);
                txtDanh.setLayoutParams(textViewParams);
                layout.addView(txtDanh);
                txtDanh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView txtTemp = (TextView) v;
                        if (Integer.parseInt(txtTemp.getText().toString()) == t) {
                            arrNum1.remove(0);

                            txtTemp.animate().scaleX(0f).scaleY(0f);

                            score += 1;
                            txtScore.setText(score + "");
                            if (arrNum1.size() > 0) {
                                Collections.shuffle(arrNum1);
                                t = arrNum1.get(0);
                                txtFind.setText(t + "");
                            }
                        }
                    }
                });
            }
        }
    }
}
