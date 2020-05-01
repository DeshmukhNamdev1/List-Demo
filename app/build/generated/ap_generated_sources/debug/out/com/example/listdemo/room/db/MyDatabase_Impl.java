package com.example.listdemo.room.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.listdemo.room.dao.DaoAboutCanada;
import com.example.listdemo.room.dao.DaoAboutCanada_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDatabase_Impl extends MyDatabase {
  private volatile DaoAboutCanada _daoAboutCanada;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `DataModel` (`albumId` INTEGER NOT NULL, `id` INTEGER, `title` TEXT, `url` TEXT, `thumbnailUrl` TEXT, PRIMARY KEY(`albumId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Record` (`id` INTEGER NOT NULL, `volumeOfMobileData` TEXT, `quarter` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b6d9b194ca65eb7a29d49451ea4dac7b')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `DataModel`");
        _db.execSQL("DROP TABLE IF EXISTS `Record`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsDataModel = new HashMap<String, TableInfo.Column>(5);
        _columnsDataModel.put("albumId", new TableInfo.Column("albumId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDataModel.put("id", new TableInfo.Column("id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDataModel.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDataModel.put("url", new TableInfo.Column("url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDataModel.put("thumbnailUrl", new TableInfo.Column("thumbnailUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDataModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDataModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDataModel = new TableInfo("DataModel", _columnsDataModel, _foreignKeysDataModel, _indicesDataModel);
        final TableInfo _existingDataModel = TableInfo.read(_db, "DataModel");
        if (! _infoDataModel.equals(_existingDataModel)) {
          return new RoomOpenHelper.ValidationResult(false, "DataModel(com.example.listdemo.model.DataModel).\n"
                  + " Expected:\n" + _infoDataModel + "\n"
                  + " Found:\n" + _existingDataModel);
        }
        final HashMap<String, TableInfo.Column> _columnsRecord = new HashMap<String, TableInfo.Column>(3);
        _columnsRecord.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecord.put("volumeOfMobileData", new TableInfo.Column("volumeOfMobileData", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecord.put("quarter", new TableInfo.Column("quarter", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecord = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecord = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecord = new TableInfo("Record", _columnsRecord, _foreignKeysRecord, _indicesRecord);
        final TableInfo _existingRecord = TableInfo.read(_db, "Record");
        if (! _infoRecord.equals(_existingRecord)) {
          return new RoomOpenHelper.ValidationResult(false, "Record(com.example.listdemo.model.Record).\n"
                  + " Expected:\n" + _infoRecord + "\n"
                  + " Found:\n" + _existingRecord);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b6d9b194ca65eb7a29d49451ea4dac7b", "85ee177951a71b1ee9365f9c7f60da41");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "DataModel","Record");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `DataModel`");
      _db.execSQL("DELETE FROM `Record`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public DaoAboutCanada daoAboutCanada() {
    if (_daoAboutCanada != null) {
      return _daoAboutCanada;
    } else {
      synchronized(this) {
        if(_daoAboutCanada == null) {
          _daoAboutCanada = new DaoAboutCanada_Impl(this);
        }
        return _daoAboutCanada;
      }
    }
  }
}
