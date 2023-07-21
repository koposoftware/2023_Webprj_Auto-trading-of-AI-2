package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.MachinePredict;
import com.kopo.trading.core.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachinePredictRepository extends JpaRepository<MachinePredict, Long> {

    Optional<MachinePredict> findByCodeOrderByCreatedDateDesc(String code);

    // 상승 예측률이 가장 높은 상위 5개의 종목 추천
    @Query(value = "SELECT *\n" +
            "FROM (\n" +
            "  SELECT *\n" +
            "  FROM MACHINE_PREDICT\n" +
            "  WHERE TO_CHAR(created_date, 'YY/MM/DD') = :date  AND PRED_RATE < 99999  ORDER BY PRED_RATE DESC) WHERE DIFF <99999 AND ROWNUM <= 5", nativeQuery = true)
    List<MachinePredict> findMostProfitStockByGapOrderByCreatedDate(@Param("date") String date);

    @Query(value = "SELECT * FROM (SELECT * FROM MACHINE_PREDICT m WHERE TRUNC(m.created_date) = TRUNC(m.created_date - 1) ORDER BY (m.pred_close - m.prev_close) desc) WHERE ROWNUM <= 5", nativeQuery = true)
    List<MachinePredict> findMostProfitStockByGapYesterdayOrderByCreatedDate();

    @Query(value = "SELECT * FROM (SELECT * FROM MACHINE_PREDICT m WHERE TRUNC(m.created_date) = TRUNC(m.created_date) ORDER BY (m.prev_close - m.pred_close) desc) WHERE ROWNUM <= 5", nativeQuery = true)
    List<MachinePredict> findMostLossStockByGapOrderByCreatedDate();

    @Query(value = "SELECT * FROM (SELECT * FROM MACHINE_PREDICT m WHERE TRUNC(m.created_date) = TRUNC(m.created_date - 1) ORDER BY (m.prev_close - m.pred_close) desc) WHERE ROWNUM <= 5", nativeQuery = true)
    List<MachinePredict> findMostLossStockByGapYesterdayOrderByCreatedDate();

    List<MachinePredict> findByNameOrderByCreatedDateDesc(String name);
}

