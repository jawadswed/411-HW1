package org.csuf.cpsc411.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor (var dbName : String = ""){

    init {
        // Create the database, tables, and keep the database connection
        dbName = "C:\\Users\\jawad\\Documents\\CPSC411\\claimEntityDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "Create table if not exists claim (id UUID, title text, date text, isSolved boolean)"
        dbConn.exec(sqlStmt)
    }

    fun getDBConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}

fun main() {

}