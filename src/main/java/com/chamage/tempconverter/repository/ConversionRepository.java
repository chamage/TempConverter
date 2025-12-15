package com.chamage.tempconverter.repository;

import com.chamage.tempconverter.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    List<Conversion> findAllByOrderByTimestampDesc();
}

