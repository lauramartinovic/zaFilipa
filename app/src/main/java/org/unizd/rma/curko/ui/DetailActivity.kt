package org.unizd.rma.curko.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.unizd.rma.curko.databinding.ActivityDetailBinding
import org.unizd.rma.curko.model.ApodItem

class DetailActivity : AppCompatActivity() {

    private lateinit var b: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        b = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        b.toolbarDetail.setNavigationOnClickListener { finish() }

        val item = intent.getParcelableExtra<ApodItem>("apod")
        if (item != null) {
            val img = item.hdurl ?: item.url ?: item.thumbnail_url
            if (!img.isNullOrBlank()) b.imgFull.load(Uri.parse(img))
            b.txtTitle.text = item.title ?: ""
            b.txtDate.text = item.date ?: ""
            b.txtExplanation.text = item.explanation ?: ""
        }
    }
}
