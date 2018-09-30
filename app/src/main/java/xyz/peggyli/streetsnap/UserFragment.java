package xyz.peggyli.streetsnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.SupportMapFragment;
import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.login.models.MeData;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        final TextView nameTextView = v.findViewById(R.id.textView);
        final Button unlinkButton = v.findViewById(R.id.button2);
        final ImageView avatarImageView = v.findViewById(R.id.imageView);
        if (SnapLogin.isUserLoggedIn(getContext())) {
            Toast.makeText(getContext(), "Logged in", Toast.LENGTH_LONG).show();

//            Intent randomIntent = new Intent(this, MainActivity.class);
//
//            // Start the new activity.
//            startActivity(randomIntent);
            String query = "{me{bitmoji{avatar},displayName}}";
            String variables = null;
            SnapLogin.fetchUserData(getContext(), query, null, new FetchUserDataCallback() {
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
                    Toast.makeText(getContext(), "Display name : " + userDataResponse.getData().getMe().getDisplayName(), Toast.LENGTH_LONG).show();
                    if (meData.getBitmojiData() != null) {
                        Glide.with(getContext())
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
            Toast.makeText(getContext(), "NOT Logged in", Toast.LENGTH_LONG).show();
            View mLoginButton = SnapLogin.getButton(getContext(), (ViewGroup)v.findViewById(R.id.content_frame));
        }

        final LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener =
                new LoginStateController.OnLoginStateChangedListener() {
                    @Override
                    public void onLoginSucceeded() {
                        // Here you could update UI to show login success
                        Toast.makeText(getContext(), "Logged in", Toast.LENGTH_LONG).show();
//                        recreate();
//                        recreate();
//                        recreate();
                        Intent randomIntent = new Intent(getContext(), MainActivity.class);

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
                        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_LONG).show();
                        Intent randomIntent = new Intent(getContext(), LoginActivity.class);
//
//            // Start the new activity.
                        startActivity(randomIntent);
                    }
                };
        final Button loginButton = (Button) v.findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                unlink(v);
            }
        });

// Add the LoginStateChangedListener you’ve defined to receive LoginInState updates
        SnapLogin.getLoginStateController(getContext()).addOnLoginStateChangedListener(mLoginStateChangedListener);

//        SupportMapFragment userFragment = (SupportMapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.drawer_layout);
//
//        if (userFragment == null) {
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            userFragment = SupportMapFragment.newInstance();
//            ft.replace(R.id.user_layout, userFragment).commit();
//        }

//        userFragment.getMapAsync(this);
        return v;
    }

    public void unlink(View view){
        SnapLogin.getAuthTokenManager(getContext()).revokeToken();
        Toast.makeText(getContext(), "unlinking", Toast.LENGTH_LONG).show();
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);
        Intent randomIntent = new Intent(getContext(), LoginActivity.class);
//
//            // Start the new activity.
        startActivity(randomIntent);

    }

}