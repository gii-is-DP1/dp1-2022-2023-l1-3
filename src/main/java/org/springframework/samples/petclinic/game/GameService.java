package org.springframework.samples.petclinic.game;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public Optional<Game> findGameById(Integer gameId) {
        return gameRepository.findById(gameId);
    }

    @Transactional
    public Collection<Game> getAll(){
        return gameRepository.findAll();
    }

    @Transactional
    public void deleteGame(Integer gameId){
        gameRepository.deleteById(gameId);
    }
    
}