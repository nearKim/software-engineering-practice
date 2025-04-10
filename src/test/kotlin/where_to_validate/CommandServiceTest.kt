package where_to_validate

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import where_to_validate.problem.Command
import where_to_validate.problem.CommandService.parseCommand

class CommandServiceTest {
    @Test
    fun `parse add command with due date`() {
        val input = "add Buy milk --due 2023-12-01"
        val result = parseCommand(input).getOrThrow()
        assertEquals(Command.Add("Buy milk", "2023-12-01"), result, "Expected Add command with due date")
    }

    @Test
    fun `parse empty input fails`() {
        val input = ""
        val result = parseCommand(input)
        assertTrue(result.isFailure, "Expected failure for empty input")
    }

    // Fails
    @Test
    fun `parse empty add command fails`() {
        val input = "add"
        val result = parseCommand(input)
        assertTrue(result.isFailure, "Expected failure for empty add command")
    }
}
