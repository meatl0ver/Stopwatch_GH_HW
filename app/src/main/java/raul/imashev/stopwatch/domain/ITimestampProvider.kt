package raul.imashev.stopwatch.domain

interface ITimestampProvider {
    fun getMilliseconds(): Long
}