package manager

import clock.DefaultClock
import process.Priority
import process.Process
import process.ScheduledProcess
import registry.CapacityBoundRegistry
import registry.ProcessRegistry

class ApplicationTaskManager(
    private val registry: ProcessRegistry = CapacityBoundRegistry(DefaultClock())
) : TaskManager {

    override fun add(process: Process) {
        registry.offer(process)
    }

    override fun list(): List<Process> = list(compareBy { it.timestamp })

    override fun list(comparator: Comparator<in ScheduledProcess>): List<Process> = registry.toList(comparator)

    override fun kill(process: Process) {
        registry.remove(process)
        process.kill()
    }

    override fun kill(processes: Collection<Process>) {
        processes.forEach { process ->
            kill(process)
        }
    }

    override fun kill(priority: Priority) {
        registry.removeGroup(priority).forEach { it.kill() }
    }

    override fun killAll() {
        registry.toList().forEach { kill(it) }
        registry.clear()
    }
}
