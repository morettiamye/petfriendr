package com.depaul.se491.petfriendr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MyArrayAdapterClass extends ArrayAdapter<ProfileCard> {

    Context context;

    public MyArrayAdapterClass(Context context, int resourceID, List<ProfileCard> items){ //will call when start populating the cards
        super(context, resourceID, items);
    }
    //will populate the card
    public View getView(int position, View convertView, ViewGroup parent){
        //get card specific to current view on the screen
        ProfileCard card_item = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        //find view by id for each item that we need
        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        name.setText(card_item.getThisName());
        image.setImageResource(R.mipmap.ic_launcher);//random image addded here
        return convertView;

    }
}
