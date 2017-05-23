package ac.shenkar.di.component;


import ac.shenkar.di.module.AppModule;
import ac.shenkar.di.module.BuildersModule;
import ac.shenkar.di.module.UtilsModule;
import ac.shenkar.workshoptwo.MyApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Dagger 2
 * Singleton example
 * <p>
 * Created by amir on 3/30/17.
 * <p>
 * adapted from Dayan CodePond.org
 */
@Component(modules = {
        /* Use AndroidInjectionModule.class if you're not using support library */
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class,
        UtilsModule.class
})
public interface AppComponent {

    void inject(MyApplication app);
//    void inject(MainActivity app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApplication application);

        AppComponent build();
    }
}