package com.chamados.Per2park.controller.ResponseDTO;

import com.chamados.Per2park.controller.RequestDTO.ReplicationQueueDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor // opcional, se precisar do construtor vazio

public class MonitorServerResponseDTO {
//    @JsonProperty("Name")
    private String NomeUnidade;

//    @JsonProperty("UploadDate")
    private String UploadDate;

//    @JsonProperty("ClientName")
    private String NomeCliente;



    private ReplicationQueueDTO replicacao;



}
