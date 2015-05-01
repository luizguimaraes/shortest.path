package br.com.s2it.shortest.path.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando o custo do combustivel não é maior que 0.
 * */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "CostOfLitre must be greater than 0")
public class NegativeCostOfLitreException extends RuntimeException {
	private static final long serialVersionUID = 8605082219411993961L;
}
