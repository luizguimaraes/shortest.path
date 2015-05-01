package br.com.s2it.shortest.path.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando a autonimia do veículo não é maior que 0.
 * */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Autonomy must be greater than 0")
public class NegativeAutonomyException extends RuntimeException {
	private static final long serialVersionUID = -4636626412913094234L;
}
