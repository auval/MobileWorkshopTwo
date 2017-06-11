package ac.shenkar.workshoptwo;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import ac.shenkar.di.component.DaggerAppComponent;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasDispatchingActivityInjector;

/**
 * Need to add a reference in the manifest to here
 * <p>
 * Created by amir on 3/30/17.
 */
public class MyApplication extends Application implements HasDispatchingActivityInjector {
    /**
     * Injecting a map from class names to providers
     * It doesn't hold a reference to an activity, so no memory leak here
     */
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        DaggerAppComponent.builder().application(this).build().inject(this);

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


}
