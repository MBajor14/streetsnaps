package xyz.peggyli.streetsnap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SubmitActivity extends AppCompatActivity {
    private static final String IMAGE_DATA = "image_data";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Bitmap imageData = getIntent().getParcelableExtra(IMAGE_DATA);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageBitmap(imageData);
    }
}
