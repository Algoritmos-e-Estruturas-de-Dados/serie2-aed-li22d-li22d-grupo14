package serie2.problema

import java.io.File

data class Ponto2(val id: String, val x: Int, val y: Int) {
    override fun toString() = "v $id $x $y"
}

class Implementacao2 {
    // Lista com todos os pontos (pode haver pontos com mesmo id, coords diferentes)
    private val pontos = mutableListOf<Pair<Ponto2, Boolean>>() // Boolean = veio do 1º ficheiro

    fun run() {
        while (true) {
            print("> ")
            val input = readlnOrNull()?.trim() ?: continue
            val parts = input.split(" ")

            when (parts[0]) {
                "load" -> if (parts.size == 3) {
                    pontos.clear()
                    carregar(parts[1], isFirst = true)
                    carregar(parts[2], isFirst = false)
                    println("Ficheiros carregados.")
                }

                "union" -> if (parts.size == 2) {
                    // Construir resultado sem eliminar pontos diferentes com mesmo id,
                    // mas eliminar duplicados EXATOS (mesmo id, x e y)
                    val resultado = mutableListOf<Ponto2>()
                    val vistos = mutableSetOf<Ponto2>()

                    for ((ponto, _) in pontos) {
                        if (!vistos.contains(ponto)) {
                            resultado.add(ponto)
                            vistos.add(ponto)
                        }
                    }

                    guardar(parts[1], resultado)
                }

                "intersection" -> if (parts.size == 2) {
                    // Pontos que aparecem em ambos ficheiros exatamente iguais
                    val resultado = mutableListOf<Ponto2>()
                    val mapa1 = pontos.filter { it.second }.map { it.first }.toSet()
                    val mapa2 = pontos.filter { !it.second }.map { it.first }.toSet()

                    for (p in mapa1) {
                        if (p in mapa2) resultado.add(p)
                    }

                    guardar(parts[1], resultado)
                }

                "difference" -> if (parts.size == 2) {
                    // Pontos que estão só no 1º ficheiro, não aparecem no 2º
                    val resultado = mutableListOf<Ponto2>()
                    val mapa2 = pontos.filter { !it.second }.map { it.first }.toSet()

                    for ((p, veioDo1) in pontos) {
                        if (veioDo1 && p !in mapa2 && p !in resultado) {
                            resultado.add(p)
                        }
                    }

                    guardar(parts[1], resultado)
                }

                "exit" -> return

                else -> println("Comando inválido.")
            }
        }
    }

    private fun carregar(nome: String, isFirst: Boolean) {
        File("src/main/kotlin/serie2/problema/$nome").forEachLine { linha ->
            val partes = linha.trim().split(" ")
            if (partes.size == 4 && partes[0] == "v") {
                val ponto = Ponto2(partes[1], partes[2].toInt(), partes[3].toInt())
                pontos.add(ponto to isFirst)
            }
        }
    }

    private fun guardar(nome: String, pontos: List<Ponto2>) {
        File(nome).printWriter().use { out ->
            for (p in pontos) {
                out.println("${p.x.toDouble()}, ${p.y.toDouble()}")
            }
        }
        println("Resultado guardado em $nome")
    }
}