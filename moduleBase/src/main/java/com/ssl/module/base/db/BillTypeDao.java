package com.ssl.module.base.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BILL_TYPE".
*/
public class BillTypeDao extends AbstractDao<BillType, Long> {

    public static final String TABLENAME = "BILL_TYPE";

    /**
     * Properties of entity BillType.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TypeId = new Property(0, Long.class, "typeId", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Type = new Property(2, int.class, "type", false, "TYPE");
    }


    public BillTypeDao(DaoConfig config) {
        super(config);
    }
    
    public BillTypeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BILL_TYPE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: typeId
                "\"TITLE\" TEXT NOT NULL ," + // 1: title
                "\"TYPE\" INTEGER NOT NULL );"); // 2: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BILL_TYPE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BillType entity) {
        stmt.clearBindings();
 
        Long typeId = entity.getTypeId();
        if (typeId != null) {
            stmt.bindLong(1, typeId);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindLong(3, entity.getType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BillType entity) {
        stmt.clearBindings();
 
        Long typeId = entity.getTypeId();
        if (typeId != null) {
            stmt.bindLong(1, typeId);
        }
        stmt.bindString(2, entity.getTitle());
        stmt.bindLong(3, entity.getType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BillType readEntity(Cursor cursor, int offset) {
        BillType entity = new BillType( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // typeId
            cursor.getString(offset + 1), // title
            cursor.getInt(offset + 2) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BillType entity, int offset) {
        entity.setTypeId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.getString(offset + 1));
        entity.setType(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BillType entity, long rowId) {
        entity.setTypeId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BillType entity) {
        if(entity != null) {
            return entity.getTypeId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BillType entity) {
        return entity.getTypeId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
