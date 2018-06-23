package com.addon.crmdroid;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.addon.crmdroid.models.User;

import static com.addon.crmdroid.utils.AppConstants.PREFERENCE_NAME;

import static com.addon.crmdroid.utils.AppConstants.REQUEST_AUDIO_RECORDING;
import static com.addon.crmdroid.utils.AppConstants.REQUEST_EXTERNAL_STORAGE;
import static com.addon.crmdroid.utils.AppConstants.REQUEST_LOGIN;
import static com.addon.crmdroid.utils.AppConstants.REQUEST_MAKE_A_CALL;
import static com.addon.crmdroid.utils.AppConstants.REQUEST_PHONE_STATE;
import static com.addon.crmdroid.utils.AppConstants.USER_INFO;

public class MainActivity extends AppCompatActivity {

    User _user;
    private boolean hasPhoneCallPermission = false;
    private boolean hasPhoneStatePermission = false;
    private boolean hasRecordPermission = false;
    private boolean hasStoragePermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking All Permissions.
        requestAllPermission();


        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(USER_INFO)) {
            //TODO: Get user info from shared preference.
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        }
    }

    private void requestAllPermission() {
        if (!hasPhoneCallPermission) {
            //CALL PHONE PERMISSION
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_MAKE_A_CALL);
            }
        }
        if (!hasPhoneStatePermission) {
            //PHONE STATE PERMISSION
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_PHONE_STATE);
            }
        }
        if (!hasRecordPermission) {
            //AUDIO RECORD PERMISSION.
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_AUDIO_RECORDING);
            }
        }
        if (!hasStoragePermission) {
            //AUDIO RECORD PERMISSION.
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_AUDIO_RECORDING);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
        switch (requestCode) {
            case REQUEST_MAKE_A_CALL:
                hasPhoneCallPermission = true;
                break;
            case REQUEST_PHONE_STATE:
                hasPhoneStatePermission = true;
                break;
            case REQUEST_AUDIO_RECORDING:
                hasRecordPermission = true;
                break;
            case REQUEST_EXTERNAL_STORAGE:
                hasStoragePermission = true;
                break;
        }
        requestAllPermission();

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
