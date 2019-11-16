package com.dmoster.benny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PauseMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_menu);
    }


    public void resume(View view) {
        // Create Intent that returns to MainActivity
        Intent resume = new Intent(this, MainActivity.class);
        startActivity(resume);
    }
}
