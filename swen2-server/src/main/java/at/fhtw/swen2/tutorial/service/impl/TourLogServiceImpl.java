package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.controller.ApiCall;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourLogMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(TourLogServiceImpl.class);

    @Override
    public List<TourLog> getList(Long tourId) {
        logger.debug("getlist called");
        return tourLogMapper.fromEntity(tourLogRepository.findAllByTourId(tourId));
    }

    @Override
    public TourLog addNew(TourLog tourLog) {
        logger.debug("addNew called");
        if (tourLog == null){
            return null;
        }

        TourLogEntity entity = tourLogRepository.save(tourLogMapper.toEntity(tourLog));

        return tourLogMapper.fromEntity(entity);
    }

    @Override
    public void delOldById(Long idLog) {
        logger.debug("delete");
        tourLogRepository.deleteById(idLog);
    }

    @Override
    public TourLog updOld(TourLog tourLog) {
        logger.debug("update");
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
        return tourLogMapper.fromEntity(modifiedEntity);
    }

    @Override
    public void delOldByTourId(Long idLog) {

        logger.debug("delete");
        tourLogRepository.deleteByTourId(idLog);
    }

}