package org.csuf.cpsc411.Dao.claim

import org.csuf.cpsc411.Dao.Dao
import org.csuf.cpsc411.Dao.Database
import java.util.*

class ClaimDao : Dao() {

    fun addClaim(cObj : Claim){
        // First, get database connection
        val conn = Database.getInstance()?.getDBConnection()

        // Second, prepare the SQL statement
        sqlStmt = "Insert into claim (id, title, date, isSolved) values ('${cObj.id}', '${cObj.title}', '${cObj.date}', '${cObj.isSolved}')"

        // Third, submit the SQL statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        // First, get database connection
        val conn = Database.getInstance()?.getDBConnection()

        // Second, prepare the SQL statement
        sqlStmt = "select id, title, date, isSolved from claim"

        // Third, submit the SQL statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // Fourth, convert into Kotlin object format
        while (st?.step()!!){
            //Convert each record into Claim object
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnNull(3)

            claimList.add(Claim(UUID.fromString(id),title,date,isSolved))
        }
        return claimList
    }
}