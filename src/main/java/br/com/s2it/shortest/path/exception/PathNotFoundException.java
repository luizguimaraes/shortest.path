package br.com.s2it.shortest.path.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception lançada quando não há nenhuma caminho possível entre a origem e o
 * destino.
 * */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There's path between Origin and Destination")
public class PathNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 848814386628860509L;
}
