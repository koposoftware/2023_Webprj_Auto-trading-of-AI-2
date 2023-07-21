package com.kopo.trading.core.repository;

import com.kopo.trading.core.entity.MainView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainViewRepository extends JpaRepository<MainView, Long> {
    @Query(value = "select category, price, status " +
            "from main_view " +
            "where created_date = (select max(created_date) from main_view)", nativeQuery = true)
    List<Object[]> findMain();
}
