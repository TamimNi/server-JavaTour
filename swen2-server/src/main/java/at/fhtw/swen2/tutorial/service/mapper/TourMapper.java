package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.stereotype.Component;

@Component
public class TourMapper extends AbstractMapper<TourEntity, Tour> {
    @Override
    public Tour fromEntity(TourEntity entity) {
        return Tour.builder()
                .id(entity.getId())
                .name(entity.getName())
                .tourDescription(entity.getTourDescription())
                .transportType(entity.getTransportType())
                .tourDistance(entity.getTourDistance())
                .estimatedTime(entity.getEstimatedTime())
                .from(entity.getFrom())
                .to(entity.getTo())
                .popularity(entity.getPopularity())
                .childFriendly(entity.getChildFriendly())
                .build();
    }

    @Override
    public TourEntity toEntity(Tour dto) {
        return TourEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .transportType(dto.getTransportType())
                .tourDescription(dto.getTourDescription())
                .tourDistance(dto.getTourDistance())
                .estimatedTime(dto.getEstimatedTime())
                .from(dto.getFrom())
                .to(dto.getTo())
                .popularity(dto.getPopularity())
                .childFriendly(dto.getChildFriendly())
                .build();
    }
}
