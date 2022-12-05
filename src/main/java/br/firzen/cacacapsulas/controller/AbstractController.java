package br.firzen.cacacapsulas.controller;

import org.springframework.beans.factory.annotation.Autowired;

import br.firzen.cacacapsulas.config.UsuarioAdvice;
import br.firzen.cacacapsulas.service.AbstractService;

public class AbstractController<T> {
	
	@Autowired
	protected UsuarioAdvice usuarioAdvice;
	
	protected final static String URL_PAGE = "";
		
	protected final static String URL_LIST = "/list";
	
	protected final static String URL_VIEW = "/view";
	
	protected final static String URL_FORM = "/form";
	
	protected final static String URL_EDIT = "/edit";
	
	protected final static String URL_DELETE = "/delete";

	@Autowired
	public AbstractService<T> service;
}


