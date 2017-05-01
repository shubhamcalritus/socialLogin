package shubham.jain.space.sociallogin.facebook;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by Shubham Jain on 28/4/17 2:11 PM
 */


public class FacebookLoginHelper {

    private FacebookLoginListener mListener;
    private CallbackManager mCallBackManager;

    /**
     * FacebookLoginHelper is help full to developer where he want to login either in activity or in fragment and deliver the result there
     *
     * @param facebookLoginListener to listen the status of the facebook login status what status is
     *                              get will be delivered to this called listener
     */
    public FacebookLoginHelper(@NonNull FacebookLoginListener facebookLoginListener) {
        mListener = facebookLoginListener;
        mCallBackManager = CallbackManager.Factory.create();
        FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mListener.onFbSignInSuccess(loginResult.getAccessToken().getToken(),
                        loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                mListener.onFbSignInFail("User cancelled operation");
            }

            @Override
            public void onError(FacebookException e) {
                mListener.onFbSignInFail(e.getMessage());
            }
        };
        LoginManager.getInstance().registerCallback(mCallBackManager, mCallBack);
    }

    @NonNull
    @CheckResult

    public CallbackManager getCallbackManager() {
        return mCallBackManager;
    }


    /** to perform the login from your activity
     * @param activity reference of your activity
     */
    public void performSignIn(Activity activity) {
        LoginManager.getInstance()
                .logInWithReadPermissions(activity,
                        Arrays.asList("public_profile", "user_friends", "email"));
    }

    /**
     * to perform the login from your fragment
     * @param fragment reference of your fragment
     */
    public void performSignIn(Fragment fragment) {
        LoginManager.getInstance()
                .logInWithReadPermissions(fragment,
                        Arrays.asList("public_profile", "user_friends", "email"));
    }

    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * when ever user want to preform sign-out he need to call this method
     */
    public void performSignOut() {
        LoginManager.getInstance().logOut();
        mListener.onFBSignOut();
    }
}
