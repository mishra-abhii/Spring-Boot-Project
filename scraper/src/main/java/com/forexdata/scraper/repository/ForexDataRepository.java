package com.forexdata.scraper.repository;

import java.util.Optional;
import com.forexdata.scraper.entity.ForexData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForexDataRepository extends JpaRepository<ForexData, Long> {

    Optional<ForexData> findByDateAndFromCurrencyAndToCurrency(String date, String fromCurrency, String toCurrency);

}

