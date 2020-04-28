package com.mokresh.analyticsdashboard

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mokresh.analyticsdashboard.databinding.ActivityMainBinding
import com.mokresh.analyticsdashboard.databinding.JobItemBinding
import com.mokresh.analyticsdashboard.models.Analytics
import com.mokresh.analyticsdashboard.ui.AnalyticsViewModel
import com.mokresh.analyticsdashboard.utils.SCOPES
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val analyticsViewModel: AnalyticsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        analyticsViewModel.getAnalytics(SCOPES.ALL.scope)
        analyticsViewModel.analyticsResponseBody.observe(this, Observer {
            showJobs(it)

        })


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
