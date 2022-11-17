package com.prodyna.movieapp.mapstruct;

import com.prodyna.movieapp.domain.Actor;
import com.prodyna.movieapp.domain.Review;
import com.prodyna.movieapp.dto.ActorDTO;
import com.prodyna.movieapp.dto.ReviewDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDTO mapReviewToReviewDTO(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    Review mapReviewDTOToReview(ReviewDTO reviewDTO);

    List<ReviewDTO> mapListOfReviewsToListOfReviewsDTOs(List<Review> reviews);
}
