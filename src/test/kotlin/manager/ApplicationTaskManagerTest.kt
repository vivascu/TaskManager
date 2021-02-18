package manager

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import process.Process
import queue.ProcessQueue

internal class ApplicationTaskManagerTest {

    private lateinit var queue: ProcessQueue

    private lateinit var tested: ApplicationTaskManager

    @BeforeEach
    internal fun setUp() {
        queue = mockk(relaxed = true)
        tested = ApplicationTaskManager(queue)
    }

    @Test
    internal fun `adding an process offers it to the queue`() {
        //Given
        val mockProcess1: Process = mockk(relaxed = true)

        //When
        tested.add(mockProcess1)

        //Then
        verify { queue.offer(mockProcess1) }
    }

    @Test
    internal fun `listing the processes fetches them from the queue`() {
        //Given
        val mockProcess1: Process = mockk(relaxed = true)
        val mockProcess2: Process = mockk(relaxed = true)
        val expected = listOf(mockProcess1, mockProcess2)
        `given the process queue has items`(expected)

        //When
        val actual = tested.list()

        //Then
        assertEquals(expected, actual)
    }

    @Test
    internal fun `killing all processes should clear the queue`() {
        //Given
        `given the process queue is empty`()

        //When
        tested.killAll()

        //Then
        verify { queue.clear() }
    }

    @Test
    internal fun `killing all processes should kill each process in particular`() {
        //Given
        val mockProcess1: Process = mockk(relaxed = true)
        val mockProcess2: Process = mockk(relaxed = true)
        `given the process queue has items`(listOf(mockProcess1, mockProcess2))

        //When
        tested.killAll()

        //Then
        verify { mockProcess1.kill() }
        verify { mockProcess2.kill() }
    }

    @Test
    internal fun `killing a process should remove it from the queue`() {
        //Given
        val mockProcess: Process = mockk(relaxed = true)

        //When
        tested.kill(mockProcess)

        //Then
        verify { queue.remove(mockProcess) }
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

    private fun `given the process queue has items`(expected: List<Process>) {
        every { queue.toList() } returns expected
    }

    private fun `given the process queue is empty`() {
        every { queue.toList() } returns emptyList()
    }
}