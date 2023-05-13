package at.fhtw.swen2.tutorial.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // Add no-arg constructor
public class Tour {
    private Long id;
    private String name;
    private String tourDescription;
    private String from;
    private String to;
    private String transportType;
    private Long tourDistance;
    private Long estimatedTime;
    private String popularity;
    private String childFriendly;
}
