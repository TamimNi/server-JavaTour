package at.fhtw.swen2.tutorial.utils;
import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import at.fhtw.swen2.tutorial.service.mapper.TourLogMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MapperTest {

    TourMapper tourMapper = new TourMapper();

    TourLogMapper tourLogMapper = new TourLogMapper();

    @Test
    void testTourFromEntity() {
        TourEntity tourEntity = TourEntity.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();

        Tour tour = tourMapper.fromEntity(tourEntity);
        System.out.println("NAME"+ tour);
        assertEquals("testName", tour.getName());
        assertEquals("testDes", tour.getTourDescription());
        assertEquals("testType", tour.getTransportType());
        assertEquals(12L, tour.getTourDistance());
        assertEquals(11L, tour.getEstimatedTime());
        assertEquals("testHere", tour.getFrom());
        assertEquals("testTo", tour.getTo());
    }

    @Test
    void testTourToEntity() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();

        TourEntity tourEntity = tourMapper.toEntity(tour);

        assertEquals("testName", tourEntity.getName());
        assertEquals("testDes", tourEntity.getTourDescription());
        assertEquals("testType", tourEntity.getTransportType());
        assertEquals(12L, tourEntity.getTourDistance());
        assertEquals(11L, tourEntity.getEstimatedTime());
        assertEquals("testHere", tourEntity.getFrom());
        assertEquals("testTo", tourEntity.getTo());
    }

    @Test
    void testTourLogFromEntity() {
        TourLogEntity tourLogEntity = TourLogEntity.builder()
                .tourId(1L)
                .dateLog("12-02-2022")
                .commentLog("testCom")
                .difficultyLog("2")
                .totalTimeLog(3L)
                .ratingLog(10L)
                .build();

        TourLog tourLog = tourLogMapper.fromEntity(tourLogEntity);

        assertEquals(1L, tourLog.getTourId());
        assertEquals("12-02-2022", tourLog.getDateLog());
        assertEquals("testCom", tourLog.getCommentLog());
        assertEquals("2", tourLog.getDifficultyLog());
        assertEquals(3L, tourLog.getTotalTimeLog());
        assertEquals(10L, tourLog.getRatingLog());
    }

    @Test
    void testTourLogToEntity() {
        TourLog tourLog = TourLog.builder()
                .tourId(1L)
                .dateLog("12-02-2022")
                .commentLog("testCom")
                .difficultyLog("2")
                .totalTimeLog(3L)
                .ratingLog(10L)
                .build();

        TourLogEntity tourLogEntity = tourLogMapper.toEntity(tourLog);

        assertEquals(1L, tourLogEntity.getTourId());
        assertEquals("12-02-2022", tourLogEntity.getDateLog());
        assertEquals("testCom", tourLogEntity.getCommentLog());
        assertEquals("2", tourLogEntity.getDifficultyLog());
        assertEquals(3L, tourLogEntity.getTotalTimeLog());
        assertEquals(10L, tourLogEntity.getRatingLog());
    }

}
