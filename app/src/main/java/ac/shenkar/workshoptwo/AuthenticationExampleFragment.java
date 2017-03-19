package ac.shenkar.workshoptwo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationExampleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationExampleFragment extends Fragment {
    public static final int RC_SIGN_IN = 234;
    private static final String TAG = AuthenticationExampleFragment.class.getSimpleName();
    private static final String DB_NAME = "users";
    EditText editText;
    TextView usernameTv;
    Button buttonLogin;
    private boolean signedIn = false;

    public AuthenticationExampleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthenticationExampleFragment newInstance() {
        AuthenticationExampleFragment fragment = new AuthenticationExampleFragment();
        Bundle args = new Bundle();
        // The arguments supplied here will be retained across fragment destroy and creation
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            signedIn = true;
        } else {
            // not signed in
            signedIn = false;
        }
    }

    /**
     * don't forget to register and unregister in start/stop
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotNewBoardMessage(BoardMessage event) {
        if (editText != null) {
            editText.setText(event.getMessage());
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void gotSignEvent(SignEvent event) {
        Log.i(TAG, "got sticky sign " + event.getData() + " event");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            // logged out
            usernameTv.setText("Please login");
            buttonLogin.setText(R.string.login);
        } else {
            usernameTv.setText("Hello " + currentUser.getDisplayName());
            Log.i(TAG, "user id is " + currentUser.getUid());
            buttonLogin.setText(R.string.logout);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_w_authentication, container, false);

        editText = (EditText) view.findViewById(R.id.editText);

        FirebaseHelper.wireFirebase(DB_NAME, BoardMessage.class);

        View buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper.saveInFirebase(DB_NAME, editText.getText().toString());
            }
        });

        usernameTv = (TextView) view.findViewById(R.id.tv_user);
        buttonLogin = (Button) view.findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignInOutToggle();
            }
        });


        EventBus.getDefault().postSticky(new SignEvent("unknown"));

        return view;
    }

    private void doSignInOutToggle() {
        if (signedIn) {
            // sign out
            AuthUI.getInstance()
                    .signOut(AuthenticationExampleFragment.this.getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "You're now signed out", Toast.LENGTH_SHORT);
                            signedIn = false;
                            EventBus.getDefault().postSticky(new SignEvent("out"));
                        }
                    });

        } else {

            // open a sign in dialog
            getActivity().startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                            ))
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
