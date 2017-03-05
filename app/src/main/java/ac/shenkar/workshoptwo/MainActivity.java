package ac.shenkar.workshoptwo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static ac.shenkar.workshoptwo.MyFragmentEvent.SHOW_TOAST;
import static ac.shenkar.workshoptwo.MyFragmentEvent.SOME_COMMAND;
import static ac.shenkar.workshoptwo.MyFragmentEvent.SOME_OTHER_COMMAND;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("my_frag_tag");
        if (fragment == null) {

            SomeFragment frag = SomeFragment.newInstance("Hi", "I'm a fragment");
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_placeholder, frag, "my_frag_tag").commitNow();
            Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fragment already added!", Toast.LENGTH_SHORT).show();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventFromFragment(MyFragmentEvent event) {
        switch (event.getCommand()) {
            case SHOW_TOAST:
                Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
                break;
            case SOME_COMMAND:
            case SOME_OTHER_COMMAND:
            default:

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

    public void onButtonClicked(View view) {
        EventBus.getDefault().post(new MyEvent("Added Fragment!"));
    }
}
