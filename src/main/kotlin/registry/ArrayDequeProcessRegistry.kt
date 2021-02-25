package registry

import process.Process
import process.ScheduledProcess

/**
 *  This abstraction level is created in order to improve the readability of the different
 *  [ProcessRegistry] implementations.
 *
 *  Inheritance was consciously used in order not to over-complicate the solution for readability purposes.
 */
internal abstract class ArrayDequeProcessRegistry : ProcessRegistry {
    protected val data = ArrayDeque<ScheduledProcess>()

    override fun remove(process: Process): Boolean {
        return data.removeIf { process.id == it.id }
    }

    override fun clear() {
        data.clear()
    }

    override fun toList(): List<ScheduledProcess> = data.toList().sortedBy { it.timestamp }
}
