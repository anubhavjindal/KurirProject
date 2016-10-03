package com.anubhavj.kurir.views;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.activities.BaseActivity;
import com.anubhavj.kurir.services.entities.ContactRequest;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by Udgeeth on 03-10-2016.
 */
public class ContactRequestsAdapter extends ArrayAdapter<ContactRequest> {
    private LayoutInflater inflater;


    public ContactRequestsAdapter(BaseActivity activity){
        super(activity, 0); // we won't use inflated layout
        inflater = activity.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // responsible for returning inflated layout
        ContactRequest request = getItem(position);
        ViewHolder view;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_contact_request, parent , false);
            view = new ViewHolder(convertView);
            convertView.setTag(view);

        } else {
            view = (ViewHolder) convertView.getTag();
        }

        view.DisplayName.setText(request.getUser().getDisplayName());
        Picasso.with(getContext()).load(request.getUser().getAvatarUrl()).into(view.Avatar);

        String createdAt = DateUtils.formatDateTime(
                getContext(),
                request.getCreatedAt().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);

        if (request.isFromUs()) {
            view.CreatedAt.setText("Sent at "+createdAt);
        }
        else {
            view.CreatedAt.setText("Received "+ createdAt);

        }
        return convertView;
    }

    private class ViewHolder{
        public TextView DisplayName;
        public TextView CreatedAt;
        public ImageView Avatar;

        public ViewHolder(View view) {
            DisplayName = (TextView) view.findViewById(R.id.list_item_contact_request_displayName);
            CreatedAt = (TextView) view.findViewById(R.id.list_item_contact_request_createdAt);
            Avatar = (ImageView) view.findViewById(R.id.list_item_contact_request_avatar);
            // this line was there - Avatar = (TextView) view.findViewById(R.id.list_item_contact_request_avatar);

        }
    }
}
