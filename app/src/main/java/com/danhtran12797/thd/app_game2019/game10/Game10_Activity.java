package com.danhtran12797.thd.app_game2019.game10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game10_Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int SELECT_PHOTO = 1997;
    private static final String SHARED_PREFERENCES_NAME = "game10";
    private final String[] animals = {"3 X 3", "4 X 4", "5 X 5"};

    LinearLayout layout_chim, layout_func;

    Button btnChooseImg, btnChooseNum, btnPlay, btnSeen;

    ArrayList<String> lstNameChim;

    ImageView arrItemImg[][];

    Bitmap bitmap;
    ArrayList<Bitmap> lstBitmap;
    ArrayList<Bitmap> lstBitmap1;
    int nextImg = 7;
    int idMain;
    int number_cell = 2;
    int iC, jC, iM, jM;

    private Chronometer chronometer;
    long pauseOffset;
    int time = 180000;

    Toolbar toolbar_game10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game10_activity);

        toolbar_game10 = findViewById(R.id.toolbar_game10);
        setSupportActionBar(toolbar_game10);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor"))) {
            String infor = "Game xếp hình\n\"hmmm..k biết chơi :(\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.apply();
        }

        //lấy tên image từ sources đưa vào List<String> lstNameChim
        String arrImg[] = getResources().getStringArray(R.array.list);
        lstNameChim = new ArrayList<>(Arrays.asList(arrImg));

        //lấy id Hình ảnh default
        idMain = getResources().getIdentifier(lstNameChim.get(nextImg - 1), "drawable", getPackageName());

        layout_chim = findViewById(R.id.layout_chim);
        layout_func = findViewById(R.id.layout_func);
        Set_Width_Height();

        bitmap = BitmapFactory.decodeResource(getResources(), idMain);
        Create_List_Bitmap1();
        Draw_Layout_Img();
        Add_Img();
        Close_Img();

        btnChooseImg = findViewById(R.id.btnChooseImg);
        btnChooseNum = findViewById(R.id.btnChooseNum);
        btnPlay = findViewById(R.id.btnPlay);
        btnSeen = findViewById(R.id.btnSeen);

        chronometer = findViewById(R.id.kaka);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= time) {
                    Toast.makeText(Game10_Activity.this, "Bạn không hoàn thành thử thách!\nTry hard it...", Toast.LENGTH_SHORT).show();
                    clearChronometer();
                }
            }
        });
        Translate_Time();

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Choose_Img();
                Close_Img();
            }
        });
        btnChooseNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Choose_Num();
                Close_Img();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number_cell == 3)
                    Toast.makeText(Game10_Activity.this, "Bạn có 3' để hoàn thành\nGood luck", Toast.LENGTH_SHORT).show();
                else if (number_cell == 4)
                    Toast.makeText(Game10_Activity.this, "Bạn có 4' để hoàn thành\nGood luck", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Game10_Activity.this, "Bạn có 5' để hoàn thành\nGood luck", Toast.LENGTH_SHORT).show();
                Create_List_Bitmap1();
                lstBitmap1 = (ArrayList<Bitmap>) lstBitmap.clone();

                lstBitmap.set(lstBitmap.size() - 1, BitmapFactory.decodeResource(getResources(), R.drawable.cell_gray_thdgames));

                int id = lstBitmap.get(lstBitmap.size() - 1).getGenerationId();

                Collections.shuffle(lstBitmap);
                for (int i = 0; i < lstBitmap.size(); i++) {
                    if (id == lstBitmap.get(i).getGenerationId()) {
                        iM = i / number_cell;
                        jM = i % number_cell;
                        break;
                    }
                }
                Add_Img();
                Check_Click();
                clearChronometer();
                startChronometer();
            }
        });
        btnSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Seen();
            }
        });
    }

    private void Translate_Time() {
        final ViewTreeObserver observer = layout_func.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        chronometer.animate().translationY(-layout_func.getHeight() / 2).setDuration(1000);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap = BitmapFactory.decodeStream(imageStream);
                    Create_List_Bitmap1();
                    Add_Img();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game10_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_game10_media:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                break;
            case R.id.menu_game10_reload:
                clearChronometer();
                Create_List_Bitmap1();
                Add_Img();
                break;
            case R.id.menu_game10_my_home:
                finish();
                break;
            case R.id.menu_game10_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game10_exit:
                Exit();
                onDestroy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearChronometer();
    }

    public void Dialog_Choose_Img() {
        final Dialog dialog = new Dialog(Game10_Activity.this);
        dialog.setContentView(R.layout.game10_custom_dialog);
        ImageView btnLeft = dialog.findViewById(R.id.btnLeft);
        ImageView btnRight = dialog.findViewById(R.id.btnRight);
        ImageView btnOK = dialog.findViewById(R.id.btnOK);
        final TextView txtNameChim = dialog.findViewById(R.id.txtNameChim);

        final ImageView imgChim = dialog.findViewById(R.id.imgChim);
        imgChim.setImageResource(idMain);

        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) imgChim.getLayoutParams();
        param.width = layout_chim.getWidth() - 30;
        param.height = layout_chim.getWidth() - 30;
        imgChim.setLayoutParams(param);

        //set Width, Height Img màn hình 2


        txtNameChim.setText(lstNameChim.get(nextImg - 1));

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImg--;
                if (nextImg == 0)
                    nextImg = 18;
                idMain = getResources().getIdentifier(lstNameChim.get(nextImg - 1), "drawable", getPackageName());
                imgChim.setImageResource(idMain);
                txtNameChim.setText(lstNameChim.get(nextImg - 1));
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextImg++;
                if (nextImg == 19)
                    nextImg = 1;
                idMain = getResources().getIdentifier(lstNameChim.get(nextImg - 1), "drawable", getPackageName());
                imgChim.setImageResource(idMain);
                txtNameChim.setText(lstNameChim.get(nextImg - 1));
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                bitmap = BitmapFactory.decodeResource(getResources(), idMain);
                Create_List_Bitmap1();
                Add_Img();
            }
        });
        dialog.show();
    }

    public void Dialog_Choose_Num() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a number cell");

        // add a radio button list
        final int[] item_select = new int[1];
        item_select[0] = 3;
        builder.setSingleChoiceItems(animals, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item
                switch (which) {
                    case 0:
                        item_select[0] = 3;
                        break;
                    case 1:
                        item_select[0] = 4;
                        break;
                    case 2:
                        item_select[0] = 5;
                        break;
                }
            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                number_cell = item_select[0];
                if (number_cell == 3)
                    time = 180000;
                else if (number_cell == 4)
                    time = 240000;
                else
                    time = 300000;
                Create_List_Bitmap1();
                Draw_Layout_Img();
                Add_Img();
            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Draw_Layout_Img() {
        layout_chim.removeAllViews();

        arrItemImg = new ImageView[number_cell][number_cell];

        for (int i = 0; i < number_cell; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);
            for (int j = 0; j < number_cell; j++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                ImageView imageView = new ImageView(this);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setBackgroundResource(R.drawable.gme10_custom_item_img);
                imageView.setLayoutParams(params);
                linearLayout.addView(imageView);
                arrItemImg[i][j] = imageView;
                arrItemImg[i][j].setTag(i + "," + j);
                arrItemImg[i][j].setOnClickListener(this);
            }
            layout_chim.addView(linearLayout);
        }
    }

    public void Add_Img() {
        for (int i = 1; i < number_cell + 1; i++) {
            for (int j = 1; j < number_cell + 1; j++) {
                int k = number_cell * (i - 1) + j - 1;
                arrItemImg[i - 1][j - 1].setImageBitmap(lstBitmap.get(k));
            }
        }
    }

    public void Set_Width_Height() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) layout_chim.getLayoutParams();
        param.width = width;
        param.height = width;
        layout_chim.setLayoutParams(param);
    }

    public void Create_List_Bitmap1() {
        lstBitmap = new ArrayList<>();
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getWidth(), true);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int width_item = width / number_cell;
        int height_item = height / number_cell;
        int y = 0;

        for (int i = 0; i < number_cell; i++) {
            Bitmap bmRow = Bitmap.createBitmap(bitmap, 0, y, width, height_item);
            int x = 0;
            for (int j = 0; j < number_cell; j++) {
                Bitmap result = Bitmap.createBitmap(bmRow, x, 0, width_item, height_item);
                x += width_item;
                lstBitmap.add(result);
            }
            y += height_item;
        }
    }

    public void Close_Img() {
        for (int i = 0; i < number_cell * number_cell; i++) {
            arrItemImg[i / number_cell][i % number_cell].setEnabled(false);
        }
    }

    public void Dialog_Seen() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(lstNameChim.get(nextImg - 1));
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        builder.setView(imageView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void Check_Click() {
        Close_Img();
        for (int i = 0; i < number_cell; i++) {
            for (int j = 0; j < number_cell; j++) {
                if ((i == iM - 1) || (i == iM + 1)) {
                    if (j == jM) {
                        arrItemImg[i][j].setEnabled(true);
                    }
                }
                if ((j == jM - 1) || (j == jM + 1)) {
                    if (i == iM) {
                        arrItemImg[i][j].setEnabled(true);
                    }
                }
            }
        }
    }

    public void Get_ij_Cell(ImageView imageView) {
        String s = (String) imageView.getTag();
        String arr[] = s.split(",");
        iC = Integer.parseInt(arr[0]);
        jC = Integer.parseInt(arr[1]);
    }

    public boolean Check_Compled() {
        for (int i = 0; i < lstBitmap.size() - 1; i++) {
            if (lstBitmap.get(i).getGenerationId() != lstBitmap1.get(i).getGenerationId())
                return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        ImageView imageView = (ImageView) v;
        Get_ij_Cell(imageView);
        int n = number_cell * (iC + 1 - 1) + jC + 1 - 1;
        int m = number_cell * (iM + 1 - 1) + jM + 1 - 1;

        imageView.setImageBitmap(lstBitmap.get(m));
        //arrItemImg[iC][jC].setImageBitmap(lstBitmap.get(m));
        arrItemImg[iM][jM].setImageBitmap(lstBitmap.get(n));

        Bitmap temp = lstBitmap.get(n);
        //lstBitmap.set(n,BitmapFactory.decodeResource(getResources(), R.drawable.background_gray1));
        lstBitmap.set(n, lstBitmap.get(m));
        lstBitmap.set(m, temp);

        iM = iC;
        jM = jC;
        Check_Click();
        if (Check_Compled()) {
            Toast.makeText(this, "Chúc mừng bạn hoàn thành trong " + chronometer.getText().toString(), Toast.LENGTH_SHORT).show();
            clearChronometer();
            arrItemImg[number_cell - 1][number_cell - 1].setImageBitmap(lstBitmap1.get(lstBitmap1.size() - 1));
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.game10_anim);
            arrItemImg[number_cell - 1][number_cell - 1].startAnimation(animation);
            //đóng các item Image
            Close_Img();
        }
    }

    public void startChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
    }

    public void clearChronometer() {
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}
