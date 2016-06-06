package com.verzion.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.verzion.assignment.db.Department;
import com.verzion.assignment.db.DepartmentRepository;

@SpringBootApplication
public class DepartmentApplication {

	private static final Logger log = LoggerFactory
			.getLogger(DepartmentApplication.class);


	@Bean
	public CommandLineRunner demo(DepartmentRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Department("Computer",10000,20000));
			repository.save(new Department ("Computer",20000,21000));
			repository.save(new Department( "Accounting",20000,30000));
			repository.save(new Department( "Marketing",30000,35000));
			repository.save(new Department( "Sales",30000,41000));

			
			// fetch all departments
			log.info("\n\n Departments found with findAll():");
			repository.findAll().forEach(c -> log.info(c.toString()));			

			// fetch an individual department by ID
			log.info("\n\nDepartment found with findOne(1L):");
			log.info(repository.findOne(1L).toString());


			// fetch an individual department by ID
			log.info("\n\n Department found with findId(1L):");			
			log.info(repository.findById(1L).toString());

			// fetch departments by  name
			log.info("\n\n Department found with findByName('Computer'):");
			repository.findByName("Computer").forEach(c -> log.info(c.toString()));
		};
	}

}
