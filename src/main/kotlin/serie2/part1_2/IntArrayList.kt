package serie2.part1_2

class IntArrayList(private val capacity: Int) : Iterable<Int> {
    private val data = IntArray(capacity)
    private var head = 0
    private var tail = 0
    private var size = 0
    private var delta = 0

    fun append(x: Int): Boolean {
        if (size == capacity) return false
        data[tail] = x - delta
        tail = (tail + 1) % capacity
        size++
        return true
    }

    fun get(n: Int): Int? {
        if (n < 0 || n >= size) return null
        val index = (head + n) % capacity
        return data[index] + delta
    }

    fun addToAll(x: Int) {
        delta += x
    }

    fun remove(): Boolean {
        if (size == 0) return false
        head = (head + 1) % capacity
        size--
        return true
    }

    override fun iterator(): Iterator<Int> {
        return object : Iterator<Int> {
            private var index = 0
            override fun hasNext(): Boolean = index < size
            override fun next(): Int {
                val value = get(index) ?: throw NoSuchElementException()
                index++
                return value
            }
        }
    }
}