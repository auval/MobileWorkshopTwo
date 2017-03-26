package ac.shenkar.workshoptwo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by amir on 3/26/17.
 */

public class ExampleAppWidgetProvider extends AppWidgetProvider {


    private static final String TAG = ExampleAppWidgetProvider.class.getSimpleName();
    private Context context;
    private int[] appWidgetIds;
    ValueEventListener firebaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.

            BoardMessage value = dataSnapshot.getValue(BoardMessage.class);

            if (value != null) {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                for (int wid : appWidgetIds) {
                    doUpdateWidget(manager, wid, value.getMessage());
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    };

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    /**
     * The first thing that is called on widget install
     * if you have declared a configuration Activity, this method is not called
     * when the user adds the App Widget, but is called for the subsequent updates
     * <p>
     * It is the responsibility of the configuration Activity to perform the first update when configuration is done
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.context = context;
        this.appWidgetIds = appWidgetIds;
        final int N = appWidgetIds.length;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference(SomeFragment.DB_NAME);
        myRef.removeEventListener(firebaseListener);
        // Read from the database
        myRef.addValueEventListener(firebaseListener);

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            doUpdateWidget(appWidgetManager, appWidgetId, "Fetching data...");
        }
    }

    private void doUpdateWidget(AppWidgetManager appWidgetManager, int wid, String text) {
        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget_layout);
        views.setTextViewText(R.id.widget_tv, text);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(wid, views);
    }

    /**
     * called when removed from the launcher
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    /**
     * called on first placement and on resizing
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param newOptions
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

}
