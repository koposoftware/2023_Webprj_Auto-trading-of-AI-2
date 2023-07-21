package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<Stock, Long> {
    @Query(value = "SELECT k.name, s.kospi_id as code ,s.close, s.changed_rate " +
            "FROM stock s JOIN kospi k ON s.kospi_id = k.id " +
            "WHERE s.trading_day = (SELECT MAX(s2.trading_day) FROM stock s2 WHERE s2.kospi_id = s.kospi_id) " +
            "ORDER BY s.volume DESC " +
            "FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Object[]> findTop5StockData();


    @Query(value = "SELECT k.name, s.kospi_id as code ,s.close, s.changed_rate " +
            "FROM stock s JOIN kospi k ON s.kospi_id = k.id " +
            "WHERE s.trading_day = (SELECT MAX(s2.trading_day) FROM stock s2 WHERE s2.kospi_id = s.kospi_id) " +
            "ORDER BY s.changed_rate DESC " +
            "FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Object[]> findTop5UpStockData();

    @Query(value = "SELECT k.name, s.kospi_id as code ,s.close, s.changed_rate " +
            "FROM stock s JOIN kospi k ON s.kospi_id = k.id " +
            "WHERE s.trading_day = (SELECT MAX(s2.trading_day) FROM stock s2 WHERE s2.kospi_id = s.kospi_id) " +
            "ORDER BY s.changed_rate ASC " +
            "FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Object[]> findTop5DownStockData();


}

