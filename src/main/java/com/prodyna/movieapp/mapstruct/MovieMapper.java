package com.prodyna.movieapp.mapstruct;

import com.prodyna.movieapp.domain.Movie;
import com.prodyna.movieapp.domain.Review;
import com.prodyna.movieapp.dto.MovieDTO;
import com.prodyna.movieapp.dto.ReviewDTO;
import java.time.LocalDate;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        uses = {ActorMapper.class, ReviewMapper.class})
public abstract class MovieMapper {

    @Mapping(source = "releaseDate", target = "releaseDate", qualifiedByName = "dateToInt")
    @Mapping(source = "desc", target = "description")
    public abstract MovieDTO mapMovieToMovieDTO(Movie movie);

    @Mapping(source = "releaseDate", target = "releaseDate", qualifiedByName = "intToDate")
    @Mapping(target = "id", ignore = true)
    public abstract Movie mapMovieDTOToMovie(MovieDTO movieDTO);

    @Named("dateToInt")
    public Integer convertYearInMovieDTO(LocalDate date) {
        return date.getYear();
    }

    @Named("intToDate")
    public LocalDate convertYearInMovie(Integer year) {
        return LocalDate.of(year, 1, 1);
    }

    public abstract List<MovieDTO> mapListOfMoviesToListOfMoviesDTOs(List<Movie> movies);

    @AfterMapping
    protected void calculateAvgRatingOfReviewsInMovie(@MappingTarget MovieDTO movieDTO) {
        double sum = 0;
        int count = 0;

        if (movieDTO.getReviews() == null) {
            movieDTO.setAverageRating(Double.valueOf(0));
        }
        for (ReviewDTO r : movieDTO.getReviews()) {
            sum += r.getRating();
            count++;
        }
        if (count == 0)
            movieDTO.setAverageRating(Double.valueOf(0));
        else {
            movieDTO.setAverageRating(Double.valueOf((double) sum / count));
        }
    }
}
