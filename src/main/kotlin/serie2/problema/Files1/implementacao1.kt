package serie2.problema

import java.io.File

data class Ponto(val id: String, val x: Int, val y: Int) {
    override fun toString(): String = "v $id $x $y"
}

class Implementacao1 {
    fun run() {
        var ficheiro1 = emptySet<Ponto>()
        var ficheiro2 = emptySet<Ponto>()

        while (true) {
            print("> ")
            val input = readlnOrNull()?.trim() ?: continue
            val parts = input.split(" ")

            when (parts[0]) {
                "load" -> if (parts.size == 3) {
                    ficheiro1 = carregar(parts[1])
                    ficheiro2 = carregar(parts[2])
                    println("Ficheiros carregados.")
                }
                "union" -> if (parts.size == 2) guardar(parts[1], ficheiro1 union ficheiro2)
                "intersection" -> if (parts.size == 2) guardar(parts[1], ficheiro1 intersect ficheiro2)
                "difference" -> if (parts.size == 2) guardar(parts[1], ficheiro1 subtract ficheiro2)
                "exit" -> return
                else -> println("Comando inválido.")
            }
        }
    }

    private fun carregar(nome: String): Set<Ponto> {
        val pontos = mutableSetOf<Ponto>()
        File("src/main/kotlin/serie2/problema/$nome").forEachLine { linha ->
            val partes = linha.trim().split(" ")
            if (partes.size == 4 && partes[0] == "v") {
                pontos += Ponto(partes[1], partes[2].toInt(), partes[3].toInt())
            }
        }
        return pontos
    }

    private fun guardar(nome: String, pontos: Set<Ponto>) {
        File(nome).printWriter().use { out ->
            for (p in pontos) {
                out.println("${p.x.toDouble()}, ${p.y.toDouble()}")
            }
        }
        println("Resultado guardado em $nome")
    }
}