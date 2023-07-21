package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.Kospi;
import com.kopo.trading.core.entity.KospiMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface KospiRepository extends JpaRepository<Kospi, String> {
    // kospi와 stock이 일치하는 id 추출. stock의 시가총액 내림차순으로
    @Query("select k from Kospi k join Stock s on k.id = s.kospi.id order by s.cap DESC")
    Page<Kospi> findAllByCapDesc(Pageable pageable);

    Optional<Kospi> findById(String id);

    @Query(value = "select distinct k from Kospi k join Stock s on k.id = s.kospi order by k.stockSupply", nativeQuery = false)
    Page<Kospi> findAll(Pageable pageable);

    @Query(value = "select k.id as code, k.name, s.close as current_price from kospi k join stock s on k.id = s.kospi_id where trunc(s.created_date) between trunc(sysdate -1) and trunc(sysdate)", nativeQuery = true)
    List<KospiMapping> findStockInfo();

    Kospi findByName(String name);
}
