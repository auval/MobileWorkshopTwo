package ac.shenkar.workshoptwo;

import android.util.Log;

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
//    private static final String REF_NAME = "board";

    public static void saveInFirebase(String ref, String s) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(ref);

        // Write a message to the database
        BoardMessage b = new BoardMessage();
        b.setMessage(s);
        myRef.setValue(b);
    }

    public static void initFirebase() {
        // enable disk persistence with just one line of code.
        // must be the first call, and not called after other Firebase calls
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static void wireFirebase(String ref, final Class callbackType) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference(ref);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.d(TAG, "got " + dataSnapshot);

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
