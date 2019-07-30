package com.example.savedata;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String str = " ";
    int count = 0;
    int last = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(str.isEmpty()) {
            str = Calendar.getInstance().getTime().toString() + "\n";
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int frag = 1;
                Fragment fragment = new Frag1();
                switch (menuItem.getItemId()) {
                    case R.id.btn1:
                        fragment = new Frag1();
                        frag = 1;
                        break;
                    case R.id.btn2:
                        fragment = new Frag2();
                        frag = 2;
                        break;
                    case R.id.btn3:
                        fragment = new Frag3();
                        frag = 3;
                        break;
                    case R.id.btn4:
                        fragment = new Frag4();
                        frag = 4;
                        break;
                }

                SharedPreferences sp = getSharedPreferences("all", MODE_PRIVATE);
                int last = sp.getInt("last", -1);
                int count1 = sp.getInt("count1", 0);
                int count2 = sp.getInt("count2", 0);
                int count3 = sp.getInt("count3", 0);
                int count4 = sp.getInt("count4", 0);
                if(frag == last+1 || (frag == 1 && last == 4 )) {
                    switch (frag) {
                        case 1:
                            count1++;
                            break;
                        case 2:
                            count2++;
                            break;
                        case 3:
                            count3++;
                            break;
                        case 4:
                            count4++;
                            break;
                    }
                }

                if(last != -1) {
                    str += "Fragment" + last + " -> " + "Fragment" + frag + "\n";
                } else {
                    str += "No Fragment" + " -> " + "Fragment" + frag + "\n";
                }

                last = frag;

                SharedPreferences.Editor ed = sp.edit();
                ed.putInt("count1", count1);
                ed.putInt("count2", count2);
                ed.putInt("count3", count3);
                ed.putInt("count4", count4);
                ed.putInt("last", last);
                ed.apply();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();

                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

       SharedPreferences sp = getSharedPreferences("all", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.apply();

        try {
            str += "\n\n";
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "saveFile.txt");
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
