package com.kerite.fission

@Suppress("unused")
class AntiShaker(
    private val interval: Long = DEFAULT_ANTI_SHAKE_INTERVAL
) {
    @Volatile
    private var latestActionTime: Long = 0L

    companion object {
        const val DEFAULT_ANTI_SHAKE_INTERVAL = 1000L
        val GlobalAntiShaker: AntiShaker = AntiShaker(DEFAULT_ANTI_SHAKE_INTERVAL)
    }

    fun antiShake(action: () -> Unit) {
        if (latestActionTime == 0L || System.currentTimeMillis() - latestActionTime >= interval) {
            latestActionTime = System.currentTimeMillis()
            action()
        }
    }

    fun resetInterval() {
        latestActionTime = 0
    }
}