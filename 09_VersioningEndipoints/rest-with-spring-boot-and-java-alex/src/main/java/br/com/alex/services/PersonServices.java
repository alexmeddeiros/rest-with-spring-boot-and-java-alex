package br.com.alex.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alex.data.vo.v1.PersonVO;
import br.com.alex.data.vo.v2.PersonVOV2;
import br.com.alex.exceptions.ResourceNotFoundException;
import br.com.alex.mapper.DozerMapper;
import br.com.alex.mapper.custom.PersonMapper;
import br.com.alex.model.Person;
import br.com.alex.repositories.PersonRepository;

@Service
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	private PersonRepository repository;
	@Autowired
	private PersonMapper mapper;

	public List<PersonVO> findAll() {
		logger.info("Find all people");
		return  DozerMapper.parseObject(repository.findAll(), PersonVO.class) ;

	}

	public PersonVO create(PersonVO person) {
		logger.info("Creating one person");
		var entity = DozerMapper.parseObject(person, Person.class) ;
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class) ;
		return vo;
	}
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person with V2");
		var entity = mapper.convertVoToEntity(person) ;
		var vo =  mapper.convertEntityToVo(repository.save(entity));
		return vo;
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating one person");
		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class) ;
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one person");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		repository.delete(entity);
	}	

	public PersonVO findById(Long id) {

		logger.info("Find one PersonVO");
		var entity =  repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}

}
