package process

/**
 * A unit of work to be executed.
 */
interface Process {
    /**
     * An unique identifier for the [Process].
     */
    val id: ProcessId

    /**
     * The [Priority] assigned to the [Process].
     */
    val priority: Priority

    /**
     * Stops all the work within the [Process] and terminates it immediately.
     */
    fun kill()
}