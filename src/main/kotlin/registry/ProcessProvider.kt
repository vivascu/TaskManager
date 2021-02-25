package registry

import process.Process
import process.ScheduledProcess

interface ProcessProvider {
    /**
     * Returns all the registered [Process]es sorted by the specified [comparator].
     */
    fun toList(comparator: Comparator<in ScheduledProcess> = compareBy { it.timestamp }): List<ScheduledProcess>
}
