/*
 * Copyright © 2021 Assa Abloy. All Rights Reserved.
 */
package com.assaabloy.counter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assaabloy.counter.CounterApplication;
import com.assaabloy.counter.entity.Counter;
import com.assaabloy.counter.exceptions.ResourceNotFoundException;
import com.assaabloy.counter.repository.CounterRepository;


@RestController
@RequestMapping("/api")
public class CounterController {

	@Autowired
	private CounterRepository counterRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CounterApplication.class);
	
	@GetMapping(path = "/counters")
	public ResponseEntity<?> listCounters() {
		log.info("CounterController:  list all counters");
		List<Counter> counters = counterRepository.findAll();
		return ResponseEntity.ok(counters);
	}
	
	@PutMapping(path = "/increment")
	public ResponseEntity<Counter> incrementCounter(@RequestParam String name) throws ResourceNotFoundException {
		log.info("CounterController:  increment counter by 1");
		Counter counter = counterRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Counter not found with this name :: " + name));
		counter.setValue(counter.getValue() + 1);
		final Counter updatedCounter = counterRepository.save(counter);
		return ResponseEntity.ok(updatedCounter);
	}
	
	@PostMapping(path = "/create")
	public Counter createCounter(@RequestBody Counter counter) {
		log.info("CounterController:  create counter");
		if(counterRepository.findByName(counter.getName()).isPresent()) {
			throw new RuntimeException("Counter already exist");
		}	
		return counterRepository.save(counter);
	}
	
	@GetMapping(path = "/counter")
	public ResponseEntity<Counter> getCounterByName(@RequestParam String name) {
		log.info("CounterController:  get counter by name");
		Counter counter = counterRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Counter not found with this name :: " + name));
		return ResponseEntity.ok(counter);
	}
	
	@DeleteMapping(path = "/delete")
	public void deleteCounter(@RequestParam String name) throws ResourceNotFoundException {
		log.info("CounterController:  delete counter");
		Counter counter = counterRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Counter not found with this name :: " + name));
		counterRepository.delete(counter);
	}
}
