package ac.shenkar.di.module;

import android.content.Context;

import ac.shenkar.di.component.FeatureSubComponent;
import ac.shenkar.workshoptwo.MyApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Application module refers to sub components and provides application level dependencies.
 */
@Module(subcomponents = {FeatureSubComponent.class /* Add additional sub components here */})
public class AppModule {

//    private MyApplication app;

//    public AppModule(MyApplication app) {
//
//        this.app = app;
//    }

    @Provides
    Context provideContext(MyApplication application) {
        return application.getApplicationContext();
    }

//    @Provides
//    MyApplication provideApp() {
//        return app;
//    }

    // Add application level bindings here, e.g.: RestClientApi, Repository, etc.
}
