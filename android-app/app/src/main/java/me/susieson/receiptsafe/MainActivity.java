package me.susieson.receiptsafe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ReceiptsFragment.OnFragmentInteractionListener,
        AnalyticsFragment.OnFragmentInteractionListener, OverviewFragment.OnFragmentInteractionListener {

    private FragmentPagerAdapter mAdapterViewPager;
    private FirebaseInterface mFirebaseInterface;
    private ViewPager mViewPager;
    private User mUser;
    private Uri fileUri;
    private FloatingActionButton mNewReceiptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);

        mFirebaseInterface = FirebaseClient.getClient().create(FirebaseInterface.class);

        Call<User> userCall = mFirebaseInterface.getAllInfo();
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                mUser = response.body();
                update();
                Toast.makeText(MainActivity.this, String.format("Welcome back, %s!", mUser.getUserId()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

            }
        });

        mNewReceiptButton = findViewById(R.id.new_receipt_button);
        mNewReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        Log.d("OverviewFragment", "takePicture");
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".me.susieson.receiptsafe.provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                try {
                    //Encode the image as a Byte64 array for sending to https://vision.infrrdapis.com/ocr/v2/receipt API
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] byteImage = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(byteImage, Base64.DEFAULT);

                    Log.d("MainActivity", "encodedImage " + encodedImage);

                    //Not working effectively atm
                    //processImage(bitmap);
                    sendImageData(encodedImage);
                    update();
                } catch (IOException e) {

                }
            }
        }
    }

    private boolean sendImageData(String encodedImage) {
        try{
            //Create POST request to https://vision.infrrdapis.com/ocr/v2/receipt

        }
        catch(Exception ex){
            Log.e("MainActivity", "Failed to send POST request.");
            return false;
        }


        return true;
    }




/*
    private void processImage(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        //String resultText = firebaseVisionText.getText();
                        for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                            //String blockText = block.getText();
                            //Float blockConfidence = block.getConfidence();
                            //List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                            //Point[] blockCornerPoints = block.getCornerPoints();
                            //Rect blockFrame = block.getBoundingBox();
                            for (FirebaseVisionText.Line line : block.getLines()) {
                                //String lineText = line.getText();
                                Log.d("MainActivity", line.getText());
                                //Float lineConfidence = line.getConfidence();
                                //List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                                //Point[] lineCornerPoints = line.getCornerPoints();
                                //Rect lineFrame = line.getBoundingBox();
                                //for (FirebaseVisionText.Element element: line.getElements()) {
                                //    String elementText = element.getText();
                                //    Float elementConfidence = element.getConfidence();
                                //    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                                //    Point[] elementCornerPoints = element.getCornerPoints();
                                //   Rect elementFrame = element.getBoundingBox();
                                //}
                            }
                        }
                    }
                });
    }
    */

    private void update() {
        mAdapterViewPager = new TabPagerAdapter(getSupportFragmentManager(), mUser);
        mViewPager.setAdapter(mAdapterViewPager);
    }

    public static class TabPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;
        private User mUser;

        public TabPagerAdapter(FragmentManager fragmentManager, User user) {
            super(fragmentManager);
            mUser = user;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return OverviewFragment.newInstance(mUser);
                case 1:
                    return ReceiptsFragment.newInstance(mUser);
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

}
