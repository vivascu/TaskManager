package manager

import process.Priority
import process.Process
import process.ScheduledProcess

/**
 * A component for handling multiple [Process]es in an operating system.
 */
interface TaskManager {

    /**
     * Add a process to be executed by the [TaskManager].
     */
    fun add(process: Process)

    /**
     * List all currently running processes.
     * Ordered by timestamp by default.
     */
    fun list(): List<Process>

    /**
     * List all currently running processes ordered by the provided comparator.
     */
    fun list(comparator: Comparator<in ScheduledProcess>): List<Process>

    /**
     * Kill the specified [process] if running.
     */
    fun kill(process: Process)

    /**
     * Kill all the specified [processes] if running.
     */
    fun kill(processes: Collection<Process>)

    /**
     * Kill all the processes of the specified [Priority] if running.
     */
    fun kill(priority: Priority)

    /**
     * Kill all the ongoing processes.
     */
    fun killAll()

}
