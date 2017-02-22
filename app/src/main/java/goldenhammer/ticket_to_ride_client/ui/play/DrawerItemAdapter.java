package goldenhammer.ticket_to_ride_client.ui.play;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import goldenhammer.ticket_to_ride_client.R;

/**
 * Created by McKean on 2/22/2017.
 */

public class DrawerItemAdapter extends ArrayAdapter<ObjectDrawerItem> {
    Context mContext;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;

    public DrawerItemAdapter(Context context, int layoutResourceId, ObjectDrawerItem[] data) {
        super(context, layoutResourceId,data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        ImageView imageViewIcon = (ImageView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        ObjectDrawerItem folder = data[position];


        imageViewIcon.setImageResource(folder.getIcon());
        textViewName.setText(folder.getName());

        return listItem;
    }
}
