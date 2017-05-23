package ac.shenkar.di.module;


import javax.inject.Named;

import ac.shenkar.workshoptwo.FeaturePresenter;
import ac.shenkar.workshoptwo.FeatureView;
import ac.shenkar.workshoptwo.MainActivity;
import ac.shenkar.workshoptwo.MyModelInterface;
import ac.shenkar.workshoptwo.models.MyModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Feature level module holds all the bindings needed for this feature.
 */
@Module
public abstract class FeatureModule {
    @Provides
    @Named("someId")
    static String provideSomeId(MainActivity featureActivity) {
        return featureActivity.getSomeId();
    }

    @Provides
    static FeaturePresenter provideFeaturePresenter(FeatureView view, MyModelInterface model) {
        return new FeaturePresenter(view, model);
    }

    @Provides
    static MyModelInterface provideMyModelInterface() {
        return new MyModel();
    }

    @Binds
    abstract FeatureView provideFeatureView(MainActivity featureActivity);

}