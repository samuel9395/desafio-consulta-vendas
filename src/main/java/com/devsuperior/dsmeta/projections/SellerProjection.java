package com.devsuperior.dsmeta.projections;

/*
 * Projection usada para consultas personalizadas.
 * Apenas os campos necessários são buscados do banco.
 */
public interface SellerProjection {

    String getName();
    Double getTotal();
}
