package br.com.alex.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alex.controllers.BookController;
import br.com.alex.data.vo.v1.BookVO;
import br.com.alex.exceptions.RequiredObjectIsNullException;
import br.com.alex.exceptions.ResourceNotFoundException;
import br.com.alex.mapper.DozerMapper;
import br.com.alex.model.Book;
import br.com.alex.repositories.BookRepository;

@Service
public class BookServices {
	private Logger logger = Logger.getLogger(BookServices.class.getName());

	@Autowired
	private BookRepository repository;

	public List<BookVO> findAll() {
		logger.info("Find all book");

		var books = DozerMapper.parseListObject(repository.findAll(), BookVO.class);
		books.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		
		return books;

	}

	public BookVO create(BookVO book) {
		if(book == null) throw new RequiredObjectIsNullException();		
		
		logger.info("Creating one book");
		var entity = DozerMapper.parseObject(book, Book.class) ;
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		repository.save(entity);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public BookVO update(BookVO book) {
		
		if(book == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one book");
		var entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		entity.setAuthor(book.getAuthor());
		entity.setTitle(book.getTitle());
		entity.setLaunch_date(book.getLaunch_date());
		entity.setPrice(book.getPrice());
		repository.save(entity);
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one book");

		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));

		repository.delete(entity);
	}	

	public BookVO findById(Long id) {

		logger.info("Find one book");
		var entity =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Records nor Fond for this ID"));
		
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

}
