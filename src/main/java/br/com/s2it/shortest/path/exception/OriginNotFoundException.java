package br.com.s2it.shortest.path.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando não é possível encontrar o nó de origem no grafo.
 * */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such Origin")
public class OriginNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6023067795173397894L;
}
