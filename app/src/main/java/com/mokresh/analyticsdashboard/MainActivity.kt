package com.mokresh.analyticsdashboard

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.EntryXComparator
import com.mokresh.analyticsdashboard.databinding.ActivityMainBinding
import com.mokresh.analyticsdashboard.databinding.JobItemBinding
import com.mokresh.analyticsdashboard.models.Analytics
import com.mokresh.analyticsdashboard.ui.AnalyticsViewModel
import com.mokresh.analyticsdashboard.utils.SCOPES
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val analyticsViewModel: AnalyticsViewModel by viewModel()

    var xVals: ArrayList<String> = ArrayList<String>()
    var yValsjobs = ArrayList<Entry>()
    var yValsServices = ArrayList<Entry>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        analyticsViewModel.getAnalytics(SCOPES.ALL.scope)
        analyticsViewModel.analyticsResponseBody.observe(this, Observer {
            showJobs(it)

            setData(it)
        })


    }


    private fun setData(analytics: Analytics) {

        binding.lineChartTitleTextView.text = analytics.lineCharts.get(0).get(0).lineChartTitle
        binding.lineChartDescriptionTextView.text =
            analytics.lineCharts.get(0).get(0).lineChartDescription

        for ((index, value) in analytics.lineCharts[0].withIndex()) {
            for ((i, v) in value.lineChartItemsList.withIndex()) {

                xVals.add(v.lineChartItemKey)
                yValsjobs.add(Entry(v.lineChartItemValue[0].value.toFloat(), i.toFloat()))
                yValsServices.add(Entry(v.lineChartItemValue[1].value.toFloat(), i.toFloat()))

            }
        }

        Collections.sort(yValsjobs, EntryXComparator())
        Collections.sort(yValsServices, EntryXComparator())

        val set1: LineDataSet
        val set2: LineDataSet
        if (binding.lineChart.data != null &&
            binding.lineChart.data.dataSetCount > 0
        ) {
            set1 = binding.lineChart.data.getDataSetByIndex(0) as LineDataSet
            set2 = binding.lineChart.data.getDataSetByIndex(1) as LineDataSet
            set1.values = yValsjobs
            set2.values = yValsServices
            binding.lineChart.data.notifyDataChanged()
            binding.lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yValsjobs, "Jobs")
            set1.axisDependency = AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.WHITE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)
//            set1.setFillFormatter( MyFillFormatter(0f));
//            set1.setDrawHorizontalHighlightIndicator(false);
//            set1.setVisible(false);
//            set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = LineDataSet(yValsServices, "Services")
            set2.axisDependency = AxisDependency.RIGHT
            set2.color = Color.RED
            set2.setCircleColor(Color.WHITE)
            set2.lineWidth = 2f
            set2.circleRadius = 3f
            set2.fillAlpha = 65
            set2.fillColor = Color.RED
            set2.setDrawCircleHole(false)
            set2.highLightColor = Color.rgb(244, 117, 117)
            //set2.setFillFormatter(new MyFillFormatter(900f));


            // create a data object with the data sets
            val data = LineData(set1, set2)
            data.setValueTextColor(Color.WHITE)
            data.setValueTextSize(9f)

            // set data
            binding.lineChart.data = data


            val formatter: ValueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    return xVals[value.toInt()]
                }

                // we don't draw numbers, so no decimal digits needed
                val decimalDigits: Int
                    get() = 0
            }

            val xAxis: XAxis = binding.lineChart.xAxis
            xAxis.granularity = 1f // minimum axis-step (interval) is 1

            xAxis.valueFormatter = formatter

        }
    }


    // show jobs in custom layout
    private fun showJobs(analytics: Analytics) {
        val viewGroup =
            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

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
                bin.totalTextView.text = getString(R.string.total) + items.jobItemTotal
            }
            bin.growthTextView.text = items.jobItemGrowth.toString()

            val checkParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            binding.jobsLinearLayout.addView(bin.root, checkParams)


        }
    }

}
