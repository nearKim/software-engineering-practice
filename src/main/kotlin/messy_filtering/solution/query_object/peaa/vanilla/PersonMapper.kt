package messy_filtering.solution.query_object.peaa.vanilla

import java.sql.ResultSet

class PersonMapper(
    dataMap: DataMap,
) : Mapper(dataMap) {
    override fun loadAll(rs: ResultSet): Set<Any> {
        val result = mutableSetOf<Any>()
        while (rs.next()) {
            val person =
                Person(
                    lastName = rs.getString("last_name"),
                    firstName = rs.getString("first_name"),
                    numberOfDependents = rs.getInt("number_of_dependents"),
                )
            result.add(person)
        }
        return result
    }
}
