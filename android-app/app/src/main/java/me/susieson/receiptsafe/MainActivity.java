package me.susieson.receiptsafe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ReceiptsFragment.OnFragmentInteractionListener,
        AnalyticsFragment.OnFragmentInteractionListener, OverviewFragment.OnFragmentInteractionListener {

    FragmentPagerAdapter mAdapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        mAdapterViewPager = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapterViewPager);
    }

    Uri fileUri;

    @Override
    public void dispatchTakePictureIntent() {
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

}
