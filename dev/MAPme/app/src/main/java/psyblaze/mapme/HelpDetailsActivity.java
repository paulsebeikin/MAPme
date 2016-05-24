package psyblaze.mapme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import Classes.Help;

public class HelpDetailsActivity extends AppCompatActivity {

    Help info;
    private TextView helpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_details);

        info = getIntent().getParcelableExtra(Help.class.toString());

        helpView = (TextView) findViewById(R.id.help_desc);
        helpView.setText(info.helpText);

    }
}
