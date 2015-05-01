package br.com.s2it.shortest.path.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando a Origem é igual ao Destino.
 * */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Origin must not be equals to Destination")
public class OriginEqualsDestinationException extends RuntimeException {
	private static final long serialVersionUID = -4137347448180263507L;
}
