/*
 * Copyright © 2021 Assa Abloy. All Rights Reserved.
 */
package com.assaabloy.counter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assaabloy.counter.entity.Counter;

public interface CounterRepository extends JpaRepository<Counter, Integer> {

	Optional<Counter> findByName(String name);
}
