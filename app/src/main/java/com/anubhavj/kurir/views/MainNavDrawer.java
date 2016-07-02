package com.anubhavj.kurir.views;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.activities.BaseActivity;
import com.anubhavj.kurir.activities.ContactsActivity;
import com.anubhavj.kurir.activities.MainActivity;
import com.anubhavj.kurir.activities.ProfileActivity;
import com.anubhavj.kurir.activities.SentMessagesActivity;
import com.anubhavj.kurir.infrastructure.User;

public class MainNavDrawer extends NavDrawer {

    private final TextView displayNameText;
    private final ImageView avatarImage;

    public MainNavDrawer(final BaseActivity activity) {
        super(activity);

        addItem(new ActivityNavDrawerItem(MainActivity.class, "Inbox", null, R.drawable.ic_message, R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(SentMessagesActivity.class,"Sent Messages",null,R.drawable.ic_sent,R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ContactsActivity.class,"Contacts",null,R.drawable.ic_contacts,R.id.include_main_nav_drawer_topItems));
        addItem(new ActivityNavDrawerItem(ProfileActivity.class,"Profile",null,R.drawable.ic_person,R.id.include_main_nav_drawer_topItems));

        addItem(new BasicNavDrawerItem("Logout",null,R.drawable.ic_backspace,R.id.include_main_nav_drawer_bottomItems) {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "You have been logged out!", Toast.LENGTH_SHORT).show();
            }
        });

        displayNameText = (TextView) navDrawerView.findViewById(R.id.include_main_nav_drawer_displayName);
        avatarImage = (ImageView) navDrawerView.findViewById(R.id.include_main_nav_drawer_avatar);

        User loggedInUser = activity.getKurirApplication().getAuth().getUser();
        displayNameText.setText(loggedInUser.getDisplayName());

        // TODO: change avatarImage to avatarUrl from loggedInUser
    }
}
