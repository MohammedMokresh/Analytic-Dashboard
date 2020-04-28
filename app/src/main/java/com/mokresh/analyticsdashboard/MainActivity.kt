package com.mokresh.analyticsdashboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mokresh.analyticsdashboard.databinding.ActivityMainBinding
import com.mokresh.analyticsdashboard.ui.AnalyticsViewModel
import com.mokresh.analyticsdashboard.utils.SCOPES
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val analyticsViewModel:AnalyticsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)




    }

}
