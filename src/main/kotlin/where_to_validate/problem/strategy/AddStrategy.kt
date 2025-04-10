package where_to_validate.problem.strategy

import where_to_validate.problem.Command

class AddStrategy : CommandParseStrategy {
    override fun parse(input: String): Command {
        val parts = input.split("\\s+".toRegex()).drop(1) // Skip the command word "add"
        val title = StringBuilder()
        var dueDate: String? = null
        var foundDue = false

        for (part in parts) {
            if (foundDue) {
                dueDate = part
                break
            } else if (part == "--due") {
                foundDue = true
            } else {
                if (title.isNotEmpty()) {
                    title.append(" ")
                }
                title.append(part)
            }
        }

        return Command.Add(title.toString(), dueDate)
    }
}
