package me.susieson.receiptsafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnFragmentInteractionListener,
        ReceiptsFragment.OnFragmentInteractionListener, AnalyticsFragment.OnFragmentInteractionListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    FragmentPagerAdapter mAdapterViewPager;
    String mCurrentPhotoPath = "";
    File mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        mAdapterViewPager = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapterViewPager);
    }

    public static class TabPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public TabPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return OverviewFragment.newInstance();
                case 1:
                    return ReceiptsFragment.newInstance();
                case 2:
                    return AnalyticsFragment.newInstance();
                case 3:
                    return new SettingsFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "Receipts";
                case 2:
                    return "Analytics";
                case 3:
                    return "Settings";
                default:
                    return null;
            }
        }

    }

    @Override
    public String dispatchTakePictureIntent() {
        Log.d("MainActivity", "dispatchTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "me.susieson.receiptsafe.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        if (mCurrentPhotoPath != null && !mCurrentPhotoPath.equals("")) {
            return mImage.getAbsolutePath();
        }
        return "";
    }

    private File createImageFile() throws IOException {
        Log.d("MainActivity", "createImageFile");
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        mImage = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = mImage.getAbsolutePath();
        return mImage;
    }

}
