package com.forexdata.scraper.repository;

import com.forexdata.scraper.entity.ForexData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForexDataRepository extends JpaRepository<ForexData, Long> {
}

