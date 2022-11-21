package com.prodyna.movieapp.mapstruct;

import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.ReviewDTO;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {ActorMapper.class, ReviewMapper.class})
public abstract class MovieMapper {
    @AfterMapping
    public void calculateAvgRatingOfReviewsInMovie(Movie movie, @MappingTarget MovieDTO.MovieDTOBuilder movieDTO) {
        double sum = 0;
        int count = 0;

        if (movie.getReviews() == null) {
            movieDTO.averageRating(Double.valueOf(0));
        }
        for (Review r : movie.getReviews()) {
            sum += r.getRating();
            count++;
        }
        if (count == 0)
            movieDTO.averageRating(Double.valueOf(0));
        else {
            movieDTO.averageRating(Double.valueOf((double) sum / count));
        }
    }

    @Mapping(source = "desc", target = "description")
    public abstract MovieDTO mapMovieToMovieDTO(Movie movie);


    @Mapping(target = "id", ignore = true)
    public abstract Movie mapMovieDTOToMovie(MovieDTO movieDTO);

    public abstract List<MovieDTO> mapListOfMoviesToListOfMoviesDTOs(List<Movie> movies);
}
