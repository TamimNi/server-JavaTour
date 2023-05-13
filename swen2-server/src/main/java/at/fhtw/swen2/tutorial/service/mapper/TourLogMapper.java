package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.stereotype.Component;

@Component
public class TourLogMapper extends AbstractMapper<TourLogEntity, TourLog>  {
    @Override
    public TourLog fromEntity(TourLogEntity entity) {
        return TourLog.builder()
                .idLog(entity.getIdLog())
                .dateLog(entity.getDateLog())
                .commentLog(entity.getCommentLog())
                .difficultyLog(entity.getDifficultyLog())
                .totalTimeLog(entity.getTotalTimeLog())
                .ratingLog(entity.getRatingLog())
                .tourId(entity.getTourId())
                .build();
    }

    @Override
    public TourLogEntity toEntity(TourLog dto) {
        return TourLogEntity.builder()
                .idLog(dto.getIdLog())
                .dateLog(dto.getDateLog())
                .commentLog(dto.getCommentLog())
                .difficultyLog(dto.getDifficultyLog())
                .totalTimeLog(dto.getTotalTimeLog())
                .ratingLog(dto.getRatingLog())
                .tourId(dto.getTourId())
                .build();
    }
}
