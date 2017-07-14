package br.com.crescer.caronas.controller;

import br.com.crescer.caronas.service.GrupoService;
import br.com.crescer.caronas.entity.Grupo;
import br.com.crescer.caronas.entity.Usuario;
import br.com.crescer.caronas.entity.UsuarioGrupo;
import br.com.crescer.caronas.service.UsuarioGrupoService;
import br.com.crescer.caronas.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chris
 */
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    GrupoService grupoService;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    UsuarioGrupoService usuarioGrupoService;

    @GetMapping
    public Iterable<Grupo> findAll() {
        return grupoService.findAll();
    }
    
    @GetMapping (value = "/usuario")
    public List<Grupo> findAllByUser(@AuthenticationPrincipal User user) {
        List<Grupo> retorno = new ArrayList<>();
        Usuario usuario = usuarioService.findByIdAutorizacao(user.getUsername());
        List<UsuarioGrupo> usuariosGrupo = usuarioGrupoService.findByUsuario(usuario);
        for (UsuarioGrupo usuarioGrupo : usuariosGrupo) {
            retorno.add(usuarioGrupo.getGrupo());
        }
        return retorno;
//        return usuariosGrupo
//                .stream()
//                .map(UsuarioGrupo::getGrupo)
//                .collect(toList());
//        Iterable<Grupo> grupos = grupoService.findAll();
        
//        for(Grupo grupo: grupos) {
//            if (grupo.getRotina().getUsuario() == usuario) {
//                gruposDoUsuario.add(grupo);
//            }
//        };
//        return gruposDoUsuario;
    }
    
    @PostMapping
    public Grupo save(@RequestBody Grupo grupo) {
        return grupoService.save(grupo);
    }

    @PutMapping
    public Grupo update(@RequestBody Grupo grupo) {
        //implementar lógica das vagas
        //o put vai ter que ser dividido em 2, colocar e retirar integrante
        return grupoService.update(grupo);
    }

    @DeleteMapping(value = "/{idGrupo}")
    public void remove(@PathVariable Long idGrupo) {
        //implementar lógica das vagas
        Grupo grupo = grupoService.loadById(idGrupo);
        grupoService.remove(grupo);
    }

    //implementar get para buscar grupos de um usuário com os participantes
    
}