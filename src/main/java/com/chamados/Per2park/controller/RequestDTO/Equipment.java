package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Equipment {
    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("IndBlock")
    private Integer indBlock;

    @JsonProperty("IndActive")
    private Integer indActive;
    // demais campos podem ser omitidos se não forem usados
    // ou mantenha como Object se não quiser mapear tudo

    // getters e setters
}
