package ac.shenkar.workshoptwo;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * see API Guide:
 * https://developer.android.com/guide/topics/appwidgets/index.html
 */
public class ExampleWidgetConfig extends AppCompatActivity {

    private static final String TAG = ExampleWidgetConfig.class.getSimpleName();
    int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_widget_config);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            mySetResult(RESULT_CANCELED, mAppWidgetId);
        } else {
            mySetResult(RESULT_CANCELED, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

    }

    public void onCancel(View view) {
        // need to tell the system to abort operation
        cleanExit();
    }

    public void onOk(View view) {
        // need to tell the system to add the widget

        // get an instance of the AppWidgetManager by calling getInstance(Context):
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        // Update the App Widget with a RemoteViews layout
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.example_widget_layout);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        // Finally, create the return Intent, set it with the Activity result, and finish the Activity
        mySetResult(RESULT_OK, mAppWidgetId);
        finish();
    }

    private void mySetResult(int result, int dataValue) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "mySetResult: " + result + "/" + dataValue);
        }
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, dataValue);
        setResult(result, resultValue);
        if (getParent() != null) {
            getParent().setResult(result, resultValue);
        }
    }

    private void cleanExit() {
        int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            mySetResult(RESULT_CANCELED, appWidgetId);
        } else {

            if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                mySetResult(RESULT_CANCELED, mAppWidgetId);
            }
        }

        finish();

    }

}
