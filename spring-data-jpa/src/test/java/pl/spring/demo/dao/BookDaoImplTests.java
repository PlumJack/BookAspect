package pl.spring.demo.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.to.BookTo;

public class BookDaoImplTests {

	@Test
	public void shouldSaveBookWithNextId() {
		//given
		BookDaoImpl bdi = new BookDaoImpl();
		BookTo book = new BookTo();
		BookTo actual;
		//when
		actual = bdi.save(book);
		long id = actual.getId();
		//then
		assertEquals(7L,id);
	}

}
