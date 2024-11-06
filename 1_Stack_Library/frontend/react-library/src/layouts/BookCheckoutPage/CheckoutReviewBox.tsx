import React, { useState, useEffect } from "react";
import BookModel from "../../models/BookModel";
import { Link } from "react-router-dom";
import LeaveAReview from "../utils/LeaveAReview";

const CheckoutReviewBox: React.FC<{
	book: BookModel | undefined;
	mobile: boolean;
	currentLoansCount: number;
	isAuthenticated: any;
	isCheckedOut: boolean;
	checkoutBook: any;
	isReviewLeft: boolean;
	submitReview: any;
}> = (props) => {
	function buttonRender() {
		if (props.isAuthenticated) {
			if (!props.isCheckedOut && props.currentLoansCount < 5) {
				return (
					<button
						onClick={() => props.checkoutBook()}
						className="btn btn-success btn-lg">
						Check Out
					</button>
				);
			} else if (props.isCheckedOut) {
				return (
					<p>
						<b>Book Checked Out. Enjoy</b>
					</p>
				);
			} else if (!props.isCheckedOut) {
				return <p className="text-danger">Too many books Checked Out</p>;
			}
		}
		return (
			<Link
				to={`/login`}
				className="btn btn-success btn-lg">
				Sign In
			</Link>
		);
	}

	function reviewRender() {
		if (props.isAuthenticated && !props.isReviewLeft) {
			return (
				<p>
					<LeaveAReview submitReview={props.submitReview} />
				</p>
			);
		} else if (props.isAuthenticated && props.isReviewLeft) {
			return (
				<p>
					<b>Thanks for leaving a Review</b>
				</p>
			);
		}
		return (
			<div>
				<hr />
				<p>Sign In to leave a Review</p>
			</div>
		);
	}

	return (
		<div className={props.mobile ? "card d-flex mt-5" : "card col-3 container d-flex mb-5"}>
			<div className="card-body container">
				<div className="mt-3">
					<p>
						<b>{props.currentLoansCount}/5</b>Books Checked Out
					</p>
					<hr />
					{props.book && props.book.copiesAvailable && props.book.copiesAvailable > 0 ? (
						<h4 className="text-success">Available</h4>
					) : (
						<h4 className="text-danger">Wait List</h4>
					)}
					<div className="row">
						<p className="col-6 lead">
							<b>{props.book?.copies}</b> copies
						</p>
						<p className="col-6 lead">
							<b>{props.book?.copiesAvailable}</b> available
						</p>
					</div>
				</div>
				{buttonRender()}
				<hr />
				<p className="mt-3">This number can change until the order is successfully placed</p>
				{reviewRender()}
			</div>
		</div>
	);
};

export default CheckoutReviewBox;
