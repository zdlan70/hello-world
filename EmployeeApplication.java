package com.verzion.assignment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.verzion.assignment.db.Department;
import com.verzion.assignment.db.DepartmentRepository;
import com.verzion.assignment.db.Employee;
import com.verzion.assignment.db.EmployeeRepository;
import com.verzion.assignment.service.DepartmentService;

@SpringBootApplication
public class EmployeeApplication {

	private static final Logger log = LoggerFactory
			.getLogger(EmployeeApplication.class);
	
	static final double SAL=51000d;

	@Bean
	public CommandLineRunner demo(EmployeeRepository employeeRepository,
			DepartmentService departmentRepository) {
		return (args) -> {
			departmentRepository.save(new Department("Computer", 10000, 20000));
			departmentRepository.save(new Department("Computer", 20000, 21000));
			departmentRepository
					.save(new Department("Accounting", 20000, 30000));
			departmentRepository
					.save(new Department("Marketing", 30000, 35000));
			departmentRepository.save(new Department("Sales", 30000, 41000));

			// save a couple of customers
			Department depart1 = departmentRepository.findOne(1L);
			Employee e1 = new Employee("Daniel", "Chen", 100000, depart1, null);
			employeeRepository.save(e1);

			Employee e2 = new Employee("Jack", "Zhu", 70000, depart1, e1);
			employeeRepository.save(e2);

			Employee e3 = new Employee("Jack", "Chen", 50000, depart1, e2);
			employeeRepository.save(e3);

			Employee e4 = new Employee("Bob", "Max", 50000,
					departmentRepository.findOne(2L), null);
			employeeRepository.save(e4);


			// fetch all employees
			log.info("\n\n DEmployeess found with findAll():");
			Iterable<Employee> all = employeeRepository.findAll();
			
			all.forEach(x -> log.info(x.toString()));
			
			//aa.forEach(c -> log.info(c.toString()));			

			// fetch an individual employee by ID
			log.info("\n\nEmployee found with findOne(1L):");
			log.info(toString(employeeRepository.findOne(3L)));


			// fetch an individual employee by ID
			log.info("\n\n Employee found with findId(3L):");			
			log.info(toString(employeeRepository.findById(3L)));
			
			long size = all.spliterator().getExactSizeIfKnown();
			if (size!=4) 
				throw new RuntimeException("size not correct " + size);
			
			List<Employee> cs = employeeRepository.findByLastName("Chen");
			if (cs.size() != 2)
				throw new RuntimeException("number of employees not correct " + cs.size());
			
			Employee e = employeeRepository.findOne(3L);
			e.setSalary(SAL);
			employeeRepository.save(e);


			// fetch an individual employee by ID
			log.info("\n\n Employee found with findId(3L):");			
			double salary=employeeRepository.findOne(3L).getSalary();
			if (salary != SAL )
				throw new RuntimeException("salary is not correct " + salary);
			
			employeeRepository.delete(e);

			all = employeeRepository.findAll();
			size = all.spliterator().getExactSizeIfKnown();
			if (size!=3) 
				throw new RuntimeException("size not correct " + size);
			
			// fetch employees by name
			log.info("\n\n Employee found with findByLastName('Chen'):");
			cs = employeeRepository.findByLastName("Chen");
			if (cs.size() != 1)
				throw new RuntimeException("number of employees not correct " + cs.size());
			cs.forEach(c -> log.info(c.toString()));
		};
	}

	private void testAndGetAllEmployees(EmployeeRepository employeeRepository) {
		// fetch all employees
		log.info("\n\n DEmployeess found with findAll():");
		Iterable<Employee> all = employeeRepository.findAll();
		
		all.forEach(x -> log.info(x.toString()));
		
		//aa.forEach(c -> log.info(c.toString()));			

		// fetch an individual employee by ID
		log.info("\n\nEmployee found with findOne(1L):");
		log.info(toString(employeeRepository.findOne(3L)));


		// fetch an individual employee by ID
		log.info("\n\n Employee found with findId(3L):");			
		log.info(toString(employeeRepository.findById(3L)));
	}

	private static String toString(Object o) {
		return o == null ? "" : o.toString();

	}
}
