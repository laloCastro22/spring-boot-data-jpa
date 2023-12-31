package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteDao clienteDao;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {

		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> map) {

		Cliente cliente = new Cliente();
		map.put("titulo", "Formulario de cliente");
		map.put("cliente", cliente);

		return "form";
	}
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {

		Cliente cliente = null;
		if(id > 0) {
			cliente = clienteDao.findById(id);
		}else {
			return "redirect:/listar";
		}

		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,SessionStatus status) { // Se pone la notacion @Valid,
																						// para habilitar la validacion
																						// al objeto cliente
		// @Valid Cliente cliente,BindingResult result -> estos siempre van juntos

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		clienteDao.save(cliente);
		status.setComplete();
		return "redirect:/listar";
	}
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model) {
		
		if(id > 0) {
			clienteDao.delete(id);
		}
		
		return "redirect:/listar";
	}
}
