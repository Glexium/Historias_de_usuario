package com.historiasdeusuario.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.historiasdeusuario.database.model.ModelProject;
import com.historiasdeusuario.database.model.ModelType;
import com.historiasdeusuario.database.model.ModelUser;
import com.historiasdeusuario.objects.ObjLogin;
import com.historiasdeusuario.objects.ObjResponse;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "historias";

    // Table Names
    private static final String TABLE_PROJECT = "project";
    private static final String TABLE_USER = "user";
    private static final String TABLE_TYPE = "type";

    // Common key column names
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TYPE_ID = "type_id";

    // Common column names
    private static final String KEY_NAME = "name";

    // Users Table - column names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LASTNAME = "last_name";
    private static final String KEY_PASSWORD = "password";

    // Project Table - columns names
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_FINISH_DATE = "finish_date";
    private static final String KEY_DELETED = "deleted";

    // Table Create Statements
    private static final String CREATE_TABLE_USER ="CREATE TABLE "
        + TABLE_USER + "("+ KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_EMAIL + " TEXT, "
            + KEY_NAME + " TEXT, "
            + KEY_LASTNAME + " TEXT, "
            + KEY_PASSWORD + " TEXT) ";

    private static final String CREATE_TABLE_PROJECT = "CREATE TABLE "
            + TABLE_PROJECT + "("+ KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_ID + " INTEGER, "
            + KEY_TYPE_ID + " INTEGER, "
            + KEY_NAME + " TEXT, "
            + KEY_START_DATE + " TEXT, "
            + KEY_FINISH_DATE + " TEXT, "
            + KEY_DELETED + " INTEGER) ";

    private static final String CREATE_TABLE_TYPE ="CREATE TABLE "
            + TABLE_TYPE + "("+ KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT) ";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        /*db.execSQL(CREATE_TABLE_PROJECT);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_USER);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        onCreate(db);
    }

    public boolean existsColumnInTable(String columnToCheck) {
        Cursor mCursor = null;
        try {
            // Query 1 row
            SQLiteDatabase db = this.getReadableDatabase();
            if(isTableExists(TABLE_USER))
            {
                mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " LIMIT 0", null);
                // getColumnIndex() gives us the index (0 to ...) of the column - otherwise we get a -1
                if (mCursor.getColumnIndex(columnToCheck) != -1)
                    return true;
                else {
                    dropTable();
                    createTable(db);
                    return false;
                }
            }
            else
            {
                createTable(db);
                return false;
            }

        } catch (Exception Exp) {
            // Something went wrong. Missing the database? The table?
            Log.d("ExistColumnTable", Exp.getMessage());
            return false;
        } finally {
            if (mCursor != null) mCursor.close();
        }
    }

    private boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    private void createTable(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_PROJECT);
        ModelUser modelUser = new ModelUser();
        modelUser.setEmail("prueba@hotmail.com");
        modelUser.setPassword("prueba");
        modelUser.setName("Nombre Prueba");
        modelUser.setLastName("Apellido");
        addUser(modelUser);
        addType(new ModelType("Tipo A"));
        addType(new ModelType("Tipo B"));
        addType(new ModelType("Tipo C"));
        addType(new ModelType("Tipo D"));
        addType(new ModelType("Tipo E"));
        addType(new ModelType("Tipo F"));
    }


    public void dropTable()
    {
        String queryUser = "DROP table "+ TABLE_USER;
        String queryProject = "DROP table "+ TABLE_PROJECT;
        String queryType = "DROP table "+ TABLE_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(queryUser);
        db.execSQL(queryProject);
        db.execSQL(queryType);
    }

    public ObjResponse addUser(ModelUser modelUser)
    {
        ObjResponse objResponse = new ObjResponse();
        if(!userExist(modelUser.getEmail()))
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_EMAIL, modelUser.getEmail());
            values.put(KEY_PASSWORD, modelUser.getPassword());
            values.put(KEY_NAME, modelUser.getName());
            values.put(KEY_LASTNAME, modelUser.getLastName());
            long rowInserted = db.insert(TABLE_USER, null, values);
            db.close(); // Closing database connection
            if (rowInserted != -1) {
                //Inserción exitosa;
                objResponse.setIdResponse(1);
                return objResponse;
            } else {
                //Ocurrió un error
                objResponse.setIdResponse(-1);
                return objResponse;
            }
        }
        else
        {
            objResponse.setIdResponse(-2);
        }
        return objResponse;
    }

    private ObjResponse addType(ModelType modelType)
    {
        ObjResponse objResponse = new ObjResponse();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, modelType.getTypeName());
        long rowInserted = db.insert(TABLE_TYPE, null, values);
        db.close(); // Closing database connection
        if (rowInserted != -1) {
            //Inserción exitosa;
            objResponse.setIdResponse(1);
        }
        else {
            //Ocurrió un error
            objResponse.setIdResponse(-1);
        }
        return objResponse;
    }

    public ObjResponse addProject(ModelProject modelProject)
    {
        ObjResponse objResponse = new ObjResponse();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, modelProject.getUserId());
        values.put(KEY_TYPE_ID, modelProject.getTypeId());
        values.put(KEY_NAME, modelProject.getName());
        values.put(KEY_START_DATE, modelProject.getStartDate());
        values.put(KEY_FINISH_DATE, modelProject.getFinishDate());
        values.put(KEY_DELETED, 0);

        long rowInserted = db.insert(TABLE_PROJECT, null, values);
        db.close(); // Closing database connection
        if (rowInserted != -1) {
            //Inserción exitosa;
            objResponse.setIdResponse(1);
        }
        else {
            //Ocurrió un error
            objResponse.setIdResponse(-1);
        }
        return objResponse;
    }

    public ObjResponse updateProject(ModelProject modelProject)
    {
        ObjResponse objResponse = new ObjResponse();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE_ID, modelProject.getTypeId());
        values.put(KEY_NAME, modelProject.getName());
        values.put(KEY_START_DATE, modelProject.getStartDate());
        values.put(KEY_FINISH_DATE, modelProject.getFinishDate());

        long rowDeleted = db.update(TABLE_PROJECT, values, KEY_ID + " = ? ", new String[] { String.valueOf(modelProject.getId()) });
        db.close(); // Closing database connection
        if (rowDeleted != -1) {
            //Inserción exitosa;
            objResponse.setIdResponse(1);
        }
        else {
            //Ocurrió un error
            objResponse.setIdResponse(-1);
        }
        return objResponse;
    }

    public ObjResponse deleteProject(ModelProject modelProject)
    {
        ObjResponse objResponse = new ObjResponse();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DELETED, 1);

        long rowDeleted = db.update(TABLE_PROJECT, values, KEY_ID + " = ? ", new String[] { String.valueOf(modelProject.getId()) });
        db.close(); // Closing database connection
        if (rowDeleted != -1) {
            //Inserción exitosa;
            objResponse.setIdResponse(1);
        }
        else {
            //Ocurrió un error
            objResponse.setIdResponse(-1);
        }
        return objResponse;
    }

    public ModelUser getUser(ObjLogin objLogin)
    {
        ModelUser modelUser = new ModelUser();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USER+" where "+ KEY_EMAIL +"='"+ objLogin.getEmail()+ "' and "+KEY_PASSWORD +" = '"+objLogin.getPassword()+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    modelUser.setId(cursor.getInt(0));
                    modelUser.setEmail(cursor.getString(1));
                    modelUser.setName(cursor.getString(2));
                    modelUser.setLastName(cursor.getString(3));
                }
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                    Log.d("DB", ignore.getMessage());
                }
            }
        }
        finally
        {
            try
            {
                db.close();
            }
            catch (Exception ignore) {}
        }
        return modelUser;
    }

    public ObjResponse getProjects(String userId)
    {
        ObjResponse objResponse = new ObjResponse();
        List<ModelProject> objModelProjectList = new ArrayList<>();
        // Select All Query
       // String selectQuery = "SELECT * FROM " + TABLE_PROJECT+" where "+ KEY_USER_ID +" = "+userId+" order by "+KEY_START_DATE;
        String selectQuery = "SELECT "+TABLE_PROJECT+".*, "+TABLE_TYPE+"."+KEY_NAME+" FROM " + TABLE_PROJECT+", "+ TABLE_TYPE+" where "+ KEY_USER_ID +" = "+userId+" and "+ KEY_DELETED + " = 0 and "+TABLE_PROJECT+"."+KEY_TYPE_ID+"="+TABLE_TYPE+"."+KEY_ID+" order by "+KEY_START_DATE;

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        ModelProject modelProject = new ModelProject();
                        modelProject.setId(cursor.getInt(0));
                        modelProject.setUserId(cursor.getInt(1));
                        modelProject.setTypeId(cursor.getInt(2));
                        modelProject.setName(cursor.getString(3));
                        modelProject.setStartDate(cursor.getString(4));
                        modelProject.setFinishDate(cursor.getString(5));
                        modelProject.setTypeName(cursor.getString(7));
                        objModelProjectList.add(modelProject);
                    } while (cursor.moveToNext());
                    objResponse.setIdResponse(1);
                    objResponse.setModelProjects(objModelProjectList);
                }
                else
                {
                    objResponse.setIdResponse(-1);
                }


            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                    Log.d("DB", ignore.getMessage());
                }
            }
        }
        finally
        {
            try
            {
                db.close();
            }
            catch (Exception ignore) {}
        }
        return objResponse;
    }

    public ObjResponse getTypeList()
    {
        ObjResponse objResponse = new ObjResponse();
        List<ModelType> objModelTypeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TYPE;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        ModelType modelType = new ModelType();
                        modelType.setId(cursor.getInt(0));
                        modelType.setTypeName(cursor.getString(1));
                        objModelTypeList.add(modelType);
                    } while (cursor.moveToNext());
                    objResponse.setIdResponse(1);
                    objResponse.setModelType(objModelTypeList);
                }
                else
                {
                    objResponse.setIdResponse(-1);
                }
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                    Log.d("DB", ignore.getMessage());
                }
            }
        }
        finally
        {
            try
            {
                db.close();
            }
            catch (Exception ignore) {}
        }
        return objResponse;
    }

    public String getType(int id)
    {
        String selectQuery = "SELECT * FROM " + TABLE_TYPE +" where "+KEY_ID+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        String type="";
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    type = cursor.getString(1);
                }
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                    Log.d("DB", ignore.getMessage());
                }
            }
        }
        finally
        {
            try
            {
                db.close();
            }
            catch (Exception ignore) {}
        }
        return type;
    }

    public Boolean userExist(String email)
    {
        Boolean userExist=false;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USER+" where "+ KEY_EMAIL +"='"+ email+ "'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                if (cursor.moveToFirst()) {
                    userExist=true;
                }
            } finally {
                try {
                    cursor.close();
                } catch (Exception ignore) {
                    Log.d("DB", ignore.getMessage());
                }
            }
        }
        finally
        {
            try
            {
                db.close();
            }
            catch (Exception ignore) {}
        }
        return userExist;
    }
}