package com.danhtran12797.thd.app_game2019.game7;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Game7_Activity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "game7";
    int myMoney = 100;
    int myMoney_temp;
    boolean check_Select = true;

    ImageView imgChim1, imgChim2, imgChim3, imgChim4, imgChim5, imgChim6;
    ImageView imgHinh1, imgHinh2, imgHinh3;
    TextView txt1, txt2, txt3, txt4, txt5, txt6;
    RadioButton rad1, rad2, rad5, rad10;
    Button btnXoay, btnXacNhan;
    TextView txtTinhTien;

    ArrayList<String> arrayChim, arrayChimAll, arrayChim1;
    String idHinh1, idHinh3, idHinh2;

    ArrayList<Chim_User> arrayUser;
    android.support.v7.widget.Toolbar toolbar_game7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game7_activity);

        toolbar_game7 = findViewById(R.id.toolbar_game7);
        setSupportActionBar(toolbar_game7);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor"))) {
            String infor = "Game bầu cua\n\"Đặt cược đi nào...\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.apply();
        }

        addControls();
        addEvents();
    }

    private void addControls() {
        String[] mangTen = getResources().getStringArray(R.array.list);
        arrayChimAll = new ArrayList<>(Arrays.asList(mangTen));

        imgChim1 = findViewById(R.id.imgChim1);
        imgChim2 = findViewById(R.id.imgChim2);
        imgChim3 = findViewById(R.id.imgChim3);
        imgChim4 = findViewById(R.id.imgChim4);
        imgChim5 = findViewById(R.id.imgChim5);
        imgChim6 = findViewById(R.id.imgChim6);

        Random_Array();

        arrayUser = new ArrayList<Chim_User>();

        imgHinh1 = findViewById(R.id.imgQues1);
        imgHinh2 = findViewById(R.id.imgQues2);
        imgHinh3 = findViewById(R.id.imgQues3);

        txt1 = findViewById(R.id.txtChim1);
        txt2 = findViewById(R.id.txtChim2);
        txt3 = findViewById(R.id.txtChim3);
        txt4 = findViewById(R.id.txtChim4);
        txt5 = findViewById(R.id.txtChim5);
        txt6 = findViewById(R.id.txtChim6);

        txtTinhTien = findViewById(R.id.txtTinhTien);

        btnXoay = findViewById(R.id.btnXoay);
        btnXoay.setEnabled(false);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }

    private void addEvents() {

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayUser.size() != 0) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược " + Total_TienCuoc() + " đô\nGood Luck", Toast.LENGTH_SHORT).show();
                    check_Select = false;
                    btnXoay.setEnabled(true);
                    Tat_Image();
                    Tat_Choi();
                } else
                    Toast.makeText(Game7_Activity.this, "Vui lòng đặt cược!", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                new CountDownTimer(5000, 300) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        Collections.shuffle(arrayChim1);
                        idHinh1 = arrayChim1.get(5);
                        int idHinh_1 = getResources().getIdentifier(idHinh1, "drawable", getPackageName());
                        imgHinh1.setImageResource(idHinh_1);

                        Collections.shuffle(arrayChim1);
                        idHinh2 = arrayChim1.get(5);
                        int idHinh_2 = getResources().getIdentifier(idHinh2, "drawable", getPackageName());
                        imgHinh2.setImageResource(idHinh_2);

                        Collections.shuffle(arrayChim1);
                        idHinh3 = arrayChim1.get(5);
                        int idHinh_3 = getResources().getIdentifier(idHinh3, "drawable", getPackageName());
                        imgHinh3.setImageResource(idHinh_3);
                    }

                    @Override
                    public void onFinish() {
                        txtTinhTien.setText("");
                        myMoney_temp = myMoney;
                        for (Chim_User u : arrayUser) {
                            if (u.getIdChim().equals(idHinh1) || u.getIdChim().equals(idHinh3) || u.getIdChim().equals(idHinh2)) {
                                if (u.getIdChim().equals(idHinh1))
                                    myMoney += u.getTienCuoc();
                                if (u.getIdChim().equals(idHinh2))
                                    myMoney += u.getTienCuoc();
                                if (u.getIdChim().equals(idHinh3))
                                    myMoney += u.getTienCuoc();
                            } else
                                myMoney -= u.getTienCuoc();
                        }
                        int temp = myMoney - myMoney_temp;
                        if (temp >= 0) {
                            Toast.makeText(Game7_Activity.this, "Chúc mừng bạn được +" + temp, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Game7_Activity.this, "Rất tiếc bạn bị " + temp, Toast.LENGTH_SHORT).show();

                        txtTinhTien.setText(myMoney + "");
                        check_Select = true;
                        btnXoay.setEnabled(false);
                        Mo_Image();
                        Mo_Choi();
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                ClearDiem();
                            }
                        }, 2000);

                    }
                }.start();
            }
        });


        imgChim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_Select == false) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược, không thể thay đổi!\nVui lòng click 'XOAY'.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idHinh = getResources().getIdentifier(arrayChim.get(0), "drawable", getPackageName());
                Dialog_Dat_Cuoc(idHinh, "chim1");
            }
        });
        imgChim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_Select == false) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược, không thể thay đổi!\nVui lòng click 'XOAY'.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idHinh = getResources().getIdentifier(arrayChim.get(1), "drawable", getPackageName());
                Dialog_Dat_Cuoc(idHinh, "chim2");
            }
        });

        imgChim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_Select == false) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược, không thể thay đổi!\nVui lòng click 'XOAY'.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idHinh = getResources().getIdentifier(arrayChim.get(2), "drawable", getPackageName());
                Dialog_Dat_Cuoc(idHinh, "chim3");
            }
        });
        imgChim4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_Select == false) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược, không thể thay đổi!\nVui lòng click 'XOAY'.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idHinh = getResources().getIdentifier(arrayChim.get(3), "drawable", getPackageName());
                Dialog_Dat_Cuoc(idHinh, "chim4");
            }
        });
        imgChim5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_Select == false) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược, không thể thay đổi!\nVui lòng click 'XOAY'.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idHinh = getResources().getIdentifier(arrayChim.get(4), "drawable", getPackageName());
                Dialog_Dat_Cuoc(idHinh, "chim5");
            }
        });
        imgChim6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_Select == false) {
                    Toast.makeText(Game7_Activity.this, "Bạn đã đặt cược, không thể thay đổi!\nVui lòng click 'XOAY'.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int idHinh = getResources().getIdentifier(arrayChim.get(5), "drawable", getPackageName());
                Dialog_Dat_Cuoc(idHinh, "chim6");
            }
        });

        txt1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Diem_Ve_0("chim1");
                txt1.setText("0");
                return false;
            }
        });
        txt2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Diem_Ve_0("chim2");
                txt2.setText("0");

                return false;
            }
        });
        txt3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Diem_Ve_0("chim3");
                txt3.setText("0");

                return false;
            }
        });
        txt4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Diem_Ve_0("chim4");
                txt4.setText("0");
                return false;
            }
        });
        txt5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Diem_Ve_0("chim5");
                txt5.setText("0");
                return false;
            }
        });
        txt6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Diem_Ve_0("chim6");
                txt6.setText("0");
                return false;
            }
        });
    }

    private void Random_Array() {
        Collections.shuffle(arrayChimAll);
        arrayChim = new ArrayList<>();
        arrayChim1 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            arrayChim.add(arrayChimAll.get(i));
        }
        arrayChim1 = (ArrayList<String>) arrayChim.clone();

        imgChim1.setImageResource(getResources().getIdentifier(arrayChim.get(0), "drawable", getPackageName()));
        imgChim2.setImageResource(getResources().getIdentifier(arrayChim.get(1), "drawable", getPackageName()));
        imgChim3.setImageResource(getResources().getIdentifier(arrayChim.get(2), "drawable", getPackageName()));
        imgChim4.setImageResource(getResources().getIdentifier(arrayChim.get(3), "drawable", getPackageName()));
        imgChim5.setImageResource(getResources().getIdentifier(arrayChim.get(4), "drawable", getPackageName()));
        imgChim6.setImageResource(getResources().getIdentifier(arrayChim.get(5), "drawable", getPackageName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game7_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_game7_reload:
                Toast.makeText(this, "Good Luck", Toast.LENGTH_SHORT).show();
                Random_Array();

                imgHinh1.setImageResource(R.drawable.quesion);
                imgHinh2.setImageResource(R.drawable.quesion);
                imgHinh3.setImageResource(R.drawable.quesion);

                ClearDiem();
                break;
            case R.id.menu_game7_my_home:
                finish();
                break;
            case R.id.menu_game7_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game7_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Dialog_Dat_Cuoc(final int idHinhMain, final String k) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.game7_activity_dat_cuoc);

        ImageView imgHinh_DatCuoc = dialog.findViewById(R.id.imgHinhDatCuoc);
        Button btnOk = dialog.findViewById(R.id.btnOK);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        imgHinh_DatCuoc.setImageResource(idHinhMain);
        rad1 = dialog.findViewById(R.id.rad1Do);
        rad2 = dialog.findViewById(R.id.rad2Do);
        rad5 = dialog.findViewById(R.id.rad5Do);
        rad10 = dialog.findViewById(R.id.rad10Do);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (k.equals("chim1")) {
                    if (rad1.isChecked()) {
                        txt1.setText("1");
                        addUser_Chim(new Chim_User(arrayChim.get(0), 1, "chim1"));
                    }
                    if (rad2.isChecked()) {
                        txt1.setText("2");
                        addUser_Chim(new Chim_User(arrayChim.get(0), 2, "chim1"));
                    }
                    if (rad5.isChecked()) {
                        txt1.setText("5");
                        addUser_Chim(new Chim_User(arrayChim.get(0), 5, "chim1"));
                    }
                    if (rad10.isChecked()) {
                        txt1.setText("10");
                        addUser_Chim(new Chim_User(arrayChim.get(0), 10, "chim1"));
                    }
                }
                if (k.equals("chim2")) {
                    if (rad1.isChecked()) {
                        txt2.setText("1");
                        addUser_Chim(new Chim_User(arrayChim.get(1), 1, "chim2"));
                    }
                    if (rad2.isChecked()) {
                        txt2.setText("2");
                        addUser_Chim(new Chim_User(arrayChim.get(1), 2, "chim2"));
                    }
                    if (rad5.isChecked()) {
                        txt2.setText("5");
                        addUser_Chim(new Chim_User(arrayChim.get(1), 5, "chim2"));
                    }
                    if (rad10.isChecked()) {
                        txt2.setText("10");
                        addUser_Chim(new Chim_User(arrayChim.get(1), 10, "chim2"));
                    }
                }
                if (k.equals("chim3")) {
                    if (rad1.isChecked()) {
                        txt3.setText("1");
                        addUser_Chim(new Chim_User(arrayChim.get(2), 1, "chim3"));
                    }
                    if (rad2.isChecked()) {
                        txt3.setText("2");
                        addUser_Chim(new Chim_User(arrayChim.get(2), 2, "chim3"));
                    }
                    if (rad5.isChecked()) {
                        txt3.setText("5");
                        addUser_Chim(new Chim_User(arrayChim.get(2), 5, "chim3"));
                    }
                    if (rad10.isChecked()) {
                        txt3.setText("10");
                        addUser_Chim(new Chim_User(arrayChim.get(2), 10, "chim3"));
                    }
                }
                if (k.equals("chim4")) {
                    if (rad1.isChecked()) {
                        txt4.setText("1");
                        addUser_Chim(new Chim_User(arrayChim.get(3), 1, "chim4"));
                    }
                    if (rad2.isChecked()) {
                        txt4.setText("2");
                        addUser_Chim(new Chim_User(arrayChim.get(3), 2, "chim4"));
                    }
                    if (rad5.isChecked()) {
                        txt4.setText("5");
                        addUser_Chim(new Chim_User(arrayChim.get(3), 5, "chim4"));
                    }
                    if (rad10.isChecked()) {
                        txt4.setText("10");
                        addUser_Chim(new Chim_User(arrayChim.get(3), 10, "chim4"));
                    }
                }
                if (k.equals("chim5")) {
                    if (rad1.isChecked()) {
                        txt5.setText("1");
                        addUser_Chim(new Chim_User(arrayChim.get(4), 1, "chim5"));
                    }
                    if (rad2.isChecked()) {
                        txt5.setText("2");
                        addUser_Chim(new Chim_User(arrayChim.get(4), 2, "chim5"));
                    }
                    if (rad5.isChecked()) {
                        txt5.setText("5");
                        addUser_Chim(new Chim_User(arrayChim.get(4), 5, "chim5"));
                    }
                    if (rad10.isChecked()) {
                        txt5.setText("10");
                        addUser_Chim(new Chim_User(arrayChim.get(4), 10, "chim5"));
                    }
                }
                if (k.equals("chim6")) {
                    if (rad1.isChecked()) {
                        txt6.setText("1");
                        addUser_Chim(new Chim_User(arrayChim.get(5), 1, "chim6"));
                    }
                    if (rad2.isChecked()) {
                        txt6.setText("2");
                        addUser_Chim(new Chim_User(arrayChim.get(5), 2, "chim6"));
                    }
                    if (rad5.isChecked()) {
                        txt6.setText("5");
                        addUser_Chim(new Chim_User(arrayChim.get(5), 5, "chim6"));
                    }
                    if (rad10.isChecked()) {
                        txt6.setText("10");
                        addUser_Chim(new Chim_User(arrayChim.get(5), 10, "chim6"));
                    }
                }
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void ClearDiem() {
        arrayUser.clear();
        txt1.setText("0");
        txt2.setText("0");
        txt3.setText("0");
        txt4.setText("0");
        txt5.setText("0");
        txt6.setText("0");
    }

    private void Diem_Ve_0(String s) {
        for (Chim_User u : arrayUser) {
            if (s.equals(u.getChim()))
                arrayUser.remove(u);
        }
    }

    private void addUser_Chim(Chim_User d) {
        for (Chim_User u : arrayUser) {
            if (u.getIdChim().equals(d.getIdChim())) {
                return;
            }
        }
        arrayUser.add(d);
    }

    private void Tat_Image() {
        txt1.setEnabled(false);
        txt2.setEnabled(false);
        txt3.setEnabled(false);
        txt4.setEnabled(false);
        txt5.setEnabled(false);
        txt6.setEnabled(false);
    }

    private void Mo_Image() {
        txt1.setEnabled(true);
        txt2.setEnabled(true);
        txt3.setEnabled(true);
        txt4.setEnabled(true);
        txt5.setEnabled(true);
        txt6.setEnabled(true);
    }

    private void Mo_Choi() {
        btnXacNhan.setEnabled(true);
        btnXacNhan.setText("xác nhận đặt cược");
    }

    private void Tat_Choi() {
        btnXacNhan.setEnabled(false);
        btnXacNhan.setText("Đã xác nhận đặt cược");
    }

    private int Total_TienCuoc() {
        int s = 0;
        for (Chim_User u : arrayUser)
            s += u.getTienCuoc();
        return s;
    }
}
