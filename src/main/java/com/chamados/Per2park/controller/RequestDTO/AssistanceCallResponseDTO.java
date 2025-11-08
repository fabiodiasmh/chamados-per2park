package com.chamados.Per2park.controller.RequestDTO;

import com.chamados.Per2park.controller.ResponseDTO.CategoryDTO;
import com.chamados.Per2park.controller.ResponseDTO.LocalsDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssistanceCallResponseDTO {

    @JsonProperty("Id")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("OpeningDate")
    private LocalDateTime openingDate;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Status")
    private Integer status;

    @JsonProperty("ContactPhone")
    private String contactPhone;

    @JsonProperty("ContactName")
    private String contactName;

    @JsonProperty("ContactMail")
    private String contactMail;

    @JsonProperty("ParkingInoperative")
    private Integer parkingInoperative;

    @JsonProperty("Priority")
    private Integer priority;

    @JsonProperty("Reason")
    private Reason reason;

    @JsonProperty("User")
    private Object user;

    @JsonProperty("Category")
    private CategoryDTO category;

    @JsonProperty("Local")
    private LocalsDTO local;

    @JsonProperty("Equipments")
    private List<Equipment> equipments;

    @JsonProperty("HistoryCalls")
    private List<HistoryCall> historyCalls;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("ClosingDate")
    private LocalDateTime closingDate;

    @JsonProperty("ReasonCallClosed")
    private String reasonCallClosed;

    @JsonProperty("DescriptionUser")
    private String descriptionUser;

    // getters e setters
}
