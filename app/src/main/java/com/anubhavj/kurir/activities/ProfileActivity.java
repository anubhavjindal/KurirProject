package com.anubhavj.kurir.activities;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anubhavj.kurir.R;
import com.anubhavj.kurir.dialogs.ChangePasswordDialog;
import com.anubhavj.kurir.infrastructure.User;
import com.anubhavj.kurir.services.Account;
import com.anubhavj.kurir.views.MainNavDrawer;
import com.soundcloud.android.crop.Crop;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    private static final int REQUEST_SELECT_IMAGE = 100;

    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;

    private static final String BUNDLE_STATE = "BUNDLE_STATE";

    private static boolean isProgressBarVisible;

    private int currentState;
    private EditText displayNameText;
    private EditText emailText;
    private View changeAvatarButton;
    private ActionMode editProfileActionMode;
    private ImageView avatarView;
    private View avatarProgressFrame;
    private File tempOutputFile;
    private Dialog progressDialog;

    @Override
    protected void onKurirCreate(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        setNavDrawer(new MainNavDrawer(this));

        if(!isTablet) {
            View textFields = findViewById(R.id.activity_profile_textFields);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textFields.getLayoutParams();
            params.setMargins(0,params.getMarginStart(),0,0);
            params.removeRule(RelativeLayout.END_OF);
            params.addRule(RelativeLayout.BELOW, R.id.activity_profile_changeAvatar);
            textFields.setLayoutParams(params);
        }

        avatarView = (ImageView) findViewById(R.id.activity_profile_avatar);
        avatarProgressFrame = findViewById(R.id.activity_profile_avatarProgressFrame);
        changeAvatarButton = findViewById(R.id.activity_profile_changeAvatar);
        displayNameText = (EditText) findViewById(R.id.activity_profile_displayName);
        emailText = (EditText) findViewById(R.id.activity_profile_email);
        tempOutputFile = new File(getExternalCacheDir(),"temp-image.jpg");

        avatarView.setOnClickListener(this);

        //noinspection ConstantConditions
        changeAvatarButton.setOnClickListener(this);
        avatarProgressFrame.setVisibility(View.GONE);

        User user  = application.getAuth().getUser();
        getSupportActionBar().setTitle(user.getDisplayName());
        if(savedState == null) {
            displayNameText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            changeState(STATE_VIEWING);
        }
        else
            changeState(savedState.getInt(BUNDLE_STATE));

        if (isProgressBarVisible)
            setProgressBarVisible(true);

    }

    @Subscribe
    public void onUserDetailsUpdated(Account.UserDetailUpdateEvent event){
        getSupportActionBar().setTitle(event.User.getDisplayName());
    }

    @Override
    protected void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putInt(BUNDLE_STATE,currentState);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if(viewId == R.id.activity_profile_changeAvatar || viewId == R.id.activity_profile_avatar) {
            changeAvatar();
        }
    }

    private void changeAvatar() {
        List<Intent> otherImageCaptureIntents = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities = getPackageManager()
                .queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),0);

        for(ResolveInfo info : otherImageCaptureActivities) {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName,info.activityInfo.name);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));
            otherImageCaptureIntents.add(captureIntent);
        }

        Intent selectedImageIntent = new Intent(Intent.ACTION_PICK);
        selectedImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectedImageIntent,"Choose Avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,otherImageCaptureIntents.toArray(new Parcelable[otherImageCaptureActivities.size()]));

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) {
            tempOutputFile.delete();
            return;
        }
        if(requestCode == REQUEST_SELECT_IMAGE) {
            Uri outputFile;
            Uri tempFileUri = Uri.fromFile(tempOutputFile);

            if (data != null && (data.getAction() == null || !data.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE)))
                outputFile = data.getData();
            else
                outputFile = tempFileUri;

            new Crop(outputFile)
                    .asSquare()
                    .output(tempFileUri)
                    .start(this);
        }
        else if(requestCode == Crop.REQUEST_CROP) {
            avatarProgressFrame.setVisibility(View.VISIBLE);
            bus.post(new Account.ChangeAvatarRequest(Uri.fromFile(tempOutputFile)));
        }
    }

    @Subscribe
    public void onAvatarUpdated(Account.ChangeAvatarResponse response){
        avatarProgressFrame.setVisibility(View.GONE);

        if(!response.didSucceed())
            response.showErrorToast(this);
    }

    @Subscribe
    public void onProfileUpdated(Account.UpdateProfileResponse response){

        if(!response.didSucceed()){
            response.showErrorToast(this);
            changeState(STATE_EDITING);
        }
        displayNameText.setError(response.getPropertyError("displayName"));
        emailText.setError(response.getPropertyError("email"));
        setProgressBarVisible(false);
    }

    private void setProgressBarVisible(boolean visible) {
        if(visible) {
            progressDialog = new ProgressDialog.Builder(this)
                    .setTitle("Updating Profile")
                    .setCancelable(false)
                    .show();
        } else if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        isProgressBarVisible = visible;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.activity_profile_menuEdit){
            changeState(STATE_EDITING);
            return true;
        }
        else if(itemId == R.id.activity_profile_menuChangePassword){
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null);

            ChangePasswordDialog dialog = new ChangePasswordDialog();
            dialog.show(transaction,null);
            return true;
        }

        return false;
    }

    private void changeState(int state){
        if(state == currentState)
            return;

        currentState = state;

        if(state == STATE_VIEWING){
            displayNameText.setEnabled(false);
            emailText.setEnabled(false);
            changeAvatarButton.setVisibility(View.VISIBLE);

            if(editProfileActionMode != null){
                editProfileActionMode.finish();
                editProfileActionMode = null;
            }
        }
        else if (state == STATE_EDITING){
            displayNameText.setEnabled(true);
            emailText.setEnabled(true);
            changeAvatarButton.setVisibility(View.GONE);

            editProfileActionMode = toolbar.startActionMode(new EditProfileActionCallback());
        }
        else
        {
            throw new IllegalArgumentException("Invalid State: " + state);
        }
    }
    private class EditProfileActionCallback implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.activity_profile_edit,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();

            if(itemId == R.id.activity_profile_edit_menuDone){
                setProgressBarVisible(true);
                changeState(STATE_VIEWING);

                bus.post(new Account.UpdateProfileRequest(
                        displayNameText.getText().toString(),
                        emailText.getText().toString()));

                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(currentState != STATE_VIEWING)
                changeState(STATE_VIEWING);
        }
    }
}
