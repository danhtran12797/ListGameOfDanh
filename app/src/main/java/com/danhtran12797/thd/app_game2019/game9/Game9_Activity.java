package com.danhtran12797.thd.app_game2019.game9;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;

public class Game9_Activity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_NAME = "game9";
    int score_user, score_may;
    ImageView imgWin;

    Button btnSwap, btnResult, btnTest, btnReset;

    ArrayList<ImageView> arrImage;
    ArrayList<ImageView> arrImage1;

    ArrayList<String> lstCards, lstTemp;
    ArrayList<String> lstUser, lstMay;
    int a = 1, b = 1, c = 1, dem = 0;
    int idTemp = 3;
    ArrayList<Integer> idUser;
    ArrayList<Integer> idMay;
    View.OnClickListener imgUserClick = null;
    boolean chkA = false, chkB = false, chkC = false;


    private int X, Y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game9_activity);

        btnSwap = findViewById(R.id.btnSwap);
        btnTest = findViewById(R.id.btnTest);
        btnResult = findViewById(R.id.btnResult);
        btnReset = findViewById(R.id.btnReset);

        imgWin = findViewById(R.id.imgWin);
        imgWin.setVisibility(View.INVISIBLE);

        close_btn();

        idUser = new ArrayList<>();
        idUser.add(R.id.imgTest2);
        idUser.add(R.id.imgTest4);
        idUser.add(R.id.imgTest6);

        idMay = new ArrayList<>();
        idMay.add(R.id.imgTest);
        idMay.add(R.id.imgTest3);
        idMay.add(R.id.imgTest5);

        String arrCards[] = getResources().getStringArray(R.array.arrCards);
        lstCards = new ArrayList<>(Arrays.asList(arrCards));

        arrImage = new ArrayList<>();
        arrImage1 = new ArrayList<>();

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkC == false || chkB == false || chkA == false) {
                    Toast.makeText(Game9_Activity.this, "Vui lòng lật hết bài của bạn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Clear_Anim();

                int id = getResources().getIdentifier(lstMay.get(0), "drawable", getPackageName());
                arrImage1.get(0).setImageResource(id);

                int id2 = getResources().getIdentifier(lstMay.get(1), "drawable", getPackageName());
                arrImage1.get(1).setImageResource(id2);

                int id3 = getResources().getIdentifier(lstMay.get(2), "drawable", getPackageName());
                arrImage1.get(2).setImageResource(id3);

                Close();

                Animation animation = AnimationUtils.loadAnimation(Game9_Activity.this, R.anim.game9_anim);

                //imgWin.setVisibility(View.VISIBLE);
                score_user = TinhDiem(lstUser);
                score_may = TinhDiem(lstMay);
                if (score_user > score_may)
                    imgWin.setImageResource(R.drawable.win);
                else if (score_user < score_may)
                    imgWin.setImageResource(R.drawable.lose);
                else
                    Toast.makeText(Game9_Activity.this, "WIN/LOSE", Toast.LENGTH_SHORT).show();
                imgWin.startAnimation(animation);

            }
        });

        imgUserClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;

                if (chkA == true && chkB == true && chkC == true) {

                    switch (v.getId()) {
                        case R.id.imgTest2:
                            if (a == 1) {
                                idTemp = 0;
                                Clear_Anim();
                                imageView.animate().translationYBy(-20);
                                a++;
                            } else {
                                imageView.animate().translationYBy(20);
                                a--;
                            }
                            break;
                        case R.id.imgTest4:
                            if (b == 1) {
                                idTemp = 1;
                                Clear_Anim();
                                imageView.animate().translationYBy(-20);
                                b++;
                            } else {
                                imageView.animate().translationYBy(20);
                                b--;
                            }
                            break;
                        case R.id.imgTest6:
                            if (c == 1) {
                                idTemp = 2;
                                Clear_Anim();
                                imageView.animate().translationYBy(-20);
                                c++;
                            } else {
                                imageView.animate().translationYBy(20);
                                c--;
                            }
                            break;
                    }
                } else {

                    switch (v.getId()) {
                        case R.id.imgTest2:
                            if (chkA == false) {
                                int id2 = getResources().getIdentifier(lstUser.get(0), "drawable", getPackageName());
                                imageView.setImageResource(id2);
                                chkA = true;
                            }
                            break;
                        case R.id.imgTest4:
                            if (chkB == false) {
                                int id4 = getResources().getIdentifier(lstUser.get(1), "drawable", getPackageName());
                                imageView.setImageResource(id4);
                                chkB = true;
                            }
                            break;
                        case R.id.imgTest6:
                            if (chkC == false) {
                                int id3 = getResources().getIdentifier(lstUser.get(2), "drawable", getPackageName());
                                imageView.setImageResource(id3);
                                chkC = true;
                            }
                            break;
                    }
                }
            }
        };

        float dip = 20f;
        Resources r = getResources();
        final float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );

        int idImage[] = {R.id.imgTest, R.id.imgTest2, R.id.imgTest3, R.id.imgTest4, R.id.imgTest5, R.id.imgTest6};
        arrImage.add((ImageView) findViewById(idImage[1]));
        arrImage.add((ImageView) findViewById(idImage[3]));
        arrImage.add((ImageView) findViewById(idImage[5]));

        ViewTreeObserver vto = arrImage.get(0).getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] location = new int[2];
                arrImage.get(0).getLocationOnScreen(location);
                X = (int) (location[0] - px);
                Y = (int) (location[1] - px);

                arrImage.get(0).getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        arrImage1.add((ImageView) findViewById(idImage[0]));
        arrImage1.add((ImageView) findViewById(idImage[2]));
        arrImage1.add((ImageView) findViewById(idImage[4]));

        arrImage.get(0).setOnClickListener(imgUserClick);
        arrImage.get(1).setOnClickListener(imgUserClick);
        arrImage.get(2).setOnClickListener(imgUserClick);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_btn();
                btnTest.setClickable(false);
                Open();

                PropertyValuesHolder pvhX_right = PropertyValuesHolder.ofFloat(TRANSLATION_X, X);
                PropertyValuesHolder pvhX_left = PropertyValuesHolder.ofFloat(TRANSLATION_X, -X);
                PropertyValuesHolder pvhY_top = PropertyValuesHolder.ofFloat(TRANSLATION_Y, -Y);
                PropertyValuesHolder pvhY_bottom = PropertyValuesHolder.ofFloat(TRANSLATION_Y, Y);

                ObjectAnimator animator_top_left = ObjectAnimator.ofPropertyValuesHolder(arrImage1.get(0), pvhX_left, pvhY_top);
                ObjectAnimator animator_bottom_left = ObjectAnimator.ofPropertyValuesHolder(arrImage.get(0), pvhX_left, pvhY_bottom);
                ObjectAnimator animator_top = ObjectAnimator.ofPropertyValuesHolder(arrImage1.get(1), pvhY_top);
                ObjectAnimator animator_bottom = ObjectAnimator.ofPropertyValuesHolder(arrImage.get(1), pvhY_bottom);
                ObjectAnimator animator_top_right = ObjectAnimator.ofPropertyValuesHolder(arrImage1.get(2), pvhX_right, pvhY_top);
                ObjectAnimator animator_bottom_right = ObjectAnimator.ofPropertyValuesHolder(arrImage.get(2), pvhX_right, pvhY_bottom);

                animator_top.setDuration(200);
                animator_top_left.setDuration(200);
                animator_top_right.setDuration(200);
                animator_bottom.setDuration(200);
                animator_bottom_left.setDuration(200);
                animator_bottom_right.setDuration(200);

                final ArrayList<ObjectAnimator> arrAnimation = new ArrayList<>();

                arrAnimation.add(animator_top_left);
                arrAnimation.add(animator_bottom_left);
                arrAnimation.add(animator_top);
                arrAnimation.add(animator_bottom);
                arrAnimation.add(animator_top_right);
                arrAnimation.add(animator_bottom_right);
                new CountDownTimer(1400, 200) {
                    int k = 0;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        arrAnimation.get(k).start();
                        k++;
                    }

                    @Override
                    public void onFinish() {
                        RanDom_Image();
                    }
                }.start();
            }
        });
    }

    private void Exit() {
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

    private void Dialog_Infor(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(s);
        builder.show();
    }

    public void open_btn() {
        btnSwap.setClickable(true);
        btnReset.setClickable(true);
        btnResult.setClickable(true);
    }

    public void close_btn() {
        btnSwap.setClickable(false);
        btnReset.setClickable(false);
        btnResult.setClickable(false);
    }

    public void Close() {
        for (int i = 0; i < 3; i++) {
            arrImage.get(i).setClickable(false);
        }
        btnSwap.setClickable(false);
    }

    public void Open() {
        for (int i = 0; i < 3; i++) {
            arrImage.get(i).setClickable(true);
        }
        btnSwap.setClickable(true);
    }

    public int Diem(String s) {
        String arr[] = s.split("_");
        int d = Integer.parseInt(arr[1]);
        return d;
    }

    public int TinhDiem(ArrayList<String> lst) {
        int s = 0;
        int t = 0;
        int d;
        for (int i = 0; i < lst.size(); i++) {
            d = Diem(lst.get(i));
            if (d > 10) {
                t++;
            }
            if (d >= 10)
                d = 0;
            s += d;
        }
        if (t == 3)
            return 10;
        s = s % 10;
        return s;
    }

    public void RanDom_Image() {
        //lstTemp=new ArrayList<>();
        lstTemp = (ArrayList<String>) lstCards.clone();
        lstUser = new ArrayList<>();
        lstMay = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Collections.shuffle(lstTemp);
            lstUser.add(lstTemp.get(0));
            lstTemp.remove(0);
            Collections.shuffle(lstTemp);
            lstMay.add(lstTemp.get(0));
            lstTemp.remove(0);
        }
    }

    public void Clear_Anim() {
        if (a == 2)
            arrImage.get(0).animate().translationYBy(20);
        if (b == 2) {
            arrImage.get(1).animate().translationYBy(20);
        }
        if (c == 2)
            arrImage.get(2).animate().translationYBy(20);
        a = 1;
        b = 1;
        c = 1;
    }

    public void SwapView(ImageView v1, ImageView v2) {

        v1.setImageResource(R.drawable.card_blu);
        float xV1 = v1.getTranslationX();
        float yV1 = Math.abs(v1.getTranslationY());
        float xV2 = v2.getTranslationX();
        float yV2 = Math.abs(v2.getTranslationY());
        float tongY = (yV1 + yV2) * -1;

        if ((xV1 + xV2) == 0) {

            float m = Math.abs(xV1);
            float n = Math.abs(xV2);
            float tong = m + n;
            if (xV1 > 0)
                tong *= -1;
            v1.animate().translationXBy(tong);
            v1.animate().translationYBy(tongY);

            v2.animate().translationXBy(tong * -1);
            v2.animate().translationYBy(tongY * -1 + 20);
        } else if (xV1 == xV2) {
            v1.animate().translationYBy(tongY);
            v2.animate().translationYBy(tongY * -1 + 20);
        } else {
            float tongX = xV1 + xV2;
            if (xV1 < 0 || xV1 > 0)
                tongX *= -1;

            v1.animate().translationXBy(tongX);
            v1.animate().translationYBy(tongY);

            v2.animate().translationXBy(tongX * -1);
            v2.animate().translationYBy(tongY * -1 + 20);
        }
    }

    public void swap(View view) {
        if (a == 1 && b == 1 && c == 1) {
            Toast.makeText(this, "Vui lòng chọn bài để swap!", Toast.LENGTH_SHORT).show();
            return;
        }
        dem++;
        if (dem > 3) {
            Clear_Anim();
            Toast.makeText(this, "Bạn đã hết lượt swap! Vui lòng click 'RESULT'.", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Bạn còn " + (3 - dem) + " lần swap.", Toast.LENGTH_SHORT).show();

        Random rd = new Random();
        int k = rd.nextInt(3);

        SwapView(arrImage.get(idTemp), arrImage1.get(k));
        ImageView temp = arrImage.get(idTemp);
        arrImage.set(idTemp, arrImage1.get(k));
        arrImage1.set(k, temp);

        String nameImg = lstUser.get(idTemp);
        lstUser.set(idTemp, lstMay.get(k));
        lstMay.set(k, nameImg);

        if (idTemp == 0)
            a = 1;
        if (idTemp == 1)
            b = 1;
        if (idTemp == 2)
            c = 1;

        arrImage.get(idTemp).setId(idUser.get(idTemp));
        arrImage1.get(k).setId(idMay.get(k));

        arrImage.get(0).setOnClickListener(imgUserClick);
        arrImage.get(1).setOnClickListener(imgUserClick);
        arrImage.get(2).setOnClickListener(imgUserClick);

        int id = getResources().getIdentifier(lstUser.get(idTemp), "drawable", getPackageName());
        arrImage.get(idTemp).setImageResource(id);
    }

    public void Reset() {
        arrImage1.get(2).animate().translationYBy(Y);
        arrImage1.get(2).animate().translationXBy(-X);
        arrImage.get(2).animate().translationYBy(-Y);
        arrImage.get(2).animate().translationXBy(-X);

        arrImage.get(1).animate().translationYBy(-Y);
        arrImage1.get(1).animate().translationYBy(Y);

        arrImage.get(0).animate().translationYBy(-Y);
        arrImage.get(0).animate().translationXBy(X);
        arrImage1.get(0).animate().translationYBy(Y);
        arrImage1.get(0).animate().translationXBy(X);
    }

    public void Reset_Card(View view) {
        dem = 0;
        btnTest.setClickable(true);
        close_btn();
        chkA = false;
        chkB = false;
        chkC = false;
        for (int i = 0; i < 3; i++) {
            arrImage.get(i).setImageResource(R.drawable.card_blu);
            arrImage1.get(i).setImageResource(R.drawable.card_blu);
        }
        Reset();
        Animation animation = AnimationUtils.loadAnimation(Game9_Activity.this, R.anim.game9_anim1);
        imgWin.startAnimation(animation);
    }
}
