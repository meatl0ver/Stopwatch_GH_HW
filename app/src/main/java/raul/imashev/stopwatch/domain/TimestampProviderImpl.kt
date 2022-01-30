package raul.imashev.stopwatch.domain

class TimestampProviderImpl : ITimestampProvider {
    override fun getMilliseconds(): Long =
        System.currentTimeMillis()
}