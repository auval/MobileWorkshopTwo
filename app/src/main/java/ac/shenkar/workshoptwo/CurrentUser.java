package ac.shenkar.workshoptwo;

import android.support.annotation.Keep;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by amir on 3/19/17.
 */
public class CurrentUser {

    public static final String DB_REF = "users";

    private FirebaseUser userData = null;

    public CurrentUser() {
    }

    public String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    public boolean isAnonymous() {
        if (userData != null) {
            return userData.isAnonymous();
        }
        return true;
    }


    @Keep // the "Keep" annotation is useless without corresponding ProGuard rule to keep it. (todo)
    public String getUserName() {
        if (userData != null) {
            return userData.getDisplayName();
        }
        return "-";
    }

    @Keep
    public String getEmail() {
        if (userData != null) {
            return userData.getEmail();
        }
        return "-";
    }

    @Exclude
    public FirebaseUser getUserData() {
        return userData;
    }

    public void setUserData(FirebaseUser userData) {
        this.userData = userData;
    }


}
