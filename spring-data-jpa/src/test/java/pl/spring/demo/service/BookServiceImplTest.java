package pl.spring.demo.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager.getCache("booksCache").clear();
    }

    @Test
    public void testShouldFindAllBooks() {
        // when
        List<BookTo> allBooks = bookService.findAllBooks();
        // then
        assertNotNull(allBooks);
        assertFalse(allBooks.isEmpty());
        assertEquals(7, allBooks.size());
    }

    @Test
    //@Ignore
    public void testShouldFindAllBooksByTitle() {
        // given
        final String title = "Opium w rosole";
        // when
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
    }

    @Test(expected = BookNotNullIdException.class)
    public void testShouldThrowBookNotNullIdException() {
        // given
        final BookTo bookToSave = new BookTo();
        bookToSave.setId(22L);
        // when
        bookService.saveBook(bookToSave);
        // then
        fail("test should throw BookNotNullIdException");
    }
    
    @Test
    //@Ignore
    public void shouldSetProperIdDuringSaveBook() {
        // given
        final BookTo bookToSave = new BookTo();
        //bookToSave.setId(22L);
        // when
        bookService.saveBook(bookToSave);
        // then
        //assertTrue(bookToSave.getId()==55L);
        long bookId = bookToSave.getId();
        assertEquals(7L, bookId);
    }
    
    /*
    @Test
    @Ignore
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
    */
      
}
