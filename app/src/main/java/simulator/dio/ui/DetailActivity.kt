package simulator.dio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import simulator.dio.databinding.ActivityDetailBinding
import simulator.dio.databinding.ActivityDetailBinding.*
import simulator.dio.domain.Match

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

        loadMatchFromExtra()
    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let{
            Glide.with( this).load(it.place.image).into(binding.ivPlace)
            supportActionBar?.title = it.place.name

            binding.tvDescription.text = it.description

            Glide.with( this ).load(it.homeTeam.image).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.homeTeam.name
            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if (it.homeTeam.score != null){
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            }

            Glide.with( this ).load(it.AwayTeam.image).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.AwayTeam.name
            binding.rbAwayTeamStars.rating = it.AwayTeam.stars.toFloat()

            if (it.AwayTeam.score != null){
                binding.tvAwayTeamScore.text = it.AwayTeam.score.toString()
            }
        }
    }


}