package registry

import process.Priority
import process.Process
import process.ScheduledProcess

interface ProcessRegistry : ProcessProvider {
    /**
     * Inserts the specified [Process] into the registry.
     *
     * Returns *true* if the [Process] was registered.
     */
    fun offer(process: Process): Boolean

    /**
     * Removes the specified [Process] from the registry.
     *
     * Returns *true* if the [Process] was removed.
     */
    fun remove(process: Process): Boolean

    /**
     * Removes the all the [Process]es from the registry that match the specified [Priority].
     *
     * Returns all the removed [Process]es.
     */
    fun removeGroup(priority: Priority): List<Process>

    /**
     * Remove all the [Process]es in the registry.
     */
    fun clear()

    companion object {
        const val CAPACITY = 5
    }
}
