package org.unizd.rma.curko.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.unizd.rma.curko.BuildConfig   // <-- ovdje je OK koristiti BuildConfig
import org.unizd.rma.curko.databinding.ActivityMainBinding
import org.unizd.rma.curko.model.ApodItem

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private val vm: NasaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)

        val adapter = ApodAdapter { item -> openDetails(item) }
        b.recycler.layoutManager = LinearLayoutManager(this)
        b.recycler.adapter = adapter

        // pokreni dohvat odmah s ključem iz BuildConfiga
        vm.refresh(BuildConfig.NASA_API_KEY)

        lifecycleScope.launch {
            vm.state.collectLatest { state ->
                b.progress.isVisible = state.loading
                b.errorBox.isVisible = state.error != null
                b.txtError.text = state.error ?: ""
                if (state.items.isNotEmpty()) adapter.submit(state.items)
            }
        }

        b.btnRetry.setOnClickListener {
            vm.refresh(BuildConfig.NASA_API_KEY)
        }
    }

    private fun openDetails(item: ApodItem) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra("apod", item)
        startActivity(i)
    }
}
