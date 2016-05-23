package psyblaze.mapme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Classes.Template;

public class AddImage extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int SELECT_PICTURE = 1;

    private static String mCurrentPhotoPath;
    private static String selectedImagePath;
    private static String[] paths;

    private TextView image1, image2, image3;
    private TableRow row1, row2, row3;

    Template template;
    SharedPreferences settings;
    Gson gson;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        image1 = (TextView) findViewById(R.id.textView1);
        image2 = (TextView) findViewById(R.id.textView2);
        image3 = (TextView) findViewById(R.id.textView3);

        row1 = (TableRow) findViewById(R.id.row1);
        row2 = (TableRow) findViewById(R.id.row2);
        row3 = (TableRow) findViewById(R.id.row3);

        // Shared Preference restore
        settings = getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        String json = settings.getString("template", null);
        if (json != null){
            template = gson.fromJson(json, Template.class);
            if (paths == null) paths = template.images;
            if (paths[0] != null) image1.setText(extractFileName(paths[0]));
            else row1.setVisibility(View.INVISIBLE);
            if (paths[1] != null) image2.setText(extractFileName(paths[1]));
            else row2.setVisibility(View.INVISIBLE);
            if (paths[2] != null) image3.setText(extractFileName(paths[2]));
            else row3.setVisibility(View.INVISIBLE);
        }
        else {
            template = new Template();
        }
    }

    public void openGallery(View view) {
        if (paths[0] != null && paths[1] != null && paths[2] != null){
            Toast.makeText(AddImage.this, "You've already selected 3 images", Toast.LENGTH_LONG).show();}
        else {
            Intent intent = new Intent();
            intent.setType("image/");
            intent.setAction(Intent.ACTION_GET_CONTENT); // add resolveActivityCheck
            startActivityForResult(Intent.createChooser(intent, "Select Image to Submit"), SELECT_PICTURE);
        }
    }

    public void doneClicked(View view) {
        saveTemplate();
        Intent doneInt = new Intent(this, NewRecordActivity.class);
        startActivity(doneInt);
    }

    private void saveTemplate(){
        // get editor ready
        editor = settings.edit();

        // update the template
        template.images = paths;
        String json = gson.toJson(template);
        editor.putString("template", json);
        editor.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                if (paths[0] == null) {
                    setImageText(image1, selectedImagePath);
                    paths[0] = selectedImagePath;
                    row1.setVisibility(View.VISIBLE);
                }

                else if (paths[1] == null) {
                    setImageText(image2, selectedImagePath);
                    paths[1] = selectedImagePath;
                    row2.setVisibility(View.VISIBLE);
                }

                else if (paths[2] == null)  {
                    setImageText(image3, selectedImagePath);
                    paths[2] = selectedImagePath;
                    row3.setVisibility(View.VISIBLE);
                }
            }
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (paths[0] == null) {
                    setImageText(image1, mCurrentPhotoPath);
                    paths[0] = mCurrentPhotoPath;
                    row1.setVisibility(View.VISIBLE);
                }

                else if (paths[1] == null) {
                    setImageText(image2, mCurrentPhotoPath);
                    paths[1] = mCurrentPhotoPath;
                    row2.setVisibility(View.VISIBLE);
                }

                else if (paths[2] == null)  {
                    setImageText(image3, mCurrentPhotoPath);
                    paths[2] = mCurrentPhotoPath;
                    row3.setVisibility(View.VISIBLE);
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
        int x = view.getId();
        switch(x){
            case R.id.textView1:
                selectedImagePath = template.images[0];
                break;
            case R.id.textView2:
                selectedImagePath = template.images[1];
                break;
            case R.id.textView3:
                selectedImagePath = template.images[2];
                break;
            default:
                Toast.makeText(this, "Invalid image selected", Toast.LENGTH_LONG).show();
        }
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
        // check for maximum of 3 images
        if (paths[0] != null && paths[1] != null && paths[2] != null){
            Toast.makeText(AddImage.this, "You've already selected 3 images", Toast.LENGTH_LONG).show();}
        else {
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
        paths[0] = null;
        row1.setVisibility(View.INVISIBLE);
    }

    public void removeImage_2 (View view) {
        paths[1] = null;
        row2.setVisibility(View.INVISIBLE);
    }

    public void removeImage_3 (View view) {
        paths[2] = null;
        row3.setVisibility(View.INVISIBLE);
    }
} // class
