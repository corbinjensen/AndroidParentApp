package ca.sfu.fluorine.parentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 *  ChildFormActivity.java - represents a user input form
 *  activity to add a new, or modify info on a child.
 */

public class ChildFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_form);
    }
}
