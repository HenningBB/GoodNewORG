package zimkand.de.fakenewsorg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            holder.pictureView=(ImageView)convertView.findViewById(R.id.imageView_picture);
            holder.captionView=(TextView)convertView.findViewById(R.id.textView_caption);
            holder.contentView=convertView.findViewById(R.id.textView_content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        Message message = this.listDataMessages.get(position);
        holder.captionView.setText(message.getCaption());
        holder.contentView.setText(message.getContent());

        return convertView;
    }

    static class ViewHolder{
        ImageView pictureView;
        TextView captionView;
        TextView contentView;
    }
}
