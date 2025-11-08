package com.chamados.Per2park.service;

import com.chamados.Per2park.controller.ResponseDTO.ChamadoBaseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeparaStatusChamados {

    private final ApiService apiService;


    public SeparaStatusChamados(ApiService apiService) {
        this.apiService = apiService;

    }

    public void teste(String token) {
        System.out.println(apiService.serviceGetChamadosAgrupados(token).toString());

        List<ChamadoBaseDTO> teste = apiService.ServiceGetChamados(token);
//        System.out.println(teste);


    }

    public Map<String, Long> getTotalQuantidadePorStatus(String token) {
        List<ChamadoBaseDTO> resp = apiService.ServiceGetChamados(token);

        return resp.stream()
                .collect(Collectors.groupingBy(c -> getStatusLabel(c.getStatus()),
                        Collectors.counting()));
    }

    public Map<Integer, List<ChamadoBaseDTO>> getStatusCamados(String token) {
        List<ChamadoBaseDTO> resp = apiService.ServiceGetChamados(token);

        return resp.stream()
                .collect(Collectors.groupingBy(ChamadoBaseDTO::getStatus));
    }

    public List<ChamadoBaseDTO> getChamadosPorStatus(String token, int status) {
        List<ChamadoBaseDTO> resp = apiService.ServiceGetChamados(token);
        return resp.stream()
                .filter(c -> c.getStatus() == status)
                .collect(Collectors.toList());
    }

    private String getStatusLabel(Integer status) {
        return switch (status) {
            case 0 -> "Aberto";
            case 1 -> "Em Análise";
            case 2 -> "Em Atendimento";
            case 8 -> "Feedback";
            case 9 -> "Aguardando Resposta";
            case 10 -> "Encaminhado Nível 3";
            case 11 -> "Aguardando assistencia";
            case 12 -> "Encaminhado Nível 2";
            default -> "Desconhecido (" + status + ")";
        };
    }

    // Retorna top 10 locais com mais chamados
    public List<Map.Entry<String, Long>> getTop10Locais(String token) {
//        String json = apiService.ServiceGetChamados(token);
        List<ChamadoBaseDTO> chamados = apiService.ServiceGetChamados(token);

        return chamados.stream()
                .filter(c -> c.getLocal() != null && c.getLocal().getName() != null)
                .collect(Collectors.groupingBy(
                        c -> c.getLocal().getName(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "SeparaStatusChamados{" +
                "apiService=" + apiService +
                '}';
    }
}
