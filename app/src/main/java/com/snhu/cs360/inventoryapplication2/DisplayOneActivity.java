package com.snhu.cs360.inventoryapplication2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DisplayOneActivity extends AppCompatActivity {
    public static final String EXTRA_ITEM_ID = "itemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_one);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.display_one_fragment_container);

        if (fragment == null) {
            // Use item ID from displayAllFragment to instantiate displayOneFragment
            int bandId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
            fragment = DisplayOneFragment.newInstance(bandId);
            fragmentManager.beginTransaction()
                    .add(R.id.display_one_fragment_container, fragment)
                    .commit();
        }
    }
}
