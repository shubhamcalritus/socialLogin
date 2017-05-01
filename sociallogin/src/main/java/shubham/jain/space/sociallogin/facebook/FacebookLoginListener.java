package shubham.jain.space.sociallogin.facebook;

/**
 * Created by Shubham Jain on 28/4/17 4:11 PM
 */


public interface FacebookLoginListener {


    /**
     *
     * @param errorMessage  if login fail then error log
     */
    void onFbSignInFail(String errorMessage);

    /** on success get these parameter
     * @param authToken -facebook authentication
     * @param userId -userID
     */
    void onFbSignInSuccess(String authToken, String userId);

    /**
     * notify when facebook logout action perform
     */
    void onFBSignOut();
}
