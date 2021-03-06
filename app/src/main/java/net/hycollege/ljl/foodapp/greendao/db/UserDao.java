package net.hycollege.ljl.foodapp.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import net.hycollege.ljl.foodapp.bean.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, String> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Userinfobean = new Property(1, String.class, "userinfobean", false, "USERINFOBEAN");
        public final static Property Loginstatus = new Property(2, String.class, "loginstatus", false, "LOGINSTATUS");
        public final static Property Username = new Property(3, String.class, "username", false, "USERNAME");
        public final static Property Password = new Property(4, String.class, "password", false, "PASSWORD");
        public final static Property Phonenum = new Property(5, String.class, "phonenum", false, "PHONENUM");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"USERINFOBEAN\" TEXT," + // 1: userinfobean
                "\"LOGINSTATUS\" TEXT," + // 2: loginstatus
                "\"USERNAME\" TEXT," + // 3: username
                "\"PASSWORD\" TEXT," + // 4: password
                "\"PHONENUM\" TEXT);"); // 5: phonenum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String userinfobean = entity.getUserinfobean();
        if (userinfobean != null) {
            stmt.bindString(2, userinfobean);
        }
 
        String loginstatus = entity.getLoginstatus();
        if (loginstatus != null) {
            stmt.bindString(3, loginstatus);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        String phonenum = entity.getPhonenum();
        if (phonenum != null) {
            stmt.bindString(6, phonenum);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String userinfobean = entity.getUserinfobean();
        if (userinfobean != null) {
            stmt.bindString(2, userinfobean);
        }
 
        String loginstatus = entity.getLoginstatus();
        if (loginstatus != null) {
            stmt.bindString(3, loginstatus);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        String phonenum = entity.getPhonenum();
        if (phonenum != null) {
            stmt.bindString(6, phonenum);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userinfobean
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // loginstatus
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // username
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // password
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // phonenum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserinfobean(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLoginstatus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUsername(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPassword(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPhonenum(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final String updateKeyAfterInsert(User entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
