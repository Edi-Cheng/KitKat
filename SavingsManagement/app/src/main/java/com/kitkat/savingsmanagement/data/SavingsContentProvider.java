package com.kitkat.savingsmanagement.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import static com.kitkat.savingsmanagement.data.SavingsItemEntry.TABLE_NAME;

public class SavingsContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.kitkat.savingsmanager.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

//    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private SavingsDatabaseHelper mOpenHelper;

    private SQLiteDatabase mDatabase;

    public SavingsContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        mDatabase = mOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;

        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
            rowsDeleted = mDatabase.delete(TABLE_NAME, SavingsItemEntry._ID + "=" + id, null);
        } else {
            rowsDeleted = mDatabase.delete(TABLE_NAME, SavingsItemEntry._ID + "=" + id
                    + " and " + selection, selectionArgs);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        mDatabase = mOpenHelper.getWritableDatabase();

        long newRowId = mDatabase.insert(TABLE_NAME, null, values);
        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        mOpenHelper = new SavingsDatabaseHelper(
                getContext()
        );

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

//        int uriType = sURIMatcher.match(uri);
//        switch (uriType) {
//
//        }

        queryBuilder.appendWhere(SavingsItemEntry._ID + "=" + uri.getLastPathSegment());
        mDatabase = mOpenHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder);

        // make sure that potential listeners are getting notified.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
