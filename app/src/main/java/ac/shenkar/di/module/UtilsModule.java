package ac.shenkar.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import ac.shenkar.workshoptwo.MyApplication;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Dagger 2
 * Singleton example
 * <p>
 * Created by amir on 3/30/17.
 */
@Module
public class UtilsModule {


    public UtilsModule() {

    }

    /**
     * Dagger 2 example
     * Dagger will only look for methods annotated with @Provides
     *
     * @param application
     * @return
     */
    @Provides
    @Reusable
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(MyApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }



}