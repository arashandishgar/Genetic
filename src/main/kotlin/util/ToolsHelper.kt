package util.util

import ai.Population
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.style.Styler
import java.awt.Color
import java.io.BufferedWriter
import java.io.FileOutputStream
import kotlin.random.Random

fun Any.println() = println(this)
fun Any.print() = print(this)

fun Int.printBinaryString() {
    for (i in Int_DIGIT_RANGE.reversed()) {
        print(this findDigit i)
    }
}

fun Int.printBinaryStringUntilLenght() {
    for (i in (1..this.numberOfBitWithOutSign()).reversed()) {
        print(this findDigit i)
    }
}

fun generateIntRandomFromZero(untilRange: Int) = Random.Default.nextInt(untilRange)


fun  Population.showState(fromMethod:String) {
    this.population.forEachIndexed() { index, chromosome ->
        "$fromMethod index : $index  y : ${chromosome.fitness}  x : ${chromosome.returmChromosomeValueInInt()}".println()

    }
    println(" ")
}
fun  Population.saveStateInFile(fromMethod:String, bufferedWriter: BufferedWriter) {
    this.population.forEachIndexed() { index, chromosome ->
        bufferedWriter.appendln("$fromMethod index : $index  y : ${chromosome.fitness}  x : ${chromosome.returmChromosomeValueInInt()}")
    }
    bufferedWriter.appendln(" ")
}

fun getBitMapChartChart(xData:ArrayList<Double>,yData:ArrayList<Double>,tiltle:String,xAxisTitleSting:String,yAxisisTitleString:String,fileDestination: FileOutputStream) { // Create Chart
    val chart =
        CategoryChartBuilder()
            .width(1500)
            .height(1000)
            .title(tiltle)
            .xAxisTitle(xAxisTitleSting)
            .yAxisTitle(yAxisisTitleString)
            .build()
    chart.styler.setYAxisMax(64.0)
    chart.styler.setXAxisMax(31.0)
    chart.styler.setChartFontColor(Color.RED)
    // Customize Chart
    chart.styler.legendPosition = Styler.LegendPosition.InsideNW
    chart.styler.setHasAnnotations(true)
    // Series

    chart.addSeries(
        " ",
        xData,
        yData
    ).setFillColor(Color.BLUE)
    BitmapEncoder.saveBitmap(chart, fileDestination, BitmapEncoder.BitmapFormat.PNG);
}
