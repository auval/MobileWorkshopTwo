package ac.shenkar.workshoptwo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SomeFragment extends Fragment {
    private static final String TAG = SomeFragment.class.getSimpleName();
    public static final String DB_NAME = "board";
    EditText editText;

    public SomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SomeFragment newInstance(String param1, String param2) {
        SomeFragment fragment = new SomeFragment();
        Bundle args = new Bundle();
        // The arguments supplied here will be retained across fragment destroy and creation
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_some, container, false);

        editText = (EditText) view.findViewById(R.id.editText);

        FirebaseHelper.wireSomeFirebaseTable(DB_NAME, BoardMessage.class);

        View buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper.saveBoardMessageInFirebase(DB_NAME, editText.getText().toString());
            }
        });

        return view;
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
