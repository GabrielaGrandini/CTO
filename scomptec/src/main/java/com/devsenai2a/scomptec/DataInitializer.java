package com.devsenai2a.scomptec;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.devsenai2a.scomptec.model.Usuario;
import com.devsenai2a.scomptec.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        String email = System.getenv("ADMIN_EMAIL");
        String senha = System.getenv("ADMIN_PASSWORD");
        
        if(email == null || senha == null) {
        	return;
        }
        
        if (usuarioRepository.findByEmail(email).isPresent()) {
        	return;
        }
        
        Usuario admin = new Usuario();
        
        admin.setNome("Administrador");
        admin.setEmail(email);
        
        admin.setSenha(passwordEncoder.encode(senha));
        admin.setTipo("SCOMPTEC");
        
        usuarioRepository.save(admin);
    }
}