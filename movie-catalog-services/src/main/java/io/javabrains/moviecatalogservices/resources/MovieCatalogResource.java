package io.javabrains.moviecatalogservices.resources;

import io.javabrains.moviecatalogservices.models.CatalogItem;
import io.javabrains.moviecatalogservices.models.Movie;
import io.javabrains.moviecatalogservices.models.Rating;
import io.javabrains.moviecatalogservices.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId,
                UserRating.class);

        // can use Parameterized Type

       return ratings.getUserRating().stream().map(rating -> {
           // For each movie ID,call movie info service and get details
           Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" +  rating.getMovieId(),
                   Movie.class);

           // restTemplate is one of the way for calling the rest in synchronous way
           // for asynchronous way we can use the Web Client

           // Put them all together
           return new CatalogItem(movie.getName(), "DESC",  rating.getRating());
       })
               .collect(Collectors.toList());

        // get all rated movie IDs -hardcoded

//        return Collections.singletonList(
//                new CatalogItem("Wonder Women 1984", "Test",4)
//        );
    }
}
