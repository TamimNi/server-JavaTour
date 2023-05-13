package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;

import java.util.List;

public interface TourLogService {
    List<TourLog> getList(Long tourId);

    TourLog addNew(TourLog tourLog);

    void delOldById(Long idLog);

    TourLog updOld(TourLog tourLog);

    void delOldByTourId(Long idLog);
}
