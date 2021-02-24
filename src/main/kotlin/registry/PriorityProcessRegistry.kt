package registry

import clock.Clock
import process.Priority
import process.Process
import process.ScheduledProcess
import process.schedule
import registry.ProcessRegistry.Companion.CAPACITY

class PriorityProcessRegistry(
    private val clock: Clock
) : ArrayDequeProcessRegistry() {

    override fun offer(process: Process): Boolean {
        if (data.size >= CAPACITY) {
            val lowestPriority = data.last().priority
            if (process.priority > lowestPriority) {
                removeOldestWithPriority(lowestPriority)
            } else return false
        }
        add(process.schedule(clock))
        return true
    }


    private fun add(newProcess: ScheduledProcess) {
        return data.indexOfFirst { newProcess.priority > it.priority }
            .let { index ->
                when {
                    index != -1 -> data.add(index, newProcess)
                    else -> data.addLast(newProcess)
                }
            }
    }

    private fun removeOldestWithPriority(priority: Priority) {
        data.first { it.priority == priority }
            .let { oldest ->
                data.remove(oldest)
            }
    }
}
