package where_to_validate.problem

import where_to_validate.problem.strategy.*

object CommandService {
    fun parseCommand(input: String): Result<Command> {
        val strategies =
            mapOf(
                "add" to AddStrategy(),
                "list" to ListStrategy(),
                "complete" to CompleteStrategy(),
                "remove" to RemoveStrategy(),
                "exit" to ExitStrategy(),
            )

        val cmdStr =
            input.split("\\s+".toRegex()).firstOrNull()
                ?: return Result.failure(IllegalArgumentException("No command provided"))

        val strategy =
            strategies[cmdStr]
                ?: return Result.failure(IllegalArgumentException("Unknown command: '$cmdStr'"))

        return Result.success(strategy.parse(input))
    }
}
