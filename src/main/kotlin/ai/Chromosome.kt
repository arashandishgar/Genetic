package ai

import util.util.findDigit
import util.util.generateIntRandomFromZero
import util.util.getOpsiteValue
import util.util.numberOfBitWithOutSign
import kotlin.random.Random

private typealias Gene = Int

data class Chromosome(val initializedNum: Int = 0, val rangeValue: IntRange) {
    constructor(ch: ArrayList<Gene>, intRange: IntRange) : this(rangeValue = intRange) {
        chromesome = ch;

    }

    //because for complex concatination ,i decide to use array list
    val arraySize = rangeValue.last.numberOfBitWithOutSign()
    var chromesome = arrayListOf<Gene>()
    var fitness = 0.0
    var sizeInheritanceFromParent = -1

    init {

        createChromesomeFromintilaizedNum(initializedNum)
    }

    private fun createChromesomeFromintilaizedNum(rawValue: Int) {

        repeat(rawValue.numberOfBitWithOutSign()) {
            chromesome.add(rawValue.findDigit(it + 1))
        }
        chromesome.reverse()
        repeat(arraySize - rawValue.numberOfBitWithOutSign()) {
            chromesome.add(0, 0)
        }
    }

    fun returmChromosomeValueInInt(): Int {
        var result = 0.0
        chromesome.reverse()
        chromesome.forEachIndexed() { index, i ->
            result += Math.pow(2.toDouble(), index.toDouble()) * i
        }
        chromesome.reverse()
        return result.toInt();
    }

    fun mutation() {
        var randomIndex1 = generateIntRandomFromZero(chromesome.size)
        var randomIndex2 = 0
        do {
            randomIndex2 = generateIntRandomFromZero(chromesome.size)
        } while (randomIndex1 == randomIndex2)
        if (sizeInheritanceFromParent != -1) {
            randomIndex1 = generateIntRandomFromZero(sizeInheritanceFromParent)
            randomIndex2 = Random.Default.nextInt(sizeInheritanceFromParent, arraySize)

        }
        var gene1 = chromesome.get(randomIndex1)
        val gene2 = chromesome.get(randomIndex2)
        if (gene1 == gene2) {
            gene1 = gene1.getOpsiteValue()
        }
        chromesome.set(randomIndex1, gene2)
        chromesome.set(randomIndex2, gene1)
        while (returmChromosomeValueInInt() > rangeValue.last) {
            mutation();
        }
    }

    fun calcaulateFitness(fitness: () -> Double) {
        this.fitness = fitness()

    }

    fun crossOver(other: Chromosome): Chromosome {
        var randomSize = 0
        do {
            //not need minus 1 array size because of arrayIndex in sublist solve this problem
            randomSize = generateIntRandomFromZero(this.arraySize)
        } while (randomSize == 0)
        val temp1 = arrayListOf<Gene>()
        val temp2 = arrayListOf<Gene>()
        temp1.addAll(this.chromesome.subList(0, randomSize))
        temp2.addAll(other.chromesome.subList(randomSize, this.arraySize))
        temp1.addAll(temp2)
        return Chromosome(temp1, this.rangeValue).apply {
            sizeInheritanceFromParent = randomSize
        }
    }
}
