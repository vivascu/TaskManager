package registry

import process.Priority
import process.Process
import process.ScheduledProcess

/**
 *  This abstraction level is created in order to improve the readability of the different
 *  [ProcessRegistry] implementations.
 *
 *  Inheritance was consciously used in order not to over-complicate the solution for readability purposes.
 */
abstract class ArrayDequeProcessRegistry : ProcessRegistry {
    protected val data = ArrayDeque<ScheduledProcess>()

    override fun remove(process: Process): Boolean = data.removeIf { process.id == it.id }

    override fun removeGroup(priority: Priority): List<Process> = data.filter { it.priority == priority }
        .also { data.removeAll(it) }

    override fun clear() {
        data.clear()
    }

    override fun toList(comparator: Comparator<in ScheduledProcess>): List<ScheduledProcess> =
        data.toList().sortedWith(comparator)
}
