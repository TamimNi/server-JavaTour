package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TourServcieImplTest {

    @Autowired
    TourServcieImpl tourServcie;
    @Autowired
    TourLogServiceImpl tourLogService;
    @Test
    void getList() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tourServcie.addNew(tour);
        int amount = tourServcie.getList().size();
        System.out.println("amount is "+amount);
        assertTrue(amount > 0);

    }

    @Test
    void addNewTour() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        assertNotEquals(tourServcie.addNew(tour), null);
    }
    @Test
    void addNewTourWithSameValuesExceptIDUnique() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        assertNotEquals(tourServcie.addNew(tour), null);
    }
    @Test
    void addNewTourNULL() {
        Tour tour = null;
        assertEquals(tourServcie.addNew(tour), null);
    }

    @Test
    void delOld() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tour = tourServcie.addNew(tour);
        int preSize = tourServcie.getList().size();
        tourServcie.delOld(tour.getId());
        int postSize = tourServcie.getList().size();
        assertTrue(preSize>postSize);
    }
    @Test
    void delOldNull() {
        int preSize = tourServcie.getList().size();
        tourServcie.delOld(null);
        int postSize = tourServcie.getList().size();
        assertEquals(preSize,postSize);
    }
    @Test
    void delOldAndLog() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tour = tourServcie.addNew(tour);

        TourLog tourLog = TourLog.builder()
                .tourId(tour.getId())
                .dateLog("12-02-2022")
                .commentLog("testCom")
                .difficultyLog("2")
                .totalTimeLog(3L)
                .ratingLog(10L)
                .build();
        tourLog = tourLogService.addNew(tourLog);


        int preSize = tourServcie.getList().size();
        int preSizeLog = tourLogService.getList(tour.getId()).size();
        tourServcie.delOld(tour.getId());
        int postSize = tourServcie.getList().size();


        int postSizeLog = tourLogService.getList(tour.getId()).size();
        System.out.println("SIZE"+preSizeLog+".."+postSizeLog);
        assertTrue(preSize>postSize);
        assertTrue(preSizeLog>postSizeLog);
    }

    @Test
    void updOld() {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tour = tourServcie.addNew(tour);

        Tour tourEdit = Tour.builder()
                .name("xy")
                .id(tour.getId())
                .tourDescription("xy")
                .transportType("xy")
                .tourDistance(11L)
                .estimatedTime(10L)
                .from("nowhere")
                .to("somewhere")
                .build();
        tourEdit = tourServcie.updOld(tourEdit);

        assertTrue(tour != tourEdit);
        assertTrue(tour.getId() == tourEdit.getId());
    }

    @Test
    @Order(1)
    void pdfSingle() throws IOException {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tour = tourServcie.addNew(tour);

        String directoryPath = "C:/swen2/";

        long prefileCount = Files.list(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .count();

        String fileUrl = "this is pdf link "+tourServcie.pdfSingle(tour.getId());
        System.out.println("Number of files in " + directoryPath + ": " + prefileCount);

        long postfileCount = Files.list(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .count();
        assertTrue(prefileCount<postfileCount);
    }
    @Test
    @Order(2)
    void pdfSingleMultipleTimesForSameTour() throws IOException, InterruptedException {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tour = tourServcie.addNew(tour);

        String directoryPath = "C:/swen2/";

        long prefileCount = Files.list(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .count();

        tourServcie.pdfSingle(tour.getId());
        tourServcie.pdfSingle(tour.getId());
        System.out.println("Number of files in " + directoryPath + ": " + prefileCount);
        long postfileCount = Files.list(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .count();
        System.out.println(postfileCount+"AND"+prefileCount);
        assertTrue(prefileCount+2 == postfileCount);
    }
    @Test
    @Order(3)
    void pdfSingleMultipleTimesForSameTourDifferentUniqueIdentifier() throws IOException {
        Tour tour = Tour.builder()
                .name("testName")
                .tourDescription("testDes")
                .transportType("testType")
                .tourDistance(12L)
                .estimatedTime(11L)
                .from("testHere")
                .to("testTo")
                .build();
        tour = tourServcie.addNew(tour);


        String url1 = tourServcie.pdfSingle(tour.getId());
        String url2 = tourServcie.pdfSingle(tour.getId());

        assertTrue(url1 != url2);
    }
    @Test
    @Order(4)
    void pdfSummary() {
        String url = tourServcie.pdfSummary();
        assertTrue(url != null);
    }
}