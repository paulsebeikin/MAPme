package psyblaze.mapme;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;

public class AddImage extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
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
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image to Submit"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Toast.makeText(AddImage.this, selectedImagePath, Toast.LENGTH_LONG).show();
                setImageText(image1, selectedImagePath);
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
        String name = "";
        String [] x = path.split("/");
        for (int i = 0; i <  x.length; i++) {
            if (x[i].contains(".jpg")) name = x[i];
        }
        return name;
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
} // class
