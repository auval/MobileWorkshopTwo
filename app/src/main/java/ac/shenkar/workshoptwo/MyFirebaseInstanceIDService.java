package ac.shenkar.workshoptwo;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by amir on 3/19/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Got updated InstanceID token.

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        FirebaseHelper.saveInFirebase(FirebaseHelper.getCurrentUser());
    }


}
