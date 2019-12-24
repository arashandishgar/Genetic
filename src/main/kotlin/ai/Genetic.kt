package ai

import net.objecthunter.exp4j.Expression
import util.util.println
import util.util.saveStateInFile
import util.util.showState
import java.io.BufferedWriter

class Genetic(
    val populationNumber: Int,
    range: IntRange,
    val experestion: Expression,
    mutationPosssible: Float,
    val repeatTime: Int
    , numRemainInNaturalSelection: Int
    , val bufferWriterLog: BufferedWriter
) {
    val population: Population;

    companion object {
    }

    init {
        population = Population(populationNumber, range, experestion, mutationPosssible, numRemainInNaturalSelection)
    }

    fun run(): Triple<ArrayList<Chromosome>, ArrayList<Chromosome>,ArrayList<Double>> {
        val maxChromosomeEachState = arrayListOf<Chromosome>()
        val populationLastState = arrayListOf<Chromosome>()
        val avrageEachState= arrayListOf<Double>()
        repeat(repeatTime) {
            "repeat time : ${it}".println()
            bufferWriterLog.appendln("repeat time : ${it}")
            bufferWriterLog.appendln(" ")

            fitness()
            population.showState("fitness")
            population.saveStateInFile("fitness", bufferWriterLog)
            if (terminate(maxChromosomeEachState,avrageEachState)) {
                populationLastState.addAll(population.population)
                return Triple(maxChromosomeEachState, populationLastState,avrageEachState)
            }

            naturalSelection()
            population.showState("naturalSelection")
            population.saveStateInFile("naturalSelection", bufferWriterLog)
            crossOver()
            population.showState("crossOver")
            population.saveStateInFile("crossOver", bufferWriterLog)
            mutation()
            population.showState("mutation")
            population.saveStateInFile("mutation", bufferWriterLog)
        }
        "repeat time : ${repeatTime}".println()
        bufferWriterLog.appendln("repeat time : ${repeatTime}")
        bufferWriterLog.appendln(" ")
        fitness()
        population.showState("fitness")
        population.saveStateInFile("fitness", bufferWriterLog)
        terminate(maxChromosomeEachState, avrageEachState)
        populationLastState.addAll(population.population)
        avrageEachState.add(population.population.sumByDouble { it.fitness }/population.populationNumber)
        return Triple(maxChromosomeEachState, populationLastState,avrageEachState)
    }

    fun mutation() {
        population.mutation();
    }

    fun crossOver() =
        population.crossOver()


    fun fitness() =
        population.fitness()

    fun naturalSelection() =
        population.natnaturalSelection();

    fun terminate(
        maxChromosomeEachState: ArrayList<Chromosome>,
        avrageEachState: ArrayList<Double>
    ): Boolean {
        return population.terminate().apply {
            val chromosome = population.population.get(0)
            val x = chromosome.returmChromosomeValueInInt()
            val y = chromosome.fitness
            " ".println()
            " ".println()
            avrageEachState.add(population.population.sumByDouble { it.fitness }/population.populationNumber)
            maxChromosomeEachState.add(Chromosome(chromosome.chromesome.clone() as ArrayList<Int>,chromosome.rangeValue).apply {
                fitness=experestion.setVariable("x",returmChromosomeValueInInt().toDouble()).evaluate()
            })
            population.showState("terminate")
            population.saveStateInFile("terminate", bufferWriterLog)
            "goal test y : $y x : $x".println()
            bufferWriterLog.appendln(" ")
            bufferWriterLog.appendln("goal test y : $y x : $x")
            bufferWriterLog.appendln(" ")
        }
    }

}