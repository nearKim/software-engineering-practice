package where_to_validate.problem.strategy

import where_to_validate.problem.Command

interface CommandParseStrategy {
    fun parse(input: String): Command
}
