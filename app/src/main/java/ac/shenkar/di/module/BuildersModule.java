package ac.shenkar.di.module;

/**
 * Created by amir on 5/21/17.
 */

import android.app.Activity;

import ac.shenkar.di.component.FeatureSubComponent;
import ac.shenkar.workshoptwo.MainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * This module contains all the binding to the sub component builders in the app
 */
@Module
public abstract class BuildersModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindFeatureActivityInjectorFactory(FeatureSubComponent.Builder builder);

    // Add another builder binding here
}