    package serie2.part4

    class HashMap<K, V>(initialCapacity: Int = 16, val loadFactor: Float = 0.75f) : Iterable<MutableMap.MutableEntry<K, V>> {

        private class HashNode<K, V>(override val key: K, override var value: V, var next: HashNode<K, V>? = null ) : MutableMap.MutableEntry<K, V> {
            val hc = key.hashCode()
            override fun setValue(newValue: V): V {
                val oldValue = value
                value = newValue
                return oldValue
            }
        }

        private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)

        var size = 0
            private set

        val capacity: Int
            get() = table.size

        fun put(key: K, value: V): V? {
            if (size >= (loadFactor * table.size)) expand()

            val hc = key.hashCode()
            val index = (hc and 0x7fffffff) % table.size
            var node = table[index]

            while (node != null) {
                if (node.key == key) {
                    val old = node.value
                    node.value = value
                    return old
                }
                node = node.next
            }

            val newNode = HashNode(key, value)
            newNode.next = table[index]
            table[index] = newNode
            size++
            return null
        }

        fun getk(key: K): V? {
            val hc = key.hashCode()
            val index = (hc and 0x7fffffff) % table.size
            var node = table[index]

            while (node != null) {
                if (node.key == key) return node.value
                node = node.next
            }
            return null
        }

        fun clear() {
            table = arrayOfNulls(table.size)
            size = 0
        }

        operator fun get(key: K): V? = getk(key)

        operator fun set(key: K, value: V) {
            put(key, value)
        }

        private fun expand() {
            val oldTable = table
            val newCapacity = oldTable.size * 2
            table = arrayOfNulls(newCapacity)

            for (head in oldTable) {
                var node = head
                while (node != null) {
                    val next = node.next
                    val index = (node.hc and 0x7fffffff) % newCapacity

                    node.next = table[index]
                    table[index] = node

                    node = next
                }
            }
        }

        override fun iterator(): Iterator<MutableMap.MutableEntry<K, V>> {
            return object : Iterator<MutableMap.MutableEntry<K, V>> {
                var bucketIndex = 0
                var currentNode: HashNode<K, V>? = null

                init {
                    advance()
                }

                private fun advance() {
                    while (bucketIndex < table.size && table[bucketIndex] == null) {
                        bucketIndex++
                    }
                    currentNode = if (bucketIndex < table.size) table[bucketIndex] else null
                }

                override fun hasNext(): Boolean {
                    return currentNode != null
                }

                override fun next(): MutableMap.MutableEntry<K, V> {
                    val result = currentNode ?: throw NoSuchElementException()
                    currentNode = currentNode!!.next
                    if (currentNode == null) {
                        bucketIndex++
                        advance()
                    }
                    return result
                }
            }
        }
    }
