package com.danhtran12797.thd.app_game2019.game11;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static android.graphics.Typeface.defaultFromStyle;

public class Game11_Activity extends BaseActivity {

    private static final String SHARED_PREFERENCES_NAME = "game11";

    LinearLayout layout_main;
    TextView arr[][];
    int x;
    int y;
    Random random = new Random();

    ArrayList<TextView> lstArea, lstAll, lstOrder;
    int S[][];
    boolean check_select = false;

    Toolbar toolbar;

    Spinner spinner;
    ArrayList<String> arrMode;
    ArrayList<Integer> integers;

    int num = 38;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game11_activity);

        toolbar = findViewById(R.id.toolbar_game11);
        setSupportActionBar(toolbar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_sentiment);
        builder.setTitle("Lỗi bị đơ");
        builder.setMessage("SUDOKU đôi lúc mở lên bị đơ máy, bạn thông cảm cho.");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

        final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!(sharedPreferences.contains("infor"))) {
            String infor = "SUDOKU\n\"Lâu lâu bị đơ\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.apply();
        }

        String mode[] = getResources().getStringArray(R.array.MODE);

        spinner = findViewById(R.id.spinner11);
        arrMode = new ArrayList<>(Arrays.asList(mode));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrMode);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
                //((TextView) parent.getChildAt(0)).setTypeface(((TextView) parent.getChildAt(0)).getTypeface(),Typeface.BOLD);
                ((TextView) parent.getChildAt(0)).setTextSize(18);
                switch (position) {
                    case 0:
                        num = 38;
                        break;
                    case 1:
                        num = 35;
                        break;
                    case 2:
                        num = 33;
                        break;
                    case 3:
                        num = 30;
                        break;
                    case 4:
                        num = 27;
                        break;
                    case 5:
                        num = 26;
                        break;
                    case 6:
                        num = 25;
                        break;
                    case 7:
                        num = 24;
                        break;
                    case 8:
                        num = 22;
                        break;
                    case 9:
                        num = 21;
                        break;
                    case 10:
                        num = 20;
                        break;
                }
                Toast.makeText(Game11_Activity.this, "GOOD LUCK", Toast.LENGTH_SHORT).show();
                check_select = false;
                Add_All();
                Tao_Bang_Sudoku(num);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        S = new int[9][9];

        layout_main = findViewById(R.id.layout_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) layout_main.getLayoutParams();
        param.height = width;
        layout_main.setLayoutParams(param);

        int arrId[] = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int id : arrId) {
            View v = findViewById(id);
            v.setOnClickListener(Choose_number);
        }
        Add_All();
        Tao_Bang_Sudoku(num);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game11_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.menu_reload:
                check_select = false;
                Add_All();
                Tao_Bang_Sudoku(num);
                break;
            case R.id.menu_game11_my_home:
                finish();
                break;
            case R.id.menu_game11_infor:
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game11_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener Choose_number = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (check_select == false)
                Toast.makeText(Game11_Activity.this, "Vui lòng chọn ô!", Toast.LENGTH_SHORT).show();
            else {
                Button btn = (Button) v;
                arr[x][y].setTypeface(defaultFromStyle(Typeface.ITALIC));
                arr[x][y].setText(btn.getText().toString());
            }
        }
    };

    public static boolean isSafe(int[][] board,
                                 int row, int col,
                                 int num) {
        // row has the unique (row-clash)
        for (int d = 0; d < board.length; d++) {
            // if the number we are trying to
            // place is already present in
            // that row, return false;
            if (board[row][d] == num) {
                return false;
            }
        }

        // column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++) {
            // if the number we are trying to
            // place is already present in
            // that column, return false;

            if (board[r][col] == num) {
                return false;
            }
        }

        // corresponding square has
        // unique number (box-clash)
        int sqrt = (int) Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++) {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++) {
                if (board[r][d] == num) {
                    return false;
                }
            }
        }

        // if there is no clash, it's safe
        return true;
    }

    public static boolean solveSudoku(int[][] board, int n) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;

                    // we still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // no empty space left
        if (isEmpty) {
            return true;
        }

        // else for each-row backtrack
        for (int num = 1; num <= n; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board, n)) {
                    // print(board, n);
                    return true;
                } else {
                    board[row][col] = 0; // replace it
                }
            }
        }
        return false;
    }

    public void Tao_Bang_int() {
        //S=new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String t = arr[i][j].getText().toString();
                if (t.isEmpty())
                    t = "0";
                int k = Integer.parseInt(t);
                S[i][j] = k;
            }
        }
    }

    public void XuatMang(int A[][]) {
        String s = "\t\t\t";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s += "\t" + A[i][j];
            }
            s = s + "\n\t\t\t";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Máy giải:");
        builder.setMessage(s);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public String Add_lstAll(int x, int y) {
        lstArea = new ArrayList<>();
        ArrayList<TextView> lstRow = new ArrayList<>();
        ArrayList<TextView> lstCol = new ArrayList<>();
        ArrayList<TextView> lstArea = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            lstRow.add(arr[x][i]);
            lstCol.add(arr[i][y]);
        }
        lstRow.remove(y);
        lstCol.remove(x);
        lstArea.addAll(lstRow);
        lstArea.addAll(lstCol);

        int xTemp;
        if (x <= 2 && x >= 0)
            xTemp = 0;
        else if (x <= 5 && x >= 3)
            xTemp = 3;
        else
            xTemp = 6;

        int yTemp;
        if (y <= 2 && y >= 0)
            yTemp = 0;
        else if (y <= 5 && y >= 3)
            yTemp = 3;
        else
            yTemp = 6;

        for (int i = xTemp; i < xTemp + 3; i++) {
            for (int j = yTemp; j < yTemp + 3; j++) {
                for (int k = 0; k < lstArea.size(); k++) {
                    if (!lstArea.contains(arr[i][j]) && i != x && j != y)
                        lstArea.add(arr[i][j]);
                }
            }
        }

        String text = (1 + random.nextInt(9)) + "";
        boolean temp = true;
        while (temp) {
            for (int i = 0; i < lstArea.size(); i++) {
                String d = lstArea.get(i).getText().toString();
                if (text.equals(d)) {
                    text = (1 + random.nextInt(9)) + "";
                    break;
                }
                if (i == lstArea.size() - 1)
                    temp = false;
            }
        }
        return text;
    }

    public void Add_All() {
        layout_main.removeAllViews();
        arr = new TextView[9][9];

        int a = 0;

        for (int i = 0; i < 3; i++) {   //3 dòng to

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);

            int b = 0;
            for (int j = 0; j < 3; j++) {   // 3 cột to trên dòng
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                layout.setBackgroundColor(Color.BLACK);
                layout.setPadding(1, 1, 1, 1);
                layout.setLayoutParams(params);

                for (int m = 0; m < 3; m++) {// 3 dòng trên 1 cột to

                    x = a + m;

                    LinearLayout layout1 = new LinearLayout(this);
                    LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                    layout1.setBackgroundColor(Color.BLACK);
                    layout1.setLayoutParams(layoutParam);

                    for (int n = 0; n < 3; n++) { // 3 text view trên dòng
                        y = b + n;
                        final TextView textView = new TextView(this);
                        textView.setTag(x + "/" + y);
                        textView.setGravity(Gravity.CENTER);
                        textView.setTextColor(Color.BLACK);
                        textView.setTypeface(defaultFromStyle(Typeface.BOLD));
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        layoutParams1.setMargins(1, 1, 1, 1);
                        textView.setLayoutParams(layoutParams1);
                        textView.setBackgroundColor(Color.WHITE);
                        layout1.addView(textView);

                        arr[x][y] = textView;

                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                check_select = true;
                                for (int i = 0; i < 81; i++) {
                                    arr[i / 9][i % 9].setBackgroundColor(Color.WHITE);
                                }

                                String s = (String) textView.getTag();
                                String tag[] = s.split("/");
                                x = Integer.parseInt(tag[0]);
                                y = Integer.parseInt(tag[1]);

                                All_lstAll(x, y);
                                arr[x][y].setBackgroundResource(R.color.colorCell);
                            }
                        });
                    }
                    layout.addView(layout1);

                }
                linearLayout.addView(layout);
                b = b + 3;
            }
            layout_main.addView(linearLayout);

            a = a + 3;
        }
    }

    public void All_lstAll(int x, int y) {
        lstAll = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            arr[x][i].setBackgroundResource(R.color.colorArea);
            arr[i][y].setBackgroundResource(R.color.colorArea);
            lstAll.add(arr[x][i]);
            lstAll.add(arr[i][y]);
        }
        while (lstAll.contains(arr[x][y])) {
            lstAll.remove(arr[x][y]);
        }
        int xTemp;
        if (x <= 2 && x >= 0)
            xTemp = 0;
        else if (x <= 5 && x >= 3)
            xTemp = 3;
        else
            xTemp = 6;

        int yTemp;
        if (y <= 2 && y >= 0)
            yTemp = 0;
        else if (y <= 5 && y >= 3)
            yTemp = 3;
        else
            yTemp = 6;

        for (int i = xTemp; i < xTemp + 3; i++) {
            for (int j = yTemp; j < yTemp + 3; j++) {
                for (int k = 0; k < lstAll.size(); k++) {
                    if (!lstAll.contains(arr[i][j]) && i != x && j != y) {
                        arr[i][j].setBackgroundResource(R.color.colorArea);
                        lstAll.add(arr[i][j]);
                    }
                }
            }
        }
    }

    public void Tao_Bang_Sudoku(int n) {
        integers = new ArrayList<>();
        lstOrder = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            int t = random.nextInt(81);
            while (integers.contains(t)) {
                t = random.nextInt(81);
            }
            integers.add(t);
        }

        for (Integer t : integers) {
            int x = t / 9;
            int y = t % 9;

            arr[x][y].setText(Add_lstAll(x, y));
        }
        Tao_Bang_int();
        solveSudoku(S, 9);
        for (int i = 0; i < 81; i++) {
            arr[i / 9][i % 9].setText("");
        }
        integers.clear();
        for (int i = 0; i < n; i++) {
            int t = random.nextInt(81);
            while (integers.contains(t)) {
                t = random.nextInt(81);
            }
            integers.add(t);
            arr[t / 9][t % 9].setText(S[t / 9][t % 9] + "");
            arr[t / 9][t % 9].setEnabled(false);
        }

        for (int i = 0; i < 81; i++) {
            if (arr[i / 9][i % 9].getText().toString().equals(""))
                lstOrder.add(arr[i / 9][i % 9]);
        }
    }

    public void Add_Area(int x, int y) {
        lstAll = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            lstAll.add(arr[x][i]);
            lstAll.add(arr[i][y]);
        }
        while (lstAll.contains(arr[x][y])) {
            lstAll.remove(arr[x][y]);
        }
        int xTemp;
        if (x <= 2 && x >= 0)
            xTemp = 0;
        else if (x <= 5 && x >= 3)
            xTemp = 3;
        else
            xTemp = 6;

        int yTemp;
        if (y <= 2 && y >= 0)
            yTemp = 0;
        else if (y <= 5 && y >= 3)
            yTemp = 3;
        else
            yTemp = 6;

        for (int i = xTemp; i < xTemp + 3; i++) {
            for (int j = yTemp; j < yTemp + 3; j++) {
                for (int k = 0; k < lstAll.size(); k++) {
                    if (!lstAll.contains(arr[i][j]) && i != x && j != y) {
                        lstAll.add(arr[i][j]);
                    }
                }
            }
        }
    }

    public void OK(View view) {
        for (int i = 0; i < lstOrder.size(); i++) {
            Get_X_Y(lstOrder.get(i));
            Add_Area(x, y);
            for (TextView textView : lstAll) {
                if (textView.getText().toString().equals(lstOrder.get(i).getText().toString())) {
                    lstOrder.get(i).setBackgroundColor(Color.RED);
                    textView.setBackgroundColor(Color.RED);
                    return;
                }
            }
            if (i == lstOrder.size() - 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Chúc mừng bạn đã hoàn thành");
                builder.setMessage("Bạn có muốn chơi tiếp không?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        check_select = false;
                        Add_All();
                        Tao_Bang_Sudoku(num);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
    }

    public void GIAI(View view) {
        XuatMang(S);
    }

    public void Get_X_Y(TextView textView) {
        String s = (String) textView.getTag();
        String tag[] = s.split("/");
        x = Integer.parseInt(tag[0]);
        y = Integer.parseInt(tag[1]);
    }

    public void CHECK(View view) {
        if (check_select == false) {
            Toast.makeText(this, "Vui lòng chọn ô!", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = arr[x][y].getText().toString();
        for (TextView textView : lstAll) {
            if (textView.getText().toString().equals(s))
                textView.setBackgroundResource(R.color.colorError);
        }
    }
}
