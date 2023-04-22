package com.example.provabottomnav.Classibase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "favouritesdb";

    // below int is our database version
    private static final int DB_VERSION = 2;

    // below variable is for our table name.
    private static final String TABLE_NAME = "preferiti";

    // below variable is for our id column.
    private static final String IDFILM = "idfilm";

    // below variable is for our course name column
    private static final String IMMAGINE = "immagine";

    // below variable id for our course duration column.
    private static final String DURATION_COL = "immagine";


    // below variable for our course description column.
    private static final String ANNO = "anno";

    // below variable is for our course tracks column.
    private static final String DURATA = "durata";
    private  Film film;
    private static final String GENERE = "genere";
    private static final String PAESE = "paese";
    private static final String TITOLO = "titolo";
    private static final String REGISTI = "registi";
    private static final String ATTORI = "attori";
    private static final String TRAMA = "trama";
    private static final String TRAILER = "trailer";
    private ArrayList<Film> courseModalArrayList;

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + IDFILM + " INTEGER,"
                + IMMAGINE + " TEXT,"
                + ANNO + " TEXT,"
                + DURATA + " TEXT,"
                + GENERE + " TEXT,"
                + PAESE+ " TEXT,"
                + TITOLO+" TEXT,"
                +REGISTI+" TEXT,"
                +ATTORI+" TEXT,"
                +TRAMA+" TEXT,"
                +TRAILER+" TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(Film film) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(IDFILM, film.getIdfilm());
        values.put(IMMAGINE, film.getImmagine());
        values.put(ANNO, film.getAnno());
        values.put(DURATA,film.getDurata());
        values.put(GENERE, film.getGenere());
        values.put(PAESE, film.getPaese());
        values.put(TITOLO, film.getTitolo());
        values.put(REGISTI,film.getRegisti());
        values.put(ATTORI, film.getAttori());
        values.put(TRAMA, film.getTrama());
        values.put(TRAILER,film.getTrailer());
        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<Film> readCourses() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Film> courseModalArrayList = new ArrayList<Film>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                Film newFilm= new Film(cursorCourses.getInt(0),cursorCourses.getString(1),cursorCourses.getString(2),cursorCourses.getString(3),cursorCourses.getString(4),cursorCourses.getString(5),cursorCourses.getString(6),cursorCourses.getString(7),cursorCourses.getString(8),cursorCourses.getString(9),cursorCourses.getString(10));
                // on below line we are adding the data from cursor to our array list.
              //  courseModalArrayList.add(new CourseModal(cursorCourses.getString(1),
                //        cursorCourses.getString(4),
                 //       cursorCourses.getString(2),
                  //      cursorCourses.getString(3)));
                courseModalArrayList.add(newFilm);
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }
public void deleteElement(Film film){
    SQLiteDatabase db = this.getReadableDatabase();
    db.delete(TABLE_NAME,IDFILM+"=?",new String[]{String.valueOf(film.getIdfilm())});


}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}