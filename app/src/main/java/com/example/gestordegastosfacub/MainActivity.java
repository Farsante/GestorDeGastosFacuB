package com.example.gestordegastosfacub;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;

public class MainActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);

    }

}
