package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projections.SaleProjection;
import com.devsuperior.dsmeta.projections.SellerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Consulta nativa para gerar o relatório de vendas
    @Query(
            "SELECT s.id as id, s.date as date, s.amount as amount, s.seller.name as name " +
            "FROM Sale s " +
            "WHERE UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
            "AND s.date BETWEEN :minDate AND :maxDate " +
            "ORDER BY s.id"
    )
    Page<SaleProjection> getReport(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);

    // Consulta nativa para gerar o sumário de vendas por vendedor
    @Query(nativeQuery = true, value =
            "SELECT tb_seller.name, SUM(tb_sales.amount) AS total " +
                    "FROM tb_seller " +
                    "LEFT JOIN tb_sales " +
                    "ON tb_sales.seller_id = tb_seller.id " +
                    "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
                    "GROUP BY tb_seller.name " +
                    "ORDER BY tb_seller.name")
    List<SellerProjection> getSummary(LocalDate minDate, LocalDate maxDate);
}
