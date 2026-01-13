package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SellerProjection;

// DTO usado no sumário de vendas por vendedor
public class SellerMinDTO {

    private String sellerName;
    private Double total;

    // Construtor
    public SellerMinDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    /*
     * Construtor que recebe a Projection
     * Usado na query de resumo
     */
    public SellerMinDTO(SellerProjection projection) {
        sellerName = projection.getName();
        total = projection.getTotal();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
