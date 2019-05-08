package com.danhtran12797.thd.app_game2019.game1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.Random;

public class Game1_Activity1 extends BaseActivity {

    RelativeLayout relativeLayout;
    ImageButton btnPlay;
    SeekBar seerBar1, seerBar2, seerBar3, seerBar4;
    CheckBox checkBox1, checkBox3, checkBox2;
    int a = 100;
    TextView txtDiem;
    CountDownTimer countDownTimer;
    CountDownTimer countDownTimer1;
    Toolbar toolbar_game11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game1_activity1);

        toolbar_game11 = findViewById(R.id.toolbar_game11);
        setSupportActionBar(toolbar_game11);
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game1_menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1_game1_home:
                finish();
                break;
            case R.id.menu1_game1_my_home:
                onBackPressed();
                break;
            case R.id.menu1_game1_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer1.cancel();
    }

    private void EnableCheckBox() {
        checkBox1.setEnabled(true);
        checkBox3.setEnabled(true);
        checkBox2.setEnabled(true);
    }

    private void DisableCheckBox() {
        checkBox1.setEnabled(false);
        checkBox3.setEnabled(false);
        checkBox2.setEnabled(false);
    }

    private void SetCheck() {
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
    }

    private void addEvents() {
        countDownTimer1 = new CountDownTimer(60000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                seerBar4.setProgress(seerBar4.getProgress() + 1);
                if (seerBar4.getProgress() >= seerBar4.getMax())
                    seerBar4.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer = new CountDownTimer(60000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                Random rd = new Random();
                seerBar1.setProgress(seerBar1.getProgress() + rd.nextInt(5));

                seerBar2.setProgress(seerBar2.getProgress() + rd.nextInt(5));

                seerBar3.setProgress(seerBar3.getProgress() + rd.nextInt(5));

                if (seerBar1.getProgress() >= seerBar1.getMax()) {
                    this.cancel();
                    btnPlay.setVisibility(View.VISIBLE);
                    this.cancel();

                    Toast.makeText(Game1_Activity1.this, "THREE WIN", Toast.LENGTH_SHORT).show();
                    if (checkBox1.isChecked()) {
                        Toast.makeText(Game1_Activity1.this, "Bạn đoán đúng, được cộng 10 điểm.", Toast.LENGTH_SHORT).show();
                        a += 10;
                    } else {
                        a -= 5;
                        Toast.makeText(Game1_Activity1.this, "Bạn đoán sai! Bị trừ 5 điểm :(", Toast.LENGTH_SHORT).show();
                    }
                    txtDiem.setText(a + "");
                    SetCheck();
                    EnableCheckBox();
                }
                if (seerBar2.getProgress() >= seerBar2.getMax()) {
                    this.cancel();
                    btnPlay.setVisibility(View.VISIBLE);
                    this.cancel();

                    Toast.makeText(Game1_Activity1.this, "TW0 WIN", Toast.LENGTH_SHORT).show();
                    if (checkBox1.isChecked()) {
                        Toast.makeText(Game1_Activity1.this, "Bạn đoán đúng, được cộng 10 điểm.", Toast.LENGTH_SHORT).show();
                        a += 10;
                    } else {
                        a -= 5;
                        Toast.makeText(Game1_Activity1.this, "Bạn đoán sai! Bị trừ 5 điểm :(", Toast.LENGTH_SHORT).show();
                    }
                    txtDiem.setText(a + "");
                    SetCheck();
                    EnableCheckBox();
                }
                if (seerBar3.getProgress() >= seerBar3.getMax()) {
                    btnPlay.setVisibility(View.VISIBLE);
                    this.cancel();

                    Toast.makeText(Game1_Activity1.this, "ONE WIN", Toast.LENGTH_SHORT).show();
                    if (checkBox1.isChecked()) {
                        Toast.makeText(Game1_Activity1.this, "Bạn đoán đúng, được cộng 10 điểm.", Toast.LENGTH_SHORT).show();
                        a += 10;
                    } else {
                        a -= 5;
                        Toast.makeText(Game1_Activity1.this, "Bạn đoán sai! Bị trừ 5 điểm :(", Toast.LENGTH_SHORT).show();
                    }
                    txtDiem.setText(a + "");
                    SetCheck();
                    EnableCheckBox();
                }
            }

            @Override
            public void onFinish() {
            }
        };
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seerBar1.setEnabled(false);
                seerBar2.setEnabled(false);
                seerBar3.setEnabled(false);
                seerBar4.setEnabled(false);
                seerBar4.setVisibility(View.VISIBLE);

                if (checkBox1.isChecked() || checkBox3.isChecked() || checkBox2.isChecked()) {
                    seerBar1.setProgress(0);
                    seerBar2.setProgress(0);
                    seerBar3.setProgress(0);
                    seerBar4.setProgress(0);

                    btnPlay.setVisibility(View.INVISIBLE);
                    countDownTimer.start();
                    countDownTimer1.start();
                    DisableCheckBox();

                } else {
                    Toast.makeText(Game1_Activity1.this, "Vui lòng đặt cược trước khi chơi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox2.setChecked(false);
                    checkBox1.setChecked(false);
                }
            }
        });

    }

    private void addControls() {
        btnPlay = findViewById(R.id.btnPlay);
        seerBar1 = findViewById(R.id.seerBar1);
        seerBar2 = findViewById(R.id.seerBar2);
        seerBar3 = findViewById(R.id.seerBar3);
        seerBar4 = findViewById(R.id.seekBar4);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        txtDiem = findViewById(R.id.txtDiem);
        relativeLayout = findViewById(R.id.layout);

        txtDiem.setTextColor(Color.parseColor("#e60000"));
        Intent intent = getIntent();
        int x = intent.getIntExtra("CHOOSE_MAP", 0);
        if (x == 1) {
            toolbar_game11.setTitle("New City");
            relativeLayout.setBackgroundResource(R.mipmap.background_1_edit);
            seerBar4.setThumb(getResources().getDrawable(R.mipmap.air));
            seerBar3.setThumb(getResources().getDrawable(R.mipmap.sonic1));
        }
        if (x == 2) {
            toolbar_game11.setTitle("Mountain");
            relativeLayout.setBackgroundResource(R.mipmap.background_2_edit);
            seerBar4.setThumb(getResources().getDrawable(R.mipmap.maybay));
        }
        if (x == 3) {
            toolbar_game11.setTitle("Old City");
            relativeLayout.setBackgroundResource(R.mipmap.background_3_edit);
            seerBar4.setThumb(getResources().getDrawable(R.mipmap.phuthuy));
            seerBar1.setThumb(getResources().getDrawable(R.mipmap.pokemon1));
        }
    }
}
