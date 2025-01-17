package rysich.microservices.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import rysich.microservices.moviecatalogservice.models.CatalogItem;
import rysich.microservices.moviecatalogservice.models.Movie;
import rysich.microservices.moviecatalogservice.models.UserRating;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/user/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service:8082/movies/" + rating.getMovieId(), Movie.class);


            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        })
        .collect(Collectors.toList());

    }
}

/*
Movie movie = webClientBuilder.build()
        .get()
        .uri("http://localhost:8082/movies/" + rating.getMovieId())
        .retrieve()
        .bodyToMono(Movie.class)
        .block();

 */
