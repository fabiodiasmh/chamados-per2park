package com.chamados.Per2park.controller.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
public class SatEquipamentoDTO {

//    @JsonProperty("codEquipContrato")
//    private Long codEquipContrato;

//    @JsonProperty("codContrato")
//    private Long codContrato;

    @JsonProperty("numSerie")
    private String numSerie;
//
//    @JsonProperty("numSerieCliente")
//    private String numSerieCliente;
//
    @JsonProperty("codCliente")
    private Long codCliente;
//
//    @JsonProperty("codEquip")
//    private Long codEquip;
//
//    @JsonProperty("codMagnus")
//    private String codMagnus;

//    @JsonProperty("qtdEquip")
//    private Integer qtdEquip;
//
//    @JsonProperty("vlrUnitario")
//    private BigDecimal vlrUnitario;
//
//    @JsonProperty("vlrInstalacao")
//    private BigDecimal vlrInstalacao;
//
//    @JsonProperty("qtdDiaGarantia")
//    private Integer qtdDiaGarantia;

//    @JsonProperty("dataHoraCad")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
//    private LocalDateTime dataHoraCad;

//    @JsonProperty("dataHoraManut")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
//    private LocalDateTime dataHoraManut;

//    @JsonProperty("codUsuarioCad")
//    private String codUsuarioCad;
//
//    @JsonProperty("codUsuarioManut")
//    private String codUsuarioManut;

    @JsonProperty("indAtivo")
    private Integer indAtivo;

//    @JsonProperty("codTipoEquip")
//    private Integer codTipoEquip;
//
//    @JsonProperty("codGrupoEquip")
//    private Integer codGrupoEquip;

    // === Campos opcionais ou que aparecem em alguns registros ===
//    @JsonProperty("percValorEnt")
//    private BigDecimal percValorEnt;
//
//    @JsonProperty("percValorIns")
//    private BigDecimal percValorIns;
//
//    @JsonProperty("qtdLimDiaEnt")
//    private Integer qtdLimDiaEnt;
//
//    @JsonProperty("qtdLimDiaIns")
//    private Integer qtdLimDiaIns;
//
//    @JsonProperty("codContratoEquipDataGar")
//    private Integer codContratoEquipDataGar;
//
//    @JsonProperty("codContratoEquipDataEnt")
//    private Integer codContratoEquipDataEnt;
//
//    @JsonProperty("codContratoEquipDataIns")
//    private Integer codContratoEquipDataIns;

    // === Relacionamentos (opcional: você pode remover se não usar) ===
    // Se quiser evitar objetos aninhados, comente ou remova os blocos abaixo.
    // Caso contrário, crie DTOs separados (ex: ContratoDTO, ClienteDTO).

//     @JsonProperty("contrato")
//     private Object contrato; // ou ContratoDTO

     @JsonProperty("cliente")
     private Object cliente; // ou ClienteDTO

     @JsonProperty("equipamento")
     private Object equipamento;

    @JsonProperty("localAtendimento")
     private Object localAtendimento;
//
//     @JsonProperty("grupoEquipamento")
//     private Object grupoEquipamento;

//     @JsonProperty("tipoEquipamento")
//     private Object tipoEquipamento;
}
