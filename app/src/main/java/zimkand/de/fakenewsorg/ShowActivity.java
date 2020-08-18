package zimkand.de.fakenewsorg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView caption = findViewById(R.id.textView_caption);
        TextView content = findViewById(R.id.textView_content);
        ImageView picture = findViewById(R.id.imageView_picture);

        Bundle message = getIntent().getExtras();
        String[] bottle = message.getStringArray(MainActivity.MESSAGE);
        caption.setText(bottle[0]);
        content.setText(bottle[1]);
        new ImageLoader(bottle[2], picture,80,80).execute();
    }
}