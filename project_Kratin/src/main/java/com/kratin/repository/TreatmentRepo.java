package com.kratin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kratin.entity.Treatment;

public interface TreatmentRepo extends JpaRepository<Treatment, Integer> {

}
