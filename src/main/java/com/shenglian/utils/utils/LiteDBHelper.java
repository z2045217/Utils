package com.shenglian.utils.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LiteDBHelper {

	public static boolean dbIsExist(final Context con, final String path) {
		boolean flag = false;
		File database = con.getDatabasePath(path);
		if (database.exists()) {
			flag = true;
		}
		return flag;
	}

	public static boolean tableIsExist(final Context con, final String dbName,
			final String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		SQLiteDatabase db = con.openOrCreateDatabase(dbName,
				Context.MODE_PRIVATE, null);
		Cursor cursor = null;
		try {
			StringBuilder sbSQL = new StringBuilder();
			sbSQL.append("SELECT COUNT(*) as c FROM Sqlite_master WHERE type ='table' AND name ='");
			sbSQL.append(tableName.trim());
			sbSQL.append("' ");
			cursor = db.rawQuery(sbSQL.toString(), null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
		} finally {
			db.close();
			if (cursor != null) {
				try {
					cursor.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static boolean columnIsExist(final Context con, final String dbName,
			final String tableName, final String columnName) {
		boolean result = false;
		SQLiteDatabase db = con.openOrCreateDatabase(dbName,
				Context.MODE_PRIVATE, null);
		Cursor cursor = null;
		try {
			String strSQL = "SELECT * FROM [" + tableName + "]";
			cursor = db.rawQuery(strSQL, null);
			result = cursor.getColumnIndex(columnName) >= 0;
		} catch (Exception e) {
			result = false;
		} finally {
			db.close();
			if (cursor != null) {
				try {
					cursor.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static int Delete(SQLiteDatabase db, Context content,
			String database, String table, String whereClause,
			String[] whereArgs) {
		boolean Transaction = db != null;
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return -1;
			}
		}
		try {
			return db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (!Transaction) {
				db.close();
			}
		}
	}

	public static int Delete(Context content, String database, String table,
			String whereClause, String[] whereArgs) {
		return Delete(null, content, database, table, whereClause, whereArgs);
	}

	public static long Insert(SQLiteDatabase db, Context content,
			String database, String table, String nullColumnHack,
			ContentValues values) {
		boolean Transaction = db != null;
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return -1;
			}
		}
		try {
			return db.insert(table, nullColumnHack, values);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (!Transaction) {
				db.close();
			}
		}
	}

	public static long Replace(Context content, String database, String table,
			String nullColumnHack, ContentValues values) {
		return Replace(null, content, database, table, nullColumnHack, values);
	}

	public static long Replace(SQLiteDatabase db, Context content,
			String database, String table, String nullColumnHack,
			ContentValues values) {
		boolean Transaction = db != null;
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return -1;
			}
		}
		try {
			return db.replace(table, nullColumnHack, values);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (!Transaction) {
				db.close();
			}
		}
	}

	public static long Insert(Context content, String database, String table,
			String nullColumnHack, ContentValues values) {
		return Insert(null, content, database, table, nullColumnHack, values);
	}

	public static int Update(SQLiteDatabase db, Context content,
			String database, String table, ContentValues values,
			String whereClause, String[] whereArgs) {
		boolean Transaction = db != null;
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return -1;
			}
		}
		try {
			return db.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (!Transaction) {
				db.close();
			}
		}
	}

	public static int Update(Context content, String database, String table,
			ContentValues values, String whereClause, String[] whereArgs) {
		return Update(null, database, table, values, whereClause, whereArgs);
	}

	public static List<ContentValues> rawQuery(Context content,
			String database, String sql, String[] selectionArgs) {
		return rawQuery(null, content, database, sql, selectionArgs);
	}

	public static List<ContentValues> rawQuery(SQLiteDatabase db,
			Context content, String database, String sql, String[] selectionArgs) {
		boolean Transaction = db != null;
		List<ContentValues> ValueList = new ArrayList<ContentValues>();
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return ValueList;
			}
		}
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			String[] arrColumnNames = cursor.getColumnNames();
			while (cursor != null && cursor.moveToNext()) {
				ContentValues ThisValue = new ContentValues();
				for (int i = 0; i < arrColumnNames.length; i++) {
					ThisValue.put(arrColumnNames[i], cursor.getString(i));
				}
				ValueList.add(ThisValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!Transaction) {
				db.close();
			}
			if (cursor != null) {
				try {
					cursor.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ValueList;
	}

	public static int rawQueryCount(SQLiteDatabase db, Context content,
			String database, String sql, String[] selectionArgs) {
		boolean Transaction = db != null;
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return -1;
			}
		}
		int intValue = -1;
		try {
			intValue = db.rawQuery(sql, selectionArgs).getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!Transaction) {
				db.close();
			}
		}
		return intValue;
	}

	public static boolean execSQL(SQLiteDatabase db, Context content,
			String database, String sql) {
		boolean Transaction = db != null;
		if (!Transaction) {
			db = getDataBase(content, database);
			if (db == null) {
				return false;
			}
		}
		try {
			db.execSQL(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!Transaction) {
				db.close();
			}
		}
	}

	public static int rawQueryCount(Context content, String database,
			String sql, String[] selectionArgs) {
		return rawQueryCount(null, content, database, sql, selectionArgs);
	}

	public static SQLiteDatabase getDataBase(Context content, String database) {
		try {
			return content.openOrCreateDatabase(database, Context.MODE_PRIVATE,
					null);
		} catch (Exception e) {
			return null;
		}
	}
}
