package br.com.s2it.shortest.path.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando não é possível encontrar o nó de destino no grafo.
 * */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such Destination")
public class DestinationNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6869588673393919007L;
}
