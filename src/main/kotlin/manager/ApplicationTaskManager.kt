package manager

import process.Process
import queue.CapacityBoundQueue
import queue.ProcessQueue

class ApplicationTaskManager(
    private val queue: ProcessQueue = CapacityBoundQueue()
) : TaskManager {

    override fun add(process: Process) {
        queue.offer(process)
    }

    override fun list(): List<Process> = queue.toList()

    override fun kill(process: Process) {
        queue.remove(process)
        process.kill()
    }

    override fun kill(processes: Collection<Process>) {
        processes.forEach { process ->
            kill(process)
        }
    }

    override fun killAll() {
        queue.toList().forEach { kill(it) }
        queue.clear()
    }
}