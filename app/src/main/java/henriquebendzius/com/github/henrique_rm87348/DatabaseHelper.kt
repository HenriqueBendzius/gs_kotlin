package henriquebendzius.com.github.henrique_rm87348.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import henriquebendzius.com.github.henrique_rm87348.model.Dica

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dicas_energia.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_DICAS = "dicas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_DICAS_TABLE = "CREATE TABLE $TABLE_DICAS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITULO TEXT, " +
                "$COLUMN_DESCRICAO TEXT)"
        db?.execSQL(CREATE_DICAS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DICAS")
        onCreate(db)
    }

    fun addDica(dica: Dica) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, dica.titulo)
            put(COLUMN_DESCRICAO, dica.descricao)
        }
        db.insert(TABLE_DICAS, null, values)
        db.close()
    }

    fun getAllDicas(): MutableList<Dica> {
        val dicasList = mutableListOf<Dica>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_DICAS", null)

        if (cursor.moveToFirst()) {
            do {
                val dica = Dica(
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITULO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRICAO))
                )
                dicasList.add(dica)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dicasList
    }
}
