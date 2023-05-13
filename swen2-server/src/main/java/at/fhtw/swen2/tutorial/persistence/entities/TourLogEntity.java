package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TourLogEntity { //TODO link Tour
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_seq")
    @SequenceGenerator(name = "tour_seq", sequenceName = "TOUR_SEQUENCE")
    private Long idLog;
    private Date dateLog;
    private String commentLog;
    private String difficultyLog;
    private Long totalTimeLog;
    private Long ratingLog;
    private Long tourId;

}