package ai

import net.objecthunter.exp4j.Expression
import util.util.generateIntRandomFromZero

class Population(
    val populationNumber: Int,
    val range: IntRange,
    val experestion: Expression,
    val mutationPosssible: Float,
    val numberRemianInNaturalSelection: Int
) {
    val population: ArrayList<Chromosome>

    init {
        population = arrayListOf()
        initizlizePopulation()

    }

    fun initizlizePopulation() {
        var chromosome: Chromosome
        repeat(populationNumber) {
            var random = 0
            do {
                //plus one becuse until last
                random = generateIntRandomFromZero( range.last + 1)
                chromosome = Chromosome(random, range)
            } while (population.contains(chromosome))
            population.add(chromosome)
        }
    }

    fun mutation() {
        population.forEach { choromosome ->
            val random = Math.random()
            if (mutationCanHappend(random)) {
                choromosome.mutation()
            }
        }

    }


    fun mutationCanHappend(random: Double) = random <= mutationPosssible


    fun crossOver() {
        val numArrayAdd = populationNumber - numberRemianInNaturalSelection
        repeat(numArrayAdd) {
            val index = it + numberRemianInNaturalSelection
            val random1 = generateIntRandomFromZero(index)
            var random2 = 0
            do {
                random2 = generateIntRandomFromZero(index);
            } while (random2 == random1)
            population.add(population.get(random1).crossOver( population.get(random2)))

        }

    }

    fun fitness() = population.forEach() { choromosome ->
        choromosome.calcaulateFitness {
            experestion.setVariable("x", choromosome.returmChromosomeValueInInt().toDouble()).evaluate()
        }
    }

    fun natnaturalSelection() {
        population.sortBy { it.fitness }
        population.reverse()


        for (i in numberRemianInNaturalSelection until populationNumber) {
            population.removeAt(population.lastIndex)
        }


    }

    fun terminate(): Boolean = goalFunction()

    private fun goalFunction(): Boolean {
        population.sortBy { it.fitness }
        population.reverse()
        val y = population.get(0).fitness
        val x = population.get(0).returmChromosomeValueInInt()
        val x1 = x - 1
        val x2 = x + 1
        val y1 = experestion.setVariable("x", x1 * 1.0).evaluate()
        val y2 = experestion.setVariable("x", x2 * 1.0).evaluate()
        /*showState("terminate");*/

        if (y > y1 && y > y2) {
            return true
        }
        return false
    }
}
