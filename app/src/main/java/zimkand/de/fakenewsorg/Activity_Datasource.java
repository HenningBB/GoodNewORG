package zimkand.de.fakenewsorg;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private ArrayList<Message> messageList;
    private CustomListAdapter adapter;

    public Activity_Datasource(ListView listView){
        this.listView = listView;
        messageList = new ArrayList<Message>();
        adapter = new CustomListAdapter(listView.getContext(),messageList);
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
       if(!isBlank(result)){
           // String[] arr = result.split("\"");
           String[] arr = result.split(",");
           for (int i = 0; i < arr.length-2; i+=3) {
               Message message = new Message(arr[i],arr[i+1],arr[i+2]);
               messageList.add(message);
           }
            listView.setAdapter(adapter);
       }
    }
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

