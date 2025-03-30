package messy_filtering.solution.query_object.peaa.vanilla

import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class Mapper(
    val dataMap: DataMap,
) {
    fun findObjectsWhere(whereClause: String): Set<Any> {
        val sql =
            "SELECT ${dataMap.columnList()} FROM ${dataMap.getTableName()}" +
                if (whereClause.isNotEmpty()) " WHERE $whereClause" else ""
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            stmt = DB.prepare(sql)
            rs = stmt.executeQuery()
            return loadAll(rs)
        } catch (e: Exception) {
            throw Exception(e)
        } finally {
            DB.cleanUp(stmt, rs)
        }
    }

    protected abstract fun loadAll(rs: ResultSet): Set<Any>
}
