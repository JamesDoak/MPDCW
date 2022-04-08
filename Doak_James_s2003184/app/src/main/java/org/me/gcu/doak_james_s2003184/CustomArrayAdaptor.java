package org.me.gcu.doak_james_s2003184;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdaptor extends ArrayAdapter<Items> {

    private Context mContext;
    private List<Items> itemsList = new ArrayList<>();

    public CustomArrayAdaptor(@NonNull Context context, ArrayList<Items> list){
        super(context, 0, list);
        mContext = context;
        itemsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_custom_listview,parent,false);

        Items currentItem = itemsList.get(position);


        TextView title = (TextView) listItem.findViewById(R.id.item_title);
        title.setText(currentItem.getTitle());

        TextView description = (TextView) listItem.findViewById(R.id.item_description);
        description.setText(currentItem.getDescription());

        TextView published = (TextView) listItem.findViewById(R.id.item_published);
        published.setText("Published: " + currentItem.getPubDate());

        return listItem;
    }
}
