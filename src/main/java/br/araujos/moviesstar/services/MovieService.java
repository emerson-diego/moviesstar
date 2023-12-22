package br.araujos.moviesstar.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.araujos.dto.MovieDTO;
import br.araujos.moviesstar.entity.Movie;
import br.araujos.moviesstar.infraestrutura.ApiKeyInterceptor;
import br.araujos.moviesstar.repository.MovieRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class MovieService {

    private final OkHttpClient client;
    private final String baseUrl = "https://api.themoviedb.org/3/movie/popular";

    private final MovieRepository movieRepository;

    public MovieService(ApiKeyInterceptor apiKeyInterceptor, MovieRepository movieRepository) {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .build();
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> fetchAllMovies() throws IOException {
        List<MovieDTO> allMovies = new ArrayList<>();
        int page = 1;
        final int maxPages = 50; // Para buscar 1000 filmes

        while (page <= maxPages) {
            String url = baseUrl + "?page=" + page;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray moviesArray = jsonResponse.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieJson = moviesArray.getJSONObject(i);
                    MovieDTO movie = new MovieDTO(
                            movieJson.getLong("id"),
                            movieJson.getString("title"),
                            movieJson.optString("poster_path", null),
                            movieJson.optString("overview", null));
                    allMovies.add(movie);
                }

                int totalPages = jsonResponse.getInt("total_pages");
                if (page >= totalPages) {
                    break;
                }
                page++;
            }
        }

        return allMovies;
    }

    public void saveMovies(List<MovieDTO> movieDTOs) {
        for (MovieDTO dto : movieDTOs) {
            movieRepository.findById(dto.getId())
                    .orElseGet(() -> {
                        Movie movie = new Movie(dto.getId(), dto.getTitle(), dto.getPosterPath(), dto.getOverview());
                        return movieRepository.save(movie);
                    });
        }
    }
}
