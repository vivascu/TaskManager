package registry

import process.Priority
import process.Process
import registry.ProcessRegistry.Companion.CAPACITY

class PriorityProcessRegistry : ProcessRegistry {
    private val data = ArrayDeque<Process>()

    override fun offer(process: Process): Boolean {
        if (data.size >= CAPACITY) {
            val lowestPriority = data.last().priority
            if (process.priority > lowestPriority) {
                removeOldestWithPriority(lowestPriority)
            } else return false
        }
        add(process)
        return true
    }


    private fun add(newProcess: Process) {
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

    override fun remove(process: Process): Boolean {
        return data.remove(process)
    }

    override fun clear() {
        data.clear()
    }

    override fun toList(): List<Process> = data.toList()
}
