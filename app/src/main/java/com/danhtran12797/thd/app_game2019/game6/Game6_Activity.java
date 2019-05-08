package com.danhtran12797.thd.app_game2019.game6;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class Game6_Activity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "game6";
    ArrayList<String> arrayList;

    Spinner spinner;
    LinearLayout linearUsers, linearLayout;

    Button btnUser1, btnUser2, btnGO;
    ProgressBar progressBar;

    String lasted = "";// chứa cái mã ảnh(chim1, chim2,...) để kiểm tra có giống nhau hay k.

    ArrayList<String> arrCapDo;
    ArrayAdapter<String> adapter;
    TextView txtA, txtB;
    final int[] a = new int[]{0, 0};//a[0] chứa điểm người chơi 1, a[1] chứa điểm người chơi 2
    final boolean[] check = {true, false}; // kiểm tra đến lượt người chơi nào chọn(user 1 sẽ chạy đầu tiên)
    int count = 0, chon1or2 = 1;
    int soDong;
    CountDownTimer countDownTimer;
    Toolbar toolbar_game6;

    int timeTotal = 0;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game6_activity);

        toolbar_game6 = findViewById(R.id.toolbar_game6);
        setSupportActionBar(toolbar_game6);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor"))) {
            String infor = "Game lật hình 1/2\n\"Lật...lật...lật...\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.apply();
        }

        //mainLayout =  findViewById(R.id.layout);
        linearLayout = findViewById(R.id.linearLayoutDanh);
        linearUsers = findViewById(R.id.linearUsers);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        param.width = width;
        param.height = width;
        linearLayout.setLayoutParams(param);

        txtA = findViewById(R.id.txtUsserA);
        txtB = findViewById(R.id.txtUsserB);

