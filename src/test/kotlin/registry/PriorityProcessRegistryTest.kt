package registry

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import process.DefaultProcess
import process.Priority
import process.Priority.HIGH
import process.Priority.LOW
import process.Priority.MEDIUM
import process.Process
import process.ProcessId
import registry.ProcessRegistry.Companion.CAPACITY

internal class PriorityProcessRegistryTest {

    private lateinit var tested: PriorityProcessRegistry

    private val mockProcessId = "mock"

    @BeforeEach
    internal fun setUp() {
        tested = PriorityProcessRegistry(mockk(relaxed = true))
    }

    @Test
    internal fun `offering a process inserts it last based on priority`() {
        //Given
        val high = `mock process with priority`(HIGH)
        val medium = `mock process with priority`(MEDIUM)
        val low = `mock process with priority`(LOW)
        tested.offer(low)
        tested.offer(low)
        tested.offer(high)

        //When
        tested.offer(medium)

        //Then
        val actual = tested.toList().map { it.priority }
        val expected = listOf(HIGH, MEDIUM, LOW, LOW)
        assertEquals(expected, actual)
    }

    @Test
    internal fun `offering a process inserts it after highest priority when smaller do not exist `() {
        //Given
        val high = `mock process with priority`(HIGH)
        val medium = `mock process with priority`(MEDIUM)
        tested.offer(high)
        tested.offer(high)

        //When
        tested.offer(medium)

        //Then
        val actual = tested.toList().map { it.priority }
        val expected = listOf(HIGH, HIGH, MEDIUM)
        assertEquals(expected, actual)
    }

    @Test
    internal fun `offering a process to an empty registry gets added`() {
        //Given
        val medium = `mock process with priority`(MEDIUM)

        //When
        val wasInserted = tested.offer(medium)

        //Then
        val actual = tested.toList().map { it.priority }
        val expected = listOf(MEDIUM)

        assertTrue(wasInserted)
        assertEquals(expected, actual)
    }

    @Test
    internal fun `offering a process in a full registry priority gets inserted`() {
        //Given
        val high = `mock process with priority`(HIGH)
        val medium = `mock process with priority`(MEDIUM)
        val low = `mock process with priority`(LOW)
        repeat(CAPACITY - 1) {
            tested.offer(low)
        }
        tested.offer(high)

        //When
        tested.offer(medium)

        //Then
        val actual = tested.toList().map { it.priority }
        val expected = listOf(HIGH, MEDIUM, LOW, LOW, LOW)
        assertEquals(expected, actual)
    }

    @Test
    internal fun `offering a process in a full registry priority removes oldest lowest priority`() {
        //Given
        val medium = `mock process with priority`(MEDIUM)

        repeat(CAPACITY - 2) {
            tested.offer(medium)
        }

        val lowFirst = DefaultProcess(LOW, ProcessId("1"))
        val lowSecond = DefaultProcess(LOW, ProcessId("2"))

        tested.offer(lowFirst)
        tested.offer(lowSecond)

        //When
        tested.offer(medium)

        //Then
        val actual = tested.toList().map { it.id.value }
        val expected = mutableListOf<String>().apply {
            repeat(CAPACITY - 1) {
                add(mockProcessId)
            }
            add("2")
        }
        assertEquals(expected, actual)
    }

    @Test
    internal fun `offering a process in a full registry with low priority gets ignored`() {
        //Given
        val medium = `mock process with priority`(MEDIUM)
        val low = `mock process with priority`(LOW)

        repeat(CAPACITY) {
            tested.offer(medium)
        }

        //When
        val wasInserted = tested.offer(low)

        //Then
        assertFalse(wasInserted)
    }

    private fun `mock process with priority`(value: Priority) = mockk<Process> {
        every { priority } returns value
        every { id } returns ProcessId(mockProcessId)
    }
}
