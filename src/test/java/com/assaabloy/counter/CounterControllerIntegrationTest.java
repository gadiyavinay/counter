/*
 * Copyright © 2021 Assa Abloy. All Rights Reserved.
 */
package com.assaabloy.counter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.assaabloy.counter.entity.Counter;
import com.assaabloy.counter.repository.CounterRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CounterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CounterControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CounterRepository repo;

	@LocalServerPort
	private int port;
	
	private Counter counter;
	
	private static final String COUNTER_NAME = "Counter1";
	
	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	private Counter setup() {
		counter = new Counter();
		counter.setName(COUNTER_NAME);
		counter.setValue(1);
		return counter;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testListCounters() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<?> response = restTemplate.exchange(getRootUrl() + "/api/Counters", HttpMethod.GET, entity,
				String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testCreateCounter() {
		counter = this.setup();
		ResponseEntity<Counter> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/create", counter, Counter.class);
		assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        repo.delete(postResponse.getBody());
	}
	
	@Test
	public void testGetCounterByName() {
		counter = this.setup();
		//create new counter
		restTemplate.postForEntity(getRootUrl() + "/api/create", counter, Counter.class);
		
		//get counter by name and assert
		Counter counter = restTemplate.getForObject(getRootUrl() + "/api/counter?name=" + COUNTER_NAME, Counter.class);
        assertEquals(COUNTER_NAME, counter.getName());
        repo.delete(counter);
	}

	@Test
	public void testIncrementCounter() {
		counter = this.setup();
		//create new counter
		restTemplate.postForEntity(getRootUrl() + "/api/create", counter, Counter.class);
		
		//increment counter and assert
		Counter counter = restTemplate.getForObject(getRootUrl() + "/api/counter?name=" + COUNTER_NAME, Counter.class);
		restTemplate.put(getRootUrl() + "/api/increment?name=" + COUNTER_NAME, counter);
		Counter updatedCounter = restTemplate.getForObject(getRootUrl() + "/api/counter?name=" + COUNTER_NAME, Counter.class);
        assertEquals(2, updatedCounter.getValue());
        repo.delete(updatedCounter);
	}

	@Test
	public void testDeleteCounter() {
		counter = this.setup();
		//create new counter
		restTemplate.postForEntity(getRootUrl() + "/api/create", counter, Counter.class);
		
		// delete counter and assert
		restTemplate.delete(getRootUrl() + "/api/delete?name=" + COUNTER_NAME);
		Optional<Counter> counterResponse = repo.findByName(COUNTER_NAME);
		assertFalse(counterResponse.isPresent());

	}

}
