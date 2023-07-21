package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.Kospi;
import com.kopo.trading.core.entity.Transaction;
import com.kopo.trading.core.entity.TransactionMapping;
import com.kopo.trading.user.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "select new com.kopo.trading.core.entity.TransactionMapping(t.kospi.id, k.name, avg(t.pricePerStock), sum(t.amount)) from Transaction t join Kospi k on t.kospi = k  where t.member.id= ?1  group by t.kospi, k.name  having sum(t.amount) > 0")
    List<TransactionMapping> findBalanceByMemberId(Long id);

//    @Query(value = "select t.trade_date, t.kospi_id, k.name, t.price_per_stock, t.amount, t.total_price from transaction t join kospi k on t.kospi_id = k.id where t.member_id = :id and k.name like '%' || :stockName || '%' order by t.trade_date desc", nativeQuery = true)
//    List<Transaction> findByNameOrderByTradeDateDesc(@Param("id") Long id, @Param("stockName") String stockName);

    @Query("select t from Transaction t join t.kospi k where t.member.id = :id and k.name like %:stockName% order by t.tradeDate desc")
    List<Transaction> findByNameOrderByTradeDateDesc(@Param("id") Long id, @Param("stockName") String stockName);

}
