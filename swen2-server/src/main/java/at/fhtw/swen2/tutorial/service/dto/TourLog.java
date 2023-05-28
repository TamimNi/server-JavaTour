package at.fhtw.swen2.tutorial.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // Add no-arg constructor
public class TourLog {
    private Long idLog;
    private String dateLog;
    private String commentLog;
    private String difficultyLog;
    private Long totalTimeLog;
    private Long ratingLog;
    private Long tourId;
}
