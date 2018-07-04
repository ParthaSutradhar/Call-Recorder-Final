package com.xemosoft.call_recorder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.xemosoft.call_recorder.R;
import com.xemosoft.call_recorder.utility.Utility;

public class MainActivity extends AppCompatActivity {

    public static boolean isRunning = false;


    private SwitchCompat toolbar_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem swithItem = menu.findItem(R.id.switch_toolbar_menu);

        View view = swithItem.getActionView();

        toolbar_switch = view.findViewById(R.id.toolbar_switch);

        checkForRecorderState();

        toolbar_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utility.setRecorderState(true, MainActivity.this);
                } else {
                    Utility.setRecorderState(false, MainActivity.this);
                }
            }
        });
        return true;
    }

    private void checkForRecorderState() {
        boolean isOn = Utility.getRecorderState(this);
        if (isOn) {
            toolbar_switch.setChecked(true);
        } else {
            toolbar_switch.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }
}
