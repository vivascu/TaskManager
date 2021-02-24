package manager

import clock.DefaultClock
import process.Priority
import process.Process
import registry.CapacityBoundRegistry
import registry.ProcessRegistry

class ApplicationTaskManager(
    private val registry: ProcessRegistry = CapacityBoundRegistry(DefaultClock())
) : TaskManager {

    override fun add(process: Process) {
        registry.offer(process)
    }

    override fun list(): List<Process> = registry.toList()

    override fun kill(process: Process) {
        registry.remove(process)
        process.kill()
    }

    override fun kill(processes: Collection<Process>) {
        processes.forEach { process ->
            kill(process)
        }
    }

    override fun killAll() {
        registry.toList().forEach { kill(it) }
        registry.clear()
    }
}
