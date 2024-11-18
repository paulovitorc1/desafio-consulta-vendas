package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsuperior.dsmeta.dto.SaleMinDTOProjection;
import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

        @Query(value = "SELECT s.id AS saleId, s.amount, s.date, se.name AS sellerName FROM tb_sales s " +
                        "INNER JOIN tb_seller se ON s.seller_id = se.id " +
                        "WHERE (:initialDate IS NULL OR s.date >= :initialDate) " +
                        "AND (:endDate IS NULL OR s.date <= :endDate) " +
                        "AND (:sellerName IS NULL OR se.name LIKE %:sellerName%)", countQuery = "SELECT COUNT(*) FROM tb_sales s "
                                        +
                                        "INNER JOIN tb_seller se ON s.seller_id = se.id " +
                                        "WHERE (:initialDate IS NULL OR s.date >= :initialDate) " +
                                        "AND (:endDate IS NULL OR s.date <= :endDate) " +
                                        "AND (:sellerName IS NULL OR se.name LIKE %:sellerName%)", nativeQuery = true)
        Page<SaleMinDTOProjection> getSalesReport(
                        @Param("initialDate") LocalDate initialDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("sellerName") String sellerName,
                        Pageable pageable);

        // SUM? SUM (Amount) dentro de um determinado periodo.
        /*
         * @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.name, SUM(s.amount)) "
         * +
         * "FROM Sale s " +
         * "WHERE s.date BETWEEN :initialDate AND :endDate " +
         * "GROUP BY s.seller.name")
         * List<SaleSumaryDTO> getSalesSumaryBySeller(
         * 
         * @Param("initialDate") LocalDate initialDate,
         * 
         * @Param("endDate") LocalDate endDate);
         */

        @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumaryDTO(s.seller.name, SUM(s.amount)) " +
                        "FROM Sale s " +
                        "WHERE s.date BETWEEN :initialDate AND :endDate " +
                        "GROUP BY s.seller.name")
        List<SaleSumaryDTO> getSalesSummaryBySeller(@Param("initialDate") LocalDate initialDate,
                        @Param("endDate") LocalDate endDate);

}
