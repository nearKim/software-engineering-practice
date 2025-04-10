package where_to_validate.problem.strategy

import where_to_validate.problem.Command

class ListStrategy : CommandParseStrategy {
    override fun parse(input: String): Command {
        val parts = input.split("\\s+".toRegex()).drop(1) // Skip "list"
        val option = parts.firstOrNull()
        return Command.List(option)
    }
}
