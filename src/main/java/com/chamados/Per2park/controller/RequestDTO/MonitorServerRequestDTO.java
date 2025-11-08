package com.chamados.Per2park.controller.RequestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MonitorServerRequestDTO {
    @JsonProperty("Id")
    private Integer id;

    @JsonProperty("Name")
    private String NomeUnidade;

    @JsonProperty("UploadDate")
    private String UploadDate;

    @JsonProperty("DownloadDate")
    private String DownloadDate;

    @JsonProperty("Status")
    private Integer Status;

    @JsonProperty("Latitude")
    private String Latitude;

    @JsonProperty("Longitude")
    private String Longitude;

    @JsonProperty("Resume")
    private String Resume;

    @JsonProperty("ClientName")
    private String NomeCliente;

    @JsonProperty("HostDisk")
    private String HostDisk;

    @JsonProperty("DBDisk")
    private String DBDisk;

    @JsonProperty("ReplicationQueue")
    private ReplicationQueueDTO replicacao;

    public MonitorServerRequestDTO(List<MonitorServerRequestDTO> resposta) {
    }
}
