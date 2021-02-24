package registry

import process.Process
import registry.ProcessRegistry.Companion.CAPACITY

/**
 * An implementation of the [ProcessRegistry] bound to the preset [CAPACITY] of the registry.
 * When the [CAPACITY] is reached upon offering a new [Process] the least recent one is dropped and killed.
 */
class MostRecentProcessRegistry : ProcessRegistry {

    private val data = ArrayDeque<Process>()

    override fun offer(process: Process): Boolean {
        if (data.size >= CAPACITY) {
            data.removeFirst().kill()
        }
        return data.add(process)
    }

    override fun remove(process: Process): Boolean {
        return data.remove(process)
    }

    override fun clear() {
        data.clear()
    }

    override fun toList(): List<Process> = data.toList()
}
