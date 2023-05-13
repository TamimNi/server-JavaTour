package at.fhtw.swen2.tutorial.controller;

import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RestController
@RequestMapping("/api")
public class Controller {
    // private Test test;
 /*   @GetMapping("/data")
    public String getData() {
        System.out.println("moinMOinLieberSErvice");
        return "This is some data from the server";
    }*/

    @Autowired
    TourService tourService;

    @PostMapping("/tour")
    public ResponseEntity<Tour> updateData(@RequestBody Tour tour) {
        try {
            System.out.println("_|");
            System.out.println("Tour" + tour + "<-");
            System.out.println("-|");

            String origin = tour.getFrom();
            String destination = tour.getTo();
            try {
                String response = apiCall.getRoute(origin, destination);
                System.out.println(response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray timeArray = jsonResponse.getJSONArray("time");
                int timeInSeconds = timeArray.getInt(1);
                System.out.println("Time in seconds: " + timeInSeconds);

                JSONArray distanceArray = jsonResponse.getJSONArray("distance");
                double distanceInMiles = distanceArray.getDouble(1);
                System.out.println("Distance in miles: " + distanceInMiles);

                tour.setEstimatedTime((long) timeInSeconds);
                tour.setTourDistance((long) distanceInMiles);
            } catch (IOException e) {
                e.printStackTrace();
            }







            tour = tourService.addNew(tour);


            return ResponseEntity.ok(tour);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Autowired
    ApiCall apiCall;


    @GetMapping("/tour")
    public ResponseEntity<List<Tour>> getAllTours() {
        try {
            List<Tour> tours = tourService.getList();


            return ResponseEntity.ok(tours);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/tour/{tourIdd}")
    public ResponseEntity<Void> deleteTour(@PathVariable String tourIdd) {
        try {
            tourService.delOld(Long.valueOf(tourIdd));
            System.out.println(tourIdd + "<-----------");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/tour")
    public ResponseEntity<Tour> updateTour(@RequestBody Tour tour) {
        try {
            tour = tourService.updOld(tour);

            String origin = tour.getFrom();
            String destination = tour.getTo();
            try {
                String response = apiCall.getRoute(origin, destination);
                System.out.println(response);
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray timeArray = jsonResponse.getJSONArray("time");
                int timeInSeconds = timeArray.getInt(1);
                System.out.println("Time in seconds: " + timeInSeconds);

                JSONArray distanceArray = jsonResponse.getJSONArray("distance");
                double distanceInMiles = distanceArray.getDouble(1);
                System.out.println("Distance in miles: " + distanceInMiles);

                tour.setEstimatedTime((long) timeInSeconds);
                tour.setTourDistance((long) distanceInMiles);
            } catch (IOException e) {
                e.printStackTrace();
            }



            return ResponseEntity.ok(tour);
        } catch (Exception e) {
            // Return a 400 Bad Request response if there was an error processing the request
            return ResponseEntity.badRequest().build();
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Autowired
    TourLogService tourLogService;
    @PostMapping("/tourLog")
    public ResponseEntity<TourLog> updateDataLog(@RequestBody TourLog tourLog) {
        try {
            System.out.println("_|");
            System.out.println("Tour" + tourLog + "<-");
            System.out.println("-|");
            tourLog = tourLogService.addNew(tourLog);
            return ResponseEntity.ok(tourLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tourLog/{tourId}")
    public ResponseEntity<List<TourLog>> getAllTourLogs(@PathVariable String tourId) {
        try {
            System.out.println("HEELllo"+tourId);
            List<TourLog> tourLogs = tourLogService.getList(Long.valueOf(tourId));
            return ResponseEntity.ok(tourLogs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/dellog/{tourId}")
    public ResponseEntity<Void> deleteTourLog(@PathVariable String tourId) {
        try {
            tourLogService.delOldById(Long.valueOf(tourId));
            System.out.println(tourId + "<-----------");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/putlog")
    public ResponseEntity<TourLog> updateTour(@RequestBody TourLog tourLog) {
        try {
            System.out.println(tourLog);
            tourLog = tourLogService.updOld(tourLog);
            return ResponseEntity.ok(tourLog);
        } catch (Exception e) {
            // Return a 400 Bad Request response if there was an error processing the request
            return ResponseEntity.badRequest().build();
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @GetMapping("/pdf/single/{tourId}")
    public ResponseEntity<String> getSinglePdf(@PathVariable String tourId) {
        try {
            String response = tourService.pdfSingle(Long.valueOf(tourId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/pdf/summary")
    public ResponseEntity<String> getSummaryPdf() {
        try {

            String response = tourService.pdfSummary();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}