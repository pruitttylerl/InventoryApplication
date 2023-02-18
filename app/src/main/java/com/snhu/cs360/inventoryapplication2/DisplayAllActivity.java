package com.snhu.cs360.inventoryapplication2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

    public class DisplayAllActivity extends AppCompatActivity implements DisplayAllFragment.OnItemSelectedListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_all);

            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.display_all_fragment_container);

            if (fragment == null) {
                fragment = new DisplayAllFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.display_all_fragment_container, fragment)
                        .commit();
            }
        }

        @Override
        public void onItemSelected(int itemId) {
            // Send the Item ID of the clicked button to DisplayOneActivity
            Intent intent = new Intent(this, DisplayOneActivity.class);
            intent.putExtra(DisplayOneActivity.EXTRA_ITEM_ID, itemId);
            startActivity(intent);
        }
    }
