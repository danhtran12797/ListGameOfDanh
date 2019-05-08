package com.danhtran12797.thd.app_game2019.game3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Game3_Activity extends AppCompatActivity {
    private static final String SHARED_PREFERENCES_NAME = "game3";
    int iD;

    CountDownTimer countDownTimer;

    ArrayList<ImageView> arrHinh;
    ArrayList<String> arrayNameHinh;

    ProgressBar pgbTime;

    Button btnA, btnB, btnC, btnD;
    ArrayList<Button> arrayBtnDapAn;

    ImageView imgHinh26, imgHinh27, imgHinh28, imgHinh29, imgHinh30, imgHinh31, imgHinh32, imgHinh33, imgHinh34,
            imgHinh35, imgHinh36, imgHinh37, imgHinh38, imgHinh39, imgHinh40, imgHinh41, imgHinh42, imgHinh43, imgHinh44,
            imgHinh45, imgHinh46, imgHinh47, imgHinh48, imgHinh49, imgHinh50;
    Button btnOpen;
    //ArrayList<ImageView> arrayList;
    int image_hide, image_show, dung = 0, sai = 0, max;
    TextView txtDung, txtSai;
    Toolbar toolbar_game3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game3_activity);

        toolbar_game3 = findViewById(R.id.toolbar_game3);
        setSupportActionBar(toolbar_game3);

        Toast.makeText(this, "Click 'Go Go' để chơi game", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor") && sharedPreferences.contains("core"))) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String infor = "Game đếm số chim\n\"Quá nhiều...\"";
            editor.putString("infor", infor);
            editor.putInt("core", 0);
            editor.apply();
        }
        max = sharedPreferences.getInt("core", 0);

        iD = R.drawable.chim1;

        String mangNamHinh[] = getResources().getStringArray(R.array.list);
        arrayNameHinh = new ArrayList<>(Arrays.asList(mangNamHinh));

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);

        arrayBtnDapAn = new ArrayList<>();
        arrayBtnDapAn.add(btnA);
        arrayBtnDapAn.add(btnB);
        arrayBtnDapAn.add(btnC);
        arrayBtnDapAn.add(btnD);

        btnOpen = findViewById(R.id.btnOpen);

        txtDung = findViewById(R.id.txtDung);
        txtSai = findViewById(R.id.txtSai);
        pgbTime = findViewById(R.id.pgbTime);

        countDownTimer = new CountDownTimer(40000, 400) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("AAA", millisUntilFinished + "");
                int current = pgbTime.getProgress();
                pgbTime.setProgress(current + 1);
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                if (dung > max) {
                    max = dung;
                    Toast.makeText(Game3_Activity.this, "Kỉ lục mới, chúc mừng bạn.", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("core", max);
                    editor.apply();
                }

                if (dung == sai)
                    Toast.makeText(Game3_Activity.this, "Not Bad", Toast.LENGTH_LONG).show();
                if (dung > sai)
                    Toast.makeText(Game3_Activity.this, "GOOD", Toast.LENGTH_LONG).show();
                if (dung < sai)
                    Toast.makeText(Game3_Activity.this, "Hmm...Try it", Toast.LENGTH_LONG).show();
                dung = 0;
                sai = 0;
                txtDung.setText("0");
                txtSai.setText("0");
                pgbTime.setProgress(0);
                OpenAlert();
            }
        };


        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btnA.getText().toString();
                int vitri_C = s.indexOf("c");
                String kq = s.substring(0, vitri_C - 1);
                if (Integer.parseInt(kq) == image_show) {
                    Toast.makeText(Game3_Activity.this, "ĐÚNG", Toast.LENGTH_SHORT).show();
                    txtDung.setText(++dung + "");
                    btnA.setBackgroundResource(R.drawable.game3_shape_true);
                } else {
                    txtSai.setText(++sai + "");
                    Toast.makeText(Game3_Activity.this, "SAI\nĐáp án: " + image_show, Toast.LENGTH_SHORT).show();
                    btnA.setBackgroundResource(R.drawable.game3_shape_false);
                }
                button_close();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        button_open();
                        set_btn_background_default();
                        OpenSelect(iD);
                    }
                }, 1500);
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btnB.getText().toString();
                int vitri_C = s.indexOf("c");
                String kq = s.substring(0, vitri_C - 1);
                if (Integer.parseInt(kq) == image_show) {
                    Toast.makeText(Game3_Activity.this, "ĐÚNG", Toast.LENGTH_SHORT).show();
                    txtDung.setText(++dung + "");
                    btnB.setBackgroundResource(R.drawable.game3_shape_true);
                } else {
                    txtSai.setText(++sai + "");
                    Toast.makeText(Game3_Activity.this, "SAI\nĐáp án: " + image_show, Toast.LENGTH_SHORT).show();
                    btnB.setBackgroundResource(R.drawable.game3_shape_false);
                }
                button_close();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        button_open();
                        set_btn_background_default();
                        OpenSelect(iD);
                    }
                }, 1500);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btnC.getText().toString();
                int vitri_C = s.indexOf("c");
                String kq = s.substring(0, vitri_C - 1);
                if (Integer.parseInt(kq) == image_show) {
                    Toast.makeText(Game3_Activity.this, "ĐÚNG", Toast.LENGTH_SHORT).show();
                    txtDung.setText(++dung + "");
                    btnC.setBackgroundResource(R.drawable.game3_shape_true);
                } else {
                    txtSai.setText(++sai + "");
                    Toast.makeText(Game3_Activity.this, "SAI\nĐáp án: " + image_show, Toast.LENGTH_SHORT).show();
                    btnC.setBackgroundResource(R.drawable.game3_shape_false);
                }
                button_close();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        button_open();
                        set_btn_background_default();
                        OpenSelect(iD);
                    }
                }, 1500);
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btnD.getText().toString();
                int vitri_C = s.indexOf("c");
                String kq = s.substring(0, vitri_C - 1);
                if (Integer.parseInt(kq) == image_show) {
                    Toast.makeText(Game3_Activity.this, "ĐÚNG", Toast.LENGTH_SHORT).show();
                    txtDung.setText(++dung + "");
                    btnD.setBackgroundResource(R.drawable.game3_shape_true);
                } else {
                    txtSai.setText(++sai + "");
                    Toast.makeText(Game3_Activity.this, "SAI\nĐáp án: " + image_show, Toast.LENGTH_SHORT).show();
                    btnD.setBackgroundResource(R.drawable.game3_shape_false);
                }
                button_close();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        button_open();
                        set_btn_background_default();
                        OpenSelect(iD);
                    }
                }, 1500);
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(arrayNameHinh);
                iD = getResources().getIdentifier(arrayNameHinh.get(0), "drawable", getPackageName());
                countDownTimer.start();
                OpenSelect(iD);
                btnOpen.setEnabled(false);

                button_open();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    private void EditButtonDapAn(int result) {
        ArrayList<Integer> arrMinToMax = new ArrayList<>();
        int min, max;
        int k1 = 1, k2 = 1;

        min = result - 5;
        max = result + 5;
        while (true) {
            if (min <= 0)
                min += k1++;
            else
                break;
        }
        while (true) {
            if (max > 25)
                max -= k2++;
            else
                break;
        }
        for (int i = min; i < max + 1; i++) {
            if (i != result)
                arrMinToMax.add(i);
        }

        Collections.shuffle(arrayBtnDapAn);
        arrayBtnDapAn.get(0).setText(result + " con chim");
        Collections.shuffle(arrMinToMax);
        for (int i = 1; i < arrayBtnDapAn.size(); i++) {
            int d = arrMinToMax.get(i);
            arrayBtnDapAn.get(i).setText(d + " con chim");
        }
    }

    public void button_open() {
        btnA.setEnabled(true);
        btnB.setEnabled(true);
        btnC.setEnabled(true);
        btnD.setEnabled(true);
    }

    public void button_close() {
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
    }

    public void set_btn_background_default() {
        btnA.setBackgroundResource(R.drawable.game3_shape_default);
        btnB.setBackgroundResource(R.drawable.game3_shape_default);
        btnC.setBackgroundResource(R.drawable.game3_shape_default);
        btnD.setBackgroundResource(R.drawable.game3_shape_default);
    }

    private void OpenAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("THD Games");
        builder.setMessage("Bạn có muốn tiếp tục chơi?");
        builder.setIcon(R.drawable.question1);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog1, int which) {
                countDownTimer.start();
                OpenSelect(iD);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnOpen.setEnabled(true);
                btnA.setText("... con chim");
                btnB.setText("... con chim");
                btnC.setText("... con chim");
                btnD.setText("... con chim");

                button_close();
            }
        });

        builder.show();
    }

    private void OpenSelect(int id) {
        Random random = new Random();
        image_hide = 1 + random.nextInt(26);
        image_show = 25 - image_hide;

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game3_activity1);
        EditButtonDapAn(image_show);
        RelativeLayout layout = dialog.findViewById(R.id.layoutDD);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        //param.width = width;
        param.height = width;
        layout.setLayoutParams(param);


        imgHinh26 = dialog.findViewById(R.id.imgHinh26);
        imgHinh27 = dialog.findViewById(R.id.imgHinh27);
        imgHinh28 = dialog.findViewById(R.id.imgHinh28);
        imgHinh29 = dialog.findViewById(R.id.imgHinh29);
        imgHinh30 = dialog.findViewById(R.id.imgHinh30);
        imgHinh31 = dialog.findViewById(R.id.imgHinh31);
        imgHinh32 = dialog.findViewById(R.id.imgHinh32);
        imgHinh33 = dialog.findViewById(R.id.imgHinh33);
        imgHinh34 = dialog.findViewById(R.id.imgHinh34);
        imgHinh35 = dialog.findViewById(R.id.imgHinh35);
        imgHinh36 = dialog.findViewById(R.id.imgHinh36);
        imgHinh37 = dialog.findViewById(R.id.imgHinh37);
        imgHinh38 = dialog.findViewById(R.id.imgHinh38);
        imgHinh39 = dialog.findViewById(R.id.imgHinh39);
        imgHinh40 = dialog.findViewById(R.id.imgHinh40);
        imgHinh41 = dialog.findViewById(R.id.imgHinh41);
        imgHinh42 = dialog.findViewById(R.id.imgHinh42);
        imgHinh43 = dialog.findViewById(R.id.imgHinh43);
        imgHinh44 = dialog.findViewById(R.id.imgHinh44);
        imgHinh45 = dialog.findViewById(R.id.imgHinh45);
        imgHinh46 = dialog.findViewById(R.id.imgHinh46);
        imgHinh47 = dialog.findViewById(R.id.imgHinh47);
        imgHinh48 = dialog.findViewById(R.id.imgHinh48);
        imgHinh49 = dialog.findViewById(R.id.imgHinh49);
        imgHinh50 = dialog.findViewById(R.id.imgHinh50);

        arrHinh = new ArrayList<>();

        arrHinh.add(imgHinh26);
        arrHinh.add(imgHinh27);
        arrHinh.add(imgHinh28);
        arrHinh.add(imgHinh29);
        arrHinh.add(imgHinh30);
        arrHinh.add(imgHinh31);
        arrHinh.add(imgHinh32);
        arrHinh.add(imgHinh33);
        arrHinh.add(imgHinh34);
        arrHinh.add(imgHinh35);
        arrHinh.add(imgHinh36);
        arrHinh.add(imgHinh37);
        arrHinh.add(imgHinh38);
        arrHinh.add(imgHinh39);
        arrHinh.add(imgHinh40);
        arrHinh.add(imgHinh41);
        arrHinh.add(imgHinh42);
        arrHinh.add(imgHinh43);
        arrHinh.add(imgHinh44);
        arrHinh.add(imgHinh45);
        arrHinh.add(imgHinh46);
        arrHinh.add(imgHinh47);
        arrHinh.add(imgHinh48);
        arrHinh.add(imgHinh49);
        arrHinh.add(imgHinh50);


        for (ImageView img : arrHinh) {
            img.setImageResource(id);
        }

        Collections.shuffle(arrHinh);

        for (int i = 0; i < image_hide; i++) {
            arrHinh.get(i).setVisibility(View.INVISIBLE);
            //arrayList.remove(0);
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
        dialog.show();
    }

    public void Exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game3_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_game3_reload:
                dung = 0;
                sai = 0;
                txtDung.setText("0");
                txtSai.setText("0");
                pgbTime.setProgress(0);
                countDownTimer.cancel();

                btnOpen.setEnabled(true);
                btnA.setText("... con chim");
                btnB.setText("... con chim");
                btnC.setText("... con chim");
                btnD.setText("... con chim");
                button_close();
                break;
            case R.id.menu_game3_my_home:
                finish();
                break;
            case R.id.menu_game3_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game3_max_core:
                Dialog_Infor(max + "");
                break;
            case R.id.menu_game3_exit:
                Exit();
                onDestroy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Dialog_Infor(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.show();
    }
}
