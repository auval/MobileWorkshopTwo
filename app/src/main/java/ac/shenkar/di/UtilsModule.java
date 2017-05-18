package ac.shenkar.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }



}