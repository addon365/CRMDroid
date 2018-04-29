package com.addon.crmdroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.addon.crmdroid.models.User;

import static com.addon.crmdroid.utils.AppConstants.PREFERENCE_NAME;
import static com.addon.crmdroid.utils.AppConstants.REQUEST_LOGIN;
import static com.addon.crmdroid.utils.AppConstants.USER_INFO;

public class MainActivity extends AppCompatActivity {

    User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(USER_INFO)) {
            //TODO: Get user info from shared preference.
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {

        }
    }

    public void actionFilterLeads(View view) {
        Intent intent = new Intent(this, LeadsActivity.class);
        startActivity(intent);
    }
}
