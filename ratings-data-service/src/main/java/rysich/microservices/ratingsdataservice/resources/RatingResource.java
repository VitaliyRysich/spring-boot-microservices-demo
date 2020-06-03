package rysich.microservices.ratingsdataservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rysich.microservices.ratingsdataservice.models.Rating;
import rysich.microservices.ratingsdataservice.models.UserRating;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("2344", 3)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }
}
