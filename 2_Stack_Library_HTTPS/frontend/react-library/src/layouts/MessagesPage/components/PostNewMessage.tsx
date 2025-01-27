import { useOktaAuth } from "@okta/okta-react";
import React, { useState } from "react";
import MessageModel from "../../../models/MessageModel";

const PostNewMessage = () => {
	const { authState } = useOktaAuth();
	const [title, setTitle] = useState("");
	const [question, setQuestion] = useState("");
	const [displayWarning, setDisplayWarning] = useState(false);
	const [displaySuccess, setDisplaySuccess] = useState(false);

	async function submitNewQuestion() {
		const url = `${process.env.REACT_APP_API}/messages/secure/add/message`;

		if (authState?.isAuthenticated && title !== "" && question !== "") {
			const messageRequestModel: MessageModel = new MessageModel(title, question);
			const requestOptions = {
				method: "POST",
				headers: {
					Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
					"Content-Type": "application/json",
				},
				body: JSON.stringify(messageRequestModel),
			};

			const submitNewQuestionResponse = await fetch(url, requestOptions);
			console.log(submitNewQuestionResponse);
			if (!submitNewQuestionResponse.ok) {
				throw new Error("Something went wrong");
			}

			setTitle("");
			setQuestion("");
			setDisplayWarning(false);
			setDisplaySuccess(true);
		} else {
			setDisplayWarning(true);
			setDisplaySuccess(false);
		}
	}
	return (
		<div className="card mt-3">
			<div className="card-header">Ask Questions to the Admin</div>
			<div className="card-body">
				<form method="POST">
					{displayWarning && (
						<div
							className="alert alert-danger"
							role="alert">
							All fields are required
						</div>
					)}
					{displaySuccess && (
						<div
							className="alert alert-success"
							role="alert">
							Question added Successfully
						</div>
					)}
					<div className="mb-3">
						<label className="form-label">Title</label>
						<input
							type="text"
							className="form-control"
							id="exampleFormControlInput1"
							placeholder="Title"
							onChange={(e) => setTitle(e.target.value)}
							value={title}
						/>
					</div>
					<div className="mb-3">
						<label className="form-label">Question</label>
						<textarea
							className="form-control"
							id="exampleFormControlTextarea1"
							rows={3}
							placeholder="Question"
							onChange={(e) => setQuestion(e.target.value)}
							value={question}></textarea>
					</div>
					<div>
						<button
							className="btn btn-primary mt-3"
							onClick={submitNewQuestion}
							type="button">
							Submit Question
						</button>
					</div>
				</form>
			</div>
		</div>
	);
};

export default PostNewMessage;
