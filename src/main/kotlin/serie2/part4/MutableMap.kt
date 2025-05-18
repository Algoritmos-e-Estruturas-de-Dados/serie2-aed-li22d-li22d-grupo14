package serie2.part4

interface MutableMap<K,V>: Iterable<MutableMap.MutableEntry<K,V>> {
    interface MutableEntry<K, V>{
        val key: K
        var value: V
        fun setValue(newValue: V): V

        operator fun component1(): K = key
        operator fun component2(): V = value
    }
    val size: Int
    val capacity: Int
    operator fun get(key: K): V?
    fun put(key: K, value: V): V?
}