import { useOktaAuth } from "@okta/okta-react";
import React, { useEffect, useState } from "react";
import LoadingSpinner from "../utils/LoadingSpinner";
import { CardElement, useElements, useStripe } from "@stripe/react-stripe-js";
import { Link } from "react-router-dom";
import PaymentInfoRequest from "../../models/PaymentInfoRequest";

const PaymentPage = () => {
	const { authState } = useOktaAuth();
	const [httpError, setHttpError] = useState(false);
	const [submitDisabled, setSubmitDisabled] = useState(false);
	const [fees, setFees] = useState(0);
	const [loadingFees, setLoadingFees] = useState(true);

	useEffect(() => {
		const fetchFees = async () => {
			if (authState && authState.isAuthenticated) {
				const url = `${process.env.REACT_APP_API}/payments/search/findByUserEmail?userEmail=${authState.accessToken?.claims.sub}`;

				const requestOptions = {
					method: "GET",
					headers: { "Content-Type": "application/json" },
				};
				const paymentResponse = await fetch(url, requestOptions);

				// if (!paymentResponse.ok) {
				// 	throw new Error("Something went wrong");
				// }
				// const paymentResponseJson = await paymentResponse.json();
				// setFees(paymentResponseJson.amount);
				// setLoadingFees(false);

				if (paymentResponse.ok) {
					const paymentResponseJson = await paymentResponse.json();
					setFees(paymentResponseJson.amount);
					setLoadingFees(false);
				} else if (paymentResponse.status === 404) {
					setFees(0);
					setLoadingFees(false);
				} else {
					throw new Error("Something went wrong!");
				}
			}
		};

		fetchFees().catch((error: any) => {
			setLoadingFees(false);
			setHttpError(error.message);
		});
	}, []);

	const elements = useElements();
	const stripe = useStripe();

	async function checkout() {
		if (!stripe || !elements || !elements.getElement(CardElement)) {
			return;
		}

		setSubmitDisabled(!true);

		let paymentInfo = new PaymentInfoRequest(Math.round(fees * 100), "USD", authState?.accessToken?.claims.sub);

		const url = `${process.env.REACT_APP_API}/payment/secure/payment-intent`;

		const requestOptions = {
			method: "POST",
			headers: {
				Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
				"Content-Type": "application/json",
			},
			body: JSON.stringify(paymentInfo),
		};

		const stripeResponse = await fetch(url, requestOptions);

		if (!stripeResponse.ok) {
			setHttpError(true);
			setSubmitDisabled(false);
			throw new Error("Something went wrong!");
		}
		const stripeResponseJson = await stripeResponse.json();

		stripe
			.confirmCardPayment(
				stripeResponseJson.client_secret,
				{
					payment_method: {
						card: elements.getElement(CardElement)!,
						billing_details: {
							email: authState?.accessToken?.claims.sub,
						},
					},
				},
				{ handleActions: false },
			)
			.then(async function (result: any) {
				if (result.error) {
					setSubmitDisabled(false);
					alert("There was an error");
				} else {
					const url = `${process.env.REACT_APP_API}/payment/secure/payment-complete`;
					const requestOptions = {
						method: "PUT",
						headers: {
							Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
							"Content-Type": "application/json",
						},
					};

					const stripeResponse = await fetch(url, requestOptions);
					if (!stripeResponse.ok) {
						setHttpError(true);
						setSubmitDisabled(false);
						throw new Error("Something went wrong!");
					}
					setFees(0);
					setSubmitDisabled(false);
				}
			});
		setHttpError(false);
	}

	if (loadingFees) {
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
		<div className="container">
			{fees > 0 && (
				<div className="card mt-3">
					<h5>
						Fees pending : <span className="text-danger">${fees}</span>
					</h5>
					<div className="card-body">
						<h5 className="card-title mb-3">Credit Card</h5>
						<CardElement id="card-element" />
						<button
							disabled={submitDisabled}
							type="button"
							onClick={checkout}
							className="btn btn-md main-color text-white mt-3">
							Pay fees
						</button>
					</div>
				</div>
			)}
			{fees === 0 && (
				<div className="mt-3">
					<h5>You have no dues!</h5>
					<Link
						className="btn main-color text-white"
						type="button"
						to="search">
						Explore top books
					</Link>
				</div>
			)}
		</div>
	);
};

export default PaymentPage;
