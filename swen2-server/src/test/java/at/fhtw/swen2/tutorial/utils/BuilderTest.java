package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuilderTest {

    @Test
    void testTourEntityBuilder() {
        TourEntity tour = TourEntity.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();

        assertEquals("testName", tour.getName());
        assertEquals("testDes", tour.getTourDescription());
        assertEquals("testType", tour.getTransportType());
        assertEquals(12L, tour.getTourDistance());
        assertEquals(11L, tour.getEstimatedTime());
        assertEquals("testHere", tour.getFrom());
        assertEquals("testTo", tour.getTo());
    }

    @Test
    void testTourBuilder() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();

        assertEquals("testName", tour.getName());
        assertEquals("testDes", tour.getTourDescription());
        assertEquals("testType", tour.getTransportType());
        assertEquals(12L, tour.getTourDistance());
        assertEquals(11L, tour.getEstimatedTime());
        assertEquals("testHere", tour.getFrom());
        assertEquals("testTo", tour.getTo());
    }

    @Test
    void testTourLogEntityBuilder() {
        TourLogEntity tourLog = TourLogEntity.builder()
                .tourId(1L)
                .dateLog("12-02-2022")
                .commentLog("testCom")
                .difficultyLog("2")
                .totalTimeLog(3L)
                .ratingLog(10L)
                .build();

        assertEquals(1L, tourLog.getTourId());
        assertEquals("12-02-2022", tourLog.getDateLog());
        assertEquals("testCom", tourLog.getCommentLog());
        assertEquals("2", tourLog.getDifficultyLog());
        assertEquals(3L, tourLog.getTotalTimeLog());
        assertEquals(10L, tourLog.getRatingLog());
    }

    @Test
    void testTourLogBuilder() {
        TourLog tourLog = TourLog.builder()
                .tourId(1L)
                .dateLog("12-02-2022")
                .commentLog("testCom")
                .difficultyLog("2")
                .totalTimeLog(3L)
                .ratingLog(10L)
                .build();

        assertEquals(1L, tourLog.getTourId());
        assertEquals("12-02-2022", tourLog.getDateLog());
        assertEquals("testCom", tourLog.getCommentLog());
        assertEquals("2", tourLog.getDifficultyLog());
        assertEquals(3L, tourLog.getTotalTimeLog());
        assertEquals(10L, tourLog.getRatingLog());
    }

   /*
    @Test
    void testPersonEntityBuilder() {
        PersonEntity maxi = PersonEntity.builder()
                .name("Maxi")
                .email("maxi@email.com")
                .build();
    }
    @Test
    void testPersonBuilder() {
        Person maxi = Person.builder()
                .name("Maxi")
                .id(11L)
                .build();
    }
*/

}
