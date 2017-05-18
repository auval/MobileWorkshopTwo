package ac.shenkar.workshoptwo;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import ac.shenkar.workshoptwo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Dagger 2 field injection example
     * DI: there is generated code that takes care of setting value here like that:
     * instance.sharedPreferences = sharedPreferencesProvider.get();
     * <p>
     * Don't forget to call the Component:
     * InjectorClass.inject(this);
     * in onCreate();
     */
    @Inject
    SharedPreferences sharedPreferences;

    MyData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // data binding - replaced this:
        //   setContentView(R.layout.activity_main);
        // with this >>
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        myData = new MyData("Activity created");
        binding.setMyData(myData);
        // << data binding

        // assign singleton instances to fields
        MyApplication.getComponent(this).inject(this);

        // magically, not null
        Log.i(TAG, "sharedPreferences is " + sharedPreferences);
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
        // data binding observer will take care of updating the text field
        myData.setText(event.getMessage());
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
