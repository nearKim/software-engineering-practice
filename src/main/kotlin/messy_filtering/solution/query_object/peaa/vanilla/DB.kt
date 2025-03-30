package messy_filtering.solution.query_object.vanilla

import java.sql.PreparedStatement
import java.sql.ResultSet

object DB {
    fun prepare(sql: String): PreparedStatement = throw NotImplementedError("DB operations are not implemented.")

    fun cleanUp(
        stmt: PreparedStatement?,
        rs: ResultSet?,
    ) {
        // Placeholder for resource cleanup
    }
}
