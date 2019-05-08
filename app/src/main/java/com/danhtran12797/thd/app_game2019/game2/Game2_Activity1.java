package com.danhtran12797.thd.app_game2019.game2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danhtran12797.thd.app_game2019.R;

import java.util.Collections;

public class Game2_Activity1 extends Activity{

    LinearLayout layout;
    TextView txtDem;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game2_activity1);

        layout=findViewById(R.id.linearLayout);
        txtDem=findViewById(R.id.txtDem);

        //trộn mảng
        Collections.shuffle(Game2_Activity.arrayName);

        //tạo dào và cột
        for(int i=1;i<=6;i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            LinearLayout row= new LinearLayout(this);
            row.setLayoutParams(layoutParams);
            for(int j=1;j<=3;j++)
            {
                final ImageView imageView= new ImageView(this);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);
                imageView.setLayoutParams(layoutParams1);

                final int vitri=3*(i-1)+j-1;

                int idHinh=getResources().getIdentifier(Game2_Activity.arrayName.get(vitri),"drawable",getPackageName());
                imageView.setImageResource(idHinh);

                //add imageView vào tableRow
                row.addView(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.putExtra("tenhinhchon",Game2_Activity.arrayName.get(vitri));
                        setResult(RESULT_OK,intent);
                        countDownTimer.cancel();
                        finish();
                    }
                });
            }
            layout.addView(row);
        }
        countDownTimer=new CountDownTimer(4000,1000) {
            int t=4;
            @Override
            public void onTick(long millisUntilFinished) {
                txtDem.setText(--t+"");
            }

            @Override
            public void onFinish() {
                finish();
            }
        };
        countDownTimer.start();
    }

}
