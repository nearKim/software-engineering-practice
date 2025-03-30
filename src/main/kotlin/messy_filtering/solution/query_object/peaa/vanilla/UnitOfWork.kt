package messy_filtering.solution.query_object.peaa.vanilla

class UnitOfWork {
    private val mappers = mutableMapOf<Class<*>, Mapper>()

    fun registerMapper(
        klass: Class<*>,
        mapper: Mapper,
    ) {
        mappers[klass] = mapper
    }

    fun getMapper(klass: Class<*>): Mapper = mappers[klass] ?: throw IllegalArgumentException("No mapper for $klass")
}
