package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null)

fun splitEvensAndOdds(list: Node<Int>) {
    if (list.next == list) return  // lista vazia

    var current = list.next
    val sentinel = list
    var lastEven: Node<Int>? = sentinel

    do {
        val next = current!!.next
        if (current.value % 2 == 0) {
            // Remove current
            current.previous!!.next = current.next
            current.next!!.previous = current.previous

            // Insere após lastEven
            current.next = lastEven!!.next
            current.previous = lastEven
            lastEven.next!!.previous = current
            lastEven.next = current

            lastEven = current
        }
        current = next
    } while (current != sentinel)
}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    val sentinel1 = list1
    val sentinel2 = list2
    var curr1 = sentinel1.next
    var curr2 = sentinel2.next

    // Result list with sentinel node (circular at first)
    val resultSentinel = Node<T>()
    resultSentinel.next = resultSentinel
    resultSentinel.previous = resultSentinel
    var last = resultSentinel

    while (curr1 != sentinel1 && curr2 != sentinel2) {
        val compare = cmp.compare(curr1!!.value, curr2!!.value)
        when {
            compare < 0 -> curr1 = curr1.next
            compare > 0 -> curr2 = curr2.next
            else -> {
                val next1 = curr1.next
                val next2 = curr2.next

                // Remove curr1 from list1
                curr1.previous!!.next = curr1.next
                curr1.next!!.previous = curr1.previous

                // Remove curr2 from list2
                curr2!!.previous!!.next = curr2.next
                curr2.next!!.previous = curr2.previous

                // Insert curr1 into result list
                curr1.previous = last
                curr1.next = resultSentinel
                last.next = curr1
                resultSentinel.previous = curr1
                last = curr1

                // Advance skipping duplicates
                do {
                    curr1 = next1
                } while (curr1 != sentinel1 && cmp.compare(curr1!!.value, last.value) == 0)

                do {
                    curr2 = next2
                } while (curr2 != sentinel2 && cmp.compare(curr2!!.value, last.value) == 0)
            }
        }
    }

    // No matches found
    if (resultSentinel.next == resultSentinel) return null

    // Single element case — break circular link
    if (resultSentinel.next!!.next == resultSentinel) {
        val only = resultSentinel.next!!
        only.next = null
        only.previous = null
        return only
    }

    // General case — break circular structure and return head
    val head = resultSentinel.next
    val tail = resultSentinel.previous
    head!!.previous = null
    tail!!.next = null
    return head
}