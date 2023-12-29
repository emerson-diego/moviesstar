package br.araujos.moviesstar.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.araujos.moviesstar.dto.MovieDTO;
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

                // double popularityThreshold = 1000.0; // Defina um limiar realista aqui

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieJson = moviesArray.getJSONObject(i);
                    // double popularity = movieJson.optDouble("popularity", 0.0);

                    // if (popularity > popularityThreshold) {
                    long movieId = movieJson.getLong("id");
                    MovieDTO movie = fetchMovieDetails(movieId, movieJson);
                    allMovies.add(movie);
                    // }
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
            // movie.setId(dto.getId());
            movie.setTitle(dto.getTitle());
            movie.setPosterPath(dto.getPosterPath());
            movie.setOverview(dto.getOverview());
            movie.setDirector(dto.getDirector()); // Pode ser null
            movie.setMainActors(dto.getMainActors()); // Pode ser null
            movie.setGenreDescription(dto.getGenreDescription()); // Pode ser null
            movie.setPosterUrl(dto.getPosterUrl()); // Pode ser null
            movie.setReleaseDate(dto.getReleaseDate());
            movie.setNationality(dto.getNationality());
            movie.setTrailerUrl(dto.getTrailerUrl());
            movie.setImdbRating(dto.getImdbRating());
            movie.setBraziliamTitle(dto.getBraziliamTitle());
            movie.setPopularity(dto.getPopularity());

            movieRepository.save(movie); // Salva o filme no banco de dados
        }
    }

    public MovieDTO fetchMovieById(long movieId) throws IOException {
        String url = "https://api.themoviedb.org/3/movie/" + movieId
                + "?append_to_response=credits,videos,translations";
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Erro ao buscar detalhes do filme ID " + movieId + ": " + response);
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            String officialTitle = jsonResponse.optString("title");
            String overview = jsonResponse.optString("overview");
            String releaseDate = jsonResponse.optString("release_date");
            String popularity = jsonResponse.optString("popularity", "0.0");
            String imdbRating = extractImdbRating(jsonResponse); // Implementação depende da estrutura da resposta

            String posterPath = jsonResponse.optString("poster_path");
            String posterUrl = posterPath != null ? "https://image.tmdb.org/t/p/original" + posterPath : null;

            String director = extractDirector(jsonResponse);
            String mainActors = extractMainActors(jsonResponse);
            String genreDescription = extractGenres(jsonResponse);
            String trailerUrl = extractTrailerUrl(jsonResponse);
            String nationality = extractNationality(jsonResponse);
            String braziliamTitle = extractBrazilianTitle(jsonResponse);

            return new MovieDTO(
                    movieId, officialTitle, posterPath, overview, director, mainActors,
                    genreDescription, posterUrl, nationality, trailerUrl,
                    imdbRating, releaseDate, braziliamTitle, popularity);
        }
    }

    public void saveOrUpdateMovie(MovieDTO movieDTO) {
        // Busca um filme existente pelo ID
        Movie movie = movieRepository.findById(movieDTO.getId())
                .orElse(new Movie()); // Cria um novo filme se não existir

        // Atualiza as informações do filme com os dados do DTO
        updateMovieFromDTO(movie, movieDTO);

        // Salva o filme no banco de dados (atualiza se já existir ou cria um novo se
        // não existir)
        movieRepository.save(movie);
    }

    private MovieDTO fetchMovieDetails(long movieId, JSONObject movieJson) throws IOException {
        String detailsUrl = "https://api.themoviedb.org/3/movie/" + movieId
                + "?append_to_response=credits,videos,translations";
        Request detailsRequest = new Request.Builder().url(detailsUrl).build();

        try (Response response = client.newCall(detailsRequest).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Erro ao buscar detalhes do filme ID " + movieId + ": " + response);
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Extração do nome oficial do filme
            String officialTitle = jsonResponse.getString("title");

            // Extração do nome em português brasileiro
            String brazilianTitle = extractBrazilianTitle(jsonResponse);

            // Extração da data de lançamento
            String releaseDate = jsonResponse.optString("release_date", null);

            // Extração da sinopse
            String overview = jsonResponse.optString("overview", null);

            // Extração da nota do IMDb (se disponível diretamente)
            String imdbRating = extractImdbRating(jsonResponse);

            // Extração de outras informações...
            String director = extractDirector(jsonResponse);
            String mainActors = extractMainActors(jsonResponse);
            String genreDescription = extractGenres(jsonResponse);
            String trailerUrl = extractTrailerUrl(jsonResponse);
            String nationality = extractNationality(jsonResponse);

            // Extração do caminho do poster
            String posterPath = movieJson.optString("poster_path", null);

            // Construção do URL completo do poster
            String posterUrl = posterPath != null ? "https://image.tmdb.org/t/p/original" + posterPath : null;

            String popularity = movieJson.optString("popularity", "0.0");
            // Construção e retorno do MovieDTO
            return new MovieDTO(
                    movieId,
                    officialTitle,
                    posterPath,
                    overview,
                    director,
                    mainActors,
                    genreDescription,
                    posterUrl,
                    nationality,
                    trailerUrl,
                    imdbRating,
                    releaseDate,
                    brazilianTitle,
                    popularity

            );

        }
    }

    private String extractGenres(JSONObject jsonResponse) {
        StringBuilder genres = new StringBuilder();
        JSONArray genresArray = jsonResponse.getJSONArray("genres");
        for (int i = 0; i < genresArray.length(); i++) {
            if (i > 0)
                genres.append(", ");
            genres.append(genresArray.getJSONObject(i).getString("name"));
        }
        return genres.toString();
    }

    private String extractMainActors(JSONObject jsonResponse) {
        StringBuilder mainActors = new StringBuilder();
        JSONArray castArray = jsonResponse.getJSONObject("credits").getJSONArray("cast");
        for (int i = 0; i < castArray.length() && i < 5; i++) {
            if (i > 0)
                mainActors.append(", ");
            mainActors.append(castArray.getJSONObject(i).getString("name"));
        }
        return mainActors.toString();
    }

    private String extractDirector(JSONObject jsonResponse) {
        JSONArray crewArray = jsonResponse.getJSONObject("credits").getJSONArray("crew");
        for (int i = 0; i < crewArray.length(); i++) {
            JSONObject crewMember = crewArray.getJSONObject(i);
            if ("Director".equals(crewMember.getString("job"))) {
                return crewMember.getString("name");
            }
        }
        return null;
    }

    private String extractTrailerUrl(JSONObject jsonResponse) {
        JSONArray videoResults = jsonResponse.getJSONObject("videos").getJSONArray("results");
        for (int i = 0; i < videoResults.length(); i++) {
            JSONObject video = videoResults.getJSONObject(i);
            if ("Trailer".equals(video.getString("type"))) {
                return "https://www.youtube.com/watch?v=" + video.getString("key");
            }
        }
        return null;
    }

    private String extractBrazilianTitle(JSONObject jsonResponse) {
        JSONArray translations = jsonResponse.getJSONObject("translations").getJSONArray("translations");
        for (int i = 0; i < translations.length(); i++) {
            JSONObject translation = translations.getJSONObject(i);
            String language = translation.getString("iso_639_1");
            String country = translation.getString("iso_3166_1");
            if ("pt".equals(language) && "BR".equals(country)) {
                return translation.getJSONObject("data").getString("title");
            }
        }
        return null;
    }

    private String extractNationality(JSONObject jsonResponse) {
        JSONArray productionCountries = jsonResponse.getJSONArray("production_countries");
        if (productionCountries.length() > 0) {
            return productionCountries.getJSONObject(0).getString("name");
        }
        return null;
    }

    private String extractImdbRating(JSONObject jsonResponse) {
        // Implementação depende da disponibilidade dessa informação na API do TMDb
        // Se não estiver disponível, você precisará de uma chamada adicional a outra
        // API
        return jsonResponse.optString("vote_average", null);
    }

    private void updateMovieFromDTO(Movie movie, MovieDTO dto) {
        movie.setTitle(dto.getTitle());
        movie.setPosterPath(dto.getPosterPath());
        movie.setOverview(dto.getOverview());
        movie.setDirector(dto.getDirector()); // Pode ser null
        movie.setMainActors(dto.getMainActors()); // Pode ser null
        movie.setGenreDescription(dto.getGenreDescription()); // Pode ser null
        movie.setPosterUrl(dto.getPosterUrl()); // Pode ser null
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setNationality(dto.getNationality());
        movie.setTrailerUrl(dto.getTrailerUrl());
        movie.setImdbRating(dto.getImdbRating());
        movie.setBraziliamTitle(dto.getBraziliamTitle());
        movie.setPopularity(dto.getPopularity());

        // Se o DTO contém um ID, defina-o na entidade
        if (dto.getId() != null) {
            movie.setId(dto.getId());
        }
    }

}
