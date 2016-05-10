package psytalix.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void sendMessage(View view){
        Intent send = new Intent(Intent.ACTION_VIEW);
        EditText txt = (EditText) findViewById(R.id.edit_message);
        EditText msg = (EditText) findViewById(R.id.msg_body);
        send.setData(Uri.parse("sms:" + txt.getText().toString()));
        send.putExtra("msg", msg.getText().toString());
        if (send.resolveActivity(getPackageManager()) != null) startActivity(send);
    }
}
