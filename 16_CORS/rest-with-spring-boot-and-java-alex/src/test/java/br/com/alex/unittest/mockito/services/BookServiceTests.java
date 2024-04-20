package br.com.alex.unittest.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alex.data.vo.v1.BookVO;
import br.com.alex.exceptions.RequiredObjectIsNullException;
import br.com.alex.mapper.DozerMapper;
import br.com.alex.model.Book;
import br.com.alex.repositories.BookRepository;
import br.com.alex.services.BookServices;
import br.com.alex.unittest.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTests {

	MockBook mock;
	
	@InjectMocks
	private BookServices service;
	
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks () throws Exception {
		mock = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Book> list = mock.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var books = service.findAll();
		assertNotNull(books);
		assertEquals(14, books.size());
		
		
		var bookOne = books.get(1);
		assertNotNull(bookOne);
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertNotNull(bookOne.getLaunch_date());
		assertEquals(3F, bookOne.getPrice());
		assertEquals("Author Test1", bookOne.getAuthor());
		assertEquals("Title Test1", bookOne.getTitle());
		
		
		var bookFive = books.get(5);
		assertNotNull(bookFive);
		assertNotNull(bookFive.getKey());
		assertNotNull(bookFive.getLinks());
		assertTrue(bookFive.toString().contains("links: [</api/book/v1/5>;rel=\"self\"]"));
		assertNotNull(bookFive.getLaunch_date());
		assertEquals(3F, bookFive.getPrice());
		assertEquals("Author Test5", bookFive.getAuthor());
		assertEquals("Title Test5", bookFive.getTitle());
		
		var bookNine = books.get(9);
		assertNotNull(bookNine);
		assertNotNull(bookNine.getKey());
		assertNotNull(bookNine.getLinks());
		assertTrue(bookNine.toString().contains("links: [</api/book/v1/9>;rel=\"self\"]"));
		assertNotNull(bookNine.getLaunch_date());
		assertEquals(3F, bookNine.getPrice());
		assertEquals("Author Test9", bookNine.getAuthor());
		assertEquals("Title Test9", bookNine.getTitle());	
	}

	@Test
	void testCreate() {
		Book entity = mock.mockEntity();
		entity.setId(1L);
		
		// testando o Parse Object <O, D>
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		
		when(repository.save(entity)).thenReturn(entity);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertNotNull(result.getLaunch_date());
		assertEquals(3F, result.getPrice());
		assertNotNull(result.getLaunch_date());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());	
	}
	
	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testUpdate() {
		Book entity = mock.mockEntity();
		entity.setId(1L);
		
		BookVO vo = mock.mockVO();
		vo.setKey(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.update(vo);
		
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertNotNull(result.getLaunch_date());
		assertEquals(3F, result.getPrice());
		assertNotNull(result.getLaunch_date());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());	
		
	}

	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "It is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testDelete() {
		Book entity = mock.mockEntity();
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

	@Test
	void testFindById() {
		Book entity = mock.mockEntity();
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertNotNull(result.getLaunch_date());
		assertEquals(3F, result.getPrice());
		assertNotNull(result.getLaunch_date());
		assertEquals("Author Test1", result.getAuthor());
		assertEquals("Title Test1", result.getTitle());		
	}

}
