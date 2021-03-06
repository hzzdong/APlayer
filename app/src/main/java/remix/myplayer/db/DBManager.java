package remix.myplayer.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName
 * @Description
 * @Author Xiaoborui
 * @Date 2016/10/18 14:53
 */
public class DBManager {
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private static DBManager mInstance;
    private static DBOpenHelper mOpenHelper;
    private SQLiteDatabase mDateBase;

    public static synchronized void initialInstance(DBOpenHelper helper){
        if(mInstance == null){
            mInstance = new DBManager();
            mOpenHelper = helper;
        }
    }

    public static synchronized DBManager getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException(DBManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return mInstance;
    }

    public synchronized SQLiteDatabase openDataBase(){
        if(mOpenCounter.incrementAndGet() == 1){
            mDateBase = mOpenHelper.getWritableDatabase();
        }
        return mDateBase;
    }

    public synchronized void closeDataBase(){
        if(mOpenCounter.decrementAndGet() == 0 && mDateBase != null){
            mDateBase.close();
        }
    }
}
