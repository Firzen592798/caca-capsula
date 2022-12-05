package br.firzen.cacacapsulas.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import br.firzen.cacacapsulas.exceptions.InvalidOperationException;
import br.firzen.cacacapsulas.exceptions.NotFoundException;

@ControllerAdvice //APENAS PRA REST
public class ExceptionHandlerAdvice {
	@ResponseBody
	@ExceptionHandler(NotFoundException.class) // Tudo q for EmployeeNotFoundException é tratado aqui
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseEntity<?> employeeNotFoundHandler(NotFoundException ex) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", ex.getMessage());
		errors.put("code", "404");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	

	@ResponseBody
	@ExceptionHandler(InvalidOperationException.class) // Tudo q for EmployeeNotFoundException é tratado aqui
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<String> handleAPIException(ServerErrorException exception, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage());
	}

	
	@ExceptionHandler(Exception.class)
	public ModelAndView  handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error");
	    mav.addObject("url", request.getRequestURL());
	    mav.addObject("error", ex.getMessage());
	    return mav;
	}
	

	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgumentExceptionExceptions(IllegalArgumentException exception) {
		return "/error/404";
	}
}
