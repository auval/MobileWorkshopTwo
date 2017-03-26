package ac.shenkar.workshoptwo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.FirebaseDatabase;

/**
 * It's here only to force some inits (such as Firebase setPersistenceEnabled() and Db creation before the first launch)
 * <p>
 * Created by amir on 1/7/17.
 */

public class Bootstrapper extends ContentProvider {
    static {
        // enable disk persistence with just one line of code.
        // must be the first call, and not called after other Firebase calls
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}