package com.snhu.cs360.inventoryapplication2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;

//not implemented yet.
// TODO: Properly implement the preference class to allow for a more professional settings menu

public class SettingsActivity extends AppCompatActivity {
    private boolean notifStatus;
    private SharedPreferences sharedPrefs;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //get notification status
//        notifStatus = notifStatus.getBoolean(SettingsFragment.sharedPrefs, false);

            // Display the fragment as the main content
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment())
                    .commit();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }
}