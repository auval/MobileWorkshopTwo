package ac.shenkar.di.component;

/**
 * Created by amir on 5/21/17.
 */

import ac.shenkar.di.module.FeatureModule;
import ac.shenkar.workshoptwo.MainActivity;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Feature level component
 */
@Subcomponent(modules = {FeatureModule.class})
public interface FeatureSubComponent extends AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {
    }
}