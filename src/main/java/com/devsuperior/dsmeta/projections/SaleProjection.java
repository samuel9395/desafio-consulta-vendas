package com.devsuperior.dsmeta.projections;

import java.time.LocalDate;

/*
 * Projection usada para consultas personalizadas.
 * Apenas os campos necessários são buscados do banco.
 */
public interface SaleProjection {

    Long getId();
    Double getAmount();
    LocalDate getDate();
    String getName();
}
