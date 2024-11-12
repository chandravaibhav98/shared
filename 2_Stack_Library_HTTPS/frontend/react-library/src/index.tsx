import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { App } from "./App";
import { BrowserRouter } from "react-router-dom";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

const stripePromise = loadStripe(
	"pk_test_51OxBE62M4ODdCRI44rQagg18jNjDXa3i40mYEc0Q6wbrtNBOt9gQqqd7p84N6yKeIKntTO1i0HTkEa3hwfvhcGxu00dDEDmwSt",
);

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);
root.render(
	<BrowserRouter>
		<Elements stripe={stripePromise}>
			<App />
		</Elements>
	</BrowserRouter>,
);
