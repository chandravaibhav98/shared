import React, { useEffect, useState } from "react";
import BookModel from "../../../models/BookModel";
import LoadingSpinner from "../../utils/LoadingSpinner";
import Pagination from "../../utils/Pagination";
import ChangeQuantityOfBook from "./ChangeQuantityOfBook";

const ChangeQuantityOfBooks = () => {
	const [books, setBooks] = useState<BookModel[]>([]);
	const [isLoading, setIsLoading] = useState(true);
	const [httpError, setHttpError] = useState(null);
	const [currentPage, setCurrentPage] = useState(1);
	const [booksPerPage] = useState(5);
	const [totalAmountOfBooks, setTotalAmountOfBooks] = useState(0);
	const [totalPages, setTotalPages] = useState(0);

	const [bookDelete, setBookDelete] = useState(false);

	useEffect(() => {
		const fetchBooks = async () => {
			const baseUrl: string = `${process.env.REACT_APP_API}/books?page=${currentPage - 1}&size=${booksPerPage}`;

			const response = await fetch(baseUrl);
			if (!response.ok) {
				throw new Error("Something went wrong");
			}

			const responseJson = await response.json();
			const responseData = responseJson._embedded.books;

			setTotalAmountOfBooks(responseJson.page.totalElements);
			setTotalPages(responseJson.page.totalPages);

			const loadedBooks: BookModel[] = [];

			for (const key in responseData) {
				loadedBooks.push({
					id: responseData[key].id,
					title: responseData[key].title,
					author: responseData[key].author,
					description: responseData[key].description,
					copies: responseData[key].copies,
					copiesAvailable: responseData[key].copiesAvailable,
					category: responseData[key].category,
					img: responseData[key].img,
				});
			}

			setBooks(loadedBooks);
			setIsLoading(false);
		};

		fetchBooks().catch((error: any) => {
			setIsLoading(false);
			setHttpError(error.message);
		});
	}, [currentPage, bookDelete]);

	const indexOfLastBook: number = currentPage * booksPerPage;
	const indexOfFirstBook: number = indexOfLastBook - booksPerPage;
	let lastItem = booksPerPage * currentPage <= totalAmountOfBooks ? booksPerPage * currentPage : totalAmountOfBooks;
	const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

	const deleteBook = () => setBookDelete(!bookDelete);

	if (isLoading) {
		return <LoadingSpinner />;
	}

	if (httpError) {
		return (
			<div className="container m-5">
				<p>{httpError}</p>
			</div>
		);
	}

	return (
		<div className="container mt-5">
			{totalAmountOfBooks > 0 ? (
				<>
					<div className="mt-3">
						<h3>Number of results : </h3>
					</div>
					<p>
						{indexOfFirstBook + 1} to {lastItem} of {totalAmountOfBooks} items :{" "}
					</p>
					{books.map((book) => (
						<ChangeQuantityOfBook
							book={book}
							key={book.id}
							deleteBook={deleteBook}
						/>
					))}
				</>
			) : (
				<h5>Let's add a book</h5>
			)}
			{totalPages > 1 && (
				<Pagination
					currentPage={currentPage}
					totalPages={totalPages}
					paginate={paginate}
				/>
			)}
		</div>
	);
};

export default ChangeQuantityOfBooks;
