package com.example.packyourbag.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.packyourbag.Model.Items;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ItemsDao_Impl implements ItemsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Items> __insertionAdapterOfItems;

  private final EntityDeletionOrUpdateAdapter<Items> __deletionAdapterOfItems;

  private final SharedSQLiteStatement __preparedStmtOfCheckUnCheck;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllSystemItems;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByCategory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByCategoryAndAddedBy;

  public ItemsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItems = new EntityInsertionAdapter<Items>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `items` (`ID`,`itemname`,`category`,`addedby`,`checked`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Items value) {
        stmt.bindLong(1, value.getID());
        if (value.getItemname() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getItemname());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
        if (value.getAddedby() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAddedby());
        }
        final int _tmp = value.isChecked() ? 1 : 0;
        stmt.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfItems = new EntityDeletionOrUpdateAdapter<Items>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `items` WHERE `ID` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Items value) {
        stmt.bindLong(1, value.getID());
      }
    };
    this.__preparedStmtOfCheckUnCheck = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "update items set checked=? where ID=?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllSystemItems = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from items where addedby=?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllByCategory = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from items where category=?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllByCategoryAndAddedBy = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from items where category=? and addedby=?";
        return _query;
      }
    };
  }

  @Override
  public void saveItems(final Items items) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfItems.insert(items);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Items items) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfItems.handle(items);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void checkUnCheck(final int id, final boolean checked) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfCheckUnCheck.acquire();
    int _argIndex = 1;
    final int _tmp = checked ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfCheckUnCheck.release(_stmt);
    }
  }

  @Override
  public void deleteAllSystemItems(final String addedBy) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllSystemItems.acquire();
    int _argIndex = 1;
    if (addedBy == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, addedBy);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllSystemItems.release(_stmt);
    }
  }

  @Override
  public int deleteAllByCategory(final String category) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllByCategory.acquire();
    int _argIndex = 1;
    if (category == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, category);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllByCategory.release(_stmt);
    }
  }

  @Override
  public int deleteAllByCategoryAndAddedBy(final String category, final String addedBy) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllByCategoryAndAddedBy.acquire();
    int _argIndex = 1;
    if (category == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, category);
    }
    _argIndex = 2;
    if (addedBy == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, addedBy);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllByCategoryAndAddedBy.release(_stmt);
    }
  }

  @Override
  public List<Items> getAll(final String category) {
    final String _sql = "select * from items where category=? order by id asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfItemname = CursorUtil.getColumnIndexOrThrow(_cursor, "itemname");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfAddedby = CursorUtil.getColumnIndexOrThrow(_cursor, "addedby");
      final int _cursorIndexOfChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "checked");
      final List<Items> _result = new ArrayList<Items>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Items _item;
        _item = new Items();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpItemname;
        if (_cursor.isNull(_cursorIndexOfItemname)) {
          _tmpItemname = null;
        } else {
          _tmpItemname = _cursor.getString(_cursorIndexOfItemname);
        }
        _item.setItemname(_tmpItemname);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpAddedby;
        if (_cursor.isNull(_cursorIndexOfAddedby)) {
          _tmpAddedby = null;
        } else {
          _tmpAddedby = _cursor.getString(_cursorIndexOfAddedby);
        }
        _item.setAddedby(_tmpAddedby);
        final boolean _tmpChecked;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfChecked);
        _tmpChecked = _tmp != 0;
        _item.setChecked(_tmpChecked);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getItemCount() {
    final String _sql = "select count(*) from items";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Items> getAllSelected(final boolean checked) {
    final String _sql = "select * from items where checked=? order by id asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = checked ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfID = CursorUtil.getColumnIndexOrThrow(_cursor, "ID");
      final int _cursorIndexOfItemname = CursorUtil.getColumnIndexOrThrow(_cursor, "itemname");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfAddedby = CursorUtil.getColumnIndexOrThrow(_cursor, "addedby");
      final int _cursorIndexOfChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "checked");
      final List<Items> _result = new ArrayList<Items>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Items _item;
        _item = new Items();
        final int _tmpID;
        _tmpID = _cursor.getInt(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpItemname;
        if (_cursor.isNull(_cursorIndexOfItemname)) {
          _tmpItemname = null;
        } else {
          _tmpItemname = _cursor.getString(_cursorIndexOfItemname);
        }
        _item.setItemname(_tmpItemname);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpAddedby;
        if (_cursor.isNull(_cursorIndexOfAddedby)) {
          _tmpAddedby = null;
        } else {
          _tmpAddedby = _cursor.getString(_cursorIndexOfAddedby);
        }
        _item.setAddedby(_tmpAddedby);
        final boolean _tmpChecked;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfChecked);
        _tmpChecked = _tmp_1 != 0;
        _item.setChecked(_tmpChecked);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
