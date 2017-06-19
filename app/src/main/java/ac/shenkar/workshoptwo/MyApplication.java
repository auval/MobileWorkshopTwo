package ac.shenkar.workshoptwo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import ac.shenkar.di.component.DaggerAppComponent;
//import ac.shenkar.di.component.InjectorComponent;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * <p>
 * Created by amir on 3/30/17.
 */
public class MyApplication extends Application implements HasActivityInjector {
    /**
     * Injecting a map from class names to providers
     * It doesn't hold a reference to an activity, so no memory leak here
     *
     * Check out this tutorial for this Dagger pattern:
     * https://android.jlelse.eu/android-and-dagger-2-10-androidinjector-5e9c523679a3
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
