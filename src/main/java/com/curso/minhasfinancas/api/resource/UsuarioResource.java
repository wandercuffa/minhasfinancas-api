package com.curso.minhasfinancas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.minhasfinancas.api.dto.UsuarioDTO;
import com.curso.minhasfinancas.exceptions.ErroAutenticacao;
import com.curso.minhasfinancas.exceptions.RegraNegocioException;
import com.curso.minhasfinancas.model.entity.Usuario;
import com.curso.minhasfinancas.service.UsuarioService;
import com.curso.minhasfinancas.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	UsuarioService service;
	
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}
		
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = Usuario.builder()
					.nome(dto.getNome())
					.email(dto.getEmail())
					.senha(dto.getSenha())
					.build();
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado =  service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
