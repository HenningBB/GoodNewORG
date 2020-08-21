package zimkand.de.fakenewsorg;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.R.layout.simple_list_item_1;

public class Activity_Datasource extends AsyncTask<String, Void, String> {

    private static final String POST_PARAM_KEYVALUE_SEPARATOR = "=";
    private static final String DESTINATION_METHOD_SHOW = "allEntrys";
    private URLConnection conn;
    private ListView listView;

    public Activity_Datasource(ListView listView){
        this.listView = listView;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            openConnection();
            return readResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openConnection() throws IOException{
        //StringBuffer für das zusammensetzen der URL
        StringBuffer dataBuffer = new StringBuffer();
        dataBuffer.append(URLEncoder.encode("method", "UTF-8"));
        dataBuffer.append(POST_PARAM_KEYVALUE_SEPARATOR);
        dataBuffer.append(URLEncoder.encode(DESTINATION_METHOD_SHOW, "UTF-8"));
        //Adresse der PHP Schnittstelle für die Verbindung zur MySQL Datenbank
        URL url = new URL("http://10.33.11.5/PHPServerEinkaufsliste/News.php");
        conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(dataBuffer.toString());
        wr.flush();
    }

    private String readResult() throws IOException{
        String result = null;
        //Lesen der Rückgabewerte vom Server
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        //Solange Daten bereitstehen werden diese gelesen.
        while ((line=reader.readLine()) != null){
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
       try {
           if (!isBlank(result)) {

               JSONArray jsonArray = new JSONArray(result);

               Message[] messages = new Message[jsonArray.length()];
               List<Message> list = new ArrayList<Message>();
               for (int i = jsonArray.length() - 1; i >= 0; i--) {
                   JSONObject obj = jsonArray.getJSONObject(i);
                   Message message = new Message(obj.getString("Caption"),obj.getString("Content"),obj.getString("PictureAddress"));
                   messages[i] = message;
                   list.add(message);
               }

                CustomListAdapter customListAdapter = new CustomListAdapter(listView.getContext(),list);

               listView.setAdapter(customListAdapter);
           }
       }
       catch (JSONException e) {
            e.printStackTrace();
        }
       catch (Exception e) {
           e.printStackTrace();
       }
    }
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}