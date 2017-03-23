package ac.shenkar.workshoptwo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
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
    private static final String DB_NAME = "auth_board";
    EditText editText;
    TextView usernameTv;
    Button buttonLogin;

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
    public void gotSignEvent(CurrentUser event) {
        Log.i(TAG, "got sticky sign " + event.getUserData() + " event");

//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser currentUser = event.getUserData();

        if (currentUser == null || event.isAnonymous()) {
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

        FirebaseHelper.wireSomeFirebaseTable(DB_NAME, BoardMessage.class);

        View buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper.saveBoardMessageInFirebase(DB_NAME, editText.getText().toString());
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

        Log.i(TAG, "onCreateView: postSticky user");
        EventBus.getDefault().postSticky(FirebaseHelper.getCurrentUser());

        return view;
    }

    private void doSignInOutToggle() {
        if (!FirebaseHelper.getCurrentUser().isAnonymous()) {
            // sign out
            AuthUI.getInstance()
                    .signOut(AuthenticationExampleFragment.this.getActivity());

        } else {

            // open a sign in dialog
            getActivity().startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                            ))
                            .build(),
                    RC_SIGN_IN); // this callback code is not really needed - we're listening to auth changes
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