//        txtA.setVisibility(View.GONE);
//        txtB.setVisibility(View.GONE);
        linearUsers.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);
        btnUser1 = findViewById(R.id.btn1User);
        btnUser2 = findViewById(R.id.btn2User);
        btnGO = findViewById(R.id.btnGO);

        String mangChim[] = getResources().getStringArray(R.array.list);
        arrayList = new ArrayList<>(Arrays.asList(mangChim));

        spinner = findViewById(R.id.spinner);
        arrCapDo = new ArrayList<>();
        arrCapDo.add("3 X 3");
        arrCapDo.add("4 X 4");
        arrCapDo.add("5 X 5");
        arrCapDo.add("6 X 6");
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arrCapDo);
        spinner.setAdapter(adapter);

        btnGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGO.setBackgroundResource(android.R.color.holo_blue_light);
                if (isRunning) {
                    countDownTimer.cancel();
                }

                int selected = spinner.getSelectedItemPosition();
                if (selected == 0) {
                    soDong = 3;
                } else if (selected == 1) {
                    soDong = 4;
                } else if (selected == 2) {
                    soDong = 5;
                } else {
                    soDong = 6;
                }

                if (chon1or2 == 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    linearUsers.setVisibility(View.GONE);
                    CountTime();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    linearUsers.setVisibility(View.VISIBLE);
                }

                Ve_Layout();
            }
        });

        btnUser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGO.setBackgroundResource(android.R.color.darker_gray);
                btnUser1.setBackgroundResource(android.R.color.holo_blue_light);
                btnUser2.setBackgroundResource(android.R.color.darker_gray);
                chon1or2 = 1;

            }
        });

        btnUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGO.setBackgroundResource(android.R.color.darker_gray);
                btnUser1.setBackgroundResource(android.R.color.darker_gray);
                btnUser2.setBackgroundResource(android.R.color.holo_blue_light);
                chon1or2 = 2;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTypeface(((TextView) parent.getChildAt(0)).getTypeface(), Typeface.BOLD);
                ((TextView) parent.getChildAt(0)).setTextSize(18);

                btnGO.setBackgroundResource(android.R.color.darker_gray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game6_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_game6_my_home:
                finish();
                break;
            case R.id.menu_game6_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game6_exit:
                Exit();
                onDestroy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    public void CountTime() {

        if (soDong == 3)
            timeTotal = 30000;
        else if (soDong == 4)
            timeTotal = 40000;
        else if (soDong == 5)
            timeTotal = 50000;
        else
            timeTotal = 60000;

        progressBar.setMax(timeTotal / 1000);

        countDownTimer = new CountDownTimer(timeTotal, 1000) {
            int t = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                t++;
                int current = progressBar.getProgress();
                progressBar.setProgress(current + 1);

                if (count == soDong * soDong) {
                    btnGO.setBackgroundResource(android.R.color.darker_gray);
                    this.cancel();
                    Toast.makeText(Game6_Activity.this, "HOÀN THÀNH TRONG " + t + "s", Toast.LENGTH_SHORT).show();
                    progressBar.setProgress(0);
                }
            }

            @Override
            public void onFinish() {
                btnGO.setBackgroundResource(android.R.color.darker_gray);
                isRunning = false;
                progressBar.setProgress(0);
                Toast.makeText(Game6_Activity.this, "HẾT GIỜ", Toast.LENGTH_SHORT).show();
            }
        };

        countDownTimer.start();
    }

    public void Ve_Layout() {
        final ArrayList<ImageView> imgDanh = new ArrayList<>();// ds chứa phần từ thứ 1, đã chọn đúng, mục đích đưa nó về ennable(false);

        progressBar.setProgress(0);
        linearLayout.removeAllViews();

        a[0] = 0;
        a[1] = 0;
        check[0] = true;
        check[1] = false;
        txtA.setText("0");
        txtB.setText("0");

        count = 0;// đếm số ảnh  đã chọn đúng trên màn hình

        final int[] a = new int[]{0, 0};
        final boolean[] check = {true, false};


        final ArrayList<ImageView> imgTemp = new ArrayList<>();

        Collections.shuffle(arrayList);
        final ArrayList<String> arrSoDOng = new ArrayList<>();
        if (soDong == 3 || soDong == 5) {
            arrSoDOng.add("star");
            if (soDong == 3) {
                for (int i = 1; i < soDong + 2; i++) {
                    arrSoDOng.add(arrayList.get(i));
                    arrSoDOng.add(arrayList.get(i));
                }
            } else {
                for (int i = 1; i < soDong + 2; i++) {
                    arrSoDOng.add(arrayList.get(i));
                    arrSoDOng.add(arrayList.get(i));
                    arrSoDOng.add(arrayList.get(i));
                    arrSoDOng.add(arrayList.get(i));
                }
            }
        } else if (soDong == 6) {
            for (int i = 0; i < soDong + 3; i++) {
                arrSoDOng.add(arrayList.get(i));
                arrSoDOng.add(arrayList.get(i));
                arrSoDOng.add(arrayList.get(i));
                arrSoDOng.add(arrayList.get(i));
            }
        } else {
            for (int i = 0; i < soDong; i++) {
                arrSoDOng.add(arrayList.get(i));
                arrSoDOng.add(arrayList.get(i));
                arrSoDOng.add(arrayList.get(i));
                arrSoDOng.add(arrayList.get(i));
            }
        }

        Collections.shuffle(arrSoDOng);
        Toast.makeText(this, "GOOD LUCK", Toast.LENGTH_SHORT).show();

        for (int i = 1; i <= soDong; i++) {
            LinearLayout layout = new LinearLayout(this);

            layout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            layout.setLayoutParams(layoutParams);
            if (i == 0)
                layoutParams.setMargins(0, 2, 0, 0);
            for (int j = 1; j <= soDong; j++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                final ImageView imageView = new ImageView(this);
                params.setMargins(2, 2, 2, 2);

                final int vitri = soDong * (i - 1) + j - 1;
                final int idImage = getResources().getIdentifier(arrSoDOng.get(vitri), "drawable", getPackageName());
                imageView.setImageResource(R.color.colorGray);
                imageView.setLayoutParams(params);

                layout.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        imageView.setImageResource(idImage);
                        if (arrSoDOng.get(vitri).equals("star")) {
                            imageView.setEnabled(false);
                            count += 1;
                            if (check[0] == true)
                                a[0] += 1;
                            else
                                a[1] += 1;
                            Toast.makeText(Game6_Activity.this, "GREAT!", Toast.LENGTH_SHORT).show();
                        } else {
                            imgTemp.add(imageView);
                            if (imgTemp.size() == 2) {
                                if (lasted.equals(arrSoDOng.get(vitri))) {
                                    imgDanh.add(imgTemp.get(0));// imgTemp tối đa có 2 phần tử, mục đích là khi đủ 2 phần tử thì sẽ lấy ra kiểm tra có giống nhau hay k.
                                    imgDanh.get(imgDanh.size() - 1).setEnabled(false);

                                    imageView.setEnabled(false);
                                    count += 2;

                                    if (check[0] == true)
                                        a[0] += 1;
                                    else
                                        a[1] += 1;
                                    lasted = "";
                                    imgTemp.clear();
                                } else {
                                    new CountDownTimer(600, 100) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                        }

                                        @Override
                                        public void onFinish() {
                                            imgTemp.get(0).setImageResource(R.color.colorGray);
                                            imageView.setImageResource(R.color.colorGray);
                                            imgTemp.clear();
                                            lasted = "";
                                            if (check[0] == true) {
                                                check[1] = true;
                                                check[0] = false;
                                            } else {
                                                check[1] = false;
                                                check[0] = true;
                                            }
                                        }
                                    }.start();
                                }
                            } else {
                                lasted = arrSoDOng.get(vitri);
                            }
                        }
                        txtA.setText(a[0] + "");
                        txtB.setText(a[1] + "");
                    }
                });
            }
            linearLayout.addView(layout);
        }
    }
}
