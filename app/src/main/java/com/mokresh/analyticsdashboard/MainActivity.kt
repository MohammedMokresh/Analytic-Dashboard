package com.mokresh.analyticsdashboard

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mokresh.analyticsdashboard.databinding.ActivityMainBinding
import com.mokresh.analyticsdashboard.databinding.JobItemBinding
import com.mokresh.analyticsdashboard.databinding.PieChartItemBinding
import com.mokresh.analyticsdashboard.models.Analytics
import com.mokresh.analyticsdashboard.models.PieChartItem
import com.mokresh.analyticsdashboard.ui.AnalyticsViewModel
import com.mokresh.analyticsdashboard.utils.SCOPES
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val analyticsViewModel: AnalyticsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.navigation_all -> {
                            analyticsViewModel.getAnalytics(SCOPES.ALL.scope)

                            return true
                        }
                        R.id.navigation_today -> {
                            analyticsViewModel.getAnalytics(SCOPES.TODAY.scope)

                            return true
                        }

                        R.id.navigation_7_days -> {
                            analyticsViewModel.getAnalytics(SCOPES.LAST_7_DAYS.scope)

                            return true
                        }

                        R.id.navigation_30_days -> {
                            analyticsViewModel.getAnalytics(SCOPES.LAST_30_DAYS.scope)

                            return true
                        }

                    }
                    return false
                }
            }
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

        analyticsViewModel.getAnalytics(SCOPES.ALL.scope)
        analyticsViewModel.analyticsResponseBody.observe(this, Observer {
            showJobs(it)
            lineChart(it)
            showPieCharts(it)
            showServices(it)
            rating(it)
        })


    }


    private fun showPieCharts(analytics: Analytics) {
        val viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

        binding.pieChartsLineaLayout.removeAllViews()
        if (analytics.pieCharts != null) {
            binding.pieChartCardView.visibility=View.VISIBLE

            for (items in analytics.pieCharts) {
                val bin = DataBindingUtil.inflate<PieChartItemBinding>(
                    layoutInflater,
                    R.layout.pie_chart_item,
                    viewGroup,
                    false
                )
                bin.pieChartTitleTextView.text = items.pieChartTitle
                bin.pieChartDescriptionTextView.text = items.pieChartDescription

                setPieChartData(items.pieChartItemsList, bin.pieChart)

                val checkParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                binding.pieChartsLineaLayout.addView(bin.root, checkParams)


            }

        }else{
            binding.pieChartCardView.visibility=View.GONE
        }

    }


    private fun setPieChartData(lineChartItemsList: List<PieChartItem>, chart: PieChart) {
        val entries = ArrayList<PieEntry>()
        for (item in lineChartItemsList) {
            entries.add(
                PieEntry(item.pieChartItemValue.toFloat(), item.pieChartItemKey)
            )
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0F, 40F)
        dataSet.selectionShift = 5f

        // add a lot of colors
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
        colors.add(ColorTemplate.getHoloBlue())
        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data

        // undo all highlights
        chart.highlightValues(null)
        chart.invalidate()
    }


    private fun lineChart(analytics: Analytics) {
        if (analytics.lineCharts != null) {
            binding.lineChartCardView.visibility=View.VISIBLE

            val xVal: ArrayList<String> = ArrayList<String>()
            val yValJobs = ArrayList<Entry>()
            val yValServices = ArrayList<Entry>()



            binding.lineChartTitleTextView.text = analytics.lineCharts[0][0].lineChartTitle
            binding.lineChartDescriptionTextView.text =
                analytics.lineCharts[0][0].lineChartDescription

            for (value in analytics.lineCharts[0]) {
                for ((i, v) in value.lineChartItemsList.withIndex()) {

                    xVal.add(v.lineChartItemKey)
                    yValJobs.add(Entry(v.lineChartItemValue[0].value.toFloat(), i.toFloat()))
                    yValServices.add(Entry(v.lineChartItemValue[1].value.toFloat(), i.toFloat()))

                }
            }

            Collections.sort(yValJobs, EntryXComparator())
            Collections.sort(yValServices, EntryXComparator())

            val set1: LineDataSet
            val set2: LineDataSet
            if (binding.lineChart.data != null &&
                binding.lineChart.data.dataSetCount > 0
            ) {
                set1 = binding.lineChart.data.getDataSetByIndex(0) as LineDataSet
                set2 = binding.lineChart.data.getDataSetByIndex(1) as LineDataSet
                set1.values = yValJobs
                set2.values = yValServices
                binding.lineChart.data.notifyDataChanged()
                binding.lineChart.notifyDataSetChanged()
            } else {
                // create a dataset and give it a type
                set1 = LineDataSet(yValJobs, "Jobs")
                set1.axisDependency = AxisDependency.LEFT
                set1.color = ColorTemplate.getHoloBlue()
                set1.setCircleColor(Color.WHITE)
                set1.lineWidth = 2f
                set1.circleRadius = 3f
                set1.fillAlpha = 65
                set1.fillColor = ColorTemplate.getHoloBlue()
                set1.highLightColor = Color.rgb(244, 117, 117)
                set1.setDrawCircleHole(false)


                // create a dataset and give it a type
                set2 = LineDataSet(yValServices, "Services")
                set2.axisDependency = AxisDependency.RIGHT
                set2.color = Color.RED
                set2.setCircleColor(Color.WHITE)
                set2.lineWidth = 2f
                set2.circleRadius = 3f
                set2.fillAlpha = 65
                set2.fillColor = Color.RED
                set2.setDrawCircleHole(false)
                set2.highLightColor = Color.rgb(244, 117, 117)


                // create a data object with the data sets
                val data = LineData(set1, set2)
                data.setValueTextColor(Color.WHITE)
                data.setValueTextSize(9f)

                // set data
                binding.lineChart.data = data


                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float, axis: AxisBase): String {
                        return xVal[value.toInt()]
                    }
                }

                val xAxis: XAxis = binding.lineChart.xAxis
                xAxis.granularity = 1f // minimum axis-step (interval) is 1

                xAxis.valueFormatter = formatter

            }
        }else{
            binding.lineChartCardView.visibility=View.GONE
        }

    }

    // show jobs in custom layout
    private fun showJobs(analytics: Analytics) {
        val viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        binding.jobsLinearLayout.removeAllViews()

        if (analytics.job != null) {
            binding.jobsCardView.visibility=View.VISIBLE

            binding.jobsTitleTextView.text = analytics.job.jobTitle
            binding.jobsDescriptionTextView.text = analytics.job.jobDescription

            for (items in analytics.job.jobItemsList) {
                val bin = DataBindingUtil.inflate<JobItemBinding>(
                    layoutInflater,
                    R.layout.job_item,
                    viewGroup,
                    false
                )
                bin.titleTextView.text = items.jobItemTitle
                bin.descriptionTextView.text = items.jobItemDescription

                if (items.jobItemTotal != null) {
                    val total = getString(R.string.total) + items.jobItemTotal
                    bin.totalTextView.text = total
                }
                bin.growthTextView.text = items.jobItemGrowth.toString()

                val checkParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                binding.jobsLinearLayout.addView(bin.root, checkParams)

            }
        }else{
            binding.jobsCardView.visibility=View.GONE
        }

    }

    private fun showServices(analytics: Analytics) {
        val viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        binding.servicesLinearLayout.removeAllViews()

        if (analytics.service != null) {
            binding.servicesCardView.visibility=View.VISIBLE

            binding.servicesTitleTextView.text = analytics.service.serviceTitle
            binding.servicesDescriptionTextView.text = analytics.service.serviceDescription

            for (items in analytics.service.serviceItemsList) {
                val bin = DataBindingUtil.inflate<JobItemBinding>(
                    layoutInflater,
                    R.layout.job_item,
                    viewGroup,
                    false
                )
                bin.titleTextView.text = items.serviceItemTitle
                bin.descriptionTextView.text = items.serviceItemDescription

                if (items.serviceItemTotal != null) {
                    val total = getString(R.string.total) + items.serviceItemTotal
                    bin.totalTextView.text = total
                }
                bin.growthTextView.text = items.serviceItemGrowth.toString()

                val checkParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                binding.servicesLinearLayout.addView(bin.root, checkParams)


            }
        }else{
            binding.servicesCardView.visibility=View.GONE
        }

    }


    private fun rating(analytics: Analytics) {
        if (analytics.rating != null) {
            binding.ratingCardView.visibility=View.VISIBLE

            val values = ArrayList<BarEntry>()
            binding.ratingTitleTextView.text = analytics.rating.ratingTitle
            binding.ratingDescriptionTextView.text = analytics.rating.ratingDescription

            values.add(
                BarEntry(1f, analytics.rating.ratingItems.oneStarRating.toFloat())
            )
            values.add(
                BarEntry(2f, analytics.rating.ratingItems.twoStarRating.toFloat())

            )
            values.add(
                BarEntry(3f, analytics.rating.ratingItems.threeStarRating.toFloat())

            )
            values.add(
                BarEntry(4f, analytics.rating.ratingItems.fourStarRating.toFloat())

            )
            values.add(
                BarEntry(5f, analytics.rating.ratingItems.fiveStarRating.toFloat())

            )
            val set1: BarDataSet
            if (binding.ratingChart.data != null &&
                binding.ratingChart.data.dataSetCount > 0
            ) {
                set1 = binding.ratingChart.data.getDataSetByIndex(0) as BarDataSet
                set1.values = values
                binding.ratingChart.data.notifyDataChanged()
                binding.ratingChart.notifyDataSetChanged()
            } else {
                set1 = BarDataSet(values, "Rating")
                set1.setDrawIcons(false)

                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(set1)
                val data = BarData(dataSets)
                data.setValueTextSize(10f)
                data.barWidth = 0.9f
                binding.ratingChart.data = data


                val quarters =
                    arrayOf("1 star", "2 stars", "3 stars", "4 stars", "5 stars")

                val formatter: ValueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float, axis: AxisBase?): String? {
                        return quarters[value.toInt()]
                    }
                }

                val xAxis: XAxis = binding.ratingChart.xAxis
                xAxis.granularity = 1f // minimum axis-step (interval) is 1

                xAxis.valueFormatter = formatter
            }
        }else{
            binding.ratingCardView.visibility=View.GONE
        }


    }


}
