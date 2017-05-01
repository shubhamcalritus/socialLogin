# socialLogin
**Step 1**
Add it in your root build.gradle at the end of repositories:


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}

**Step 2**
Add the dependency	

	dependencies {
	        compile 'com.github.shubhamcalritus:socialLogin:0.1.0'
	}

**Step3**
after getting the facebook App ID add 
    `<string name="facebook_app_id">XXXXXXXXXXXXXXXXX</string>`
into the string.xml file 

**Step 4**
Add meta and facebook activity into your AndroidManifest.xml

                
        <activity android:name="com.facebook.FacebookActivity"
                     android:configChanges=
                         "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
                     android:label="@string/app_name" />
        
        <meta-data android:name="com.facebook.sdk.ApplicationId"
                     android:value="@string/facebook_app_id"/>

**Step 5**
create the object of FacebookLoginHelper class and pass the reference of FacebookLoginListener into its constructor and overwrite 
        
        facebookLoginHelper = new FacebookLoginHelper(facebookLoginListener);
        FacebookLoginListener facebookLoginListener=new FacebookLoginListener() {
                @Override
                public void onFbSignInFail(String errorMessage) {
                    
                }
        
                @Override
                public void onFbSignInSuccess(String authToken, String userId) {
        
                }
        
                @Override
                public void onFBSignOut() {
        
                }
            };

**Step 6**

overwrite the activities onActivityResult method into your activity 

        @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                facebookLoginHelper.onActivityResult(requestCode, resultCode, data);
            }
            
            
**Step 7**

on button click call the method 

        facebookLoginHelper.performSignIn(this);