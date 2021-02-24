package process

import clock.Clock

/**
 * A [Process] which has been scheduled at the [timestamp] mentioned.
 */
data class ScheduledProcess(
    val timestamp: Long,
    private val process: Process
) : Process by process

fun Process.schedule(clock: Clock) = ScheduledProcess(clock.now(), this)
