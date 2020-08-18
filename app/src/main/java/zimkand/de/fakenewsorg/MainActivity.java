package zimkand.de.fakenewsorg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listView_message);
        new Activity_Datasource(listView).execute();

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Message message = (Message) o;
                String[] bottle = new String[3];
                bottle[0] = message.getCaption();
                bottle[1] = message.getContent();
                bottle[2] = message.getPictureName();
                Intent secAct = new Intent(MainActivity.this, ShowActivity.class);
                secAct.putExtra(MESSAGE, bottle);
                startActivity(secAct);
                /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(message.getContent());
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });
    }
}