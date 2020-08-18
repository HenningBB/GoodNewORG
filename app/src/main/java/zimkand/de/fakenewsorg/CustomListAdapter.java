package zimkand.de.fakenewsorg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private List<Message> listDataMessages;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context context, List<Message> listDataMessages){
        this.context = context;
        this.listDataMessages = listDataMessages;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listDataMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_layout,null);
            holder = new ViewHolder();
            holder.pictureView=convertView.findViewById(R.id.imageView_picture);
            holder.captionView=convertView.findViewById(R.id.textView_caption);
            holder.contentView=convertView.findViewById(R.id.textView_content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Message message = this.listDataMessages.get(position);
        URL url = null;
        try {
            url = new URL("http://10.33.11.5" + message.getPictureName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.captionView.setText(message.getCaption());
        holder.contentView.setText(message.getContent());
        new ImageLoader(url,holder.pictureView,80,80).execute();


       // new ImageLoader(url,holder.pictureView,10,10);

        return convertView;
    }

    static class ViewHolder{
        ImageView pictureView;
        TextView captionView;
        TextView contentView;
    }
}
