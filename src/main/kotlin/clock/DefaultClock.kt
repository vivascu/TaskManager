package clock

import java.util.Date

class DefaultClock : Clock {
    override fun now(): Long = Date().time
}
