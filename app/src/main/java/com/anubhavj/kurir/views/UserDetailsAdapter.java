package com.anubhavj.kurir.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.activities.BaseActivity;
import com.anubhavj.kurir.services.entities.UserDetails;
import com.squareup.picasso.Picasso;

/**
 * Created by anubhavj on 03/10/16.
 */
public class UserDetailsAdapter extends ArrayAdapter<UserDetails> {

    private LayoutInflater inflater;

    public UserDetailsAdapter(BaseActivity activity)
    {
        super(activity,0);
        inflater = activity.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserDetails details = getItem(position);
        ViewHolder view;

        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.list_item_user_details,parent,false);
            view = new ViewHolder(convertView);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.DisplayName.setText(details.getDisplayName());
        Picasso.with(getContext()).load(details.getAvatarUrl()).into(view.Avatar);

        return convertView;
    }

    private class ViewHolder
    {
        public TextView DisplayName;
        public ImageView Avatar;

        public ViewHolder(View view)
        {
            DisplayName = (TextView)view.findViewById(R.id.list_item_user_details_displayName);
            Avatar = (ImageView) view.findViewById(R.id.list_item_user_details_avatar);
        }
    }
}
