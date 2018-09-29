package xyz.peggyli.streetsnap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
