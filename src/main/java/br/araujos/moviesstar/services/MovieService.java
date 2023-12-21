package br.araujos.moviesstar.services;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.araujos.moviesstar.infraestrutura.ApiKeyInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class MovieService {

    private final OkHttpClient client;
    private final String baseUrl = "https://api.themoviedb.org/3/movie/popular";

    public MovieService(ApiKeyInterceptor apiKeyInterceptor) {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .build();
    }

    public String fetchAllMovies() throws IOException {
        StringBuilder allMovies = new StringBuilder("[");
        int page = 1;
        final int maxPages = 3;
        boolean hasMorePages = true;

        while (page <= maxPages) {
            String url = baseUrl + "?page=" + page;
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray moviesArray = jsonResponse.getJSONArray("results");

                if (page > 1) {
                    allMovies.append(",");
                }
                allMovies.append(moviesArray.toString());

                int totalPages = jsonResponse.getInt("total_pages");
                hasMorePages = page < totalPages;
                page++;
            }
        }

        allMovies.append("]");
        return allMovies.toString();
    }
}
