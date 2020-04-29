package com.mokresh.analyticsdashboard.utils

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.DrawableContainer
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import com.mokresh.analyticsdashboard.R
import com.mokresh.analyticsdashboard.databinding.JobItemBinding
import com.mokresh.analyticsdashboard.models.Analytics
import com.mokresh.analyticsdashboard.models.PieChartItem
import java.util.*

object Utils {


    // set up data for pie chart
    fun setPieChartData(lineChartItemsList: List<PieChartItem>, chart: PieChart) {
        val entries = ArrayList<PieEntry>()
        for (item in lineChartItemsList) {
            entries.add(
                PieEntry(item.pieChartItemValue.toFloat(), item.pieChartItemKey)
            )
        }
        val dataSet = PieDataSet(entries, "")

        // add colors
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.MATERIAL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextColor(Color.WHITE)
        chart.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }





    fun showError(@StringRes errorMessage: Int,view: View) {
        val errorSnackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.show()
    }

}