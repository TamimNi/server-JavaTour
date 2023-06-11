package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicReference;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@Transactional
public class TourServcieImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourMapper tourMapper;

    @Autowired
    TourLogServiceImpl tourLogService;

    private static final Logger logger = LogManager.getLogger(TourLogServiceImpl.class);
    @Override
    public List<Tour> getList() {
        logger.debug("getlist");
        List<Tour> tourList = tourMapper.fromEntity(tourRepository.findAll());
        tourList.forEach(tour -> {

            List<TourLog> logs= tourLogService.getList(tour.getId());
            long amountLogs = logs.size();
            if(amountLogs >= 3){
                tour.setPopularity("popular");
            }else{
                tour.setPopularity("unpopular");
            }

            long time = tour.getEstimatedTime();
            long distance = tour.getTourDistance();
            AtomicLong diffiEntries = new AtomicLong();
            AtomicLong difficultyWhole = new AtomicLong(0);
            logs.forEach(tourLog -> {

                if (tourLog.getDifficultyLog() != null) {

                    diffiEntries.getAndIncrement();

                    difficultyWhole.addAndGet(Long.parseLong(tourLog.getDifficultyLog()));
                }

            });
            long quotient = 0;
            if(diffiEntries.get() > 0)
            quotient = difficultyWhole.get() / diffiEntries.get();

            if(quotient < 20 && time < 1000 && distance < 30) {
                tour.setChildFriendly("friendly");
            }
            else{
                tour.setChildFriendly("unfriendly");
            }
            updOld(tour);
        });
        tourList = tourMapper.fromEntity(tourRepository.findAll());

        return tourList;
    }

    @Override
    public Tour addNew(Tour tour) {
        logger.debug("addnew");
        if (tour == null) {
            return null;
        }

        TourEntity entity = tourRepository.save(tourMapper.toEntity(tour));
        return tourMapper.fromEntity(entity);
    }

    //    @Autowired
//    private TourLogRepository tourLogRepository;
    @Override
    public void delOld(Long id) {
        logger.debug("delete");
        if (id == null) {
            return;
        }
        tourRepository.deleteById(id);
        tourLogService.delOldByTourId(id);
    }

    @Override
    public Tour updOld(Tour tour) {
        logger.debug("update");
        if (tour == null || tour.getId() == null) {
            return null;
        }
        TourEntity existingEntity = tourRepository.findById(tour.getId()).orElse(null);
        if (existingEntity == null) {
            return null; // entity with given ID not found
        }
        TourEntity modifiedEntity = tourMapper.toEntity(tour);
        modifiedEntity.setId(existingEntity.getId()); // set ID to ensure update instead of insert
        modifiedEntity = tourRepository.save(modifiedEntity);
        return tourMapper.fromEntity(modifiedEntity);
    }

    @Override
    public String pdfSingle(Long id) throws IOException {
        logger.debug("pdfsingle");
        List<Tour> tourList = getList();
        // Create a Document object
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int min = 100;
        int max = 999;

        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;

        try {
            // Create a PdfWriter object to write the PDF file
            PdfWriter.getInstance(document, new FileOutputStream("C:/swen2/myfile" + timestamp + randomNumber+".pdf"));

            // Open the document
            document.open();
            Optional<TourEntity> tour = null;

            tour = tourRepository.findById(id);
            List<TourLog> tourLogList = tourLogService.getList(id);
            // Add content to the document
            String content = ""; // Replace getContent() with a method that returns your object's content
            if (tour.isPresent()) {
                TourEntity tourEntity = tour.get();
                content += "Tour ID: " + tourEntity.getId() + "\n"
                        + "Tour Name: " + tourEntity.getName() + "\n"
                        + "Tour Description: " + tourEntity.getTourDescription() + "\n"
                        + "Transport Type: " + tourEntity.getTransportType() + "\n"
                        + "Tour Distance: " + tourEntity.getTourDistance() + "\n"
                        + "Estimated Time: " + tourEntity.getEstimatedTime() + "\n"
                        + "Popularity: " + tourEntity.getPopularity() + "\n"
                        + "Child Friendly: " + tourEntity.getChildFriendly() + "\n"
                        + "From: " + tourEntity.getFrom() + "\n"
                        + "To: " + tourEntity.getTo() + "\n\n";
            }
            for (TourLog tourLog : tourLogList) {
                content += "\n\nID_______________" +tourLog.getIdLog() +
                "\nDate_______________" + tourLog.getDateLog() +
                "\nComment_______________" + tourLog.getCommentLog() +
                "\nDifficulty_______________" + tourLog.getDifficultyLog() +
                "\nTime_______________" + tourLog.getTotalTimeLog() +
                "\nRating_______________" + tourLog.getRatingLog();
            }
            Paragraph paragraph = new Paragraph(content);
            document.add(paragraph);

            // Close the document
            document.close();

            // Generate the URL for the PDF file
            File file = new File("C:/swen2/myfile" + timestamp + randomNumber+ ".pdf");
            String url = "http://localhost:8080/files/" + file.getName();

            // Print the URL
            logger.info("url "+url);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String pdfSummary() {//Distance. rating. avg time
        logger.debug("pdfsummary");
        // Create a Document object
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        int min = 100;
        int max = 999;

        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        try {

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            // Create a PdfWriter object to write the PDF file
            PdfWriter.getInstance(document, new FileOutputStream("C:/swen2/myfile" + timestamp + randomNumber + ".pdf"));
            String content = "";
            List<Tour> tourList = getList();
            for (Tour tour : tourList) {
                List<TourLog> tourLog = tourLogService.getList(tour.getId());
                Double time = 0.0;
                Double rating = 0.0;

                for (TourLog log : tourLog) {
                    time += Double.valueOf(log.getTotalTimeLog());
                    rating += log.getRatingLog();
                }

                content += "Tour__" + tour.getName() +
                        "\ndistance__" + tour.getTourDistance() +
                        "\ntime__" + time +
                        "\nrating__" + rating + "\n\n";
            }
            // Open the document
            document.open();

            Paragraph paragraph = new Paragraph(content);
            document.add(paragraph);

            // Close the document
            document.close();

            // Generate the URL for the PDF file
            File file = new File("C:/swen2/myfile" + timestamp + randomNumber + ".pdf");
            String url = "http://localhost:8080/files/" + file.getName();

            // Print the URL
            logger.info("<a href=\"" + url + "\">" + url + "</a>");
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
