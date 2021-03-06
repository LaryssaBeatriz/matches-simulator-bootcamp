package simulator.dio.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import simulator.dio.databinding.MatchItemBinding;
import simulator.dio.domain.Match;
import simulator.dio.ui.DetailActivity;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder>{

    private List<Match> matches;

    // construtor que recebe a lista de partidas por parametro
    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    @NonNull
    @Override  //pega o binding do main activity
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater LayoutInflater = android.view.LayoutInflater.from(parent.getContext());
        MatchItemBinding binding = MatchItemBinding.inflate(LayoutInflater, parent, false );
        return new ViewHolder(binding);
    }

    @Override // onde conseguimos entender qual o objeto que ta sendo pego
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match match = matches.get(position);

        //o contexto seria o contexto da nossa aplicação
        Context context = holder.itemView.getContext();

        //pega a posição da partida e assim consegue definir os dados a seguir como o nome do time visitante e do mandante
        Glide.with(context).load(match.getHomeTeam().getImage()).circleCrop().into(holder.binding.ivHomeTime);
        holder.binding.tvHomeTimeName.setText(match.getHomeTeam().getName());
        if (match.getHomeTeam().getScore() != null){
            holder.binding.tvHomeTimeScore.setText(String.valueOf(match.getHomeTeam().getScore()));
        }
        Glide.with(context).load(match.getAwayTeam().getImage()).circleCrop().into(holder.binding.ivAwayTime);
        holder.binding.tvAwayTimeName.setText(match.getAwayTeam().getName());
        if (match.getAwayTeam().getScore() != null){
            holder.binding.tvAwayTimeScore.setText(String.valueOf(match.getAwayTeam().getScore()))  ;
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra( DetailActivity.Extras.MATCH, match);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final MatchItemBinding binding;
        //construtor do viewholder que chama uma view binding
        public ViewHolder(MatchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
