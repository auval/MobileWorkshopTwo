package ac.shenkar.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import ac.shenkar.workshoptwo.MainActivity;
import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger 2
 * Singleton example
 * <p>
 * Created by amir on 3/30/17.
 */
@Singleton
@Component(modules = {AppModule.class, UtilsModule.class})
public interface InjectorComponent {
    void inject(MainActivity activity);
    // add here your other targets
}