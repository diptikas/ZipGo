package com.zipgo.diptika.zipgoassignment.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zipgo.diptika.zipgoassignment.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mUserNameText;
    private TextView mUserGenderText;
    private TextView mUserEmailText;
    private TextView mIdText;
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private Long mFacebookId ;
    private String mFullName = null;
    private String mProfileImage = null;
    private String mEmail = null;
    private String mGender = null;
    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        initView();
        fbSignIn();
    }

    private void fbSignIn() {
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                setPreference(loginResult);
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    mFullName = profile.getName();
                    mProfileImage = profile.getProfilePictureUri(400, 400).toString();
                }
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    mEmail = object.getString("email");
                                    mGender = object.getString("gender");
                                    mFullName = object.getString("name");
                                    mFacebookId = object.getLong("id"); //use this for logout
                                    setUserInfo();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        });

                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setPreference(LoginResult loginResult) {
        AccessToken accessToken=loginResult.getAccessToken();
        if(accessToken!=null){
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(getString(R.string.access_token), String.valueOf(accessToken));
            editor.commit();
        }

    }

    private void setUserInfo() {
        mUserNameText.setText(mFullName);
        mIdText.setText(""+mFacebookId);
        mUserEmailText.setText(mEmail);
        mUserGenderText.setText(mGender);
    }

    private void initView() {
        mLoginButton = (LoginButton) findViewById(R.id.login_button);
        mUserNameText = (TextView) findViewById(R.id.u_name);
        mUserEmailText = (TextView) findViewById(R.id.u_email);
        mUserGenderText = (TextView) findViewById(R.id.u_gender);
        mIdText = (TextView) findViewById(R.id.u_id);
        mNextButton = (Button) findViewById(R.id.next_btn);
        mNextButton.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent i) {
        mCallbackManager.onActivityResult(reqCode, resCode, i);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_btn) {
            startActivity(new Intent(LoginActivity.this, RoutesActivity.class));
        }
    }
}
