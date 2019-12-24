import ai.Chromosome
import ai.Genetic
import net.objecthunter.exp4j.ExpressionBuilder
import util.util.getBitMapChartChart
import java.io.File
import java.text.DecimalFormat
import java.util.*


val baseInput = "C:\\dev\\workspace\\ai\\Genetic Algorithm\\src\\main\\resources".replace("\\", "/")
fun main() {
    val resourseAddress = File(baseInput)
    resourseAddress.mkdirs()
    val file = File(resourseAddress, "repeatCount.txt")
    var count: Int
    try {

        count = file.readText().toInt()
        count++
    } catch (e: Exception) {
        count = 1
    }
    file.writeText("" + count)
    val baseFile = File(resourseAddress, count.toString())
    baseFile.mkdirs()
    val fileLog = File(baseFile, "log.txt")

    val expression = ExpressionBuilder("8x-(1/4)x^2")
        .variables("x")
        .build()
    println("numRemainInNaturalSelection from 2 until 5")
    val numRemainInNaturalSelection = readLine()!!.toInt()
    println("repeatTime")

    val repeatTime = readLine()!!.toInt()
    val bufferWriterLog = fileLog.outputStream().bufferedWriter()
    bufferWriterLog.appendln("pay attentoin fitness in crossOver and mutation is Fake because during this process fitness is not calculated ")
    bufferWriterLog.appendln("in reapeatCount : $count")
    bufferWriterLog.appendln("numRemainInNaturalSelection : $numRemainInNaturalSelection")
    bufferWriterLog.appendln("repeatTime : $repeatTime")
    bufferWriterLog.appendln(" ")
    bufferWriterLog.appendln(" ")
    bufferWriterLog.appendln(" ")
    val result = Genetic(6, 0..32, expression, .5f, repeatTime, numRemainInNaturalSelection, bufferWriterLog).run()
    bufferWriterLog.flush()
    bufferWriterLog.close()
    val fileChartMaxEachState = File(baseFile, "Max Each State.png")
    val fileChartLastState = File(baseFile, "last State.png")
    val fileChartGoalFunction = File(baseFile, "goal function.png")
    val fileChartAvrageEachState = File(baseFile, "avrage each state.png")
    //
    convertGoalFuctionDataToImageChart(result.first, fileChartGoalFunction)
    convertAvrageEachStateDataToImageChart(result.third, fileChartAvrageEachState)
    //max each state
    convertMaxEachStateDataToImageChart(result.first, fileChartMaxEachState)
    convertLastStateDataToImageChart(result.second, fileChartLastState)

}

fun convertAvrageEachStateDataToImageChart(x1: ArrayList<Double>, x2: File) {
    val xData = arrayListOf<Double>()
    val yData = arrayListOf<Double>()
    x1.forEachIndexed() { index, value ->
        xData.add(index.toDouble())
        val numberFormat = DecimalFormat("#.00")
        yData.add(numberFormat.format(value).toDouble())

    }
    getBitMapChartChart(
        xData,
        yData,
        "avrage in each state",
        "repeat",
        "f(x)",
        x2.outputStream()
    )
}

fun convertGoalFuctionDataToImageChart(chromosomes: ArrayList<Chromosome>, fileChartGoalFunction: File) {
    val xData = arrayListOf<Double>()
    val yData = arrayListOf<Double>()
    chromosomes.forEachIndexed() { index, chromosome ->
        xData.add(chromosome.returmChromosomeValueInInt().toDouble())
        yData.add(chromosome.fitness)
    }
    getBitMapChartChart(
        xData,
        yData,
        "goal function",
        "x value",
        "f(x)",
        fileChartGoalFunction.outputStream()
    )

}

fun convertLastStateDataToImageChart(chromosomes: ArrayList<Chromosome>, fileChartLastState: File) {
    val xData = arrayListOf<Double>()
    val yData = arrayListOf<Double>()
    chromosomes.forEachIndexed() { index, chromosome ->
        xData.add(index.toDouble())
        yData.add(chromosome.fitness)
    }
    getBitMapChartChart(xData, yData, "population in last state(choromosome is sorted)", "repeat", "f(x)", fileChartLastState.outputStream())
}

fun convertMaxEachStateDataToImageChart(chromosomes: ArrayList<Chromosome>, fileChartMaxEachState: File) {
    val xData = arrayListOf<Double>()
    val yData = arrayListOf<Double>()
    chromosomes.forEachIndexed() { index, chromosome ->
        xData.add(index.toDouble())
        yData.add(chromosome.fitness)
    }
    getBitMapChartChart(xData, yData, "max each state", "repeat", "f(x)", fileChartMaxEachState.outputStream())
}



