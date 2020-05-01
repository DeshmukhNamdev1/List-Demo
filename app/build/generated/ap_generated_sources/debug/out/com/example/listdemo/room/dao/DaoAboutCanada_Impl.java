package com.example.listdemo.room.dao;

import android.database.Cursor;
import androidx.paging.DataSource;
import androidx.paging.DataSource.Factory;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.room.util.CursorUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.listdemo.model.Record;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DaoAboutCanada_Impl implements DaoAboutCanada {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Record> __insertionAdapterOfRecord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public DaoAboutCanada_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecord = new EntityInsertionAdapter<Record>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Record` (`id`,`volumeOfMobileData`,`quarter`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Record value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getId());
        }
        if (value.getVolumeOfMobileData() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVolumeOfMobileData());
        }
        if (value.getQuarter() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getQuarter());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE from DataModel";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<Record> brands) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecord.insert(brands);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public DataSource.Factory<Integer, Record> getAll() {
    final String _sql = "SELECT * FROM Record";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new DataSource.Factory<Integer, Record>() {
      @Override
      public LimitOffsetDataSource<Record> create() {
        return new LimitOffsetDataSource<Record>(__db, _statement, false , "Record") {
          @Override
          protected List<Record> convertRows(Cursor cursor) {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
            final int _cursorIndexOfVolumeOfMobileData = CursorUtil.getColumnIndexOrThrow(cursor, "volumeOfMobileData");
            final int _cursorIndexOfQuarter = CursorUtil.getColumnIndexOrThrow(cursor, "quarter");
            final List<Record> _res = new ArrayList<Record>(cursor.getCount());
            while(cursor.moveToNext()) {
              final Record _item;
              _item = new Record();
              final Integer _tmpId;
              if (cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = cursor.getInt(_cursorIndexOfId);
              }
              _item.setId(_tmpId);
              final String _tmpVolumeOfMobileData;
              _tmpVolumeOfMobileData = cursor.getString(_cursorIndexOfVolumeOfMobileData);
              _item.setVolumeOfMobileData(_tmpVolumeOfMobileData);
              final String _tmpQuarter;
              _tmpQuarter = cursor.getString(_cursorIndexOfQuarter);
              _item.setQuarter(_tmpQuarter);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }
}
