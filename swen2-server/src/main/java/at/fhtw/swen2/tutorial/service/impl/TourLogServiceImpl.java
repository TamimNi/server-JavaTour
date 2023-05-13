package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TourLogServiceImpl implements TourLogService {
    @Autowired
    private TourLogRepository tourLogRepository;
    @Autowired
    private TourLogMapper tourLogMapper;

    @Override
    public List<TourLog> getList(Long tourId) {
        return tourLogMapper.fromEntity(tourLogRepository.findAllByTourId(tourId));
    }

    @Override
    public TourLog addNew(TourLog tourLog) {
        if (tourLog == null){
            return null;
        }
        System.out.println("I bims");
        TourLogEntity entity = tourLogRepository.save(tourLogMapper.toEntity(tourLog));
        System.out.println("joa");
        return tourLogMapper.fromEntity(entity);
    }

    @Override
    public void delOldById(Long idLog) {
        tourLogRepository.deleteById(idLog);
    }

    @Override
    public TourLog updOld(TourLog tourLog) {
        if (tourLog == null || tourLog.getIdLog() == null) {
            return null;
        }
        TourLogEntity existingEntity = tourLogRepository.findById(tourLog.getIdLog()).orElse(null);
        if (existingEntity == null) {
            return null; // entity with given ID not found
        }
        TourLogEntity modifiedEntity = tourLogMapper.toEntity(tourLog);
        modifiedEntity.setIdLog(existingEntity.getIdLog()); // set ID to ensure update instead of insert
        modifiedEntity = tourLogRepository.save(modifiedEntity);
        System.out.println("THIS IS UPDATE.."+tourLog.toString()+"..");
        return tourLogMapper.fromEntity(modifiedEntity);
    }

    @Override
    public void delOldByTourId(Long idLog) {
        tourLogRepository.deleteByTourId(idLog);
    }

}