package shubham.jain.space.sociallogin.google;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;

public class GoogleLoginHelper implements GoogleApiClient.OnConnectionFailedListener {
    private final String SCOPES = "oauth2:profile email";
    private final int RC_SIGN_IN = 100;
    private FragmentActivity mContext;
    private GoogleLoginListener mListener;
    private GoogleApiClient mGoogleApiClient;


    /**
     * GoogleLoginHelper is help full to developer where he want to login either in activity or in fragment and deliver the result there
     *
     * @param listener       to listen the status of the facebook login status what status is
     *                       get will be delivered to this called listener
     * @param context        application context
     * @param serverClientId server client Id from G+ login
     */
    public GoogleLoginHelper(@NonNull GoogleLoginListener listener, FragmentActivity context,
                             @Nullable String serverClientId) {
        mContext = context;
        mListener = listener;
        buildGoogleApiClient(buildSignInOptions(serverClientId));
    }

    private GoogleSignInOptions buildSignInOptions(@Nullable String serverClientId) {
        GoogleSignInOptions.Builder gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail();
        if (serverClientId != null) gso.requestIdToken(serverClientId);
        return gso.build();
    }

    private void buildGoogleApiClient(@NonNull GoogleSignInOptions gso) {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).enableAutoManage(mContext, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /** to perform the login from your activity
     * @param activity reference of your activity
     */
    public void performSignIn(Activity activity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    /**
     * to perform the login from your fragment
     * @param fragment reference of your fragment
     */
    public void performSignIn(Fragment fragment) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * just overwrite onActivityResult in your Activity / Fragment to fetch the result
     * if developer will forget to overwrite this method login he will not able to fetch the result
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            final GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        String token = null;
                        try {
                            token =
                                    GoogleAuthUtil.getToken(mContext, result.getSignInAccount().getAccount(), SCOPES);
                        } catch (IOException | GoogleAuthException e) {
                            e.printStackTrace();
                        }
                        return token;
                    }

                    @Override
                    protected void onPostExecute(String token) {
                        GoogleSignInAccount acct = result.getSignInAccount();
                        mListener.onGoogleAuthSignIn(token, acct.getId());
                    }
                };
                task.execute();
            } else {
                mListener.onGoogleAuthSignInFailed(result.getStatus().getStatusMessage());
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mListener.onGoogleAuthSignInFailed(connectionResult.getErrorMessage());
    }

    /**
     * when ever user want to preform sign-out he need to call this method
     */
    public void performSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                mListener.onGoogleAuthSignOut();
            }
        });
    }
}