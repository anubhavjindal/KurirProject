package com.anubhavj.kurir.services;

import com.anubhavj.kurir.infrastructure.Auth;
import com.anubhavj.kurir.infrastructure.KurirApplication;
import com.anubhavj.kurir.infrastructure.User;

import com.squareup.otto.Subscribe;

public class InMemoryAccountService extends BaseInMemoryService{

    public InMemoryAccountService(KurirApplication application){
        super(application);
    }

    @Subscribe
    public void updateProfile(final Account.UpdateProfileRequest request){
       final Account.UpdateProfileResponse response = new Account.UpdateProfileResponse();
        if(request.DisplayName.equals("anubhav"))
            response.setPropertyError("displayName", "You may not be named anubhav!");

      invokeDelayed(new Runnable() {
          @Override
          public void run() {
              User user=application.getAuth().getUser();
              user.setDisplayName(request.DisplayName);
              user.setEmail(request.Email);

              bus.post(response);
              bus.post(new Account.UserDetailUpdateEvent(user));

          }
      },2000, 3000);
    }
    @Subscribe
    public void updateAvatar(final Account.ChangeAvatarRequest request){

        invokeDelayed(new Runnable() {
            @Override
            public void run() {
              User user = application.getAuth().getUser();
               user.setAvatarUrl(request.NewAvatarUri.toString());

                bus.post(new Account.ChangeAvatarResponse());
                bus.post(new Account.UserDetailUpdateEvent(user));
            }
        }, 4000, 5000);
    }


    @Subscribe
    public void changePassword(Account.ChangePasswordRequest request){
        Account.ChangePasswordResponse response = new Account.ChangePasswordResponse();

        if(!request.NewPassword.equals(request.ConfirmNewPassword))
            response.setPropertyError("confirmNewPassword","Passwords must match!");

        if(request.NewPassword.length()<3)
            response.setPropertyError("newPassword", "Password must be larger than 3 characters");

        postDelayed(response);
    }

    @Subscribe
    public void loginWithUsername(final Account.LoginWithUsernameRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithUsernameResponse response = new Account.LoginWithUsernameResponse();

                if(request.Username.equals("anubhav"))
                    response.setPropertyError("userName","Invalid Username or Password");

                loginUser(response);
                bus.post(response);
            }
        },1000, 2000);
    }


    @Subscribe
    public void loginWithExternalToken(Account.LoginWithExternalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithExternalTokenResponse response = new Account.LoginWithExternalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        },1000, 2000);
    }

    @Subscribe
    public void register(Account.RegisterRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        },1000,2000);
    }

    @Subscribe
    public void externalRegister(Account.RegisterWithExternalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.RegisterResponse response = new Account.RegisterResponse();
                loginUser(response);
                bus.post(response);
            }
        },1000,2000);
    }

    @Subscribe
    public void loginWithLocalToken(Account.LoginWithLocalTokenRequest request){
        invokeDelayed(new Runnable() {
            @Override
            public void run() {
                Account.LoginWithExternalTokenResponse response=new Account.LoginWithExternalTokenResponse();
                loginUser(response);
                bus.post(response);
            }
        },1000,2000);
    }
    private void loginUser(Account.UserResponse response){
        Auth auth = application.getAuth();
        User user = auth.getUser();

        user.setDisplayName("Anubhav Jindal");
        user.setUserName("AnubhavJindal");
        user.setEmail("anubhavskjindal@gmail.com");
        user.setAvatarUrl("http://www.gravatar.com/avatar/1?d=identicon");
        user.setIsLoggedIn(true);
        user.setId(123);

        bus.post(new Account.UserDetailUpdateEvent(user));


        auth.setAuthToken("fakeauthtoken");
    response.DisplayName = user.getDisplayName();
        response.UserName = user.getUserName();
        response.Email = user.getEmail();
        response.AvatarUrl =user.getAvatarUrl();
        response.Id = user.getId();
        response.AuthToken = auth.getAuthToken();
    }
}


