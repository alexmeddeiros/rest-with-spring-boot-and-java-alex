package br.com.alex.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alex.exceptions.ResourceNotFoundException;
import br.com.alex.model.Person;
import br.com.alex.repositories.PersonRepository;

@Service
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	private PersonRepository repository;

	public List<Person> findAll() {
		logger.info("Find all people");
		return repository.findAll();

	}

	public Person create(Person person) {
		logger.info("Creating one person");
		Person entity = new Person();
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		
		return repository.save(entity);
	}

	public Person update(Person person) {
		logger.info("Updating one person");
		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());

		return repository.save(entity);
	}

	public void delete(Long id) {
		logger.info("Deleting one person");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		repository.delete(entity);
	}

	public Person findById(Long id) {

		logger.info("Find one Person");
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));
	}

}
