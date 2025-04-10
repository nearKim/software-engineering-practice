package where_to_validate.problem.strategy

import where_to_validate.problem.Command

class ExitStrategy : CommandParseStrategy {
    override fun parse(input: String): Command = Command.Exit
}
