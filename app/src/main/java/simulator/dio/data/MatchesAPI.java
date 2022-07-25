package simulator.dio.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import simulator.dio.domain.Match;

public interface MatchesAPI {

    // camada de acesso a dados da API no GitHub
    @GET("matches.json")
    // O Call retorna uma lista de partidas(Match)
    Call<List<Match>> getMatches();

}
