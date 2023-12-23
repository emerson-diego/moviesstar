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

    public List<MovieDTO> fetchAllMovies() {
        List<MovieDTO> allMovies = new ArrayList<>();
        int page = 1;
        final int maxPages = 50; // Para buscar 1000 filmes

        while (page <= maxPages) {
            String url = baseUrl + "?page=" + page;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.err.println("Erro ao buscar filmes na página " + page + ": " + response);
                    page++;
                    continue;
                }

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray moviesArray = jsonResponse.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieJson = moviesArray.getJSONObject(i);
                    long movieId = movieJson.getLong("id");

                    MovieDTO movie = new MovieDTO(
                            movieJson.getLong("id"),
                            movieJson.getString("title"),
                            movieJson.optString("poster_path", null),
                            movieJson.optString("overview", null));

                    // Definindo propriedades que podem ser null
                    movie.setDirector(fetchDirector(movieId)); // Pode retornar null
                    movie.setMainActors(fetchMainActors(movieId)); // Pode retornar null
                    movie.setGenreDescription(fetchGenres(movieId)); // Pode retornar null
                    movie.setPosterUrl(fetchPosterUrl(movieJson)); // Pode retornar null

                    allMovies.add(movie);
                }

                int totalPages = jsonResponse.getInt("total_pages");
                if (page >= totalPages) {
                    break;
                }
                page++;
            } catch (IOException e) {
                System.err.println("Erro de IO ao buscar filmes na página " + page + ": " + e.getMessage());
                page++;
            }
        }

        return allMovies;
    }

    public void saveMovies(List<MovieDTO> movieDTOs) {
        for (MovieDTO dto : movieDTOs) {
            Movie movie = movieRepository.findById(dto.getId())
                    .orElse(new Movie()); // Cria um novo filme se não existir

            // Atualiza as informações do filme com os dados do DTO
            movie.setId(dto.getId());
            movie.setTitle(dto.getTitle());
            movie.setPosterPath(dto.getPosterPath());
            movie.setOverview(dto.getOverview());
            movie.setDirector(dto.getDirector()); // Pode ser null
            movie.setMainActors(dto.getMainActors()); // Pode ser null
            movie.setGenreDescription(dto.getGenreDescription()); // Pode ser null
            movie.setPosterUrl(dto.getPosterUrl()); // Pode ser null

            movieRepository.save(movie); // Salva o filme no banco de dados
        }
    }

    private String fetchDirector(long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Erro ao buscar diretor para o filme ID " + movieId + ": " + response);
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray crewArray = jsonResponse.getJSONArray("crew");

            for (int i = 0; i < crewArray.length(); i++) {
                JSONObject crewMember = crewArray.getJSONObject(i);
                if ("Director".equals(crewMember.getString("job"))) {
                    return crewMember.getString("name");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de IO ao buscar diretor: " + e.getMessage());
            return null;
        }

        return null;
    }

    private String fetchMainActors(long movieId) {
        StringBuilder mainActors = new StringBuilder();
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Erro ao buscar atores principais para o filme ID " + movieId + ": " + response);
                return "Informação indisponível";
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray castArray = jsonResponse.getJSONArray("cast");

            for (int i = 0; i < castArray.length() && i < 5; i++) {
                JSONObject castMember = castArray.getJSONObject(i);
                if (i > 0)
                    mainActors.append(", ");
                mainActors.append(castMember.getString("name"));
            }
        } catch (IOException e) {
            System.err.println("Erro de IO ao buscar atores principais: " + e.getMessage());
            return "Informação indisponível";
        }

        return mainActors.toString();
    }

    private String fetchGenres(long movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=YOUR_API_KEY";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Erro ao buscar gêneros para o filme ID " + movieId + ": " + response);
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray genresArray = jsonResponse.getJSONArray("genres");
            StringBuilder genres = new StringBuilder();

            for (int i = 0; i < genresArray.length(); i++) {
                JSONObject genre = genresArray.getJSONObject(i);
                if (i > 0)
                    genres.append(", ");
                genres.append(genre.getString("name"));
            }

            return genres.toString();
        } catch (IOException e) {
            System.err.println("Erro de IO ao buscar gêneros: " + e.getMessage());
            return null;
        }
    }

    private String fetchPosterUrl(JSONObject movieJson) {
        String posterPath = movieJson.optString("poster_path", null);
        if (posterPath != null) {
            return "https://image.tmdb.org/t/p/original" + posterPath;
        }
        return null;
    }
}
