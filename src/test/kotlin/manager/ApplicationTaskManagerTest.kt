package manager

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import process.Priority
import process.Priority.HIGH
import process.Process
import process.ScheduledProcess
import registry.ProcessRegistry

internal class ApplicationTaskManagerTest {

    private lateinit var registry: ProcessRegistry

    private lateinit var tested: ApplicationTaskManager

    @BeforeEach
    internal fun setUp() {
        registry = mockk(relaxed = true)
        tested = ApplicationTaskManager(registry)
    }

    @Test
    internal fun `adding a process offers it to the registry`() {
        //Given
        val mockProcess1: Process = mockk(relaxed = true)

        //When
        tested.add(mockProcess1)

        //Then
        verify { registry.offer(mockProcess1) }
    }

    @Test
    internal fun `listing the processes fetches them from the registry`() {
        //Given
        val mockProcess1: ScheduledProcess = mockk(relaxed = true)
        val mockProcess2: ScheduledProcess = mockk(relaxed = true)
        val expected = listOf(mockProcess1, mockProcess2)
        `given the process registry has items`(expected)

        //When
        val actual = tested.list()

        //Then
        assertEquals(expected, actual)
    }

    @Test
    internal fun `killing all processes should clear the registry`() {
        //Given
        `given the process registry is empty`()

        //When
        tested.killAll()

        //Then
        verify { registry.clear() }
    }

    @Test
    internal fun `killing all processes should kill each process in particular`() {
        //Given
        val mockProcess1: ScheduledProcess = mockk(relaxed = true)
        val mockProcess2: ScheduledProcess = mockk(relaxed = true)
        `given the process registry has items`(listOf(mockProcess1, mockProcess2))

        //When
        tested.killAll()

        //Then
        verify { mockProcess1.kill() }
        verify { mockProcess2.kill() }
    }

    @Test
    internal fun `killing a process should remove it from the registry`() {
        //Given
        val mockProcess: Process = mockk(relaxed = true)

        //When
        tested.kill(mockProcess)

        //Then
        verify { registry.remove(mockProcess) }
    }


    @Test
    internal fun `killing a process should invoke self destruct`() {
        //Given
        val mockProcess: Process = mockk(relaxed = true)

        //When
        tested.kill(mockProcess)

        //Then
        verify { mockProcess.kill() }
    }

    @Test
    internal fun `killing all processes in a group should invoke self destruct`() {
        //Given
        val mockProcess: Process = mockk(relaxed = true) {
            every { priority } returns HIGH
        }
        every { registry.removeGroup(HIGH) } returns listOf(mockProcess)

        //When
        tested.kill(HIGH)

        //Then
        verify { mockProcess.kill() }
    }

    private fun `given the process registry has items`(expected: List<ScheduledProcess>) {
        every { registry.toList() } returns expected
    }

    private fun `given the process registry is empty`() {
        every { registry.toList() } returns emptyList()
    }
}
