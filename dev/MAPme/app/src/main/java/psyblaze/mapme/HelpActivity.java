package psyblaze.mapme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import Classes.Help;
import Classes.HelpArrayAdapter;

public class HelpActivity extends AppCompatActivity {

    private ListView listView;
    private static final int DISPLAY_HELP = 1;
    HelpArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        listView = (ListView) findViewById(R.id.listView);
        Help [] allHelp = new Help[4];
        allHelp[0] = new Help("HELP HERE FOR NEW RECORDS");
        allHelp[1] = new Help("HELP HERE FOR LOGGING IN AND REGISTERING");
        allHelp[2] = new Help("HELP HERE PROFILE SETTINGS");
        allHelp[3] = new Help("HELP HERE FOR HISTORY");

        arrayAdapter = new HelpArrayAdapter(this, new ArrayList(Arrays.asList(allHelp)));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "clicked on ListItem Number " + position,
                        Toast.LENGTH_LONG)
                        .show();

                Intent detailsIntent = new Intent(getApplicationContext(), HelpDetailsActivity.class);
                detailsIntent.putExtra(Help.class.toString(), arrayAdapter.getHelp(position));
                startActivityForResult(detailsIntent, DISPLAY_HELP);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == DISPLAY_HELP)  {
            Help updated = data.getParcelableExtra(Help.class.toString());
            arrayAdapter.updateContact(updated);
        }

    }
}
