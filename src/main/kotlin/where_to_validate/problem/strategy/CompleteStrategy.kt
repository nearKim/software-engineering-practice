package where_to_validate.problem.strategy

import where_to_validate.problem.Command

class CompleteStrategy : CommandParseStrategy {
    override fun parse(input: String): Command {
        val parts = input.split("\\s+".toRegex()).drop(1) // Skip "complete"
        val id = parts[0].toInt() // Assumes valid integer input
        return Command.Complete(id)
    }
}
