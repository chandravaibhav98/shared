package com.chandravaibhav98.StackLibrary.ResponseModels;

import com.chandravaibhav98.StackLibrary.Entity.Book;
import lombok.Data;

@Data
public class ShelfCurrentLoansResponse {
	
	private Book book;
	
	private int daysLeft;
	
	public ShelfCurrentLoansResponse ( Book book , int daysLeft ) {
		this.book = book;
		this.daysLeft = daysLeft;
	}
	
}
