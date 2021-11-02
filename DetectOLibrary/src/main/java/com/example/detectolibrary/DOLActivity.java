package com.example.detectolibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


public class DOLActivity extends AppCompatActivity {

    public static String TEXT_OCR="";
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolactivity);

        getSupportActionBar().hide();
        setStatusBar(this);

        Button button=findViewById(R.id.DOLready);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

        loading=new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Extracting Text...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loading.dismiss();
    }

    public void start(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                loading.show();
                Uri resultUri = Uri.parse(result.getUriFilePath(this,false));//getUri();
                startTextOCR(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public String startTextOCR(Uri imageUri){
        String retStr = "No result";

        Bitmap bitmap= BitmapFactory.decodeFile(imageUri.getPath());

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        InputImage image =InputImage.fromBitmap(bitmap,0);

        if(image!=null) {
            Task<Text> result =
                    recognizer.process(image)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text visionText) {
                                    processTextML(visionText);
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("DEEBUGML",e.getMessage());
                                        }
                                    });
        }
        return retStr;
    }

    public void processTextML(Text result){
        String resultText = result.getText();
        TEXT_OCR=result.getText().trim();
        goIntent();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
    }

    public void goIntent(){
        String result =TEXT_OCR;
        if(result.equals("")){result ="No Text Detected!";}
        Intent intent =new Intent(this, DOLSearchActivity.class);
        if(TEXT_OCR.equals("")){TEXT_OCR="Solution for no Text Detected";}
        intent.putExtra(DOLSearchActivity.URL,TEXT_OCR);
        startActivity(intent);
        loading.dismiss();
        finish();
    }

    private void setStatusBar(Activity activity){
        Window window = activity.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity,R.color.DOLBackground));
    }
}