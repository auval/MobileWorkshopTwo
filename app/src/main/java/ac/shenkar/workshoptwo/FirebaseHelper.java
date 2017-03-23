package ac.shenkar.workshoptwo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by amir on 3/8/17.
 */

class FirebaseHelper {

    private static final String TAG = FirebaseHelper.class.getSimpleName();
    static String lastUid = null;

    /**
     * caching the most up to date user info
     */
    private static CurrentUser currentUser = null;

    public static CurrentUser getCurrentUser() {
        if (currentUser == null) {
            // first time checking
            currentUser = new CurrentUser();
            setupUserAuth();
        }

        return currentUser;
    }

    /**
     * called only the first time the app runs
     */
    private static void setupUserAuth() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // will be called every time the auth state changes:
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    currentUser.setUserData(user);
                } else {
                    currentUser.setUserData(null);
                }

                FirebaseUser userData = currentUser.getUserData();
                if (userData != null) {
                    // now that we have the uid, we can check if the db already have this user
                    wireUsersDb(userData.getUid());
                } else {
                    Log.w(TAG, "got null userData");
                    // if currentUser is not null, it means he's just logged off

                }

                saveInFirebase(currentUser);
                // publish that a change has occurred, and update UI
                EventBus.getDefault().postSticky(currentUser);

            }
        });

        if (user == null) {
            // will trigger the listener
            FirebaseAuth.getInstance().signInAnonymously();
        }
    }

    public static void saveBoardMessageInFirebase(String ref, String s) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(ref);

        // Write a message to the database
        BoardMessage b = new BoardMessage();
        b.setMessage(s);
        myRef.setValue(b);
    }

    public static void saveInFirebase(CurrentUser ref) {
        Log.i(TAG, "saving user");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(CurrentUser.DB_REF);

        FirebaseUser userData = ref.getUserData();
        if (userData != null) {
            myRef.child(userData.getUid()).setValue(ref);
        }
    }

    public static void initFirebase() {
        // enable disk persistence with just one line of code.
        // must be the first call, and not called after other Firebase calls
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    /**
     * generic example
     *
     * @param ref
     * @param callbackType can send any class we want to store
     */
    public static void wireSomeFirebaseTable(final String ref, final Class callbackType) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference(ref);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "onDataChange(" + ref + ") got " + dataSnapshot);

                Object value = dataSnapshot.getValue(callbackType);

                if (value != null) {
                    EventBus.getDefault().post(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private static void wireUsersDb(final String uid) {
        Log.i(TAG, "calling wireUsersDb");

        if (uid.equals(lastUid)) {
            // nothing to do
            Log.i(TAG, "same uid, nothing to do here");
            return;
        }
        // I want to check if it's already in the server
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(CurrentUser.DB_REF).child(uid);

        lastUid = uid;

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial userOnServer and again
                // whenever data at this location is updated.

                Log.d(TAG, "onDataChange(uid) got " + dataSnapshot);

                CurrentUser userOnServer = dataSnapshot.getValue(CurrentUser.class);
                CurrentUser userInClient = getCurrentUser();

                if (userOnServer != null) {
                    // found this user in the DB!
                    // if userInClient has changed, update it
                    FirebaseUser userDataClientSide = userInClient.getUserData();
                    if (userDataClientSide != null) {
                        // user data is excluded from DB
                        if (userDataClientSide.getUid() != uid) {
                            // uid has changed: different user, or user logged in or out
                            Log.d(TAG, "onDataChange(uid) uid changed!");
                            saveInFirebase(userInClient);
                        }
                    }

                    Log.i(TAG, "onDataChange: found user on the server (or it has changed): postSticky user: " + userOnServer.getUserData());
                    EventBus.getDefault().postSticky(userInClient);
                } else {
                    // ==> null userOnServer

                    // If you want to send messages to this application instance or
                    // manage this apps subscriptions on the server side, send the
                    // Instance ID token to your app server.
                    FirebaseHelper.saveInFirebase(userInClient);

                    // wait for callback to call onDataChange again
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    /**
     * helper method in case we need to check client time offset
     */
    public static void getClientOffset() {
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                long estimatedServerTimeMs = (long) (System.currentTimeMillis() + offset);
                Log.i(TAG, "client time offset is " + estimatedServerTimeMs + "ms");
                EventBus.getDefault().post(new ClientTimeOffset(estimatedServerTimeMs));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }


}
