package xyz.peggyli.streetsnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.login.models.MeData;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

public class LoginActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final TextView nameTextView = findViewById(R.id.textView);
        final Button unlinkButton = findViewById(R.id.button2);
        final ImageView avatarImageView = findViewById(R.id.imageView);
        if (SnapLogin.isUserLoggedIn(this)) {
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();

            Intent randomIntent = new Intent(this, MainActivity.class);

            // Start the new activity.
            startActivity(randomIntent);

        }else{
//            unlinkButton.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "NOT Logged in", Toast.LENGTH_LONG).show();
            View mLoginButton = SnapLogin.getButton(getApplicationContext(), (ViewGroup)findViewById(R.id.content_frame));


            RelativeLayout.LayoutParams testLP = new RelativeLayout.LayoutParams(1000, 300);

            testLP.addRule(RelativeLayout.CENTER_IN_PARENT);


            mLoginButton.setLayoutParams(testLP);
        }

        final LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener =
                new LoginStateController.OnLoginStateChangedListener() {
                    @Override
                    public void onLoginSucceeded() {
                        // Here you could update UI to show login success
                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
                        Intent randomIntent = new Intent(getApplicationContext(), MainActivity.class);

                        // Start the new activity.
                        startActivity(randomIntent);
                    }


                    @Override
                    public void onLoginFailed() {
                        // Here you could update UI to show login failure
                    }

                    @Override
                    public void onLogout() {
                        // Here you could update UI to reflect logged out state
                        Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_LONG).show();
                        recreate();
                    }
                };

// Add the LoginStateChangedListener you’ve defined to receive LoginInState updates
        SnapLogin.getLoginStateController(this).addOnLoginStateChangedListener(mLoginStateChangedListener);

//        View buttonView = findViewById(R.id.button);
//        buttonView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick() {
//                SnapLogin.getAuthTokenManager(getApplicationContext()).startTokenGrant();
//            }
//        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show a toast
     * @param view -- the view that is clicked
     */
    public void showMap(View view){
        // Create an Intent to start the second activity
        Intent randomIntent = new Intent(this, MapsActivity.class);

        // Start the new activity.
        startActivity(randomIntent);
    }

    public void unlink(View view){
        SnapLogin.getAuthTokenManager(this).revokeToken();
        Toast.makeText(getApplicationContext(), "unlinking", Toast.LENGTH_LONG).show();
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);
        recreate();
    }
}
