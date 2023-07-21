package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.Kospi;
import com.kopo.trading.core.entity.Stock;
import com.kopo.trading.core.entity.StockMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 파라미터: 테이블, PK 자료형(여러개여서 클래스로받아옴)
// JPA 한정 repository annotation을 쓰지 않아도됨
@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{ //형식

    List<Stock> findByKospiIdOrderByTradingDayAsc(String kospiId);
    List<Stock> findByKospiIdOrderByTradingDayDesc(String kospiId);
    List<Stock> findFirstByKospiIdOrderByTradingDayDesc(String kospiId);

    Optional<Stock> findFirstByKospiId(String id);

    @Query(value = "SELECT KOSPI_ID, TRADING_DAY, CLOSE\n" +
            "            FROM (\n" +
            "                SELECT KOSPI_ID, TRADING_DAY, CLOSE,\n" +
            "                ROW_NUMBER() OVER (PARTITION BY KOSPI_ID ORDER BY TRADING_DAY DESC) AS rn\n" +
            "                FROM STOCK\n" +
            "                WHERE KOSPI_ID IN (\n" +
            "                    SELECT KOSPI_ID\n" +
            "                    FROM STOCK\n" +
            "                    GROUP BY KOSPI_ID\n" +
            "                    HAVING COUNT(*) >= 20", nativeQuery = true)
    List<StockMapping> findStockByCustom();


}