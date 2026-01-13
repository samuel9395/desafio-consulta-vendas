package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleProjection;

import java.time.LocalDate;

// DTO específico para o relatório de vendas
public class SaleReportDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private String sellerName;

    // Construtor completo
    public SaleReportDTO(Long id, LocalDate date, Double amount, String sellerName) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.sellerName = sellerName;
    }

    /*
     * Construtor que recebe a Projection
     * Usado nas consultas personalizadas
     */
    public SaleReportDTO(SaleProjection projection) {
        id = projection.getId();
        date = projection.getDate();
        amount = projection.getAmount();
        sellerName = projection.getName();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSellerName() {
        return sellerName;
    }

}