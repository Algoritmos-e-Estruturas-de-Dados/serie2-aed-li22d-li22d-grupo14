package serie2.problema

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    println("Escolha a implementação:")
    println("1 - Kotlin Standard Library")
    println("2 - Estrutura da questão I.4")
    print("> ")

    when (scanner.nextLine()) {
        "1" -> Implementacao1().run()
        "2" -> Implementacao2().run()
        else -> println("Opção inválida.")
    }
}