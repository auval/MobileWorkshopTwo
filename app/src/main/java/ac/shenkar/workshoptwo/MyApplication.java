package ac.shenkar.workshoptwo;

import android.app.Application;
import android.content.Context;

import ac.shenkar.di.AppModule;
import ac.shenkar.di.DaggerInjectorComponent;
import ac.shenkar.di.InjectorComponent;
import ac.shenkar.di.UtilsModule;

/**
 * Need to add a reference in the manifest to here
 * <p>
 * Created by amir on 3/30/17.
 */

public class MyApplication extends Application {
    InjectorComponent component;

    public static InjectorComponent getComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * look inside app/build/source/apt/debug/... for the following generated java code
         */
        component = DaggerInjectorComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();
    }

}
