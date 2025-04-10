package where_to_validate.problem

sealed class Command {
    data class Add(
        val title: String,
        val dueDate: String?,
    ) : Command()

    data class List(
        val option: String?,
    ) : Command()

    data class Complete(
        val id: Int,
    ) : Command()

    data class Remove(
        val id: Int,
    ) : Command()

    data object Exit : Command()
}
