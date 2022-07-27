package simulator.dio.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import simulator.dio.R;
import simulator.dio.data.MatchesAPI;
import simulator.dio.databinding.ActivityMainBinding;
import simulator.dio.domain.Match;
import simulator.dio.ui.adapter.MatchesAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesAPI matchesAPI;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHTTPClient();
        setupMatchesList();

        setupMatchesRefresh();

        setupFloatingActionButton();
    }

    private void setupHTTPClient() {
        // esse método cria uma conexão com a api e converte ele para que o app possa compreender
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://laryssabeatriz.github.io/matches-simulator-api/matches.json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //retorna uma instância definitiva e retorna para a interface
        matchesAPI = retrofit.create(MatchesAPI.class);

    }

    private void setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener( view -> {
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                   Random random = new Random();
                   for(int i = 0; i< matchesAdapter.getItemCount();i++){
                    Match match = matchesAdapter.getMatches().get(i);
                    match.getHomeTeam().setScore(random.nextInt( match.getHomeTeam().getStarts() + 1));
                    match.getHomeTeam().setScore(random.nextInt( match.getAwayTeam().getStarts() + 1));
                    matchesAdapter.notifyItemChanged(i);
                   }
                }
            });

        });
    }

    private void setupMatchesRefresh() {
        //faz consumo da API
       binding.srlMatches.setOnRefreshListener(this::findMatchesFromApi);
    }

    private void setupMatchesList(){
        binding.rvMatches.setHasFixedSize(true);
        binding.rvMatches.setLayoutManager(new LinearLayoutManager(this));

        // o callback retorna qual a resposta do sistema, se deu certo ou se ocorreu erro e capta o que fazer
        findMatchesFromApi();
    }

    private void findMatchesFromApi() {
        //diz que o refresh é true e o ícone tem que aparecer
        binding.srlMatches.setRefreshing(true);
        matchesAPI.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if(response.isSuccessful()){
                    List<Match> matches = response.body();
                    matchesAdapter = new MatchesAdapter((matches));
                    binding.rvMatches.setAdapter(matchesAdapter);
                }else {
                    shoutErrorMessage();
                }
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                shoutErrorMessage();
            }
        });
    }

    private void shoutErrorMessage() {
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }
}
