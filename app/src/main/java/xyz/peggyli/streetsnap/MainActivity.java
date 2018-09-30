package xyz.peggyli.streetsnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.login.models.MeData;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView nameTextView = findViewById(R.id.textView);
        final Button unlinkButton = findViewById(R.id.button2);
        final ImageView avatarImageView = findViewById(R.id.imageView);
        if (SnapLogin.isUserLoggedIn(this)) {
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();


            String query = "{me{bitmoji{avatar},displayName}}";
            String variables = null;
            SnapLogin.fetchUserData(this, query, null, new FetchUserDataCallback() {
                @Override
                public void onSuccess(@Nullable UserDataResponse userDataResponse) {
                    if (userDataResponse == null || userDataResponse.getData() == null) {
                        return;
                    }

                    MeData meData = userDataResponse.getData().getMe();
                    if (meData == null) {
                        return;
                    }

                    nameTextView.setText(userDataResponse.getData().getMe().getDisplayName());
                    Toast.makeText(getApplicationContext(), "Display name : " + userDataResponse.getData().getMe().getDisplayName(), Toast.LENGTH_LONG).show();
                    if (meData.getBitmojiData() != null) {
                        Glide.with(getApplicationContext())
                                .load(meData.getBitmojiData().getAvatar())
                                .into(avatarImageView);
                    }
                }

                @Override
                public void onFailure(boolean isNetworkError, int statusCode) {

                }
            });

        }else{
            unlinkButton.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "NOT Logged in", Toast.LENGTH_LONG).show();
            View mLoginButton = SnapLogin.getButton(getApplicationContext(), (ViewGroup)findViewById(R.id.content_view));
        }

        final LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener =
                new LoginStateController.OnLoginStateChangedListener() {
                    @Override
                    public void onLoginSucceeded() {
                        // Here you could update UI to show login success
                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
//                        recreate();
//                        recreate();
                        recreate();
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
