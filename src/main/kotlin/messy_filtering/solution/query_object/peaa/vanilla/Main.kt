package messy_filtering.solution.query_object.vanilla

fun main() {
    val personDataMap =
        DataMap(
            tableName = "persons",
            fieldToColumn =
                mapOf(
                    "lastName" to "last_name",
                    "firstName" to "first_name",
                    "numberOfDependents" to "number_of_dependents",
                ),
        )

    val personMapper = PersonMapper(personDataMap)
    val uow = UnitOfWork()
    uow.registerMapper(Person::class.java, personMapper)

    val query = QueryObject(Person::class.java)
    query.addCriteria(Criteria.greaterThan("numberOfDependents", 2))

    try {
        val result = query.execute(uow)
        println("Found persons: $result")
    } catch (e: NotImplementedError) {
        println("Cannot execute query: DB operations are not implemented.")
    }
}
