package com.danhtran12797.thd.app_game2019.game4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game4_Activity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "game4";
    String result="";
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnBang,btnC,btnGo;
    TextView txtResult, txtManHinh,txtDung,txtSai;
    ProgressBar progressBar;
    int a,b,kq;
    int dung=0,sai=0;
    ArrayList<String> arrKyTu;

    Spinner spinner;
    ArrayAdapter<String > adapter;
    ArrayList<String> arrCapDo;

    int selected,max;

    CountDownTimer countDownTimer;
    Toolbar toolbar_game4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game4_activity);

        toolbar_game4=findViewById(R.id.toolbar_game4);
        setSupportActionBar(toolbar_game4);

        final SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        if(!(sharedPreferences.contains("infor")&&sharedPreferences.contains("core"))){
            String infor="Game toán cấp 1\n\"Hơi khó...\"";
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("infor",infor);
            editor.apply();
        }
        max=sharedPreferences.getInt("core",0);

        Toast.makeText(this, "Nhấn 'GO' để chơi game", Toast.LENGTH_SHORT).show();

        spinner=findViewById(R.id.spCapDo);
        arrCapDo=new ArrayList<>();
        arrCapDo.add("Dễ");
        arrCapDo.add("Trung bình");
        arrCapDo.add("Khó");

        adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,arrCapDo);
        spinner.setAdapter(adapter);

        arrKyTu=new ArrayList<>();
        arrKyTu.add("+");
        arrKyTu.add("-");
        arrKyTu.add("X");
        arrKyTu.add("/");

        txtResult=findViewById(R.id.txtResult);
        txtManHinh=findViewById(R.id.txtManHinh);
        txtDung=findViewById(R.id.txtDung);
        txtSai=findViewById(R.id.txtSai);

        btn0=findViewById(R.id.btn0);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        btn8=findViewById(R.id.btn8);
        btn9=findViewById(R.id.btn9);

        btnBang=findViewById(R.id.btnBang);
        btnC=findViewById(R.id.btnClear);
        btnGo=findViewById(R.id.btnGo);

        progressBar=findViewById(R.id.progressBar);

        Close_Button();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) parent.getChildAt(0)).setTypeface(((TextView) parent.getChildAt(0)).getTypeface(),Typeface.BOLD);
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                selected=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="0";
                txtResult.setText(result);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="1";
                txtResult.setText(result);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="2";
                txtResult.setText(result);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="3";
                txtResult.setText(result);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="4";
                txtResult.setText(result);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="5";
                txtResult.setText(result);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="6";
                txtResult.setText(result);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="7";
                txtResult.setText(result);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="8";
                txtResult.setText(result);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result+="9";
                txtResult.setText(result);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result="";
                txtResult.setText("");
            }
        });
        btnBang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtResult.getText().equals(""))
                    Toast.makeText(Game4_Activity.this, "Bạn chưa nhập đáp án :)", Toast.LENGTH_SHORT).show();
                else if(Integer.parseInt(txtResult.getText().toString())==kq){
                    Toast.makeText(Game4_Activity.this, "Đúng", Toast.LENGTH_SHORT).show();
                    txtDung.setText(""+ (++dung));
                }
                else{
                    Toast.makeText(Game4_Activity.this, "Sai\nĐáp án: "+kq, Toast.LENGTH_SHORT).show();
                    txtSai.setText(""+(++sai));
                }
                RandomSo(selected);

                result="";
                txtResult.setText("");
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Game4_Activity.this, "GOOD LUCK", Toast.LENGTH_SHORT).show();
                Open_Button();
                RandomSo(selected);
                countDownTimer.start();
                btnGo.setEnabled(false);
                spinner.setEnabled(false);
            }
        });

        countDownTimer= new CountDownTimer(40000, 400) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current=progressBar.getProgress();
                progressBar.setProgress(current+1);
            }

            @Override
            public void onFinish()
            {
                Toast.makeText(Game4_Activity.this, "hello danh game 4", Toast.LENGTH_SHORT).show();
                if(dung>max){
                    max=dung;
                    Toast.makeText(Game4_Activity.this, "Kỉ lục mới, chúc mừng bạn.", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("core",dung);
                    editor.apply();
                }
                Open_dialog();
                progressBar.setProgress(0);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game4_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        switch (item.getItemId()){
            case R.id.menu_game4_reload:
                countDownTimer.cancel();
                setCount();
                Close_Button();
                progressBar.setProgress(0);
                spinner.setEnabled(true);
                btnGo.setEnabled(true);
                break;
            case R.id.menu_game4_my_home:
                finish();
                break;
            case R.id.menu_game4_infor:
                String s= sharedPreferences.getString("infor","");
                Dialog_Infor(s);
                break;
            case R.id.menu_game4_max_core:
                Dialog_Infor(max+"");
                break;
            case R.id.menu_game4_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void RandomSo(int selected){
        Random random=new Random();
        if(selected==0){
            a=1+random.nextInt(99);
            b=random.nextInt(100);
        }
        else if(selected==1){
            a=1+random.nextInt(999);
            b=random.nextInt(1000);
        }else{
            a=1+random.nextInt(9999);
            b=random.nextInt(10000);
        }

        Game4_TinhToan toan=new Game4_TinhToan(a,b);

        Collections.shuffle(arrKyTu);
        txtManHinh.setText(toan.Show(arrKyTu.get(0)));
        kq=toan.LayKetQua(arrKyTu.get(0));
    }

    public void Open_dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("THD Games");
        builder.setIcon(R.drawable.question1);
        builder.setMessage("Bạn có muốn tiếp tục chơi?");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setCount();
                countDownTimer.start();
                RandomSo(selected);
                Open_Button();
                txtResult.setText("");
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Close_Button();
                setCount();
                btnGo.setEnabled(true);
                spinner.setEnabled(true);
                txtResult.setText("");
            }
        });

        builder.show();
    }

    public void setCount(){
        dung=0;
        txtDung.setText("0");
        sai=0;
        txtSai.setText("0");
    }
    public void Close_Button(){
        txtManHinh.setText("Danh12797");
        btnBang.setEnabled(false);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
        btn0.setEnabled(false);
        btnC.setEnabled(false);
    }
    public void Open_Button(){
        btnBang.setEnabled(true);
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
        btn0.setEnabled(true);
        btnC.setEnabled(true);
    }
}
