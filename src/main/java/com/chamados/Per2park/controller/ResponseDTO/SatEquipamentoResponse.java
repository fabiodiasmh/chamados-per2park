package com.chamados.Per2park.controller.ResponseDTO;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString

public class SatEquipamentoResponse {


    private List<SatEquipamentoDTO> items;
//    private int currentPage;
//    private boolean hasNext;
//    private boolean hasPrevious;
//    private int pageSize;
//    private int totalCount;
//    private int totalPages;
}
