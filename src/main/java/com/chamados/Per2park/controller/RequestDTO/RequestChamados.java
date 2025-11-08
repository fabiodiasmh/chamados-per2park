package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestChamados {
    @JsonIgnore
    @JsonProperty("Locals")
    private List<LocalDTO> Locals; // Pode ser List<LocalDTO> ou null

    @JsonIgnore
    @JsonProperty("ListCategory")
    private List<ListCategoryDTO> ListCategory;

    @JsonIgnore
    @JsonProperty("ListPriority")
    private List<ListPriotityDTO> ListPriority;

    @JsonProperty("ListStatus")
    private List<StatusDTO> ListStatus = new ArrayList<>(); // ← Inicializada como lista vazia


}
