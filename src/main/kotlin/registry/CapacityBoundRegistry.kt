package registry

import process.Process
import registry.ProcessRegistry.Companion.CAPACITY
import java.util.concurrent.LinkedBlockingQueue

/**
 * A simple implementation of the [ProcessRegistry] that will respect the [CAPACITY] limit by not accepting any new
 * offered [Process]es.
 */
class CapacityBoundRegistry() : ProcessRegistry {

    private val data = LinkedBlockingQueue<Process>(CAPACITY)

    override fun offer(process: Process): Boolean {
        return data.offer(process)
    }

    override fun remove(process: Process): Boolean {
        return data.remove(process)
    }

    override fun clear() {
        data.clear()
    }

    override fun toList(): List<Process> = data.toList()
}
