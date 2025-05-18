package serie2.problema

import serie2.part4.HashMap
import java.io.File

data class Ponto2(val id: String, val x: Int, val y: Int) {
    override fun toString() = "v $id $x $y"
}

class Implementacao2 {
    private val pontos = HashMap<Ponto2, Boolean>()

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
                    val resultado = mutableSetOf<Ponto2>()
                    for ((ponto, _) in pontos) {
                        resultado.add(ponto)
                    }
                    guardar(parts[1], resultado.toList())
                }

                "intersection" -> if (parts.size == 2) {
                    val mapa1 = mutableSetOf<Ponto2>()
                    val mapa2 = mutableSetOf<Ponto2>()

                    for ((p, veioDo1) in pontos) {
                        if (veioDo1) mapa1.add(p) else mapa2.add(p)
                    }

                    val resultado = mapa1.intersect(mapa2)
                    guardar(parts[1], resultado.toList())
                }

                "difference" -> if (parts.size == 2) {
                    val mapa2 = mutableSetOf<Ponto2>()
                    val resultado = mutableListOf<Ponto2>()

                    for ((p, veioDo1) in pontos) {
                        if (!veioDo1) mapa2.add(p)
                    }

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
                pontos[ponto] = isFirst
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