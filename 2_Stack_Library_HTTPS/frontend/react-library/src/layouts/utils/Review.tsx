import React from "react";
import ReviewModel from "../../models/ReviewModel";
import ReviewStars from "./ReviewStars";

const Review: React.FC<{ review: ReviewModel }> = (props) => {
	const date = new Date(props.review.date);

	const longMonth = date.toLocaleString("en-us", { month: "long" });
	const dateDay = date.getDate();
	const dateYear = date.getFullYear();

	const renderDate = longMonth + " " + dateDay + ", " + dateYear;

	return (
		<div>
			<div className="col-sm-8 col-md-8">
				<h5>{props.review.userEmail}</h5>
				<div className="row">
					<div className="col">{renderDate}</div>
					<div className="col">
						<ReviewStars
							rating={props.review.rating}
							size={16}
						/>
					</div>
				</div>
				<div className="mt-2">
					<p>{props.review.reviewDescription}</p>
				</div>
			</div>
		</div>
	);
};

export default Review;
