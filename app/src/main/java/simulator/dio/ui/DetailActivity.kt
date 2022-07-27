package simulator.dio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import simulator.dio.databinding.ActivityDetailBinding
import simulator.dio.databinding.ActivityDetailBinding.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    object Extras{
        const val MATCH = "EXTRA_MATCH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}