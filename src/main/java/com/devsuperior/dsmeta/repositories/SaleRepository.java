package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SaleProjection;
import com.devsuperior.dsmeta.projections.SellerProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Consulta nativa (SQL puro) para gerar o relatório de vendas.
    @Query(nativeQuery = true, value =
            "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name " +
                    "FROM tb_sales " +
                    "LEFT JOIN tb_seller " +
                    "ON tb_seller.id = tb_sales.seller_id " +
                    "WHERE UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%')) AND tb_sales.date BETWEEN :minDate AND :maxDate " +
                    "ORDER BY tb_sales.id ")
    List<SaleProjection> getReport(String name, LocalDate minDate, LocalDate maxDate);

    // Consulta nativa para gerar o SUMÁRIO DE VENDAS POR VENDEDOR.
    @Query(nativeQuery = true, value =
            "SELECT tb_seller.name, SUM(tb_sales.amount) AS total " +
                    "FROM tb_seller " +
                    "LEFT JOIN tb_sales " +
                    "ON tb_sales.seller_id = tb_seller.id " +
                    "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY tb_seller.name " +
                    "ORDER BY tb_seller.name ")
    List<SellerProjection> getSummary(LocalDate minDate, LocalDate maxDate);
}
