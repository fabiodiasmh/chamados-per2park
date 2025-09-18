package com.chamados.Per2park.controller.ResponseDTO;

import java.util.List;
import java.util.Map;

public class ChamadosAgrupadosPorStatusDTO {
    private Map<Integer, List<ChamadoBaseDTO>> chamadosPorStatus;

    public ChamadosAgrupadosPorStatusDTO(Map<Integer, List<ChamadoBaseDTO>> chamadosPorStatus) {
        this.chamadosPorStatus = chamadosPorStatus;
    }

    // getter
    public Map<Integer, List<ChamadoBaseDTO>> getChamadosPorStatus() {

        return chamadosPorStatus;
    }
}