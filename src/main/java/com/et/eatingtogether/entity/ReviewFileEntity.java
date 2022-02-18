package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.review.ReviewFileDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reviewFile_table")
public class ReviewFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewFile_number")
    private Long reviewFileNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_number")
    private ReviewEntity reviewEntity;


    private String reviewFilename;

    public static ReviewFileEntity toEntity(ReviewFileDTO r, ReviewEntity reviewEntity) {
        ReviewFileEntity reviewFileEntity = new ReviewFileEntity();
        reviewFileEntity.setReviewFilename(r.getReviewFilename());
        reviewFileEntity.setReviewFileNumber(r.getReviewFileNumber());
        reviewFileEntity.setReviewEntity(reviewEntity);
        return reviewFileEntity;
    }
}
