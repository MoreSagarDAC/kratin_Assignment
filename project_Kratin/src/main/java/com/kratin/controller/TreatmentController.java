package com.kratin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kratin.entity.Treatment;
import com.kratin.repository.TreatmentRepo;
@RestController
public class TreatmentController {
	@Autowired
	public TreatmentRepo treatmentrepo;
	
	@GetMapping
    public List<Treatment> getAllMedications() {
        return treatmentrepo.findAll();
    }
	
	@PostMapping
    public ResponseEntity<Treatment> createMedication(@RequestBody Treatment medication) {
		Treatment savedtreatment = treatmentrepo.save(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedtreatment);
    }
	@PutMapping("/{id}")
    public ResponseEntity<Treatment> updateMedication(
            @PathVariable("id") Integer id,
            @RequestBody Treatment updatedTreatment
    ) {
		Treatment treatment = treatmentrepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medication ID"));

		treatment.setName(updatedTreatment.getName());
		treatment.setDosage(updatedTreatment.getDosage());
		treatment.setFrequency(updatedTreatment.getFrequency());
		treatment.setTime(updatedTreatment.getTime());

        Treatment savedTreatment = treatmentrepo.save(treatment);
        return ResponseEntity.ok(savedTreatment);
    }
}
