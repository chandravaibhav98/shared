package com.chandravaibhav98.StackLibrary.Service;

import com.chandravaibhav98.StackLibrary.Dao.BookRepository;
import com.chandravaibhav98.StackLibrary.Dao.CheckoutRepository;
import com.chandravaibhav98.StackLibrary.Dao.ReviewRepository;
import com.chandravaibhav98.StackLibrary.Entity.Book;
import com.chandravaibhav98.StackLibrary.RequestModels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {
	
	private BookRepository bookRepository;
	
	private ReviewRepository reviewRepository;
	
	private CheckoutRepository checkoutRepository;
	
	@Autowired
	public AdminService ( BookRepository bookRepository , ReviewRepository reviewRepository , CheckoutRepository checkoutRepository ) {
		this.bookRepository = bookRepository;
		this.reviewRepository = reviewRepository;
		this.checkoutRepository = checkoutRepository;
	}
	
	
	public void postBook ( AddBookRequest addBookRequest ) {
		
		Book book = new Book( );
		book.setTitle( addBookRequest.getTitle( ) );
		book.setAuthor( addBookRequest.getAuthor( ) );
		book.setDescription( addBookRequest.getDescription( ) );
		book.setCopies( addBookRequest.getCopies( ) );
		book.setCopiesAvailable( addBookRequest.getCopies( ) );
		book.setCategory( addBookRequest.getCategory( ) );
		book.setImg( addBookRequest.getImg( ) );
		bookRepository.save( book );
		
	}
	
	public void increaseBookQuantity ( Long bookId ) throws Exception {
		
		Optional< Book > book = bookRepository.findById( bookId );
		
		if ( ! book.isPresent( ) ) {
			throw new Exception( "Book not Found" );
		}
		
		book.get( ).setCopiesAvailable( book.get( ).getCopiesAvailable( ) + 1 );
		book.get( ).setCopies( book.get( ).getCopies( ) + 1 );
		
		bookRepository.save( book.get( ) );
		
	}
	
	public void decreaseBookQuantity ( Long bookId ) throws Exception {
		
		Optional< Book > book = bookRepository.findById( bookId );
		
		if ( ! book.isPresent( ) || book.get( ).getCopiesAvailable( ) <= 0 || book.get( ).getCopies( ) <= 0 ) {
			throw new Exception( "Book not Found or Quantity Locked" );
		}
		
		book.get( ).setCopiesAvailable( book.get( ).getCopiesAvailable( ) - 1 );
		book.get( ).setCopies( book.get( ).getCopies( ) - 1 );
		
		bookRepository.save( book.get( ) );
		
	}
	
	public void deleteBook ( Long bookId ) throws Exception {
		
		Optional< Book > book = bookRepository.findById( bookId );
		
		if ( ! book.isPresent( ) ) {
			throw new Exception( "Book not Found" );
		}
		
		bookRepository.delete( book.get( ) );
		checkoutRepository.deleteAllByBookId( bookId );
		reviewRepository.deleteAllByBookId( bookId );
		
	}
}
