package com.danhtran12797.thd.app_game2019.game1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.danhtran12797.thd.app_game2019.BaseActivity;
import com.danhtran12797.thd.app_game2019.MainActivity;
import com.danhtran12797.thd.app_game2019.R;

public class Game1_Activity extends BaseActivity {
    private String SHARED_PREFERENCES_NAME = "game1";

    ImageButton btnNext;
    RadioButton radA, radB, radC;
    android.support.v7.widget.Toolbar toolbar_game1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_activity);
        addControls();
        addEvents();

        toolbar_game1 = findViewById(R.id.toolbar_game1);
        setSupportActionBar(toolbar_game1);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!sharedPreferences.contains("infor")) {
            String infor = "Game đua xe\n\"Siêu tốc độ\"";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("infor", infor);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game1_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_game1_my_home:
                //finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menu_game1_infor:
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                String s = sharedPreferences.getString("infor", "");
                Dialog_Infor(s);
                break;
            case R.id.menu_game1_exit:
                Exit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        btnNext = findViewById(R.id.btnNext);
        radA = findViewById(R.id.radA);
        radC = findViewById(R.id.radC);
        radB = findViewById(R.id.radB);
    }

    private void addEvents() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x;
                if (radA.isChecked())
                    x = 1;
                else if (radB.isChecked())
                    x = 2;
                else
                    x = 3;

                Intent intent = new Intent(Game1_Activity.this, Game1_Activity1.class);
                intent.putExtra("CHOOSE_MAP", x);
                startActivity(intent);
            }
        });
    }

}
