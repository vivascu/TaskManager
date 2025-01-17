package process

import java.util.UUID

/**
 * An unique identifier of a process.
 * The default value will be set to a random [UUID].
 */
data class ProcessId(val value: String = UUID.randomUUID().toString())
