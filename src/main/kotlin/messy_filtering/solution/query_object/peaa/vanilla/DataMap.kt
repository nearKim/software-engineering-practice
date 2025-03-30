package messy_filtering.solution.query_object.peaa.vanilla

class DataMap(
    private val tableName: String,
    private val fieldToColumn: Map<String, String>,
) {
    fun getTableName(): String = tableName

    fun getColumnForField(fieldName: String): String = fieldToColumn[fieldName] ?: throw Exception("Unable to find column for $fieldName")

    fun columnList(): String = fieldToColumn.values.joinToString(", ")
}
