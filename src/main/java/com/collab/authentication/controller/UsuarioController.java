package com.collab.authentication.controller;

import com.collab.authentication.models.Usuario;
import com.collab.authentication.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cadastro")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/form")
    public String formulario(){
        return "Formulario";
    }

    @GetMapping
    public List<Usuario> getAllUser(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserId(@PathVariable Long id){
        return usuarioRepository.findById(id)
                .map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario user){
        return usuarioRepository.findById(id)
                .map(userUpdate -> {
                    userUpdate.setNome(user.getNome());
                    userUpdate.setInstituicao(user.getInstituicao());
                    userUpdate.setEmail(user.getEmail());
                    userUpdate.setUsername(user.getUsername());
                    userUpdate.setPassword(user.getPassword());
                    Usuario update = usuarioRepository.save(userUpdate);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(update);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteUserId(@PathVariable Long id){
        return usuarioRepository.findById(id)
                .map(record -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
