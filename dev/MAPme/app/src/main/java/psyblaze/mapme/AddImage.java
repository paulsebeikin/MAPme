package psyblaze.mapme;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddImage extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int SELECT_PICTURE = 1;

    private String mCurrentPhotoPath;
    private String selectedImagePath;

    private TextView image1;
    private TextView image2;
    private TextView image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        image1 = (TextView) findViewById(R.id.textView1);
        image2 = (TextView) findViewById(R.id.textView2);
        image3 = (TextView) findViewById(R.id.textView3);
    }

    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT); // add resolveActivityCheck
        startActivityForResult(Intent.createChooser(intent, "Select Image to Submit"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                if (image1.getText().toString().equals("IMAGE 1")) {
                    setImageText(image1, selectedImagePath);
                }

                else if (image2.getText().toString().equals("IMAGE 2")) {
                    setImageText(image2, selectedImagePath);
                }

                else if (image3.getText().toString().equals("IMAGE 3"))  {
                    setImageText(image3, selectedImagePath);
                }
                else {
                    Toast.makeText(AddImage.this, "You've already selected 3 images", Toast.LENGTH_LONG)
                            .show();
                }
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                String fileName = mCurrentPhotoPath;

                if (image1.getText().toString().equals("IMAGE 1")) {
                    setImageText(image1, fileName);
                }

                else if (image2.getText().toString().equals("IMAGE 2")) {
                    setImageText(image2, fileName);
                }

                else if (image3.getText().toString().equals("IMAGE 3"))  {
                    setImageText(image3, fileName);
                }
                else {
                    Toast.makeText(AddImage.this, "You've already selected 3 images", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        if( uri == null ) return null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        cursor.close();
        return uri.getPath();
    }

    public String extractFileName (String path) {
        String [] x = path.split("/");
        int lastElement = x.length - 1;
        return x[lastElement];
    }

    public void setImageText(TextView view, String path) {
        view.setText(extractFileName(path));
    }

    public void openPreview (View view) {
        openImage(selectedImagePath);
    }

    public void openImage(String path) {
        try {
            Intent getImageIntent = new Intent();
            getImageIntent.setAction(Intent.ACTION_VIEW);
            getImageIntent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            startActivity(getImageIntent);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AddImage.this, "No Image Selected.", Toast.LENGTH_LONG).show();
        }
    }

    public void openCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MAPme_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void removeImage_1(View view) {
        image1.setText("IMAGE 1");
    }

    public void removeImage_2 (View view) {
        image2.setText("IMAGE 2");
    }

    public void removeImage_3 (View view) {
        image3.setText("IMAGE 3");
    }
} // class
