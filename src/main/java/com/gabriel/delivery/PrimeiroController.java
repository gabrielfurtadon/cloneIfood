package com.gabriel.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.delivery.modelo.Cliente;
import com.gabriel.delivery.service.AtivacaoClienteService;

@Controller
public class PrimeiroController {
	
	@Autowired
	private AtivacaoClienteService ativacao;
	
	public PrimeiroController(AtivacaoClienteService ativacao) {
		this.ativacao = ativacao; //Esta injetando uma instancia de ativacao
	}



	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		
		Cliente joao = new Cliente("joao", "joao@gmail.com", "111222333");
		
		ativacao.ativar(joao);
		
		return "Hello";
	}

}
