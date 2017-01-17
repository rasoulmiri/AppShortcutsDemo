/*
 * Created by Rasoul Miri on 2017.
 */

package io.rmiri.appshortcutsdemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ShortcutManager shortcutManager;
    private String ID_DYNAMIC_SHORTCUT = "dynamicShortcut";

    @TargetApi(25)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initial shortcut manager
        shortcutManager = getSystemService(ShortcutManager.class);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Toast.makeText(getApplicationContext(), "MainActivity is lunched from " + extras.getString("lunchedFrom"), Toast.LENGTH_SHORT).show();
            }
        }

    }


    @TargetApi(25)
    public void addShortcut(View v) {

        //initial shortcut info
        ShortcutInfo dynamicShortcut = new ShortcutInfo.Builder(this, ID_DYNAMIC_SHORTCUT)
                .setShortLabel("dynamic shortcut")
                .setLongLabel("dynamic shortcut long label")
                .setDisabledMessage("shortcut has disable")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_number_2))
                .setIntent(new Intent(getApplicationContext(), MainActivity.class)
                        .setAction(Intent.ACTION_VIEW)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra("lunchedFrom", ID_DYNAMIC_SHORTCUT))
                .build();

        //add shortcut to shortcut manager
        boolean isAdded = shortcutManager.setDynamicShortcuts(Collections.singletonList(dynamicShortcut));

        Toast.makeText(getApplicationContext(), "shortcut isAdded = " + isAdded, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(25)
    public void removeShortcut(View v) {

        shortcutManager.removeDynamicShortcuts(Collections.singletonList(ID_DYNAMIC_SHORTCUT));

        Toast.makeText(getApplicationContext(), "shortcut removed", Toast.LENGTH_SHORT).show();

        //for remove all shortcut use removeAllDynamicShortcuts
        //shortcutManager.removeAllDynamicShortcuts();
    }

    @TargetApi(25)
    public void disableShortcut(View v) {
        //hide shortcut from list and if a user has pinned your shortcut to home ,shortcut disable
        shortcutManager.disableShortcuts(Collections.singletonList(ID_DYNAMIC_SHORTCUT));
    }


}
