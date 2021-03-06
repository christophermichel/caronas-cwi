package br.com.crescer.caronas.service;

import br.com.crescer.caronas.entity.RotinaDiaSemana;
import br.com.crescer.caronas.repository.RotinaDiaSemanaRepository;
import br.com.crescer.caronas.service.exceptions.CaronasException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author chris
 */
@Service
public class RotinaDiaSemanaService {

    @Autowired
    RotinaDiaSemanaRepository rotinaDiaSemanaRepository;

    public Iterable<RotinaDiaSemana> findAll() {
        return rotinaDiaSemanaRepository.findAll();
    }

    public RotinaDiaSemana save(RotinaDiaSemana rotinaDiaSemana) throws CaronasException{
            if (rotinaDiaSemana.getVagasDisponiveis() < 0) {
                throw new CaronasException("O número de vagas não pode ser negativo");
            } 
        return rotinaDiaSemanaRepository.save(rotinaDiaSemana);
    }

    public RotinaDiaSemana update(RotinaDiaSemana rotinaDiaSemana) throws CaronasException{
            if (rotinaDiaSemana.getVagasDisponiveis() < 0) {
                throw new CaronasException("O número de vagas não pode ser negativo");
            } 
        return rotinaDiaSemanaRepository.save(rotinaDiaSemana);
    }

    public void remove(RotinaDiaSemana rotinaDiaSemana) {
        rotinaDiaSemanaRepository.delete(rotinaDiaSemana);
    }

    public RotinaDiaSemana loadById(Long id) {
        return rotinaDiaSemanaRepository.findOne(id);
    }
}
