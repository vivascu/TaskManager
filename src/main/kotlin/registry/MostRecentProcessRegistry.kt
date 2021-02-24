package registry

import clock.Clock
import process.Process
import process.schedule
import registry.ProcessRegistry.Companion.CAPACITY

/**
 * An implementation of the [ProcessRegistry] bound to the preset [CAPACITY] of the registry.
 * When the [CAPACITY] is reached upon offering a new [Process] the least recent one is dropped and killed.
 */
class MostRecentProcessRegistry(
    private val clock: Clock,
) : ArrayDequeProcessRegistry() {

    override fun offer(process: Process): Boolean {
        if (data.size >= CAPACITY) {
            data.removeFirst().kill()
        }
        return data.add(process.schedule(clock))
    }

}
