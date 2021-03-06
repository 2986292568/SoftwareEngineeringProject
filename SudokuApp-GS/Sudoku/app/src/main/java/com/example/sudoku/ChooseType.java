package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class ChooseType extends AppCompatActivity {

    private LastRecord lastRecord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        MaterialButton typeFour = (MaterialButton) findViewById(R.id.typeFour);
        MaterialButton typeNine = (MaterialButton) findViewById(R.id.typeNine);
        typeFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseType.this, ChooseLevel.class);
                intent.putExtra("gridLength", 4);

                SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
                String name = pref.getString("name", "");

                List<LastRecord> records = LitePal.where("user = ?", name).find(LastRecord.class);
                if (!records.isEmpty()) {
                    LastRecord preRecord = records.get(0);
                    LastRecord.LastToHistory(preRecord).save();
                    LitePal.deleteAll(LastRecord.class, "user = ?", name);
                }
                startActivity(intent);
            }
        });

        typeNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseType.this, ChooseLevel.class);
                intent.putExtra("gridLength", 9);
                SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
                String name = pref.getString("name", "");

                List<LastRecord> records = LitePal.where("user = ?", name).find(LastRecord.class);
                if (!records.isEmpty()) {
                    LastRecord preRecord = records.get(0);
                    LastRecord.LastToHistory(preRecord).save();
                    LitePal.deleteAll(LastRecord.class, "user = ?", name);
                }
                startActivity(intent);
            }
        });

        MaterialButton resume = (MaterialButton) findViewById(R.id.resume);

        SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
        String name = pref.getString("name", "");

        List<LastRecord> record = LitePal.where("user = ?", name).find(LastRecord.class);
        if (record.isEmpty()) {
//            resume.setClickable(false);
            resume.setVisibility(View.GONE);
        }

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
                String name = pref.getString("name", "");

                Intent intent = new Intent(ChooseType.this, SudokuNine.class);
                intent.putExtra("resume", true);
                intent.putExtra("gridLength", LitePal.where("user = ?", name).find(LastRecord.class).get(0).getType());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MaterialButton resume = (MaterialButton) findViewById(R.id.resume);
        SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
        String name = pref.getString("name", "");

        List<LastRecord> record = LitePal.where("user = ?", name).find(LastRecord.class);
        if (!record.isEmpty()) {
//            resume.setClickable(false);
            resume.setVisibility(View.VISIBLE);
        }
    }
}
