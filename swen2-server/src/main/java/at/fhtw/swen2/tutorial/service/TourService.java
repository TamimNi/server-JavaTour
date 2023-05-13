package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.Tour;

import java.io.IOException;
import java.util.List;

public interface TourService {

    List<Tour> getList();

    Tour addNew(Tour tour);

    void delOld(Long id);

    Tour updOld(Tour tour);

    String pdfSingle(Long id) throws IOException;

    String pdfSummary();
}
