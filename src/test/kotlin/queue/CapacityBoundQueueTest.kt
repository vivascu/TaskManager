package queue

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import queue.ProcessQueue.Companion.CAPACITY

internal class CapacityBoundQueueTest {
    lateinit var tested: CapacityBoundQueue

    @BeforeEach
    internal fun setUp() {
        tested = CapacityBoundQueue()
    }

    @Test
    internal fun `process offered when at max capacity does not get accepted`() {
        //Given
        repeat(CAPACITY) {
            tested.offer(mockk())
        }

        //When
        val wasInserted = tested.offer(mockk())

        //Then
        assertFalse(wasInserted)
    }

    @Test
    internal fun `process offered below max capacity gets accepted`() {
        //Given
        tested.offer(mockk())

        //When
        val wasInserted = tested.offer(mockk())

        //Then
        assertTrue(wasInserted)
    }
}