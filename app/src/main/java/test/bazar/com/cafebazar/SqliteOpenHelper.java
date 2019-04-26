package test.bazar.com.cafebazar;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "cafe_bazar";
    private static final String TBL_NAME = "favorites";
    private static final int VERSION = 1;
    private static final String COL_ID = "id";
    private static final String COL_APP_ID = "application_id";
    private static final String COL_APP_NAME = "app_name";
    private static final String COL_ICON = "icon";
    private static final String COL_KIND = "kind";

    private static final String QUERY = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( " +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_APP_ID + " INTEGER, " +
            COL_APP_NAME + " TEXT, " +
            COL_ICON + " TEXT," +
            COL_KIND + " TEXT); ";


    public SqliteOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addAppToFavorite(int appId, String appName, String icon, String kind) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_APP_ID, appId);
        contentValues.put(COL_APP_NAME, appName);
        contentValues.put(COL_ICON, icon);
        contentValues.put(COL_KIND, kind);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.insert(TBL_NAME, null, contentValues);
        return result;
    }

    //این یه دستوره که سلکت کنه اطلاعات رو از دیتابیس آفلاینمون
    public Cursor getInfo() {
        String query = "SELECT * FROM " + TBL_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //چون سلکت هست از دستور راوکوئری استفتده میکنیم
        return sqLiteDatabase.rawQuery(query, null);
    }
}
