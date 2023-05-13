package at.fhtw.swen2.tutorial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tour_T")
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_seq")
    @SequenceGenerator(name = "tour_seq", sequenceName = "TOUR_SEQUENCE")
    private Long id;
    private String name;
    private String tourDescription;
    private String transportType;
    private Long tourDistance;
    private Long estimatedTime;
    private String popularity;
    private String childFriendly;
    @Column(name = "FROM_TOUR")
    private String from;
    @Column(name = "TO_TOUR")
    private String to;

}
