package com.example.musicplayer.features.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.musicplayer.features.domain.Favorite;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoritesDao_Impl implements FavoritesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Favorite> __insertionAdapterOfFavorite;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavoriteSong;

  public FavoritesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorite = new EntityInsertionAdapter<Favorite>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `favorites_table` (`songName`,`artistName`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favorite value) {
        if (value.getSongName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getSongName());
        }
        if (value.getArtistName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getArtistName());
        }
      }
    };
    this.__preparedStmtOfDeleteFavoriteSong = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM favorites_table WHERE songName = ?";
        return _query;
      }
    };
  }

  @Override
  public Object addToFavorites(final Favorite favorite,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavorite.insert(favorite);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteFavoriteSong(final String songName,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavoriteSong.acquire();
        int _argIndex = 1;
        if (songName == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, songName);
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteFavoriteSong.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllFavorites(final Continuation<? super List<Favorite>> continuation) {
    final String _sql = "SELECT * FROM favorites_table ORDER BY songName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Favorite>>() {
      @Override
      public List<Favorite> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSongName = CursorUtil.getColumnIndexOrThrow(_cursor, "songName");
          final int _cursorIndexOfArtistName = CursorUtil.getColumnIndexOrThrow(_cursor, "artistName");
          final List<Favorite> _result = new ArrayList<Favorite>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Favorite _item;
            final String _tmpSongName;
            if (_cursor.isNull(_cursorIndexOfSongName)) {
              _tmpSongName = null;
            } else {
              _tmpSongName = _cursor.getString(_cursorIndexOfSongName);
            }
            final String _tmpArtistName;
            if (_cursor.isNull(_cursorIndexOfArtistName)) {
              _tmpArtistName = null;
            } else {
              _tmpArtistName = _cursor.getString(_cursorIndexOfArtistName);
            }
            _item = new Favorite(_tmpSongName,_tmpArtistName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
