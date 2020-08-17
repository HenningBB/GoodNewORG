package zimkand.de.fakenewsorg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class ImageLoader extends AsyncTask<URI,Integer, Bitmap> {
    private  URL url;
    private URLConnection conn;
    private ImageView imageView;

    private int preferredWidth = 80;
    private int preferredHeight = 80;

    public ImageLoader(URL url, ImageView imageView, int scaleWidth, int scaleHeight ) {
        this.url = url;
        this.imageView = imageView;
        this.preferredWidth = scaleWidth;
        this.preferredHeight = scaleHeight;
    }

    public Bitmap doInBackground(URI... params) {

        try {
            openConnection();
            return readResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openConnection() throws IOException{
        conn = url.openConnection();
        conn.setDoOutput(true);
    }

    private Bitmap readResult() throws IOException{
        Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        if( preferredWidth > 0 && preferredHeight > 0 && bitmap.getWidth() > preferredWidth && bitmap.getHeight() > preferredHeight ) {
            return Bitmap.createScaledBitmap(bitmap, preferredWidth, preferredHeight, false);
        } else {
            return bitmap;
        }
    }

    public void onPostExecute( Bitmap drawable ) {
        imageView.setImageBitmap( drawable );
    }
}