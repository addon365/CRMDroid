package com.addon.crmdroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.addon.crmdroid.models.User;
import com.addon.crmdroid.utils.http.HttpCaller;
import com.addon.crmdroid.utils.http.HttpRequest;
import com.addon.crmdroid.utils.http.HttpResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.addon.crmdroid.utils.AppConstants.PREFERENCE_NAME;
import static com.addon.crmdroid.utils.AppConstants.USER_INFO;
import static com.addon.crmdroid.utils.AppConstants.getLoginAction;
import static com.addon.crmdroid.utils.AppConstants.getLoginRequest;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_scroll_view)
    ScrollView scrollView;
    @BindView(R.id.input_user_id)
    EditText inputUserId;
    @BindView(R.id.input_password)
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void actionSignIn(View view) {
        String userName = inputUserId.getText().toString();
        String password = inputPassword.getText().toString();
        HttpRequest request = new HttpRequest(getLoginAction());
        request.setRequestBody(getLoginRequest(userName, password));
        new HttpCaller(this, "Validating User") {
            @Override
            public void onResponse(HttpResponse response) {
                super.onResponse(response);
                if (response.getStatus() == HttpResponse.ERROR) {
                    showSnackbar(response.getMesssage());
                } else {
                    UpdateSharedPreference(response.getMesssage());
                    finish();

                }
            }
        }.execute(request);
    }

    private void UpdateSharedPreference(String jsonString) {
        User user = User.fromJson(jsonString);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_INFO, jsonString);
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(scrollView, message, Snackbar.LENGTH_LONG);
    }
}
