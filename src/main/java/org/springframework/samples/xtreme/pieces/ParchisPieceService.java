package org.springframework.samples.xtreme.pieces;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParchisPieceService {
    
    private ParchisPieceRepository parchisPieceRepository;

    @Autowired
    public ParchisPieceService(ParchisPieceRepository parchisPieceRepository) {
        this.parchisPieceRepository = parchisPieceRepository;
    }

    @Transactional
    public void save(ParchisPiece piece) {
        parchisPieceRepository.save(piece);
    }

    @Transactional
    public void delete(ParchisPiece piece) {
        parchisPieceRepository.delete(piece);
    }

    @Transactional
    public Collection<ParchisPiece> findPieceByGameAndPlayer(Integer playerId, Integer gameId) {
        return parchisPieceRepository.findPieceByGameAndPlayer(playerId, gameId);
    }
}
