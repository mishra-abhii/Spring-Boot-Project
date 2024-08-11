package com.forexdata.scraper.repository;

import java.util.List;
import java.util.Optional;
import com.forexdata.scraper.entity.ForexData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForexDataRepository extends JpaRepository<ForexData, Long> {
    // Query to fetch data from DB for a particular period and currency pair.
    List<ForexData> findByDateBetweenAndFromCurrencyAndToCurrency(String startDate, String endDate, String fromCurrency, String toCurrency);

    // Query to find a record by date, fromCurrency and toCurrency.
    Optional<ForexData> findByDateAndFromCurrencyAndToCurrency(String date, String fromCurrency, String toCurrency);

}

