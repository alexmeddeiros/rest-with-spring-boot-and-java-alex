package br.com.alex.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.alex.controllers.PersonController;
import br.com.alex.data.vo.v1.PersonVO;
import br.com.alex.exceptions.RequiredObjectIsNullException;
import br.com.alex.exceptions.ResourceNotFoundException;
import br.com.alex.mapper.DozerMapper;
import br.com.alex.model.Person;
import br.com.alex.repositories.PersonRepository;

@Service
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	private PersonRepository repository;

	public List<PersonVO> findAll() {
		logger.info("Find all people");

		var persons = DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;

	}

	public PersonVO create(PersonVO person) {
		if(person == null) throw new RequiredObjectIsNullException();		
		
		logger.info("Creating one person");
		var entity = DozerMapper.parseObject(person, Person.class) ;
		repository.save(entity);
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonVO update(PersonVO person) {
		
		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one person");
		var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		repository.save(entity);
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one person");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		repository.delete(entity);
	}	

	public PersonVO findById(Long id) {

		logger.info("Find one PersonVO");
		var entity =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));
		
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

}
