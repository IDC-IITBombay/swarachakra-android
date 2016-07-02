package iit.android.prediction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperV2 extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "hindi.sqlite";
	public static final int DATABASE_VERSION = 1;
	public final Context ourContext;
	public SQLiteDatabase ourDatabase;

	public DBHelperV2(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.ourContext = context;
		// //Log.d("DataBaseHelper", "Constructor called");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

	public void createDataBase() throws IOException {
		// Log.d("DataBaseHelper", "createDataBase called");
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// Log.d("DataBaseHelper", "Database already exists");
		} else {
			// Log.d("DataBaseHelper",
			// "Before this.getReadableDatabase() called");
			this.getReadableDatabase();
			// Log.d("DataBaseHelper",
			// "After this.getReadableDatabase() called");
			try {
				// Log.d("DataBaseHelper",
				// "createDataBase, trying to copyDataBase ");
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkDataBase() {
		// Log.d("DataBaseHelper", "checkDataBase called");
		SQLiteDatabase checkDB = null;
		try {
			// Log.d("DataBaseHelper", "in try loop");
			// Log.d("DataBaseHelper",
			// ourContext.getFilesDir().getAbsolutePath()
			// .replace("files", "databases"));
			String myPath = ourContext.getFilesDir().getAbsolutePath()
					.replace("files", "databases")
					+ "/" + DATABASE_NAME;
			// Log.d("DataBaseHelper", "intermediate");
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
			// Log.d("DataBaseHelper", "exit from try loop");
		} catch (SQLiteException e) {
			// Log.d("DataBaseHelper", "in catch loop");
		}
		if (checkDB != null) {
			// Log.d("DataBaseHelper",
			// "checkDataBase called, DB already exits");
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		// Log.d("DataBaseHelper", "copyDataBase called");
		InputStream myInput = ourContext.getAssets().open(DATABASE_NAME);
		String outFileName = ourContext.getFilesDir().getAbsolutePath()
				.replace("files", "databases")
				+ File.separator + DATABASE_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		// Log.d("DataBaseHelper", "openDataBase called");
		String myPath = ourContext.getFilesDir().getAbsolutePath()
				.replace("files", "databases")
				+ File.separator + DATABASE_NAME;
		ourDatabase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		// Log.d("DataBaseHelper", "close called");
		if (ourDatabase != null)
			ourDatabase.close();
		super.close();
	}

	public String[] predictioncompletion(String prevword, String currword) {
		// output which is going to be returned is finalarray;initially empty
		// Log.d("Engine Start", prevword + "," + currword);
		String finalarray[] = new String[5];
		for (int i = 0; i < 5; i++) {
			finalarray[i] = "";
		}

		SQLiteDatabase db = this.getWritableDatabase();

		if (prevword == "") {
			int si, ei;
			si = -100;
			ei = -100;

			String query = ("SELECT si,ei,isword FROM trie WHERE WORD ='"
					+ currword + "'");
			Log.d("Loop 1", "before query");
			Cursor ourCursor = db.rawQuery(query, null);
			Log.d("Loop 1", "After query1");
			if (ourCursor.moveToFirst()) {
				si = ourCursor.getInt(0);
				ei = ourCursor.getInt(1);

			}
			ourCursor.close();
			Log.d("Loop 1", "before query2");
			query = ("SELECT word FROM wordfreqvector WHERE ind >= " + si
					+ " AND ind <= " + ei + " ORDER BY freq DESC");
			Cursor c = db.rawQuery(query, null);
			Log.d("Loop 1", "After query2");
			// int count=c.getCount();
			boolean there = c.moveToFirst();
			int i = 0;
			if (there) {
				do {
					/*
					 * Log.d("currword", currword); Log.d("String",
					 * c.getString(0)); if(currword==c.getString(0)) {
					 * System.out.println("true"+c.getString(0)+","+currword); }
					 * if (currword != c.getString(0)) { finalarray[i] =
					 * c.getString(0); i++; }
					 */
					if (!currword.equals(c.getString(0))) {
						finalarray[i] = c.getString(0);
						i++;
					}

				} while (c.moveToNext() && i < 5);

			}
			c.close();
			db.close();
			return finalarray;

		} else if (!prevword.equals("") && currword.equals("")) {

			String biind;

			String query = ("SELECT biind FROM wordfreqvector WHERE word = '"
					+ prevword + "'");
			Log.d("Loop 2", "before query1 " + query);
			Cursor c1 = db.rawQuery(query, null);
			Log.d("Loop 2", "After query1");
			biind = "";
			if (c1.moveToFirst()) {
				biind = c1.getString(0);

				Log.d("biind", biind);
				c1.close();
				String biinds[] = biind.split(",");
				int l = biinds.length;
				int[] inda = new int[5];
				String Order = " ORDER BY ( CASE ";
				String sql = "SELECT word FROM wordfreqvector ";
				for (int i = 0; i < l && i < 5; i++) {
					inda[i] = Integer.parseInt(biinds[i]);

					if ((i < l - 1 && i < 5)) {
						if (i == 0) {
							sql = sql + "WHERE ind=" + inda[i];
							Order = Order + " WHEN ind=" + inda[i] + " THEN "
									+ i;
						} else {
							sql = sql + " OR ind=" + inda[i];
							Order = Order + " WHEN ind=" + inda[i] + " THEN "
									+ i;
						}

					}
				}

				Order = Order + " ELSE 6 END)";
				sql = sql + Order;
				// Log.d("sql", sql);
				int m = 0;
				if (l >= 1) {
					Log.d("Loop 2", "before query2 " + sql);
					Cursor c = db.rawQuery(sql, null);
					Log.d("Loop 2", "After query2");
					if (c.moveToFirst()) {
						do {
							if (!currword.equals(c.getString(0))) {
								finalarray[m] = c.getString(0);
								m++;
							}
						} while (c.moveToNext() && m < 5);
					}
					c.close();
				}
				db.close();
				return finalarray;
			}
		} else if (!prevword.equals("") && !currword.equals("")) {

			String query = ("SELECT biind FROM wordfreqvector where word = '"
					+ prevword + "'");
			String biind = "";
			Log.d("Loop 3", "before query1 " + query);
			Cursor c1 = db.rawQuery(query, null);
			Log.d("Loop 3", "after query1");
			int si = -100, ei = -100;
			String query2 = ("SELECT si,ei FROM trie where word = '" + currword + "'");
			Log.d("Loop 3", "before query2 " + query2);
			Cursor c3 = db.rawQuery(query2, null);
			Log.d("Loop 3", "after query2");
			if (c3.moveToFirst()) {
				si = c3.getInt(0);
				ei = c3.getInt(1);
			}
			Log.d("si,ei", si + "," + ei);
			c3.close();

			if (c1.moveToFirst()) {
				Log.d("Loop 3", " Found prev Word");
				biind = c1.getString(0);
				Log.d("Loop 3 biind", biind);
				String[] biinds = biind.split(",");
				c1.close();
				int l = biinds.length;
				int k = 0;
				int n = 0;
				int[] inda = new int[l];
				String Order = " ORDER BY ( CASE ";
				String sql = "SELECT word FROM wordfreqvector ";
				String sql3 = "SELECT word FROM wordfreqvector WHERE word!= '"
						+ currword + "'";

				// prepares sql statement for fetching words based on the
				// indexes that came according to si,ei at the same time
				// preparing another sql statement for querying database if
				// the total no. of indexes that were returned arent
				// suffiecient i.e. less than 5.
				for (int i = 0; i < l && n < 5; i++) {

					k = Integer.parseInt(biinds[i]);
					if (k <= ei && k >= si) {
						inda[n] = k;
						if (n == 0) {
							sql = sql + "WHERE ind= " + inda[n];
							sql3 = sql3 + " AND ind!= " + inda[n];
							Order = Order + " WHEN ind=" + inda[i] + " THEN "
									+ i;
						} else {
							sql = sql + " OR ind=" + inda[n];
							sql3 = sql3 + " AND ind!=" + inda[n];
							Order = Order + " WHEN ind=" + inda[i] + " THEN "
									+ i;
						}
						n++;
					}
				}
				if (n > 0) {
					Order = Order + " ELSE 6 END)";
					sql = sql + Order;
				}
				sql3 = sql3 + " AND ind>=" + si + " AND ind<=" + ei
						+ " ORDER BY freq DESC";
				int m = 0;
				Log.d("Loop 3", " SQL Ready for query 3" + sql);
				if (l >= 1 && n >= 1) {
					Log.d("Loop 3", "before query3 " + sql);
					Cursor c = db.rawQuery(sql, null);
					Log.d("Loop 3", "after query3");
					if (c.moveToFirst()) {
						do {
							if (!currword.equals(c.getString(0))) {
								finalarray[m] = c.getString(0);
								m++;
							}
						} while (c.moveToNext() && m < 5);
					}
					c.close();
					Log.d("Loop 3", " filled final till " + m);
					Log.d("Loop 3", " Found prev Word" + sql3);
				}

				if (n < 5) {
					Log.d("Loop 3", "before query4 " + sql3);
					Cursor c2 = db.rawQuery(sql3, null);
					Log.d("Loop 3", "after query4");
					if (c2.moveToFirst()) {
						do {
							if (!currword.equals(c2.getString(0))) {
								finalarray[m] = c2.getString(0);
								m++;
							}
						} while (c2.moveToNext() && m < 5);
					}
					c2.close();
				}

				db.close();
				return finalarray;
			} else {
				Log.d("Loop 3", " dint Find prev Word");
				query = ("SELECT word FROM wordfreqvector WHERE word!= '"
						+ currword + "' AND ind >= " + si + " AND ind <= " + ei + " ORDER BY freq DESC");
				Log.d("Loop 3", "before query5 " + query);
				Cursor c = db.rawQuery(query, null);
				Log.d("Loop 3", "after query5");
				// int count=c.getCount();
				boolean there = c.moveToFirst();
				int i = 0;
				if (there) {
					do {
						if (!currword.equals(c.getString(0))) {
							finalarray[i] = c.getString(0);
							i++;
						}
					} while (c.moveToNext() && i < 5);
					Log.d("Loop 3", " Filled final till " + i);

				}
				c.close();
				db.close();
				return finalarray;
			}

		}
		db.close();
		return finalarray;
	}
}
