package process

/**
 * Simple implementation of a [Process] that requires a preset [Priority] for it's execution.
 */
data class DefaultProcess(
    override val priority: Priority,
    override val id: ProcessId = ProcessId()
) : Process {

    override fun kill() {
        // Stop all work and destroy the process.
    }

}