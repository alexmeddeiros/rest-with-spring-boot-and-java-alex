package br.com.alex.math;

import org.springframework.web.bind.annotation.GetMapping;

public class SimpleMath {
	public Double sum(Double numberOne, Double numberTwo) {
		return numberOne + numberTwo;
	}

	public Double subtraction(Double numberOne, Double numberTwo) {
		return numberOne - numberTwo;
	}

	public Double multiplication(Double numberOne, Double numberTwo) {
		return numberOne * numberTwo;
	}

	public Double division(Double numberOne, Double numberTwo) {
		return numberOne / numberTwo;
	}

	@GetMapping("/mean/{numberOne}/{numberTwo}")
	public Double mean(Double numberOne, Double numberTwo) {
		return (numberOne + numberTwo) / 2;
	}

	@GetMapping("/squareRoot/{number}")
	public Double squareRoot(Double number) {
		return Math.sqrt(number);
	}
}
