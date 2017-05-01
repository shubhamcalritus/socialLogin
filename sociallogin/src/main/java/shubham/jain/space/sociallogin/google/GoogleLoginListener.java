package shubham.jain.space.sociallogin.google;

public interface GoogleLoginListener {

  /** on success get these parameter
   * @param authToken -facebook authentication
   * @param userId -userID
   */
  void onGoogleAuthSignIn(String authToken, String userId);

  /**
   *
   * @param errorMessage  if login fail then error log
   */
  void onGoogleAuthSignInFailed(String errorMessage);

  /**
   * notify when facebook logout action perform
   */
  void onGoogleAuthSignOut();

}
