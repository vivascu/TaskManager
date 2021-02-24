package registry

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import process.Process
import registry.ProcessRegistry.Companion.CAPACITY

internal class MostRecentProcessRegistryTest {
    private lateinit var tested: MostRecentProcessRegistry

    @BeforeEach
    internal fun setUp() {
        tested = MostRecentProcessRegistry()
    }

    @Test
    internal fun `process offered when at max capacity gets accepted`() {
        //Given
        repeat(CAPACITY) {
            tested.offer(mockk(relaxed = true))
        }

        //When
        val wasInserted = tested.offer(mockk())

        //Then
        assertTrue(wasInserted)
    }

    @Test
    internal fun `process offered when at max capacity kills the least recent one`() {
        //Given
        val leastRecent = mockk<Process>(relaxed = true)
        tested.offer(leastRecent)
        repeat(CAPACITY - 1) {
            tested.offer(mockk())
        }

        //When
        tested.offer(mockk())

        //Then
        verify { leastRecent.kill() }
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
