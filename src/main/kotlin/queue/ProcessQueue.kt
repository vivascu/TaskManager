package queue

import process.Process

interface ProcessQueue {
    /**
     * Inserts the specified [Process] into the queue.
     *
     * Returns *true* if the [Process] was enqueued.
     */
    fun offer(process: Process): Boolean

    /**
     * Removes the specified [Process] from the queue.
     *
     * Returns *true* if the [Process] was removed.
     */
    fun remove(process: Process): Boolean

    /**
     * Remove all the [Process]es in the queue.
     */
    fun  clear()

    /**
     * Returns all the enqueued [Process]es.
     */
    fun toList(): List<Process>
}