package process

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import process.Priority.HIGH

internal class DefaultProcessTest {
    @Test
    internal fun `process IDs should be unique`() {
        //Given
        val set = mutableSetOf<Process>()
        val expectedItemCount = 10

        //When
        repeat(expectedItemCount) {
            set.add(DefaultProcess(HIGH))
        }

        //Then
        assertEquals(set.size, expectedItemCount)
    }
}
