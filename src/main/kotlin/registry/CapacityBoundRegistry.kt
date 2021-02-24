package registry

import clock.Clock
import process.Process
import process.ScheduledProcess
import process.schedule
import registry.ProcessRegistry.Companion.CAPACITY
import java.util.concurrent.LinkedBlockingQueue

/**
 * A simple implementation of the [ProcessRegistry] that will respect the [CAPACITY] limit by not accepting any new
 * offered [Process]es.
 */
class CapacityBoundRegistry(
    private val clock: Clock,
) : ProcessRegistry {

    private val data = ArrayDeque<ScheduledProcess>()

    override fun offer(process: Process): Boolean {
        if (data.size >= CAPACITY) return false
        data.addLast(process.schedule(clock))
        return true
    }

    override fun remove(process: Process): Boolean {
        return data.removeIf { process.id == it.id }
    }

    override fun clear() {
        data.clear()
    }

    override fun toList(): List<ScheduledProcess> = data.toList().sortedBy { it.timestamp }
}
