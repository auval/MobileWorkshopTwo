package ac.shenkar.workshoptwo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {
        Fragment fragment;
        switch (event.getFrag()) {
            case 1:
                fragment = getSupportFragmentManager().findFragmentByTag("my_frag_tag");
                if (fragment == null) {
                    SomeFragment frag = SomeFragment.newInstance("Hi", "I'm a fragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, frag, "my_frag_tag").commitNow();
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Fragment #1 already added!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                fragment = getSupportFragmentManager().findFragmentByTag("my_other_frag_tag");
                if (fragment == null) {
                    AuthenticationExampleFragment frag2 = AuthenticationExampleFragment.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, frag2, "my_other_frag_tag").commitNow();
                    Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Auth Fragment already added!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotNewBoardMessage(BoardMessage event) {
        Toast.makeText(this, "got message: " + event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void gotNewFirebaseId(CurrentUser event) {
        Toast.makeText(this, "got Firebase token: " + event.getToken(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseHelper.getCurrentUser();
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

    public void onButton2Clicked(View view) {
        EventBus.getDefault().post(new MyEvent("Added Auth Fragment!", 2));
    }

    public void onButton1Clicked(View view) {
        EventBus.getDefault().post(new MyEvent("Added Fragment #1!", 1));
    }
}
