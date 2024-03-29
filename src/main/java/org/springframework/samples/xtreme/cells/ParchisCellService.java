package org.springframework.samples.xtreme.cells;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.xtreme.board.ParchisBoard;
import org.springframework.samples.xtreme.pieces.Color;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisCellService {

	private ParchisCellRepository parchisCellRepository;

	@Autowired
	public ParchisCellService(ParchisCellRepository parchisCellRepository) {
		super();
		this.parchisCellRepository = parchisCellRepository;
	}
	
	@Transactional
	public void save(ParchisCell cell){
		parchisCellRepository.save(cell);
	}
	
	@Transactional
	public void delete(ParchisCell cell){
		parchisCellRepository.delete(cell);
	}
	
	@Transactional
	public ParchisCell findByPosition(Integer id){
		return parchisCellRepository.findById(id).get();
	}
	
	@Transactional
    public void generateSquares(ParchisBoard board) {
        for(int i=1;i<=104;i++) {
            if(69<=i&&i<=76){
                ParchisCell s = new ParchisCell(null,i,Color.YELLOW,board);
                parchisCellRepository.save(s);
            }else if(77<=i&&i<=84){
                ParchisCell s = new ParchisCell(null,i,Color.GREEN,board);
                parchisCellRepository.save(s);
            }else if(85<=i&&i<=92){
                ParchisCell s = new ParchisCell(null,i,Color.RED,board);
                parchisCellRepository.save(s);
            }else if(93<=i&&i<=100){
                ParchisCell s = new ParchisCell(null,i,Color.BLUE,board);
                parchisCellRepository.save(s);
            }else {
                ParchisCell s = new ParchisCell(null,i,null,board);
                parchisCellRepository.save(s);
            }
        }
    }

    @Transactional(readOnly = true)
	public List<ParchisCell> findCellYellow(){
		return parchisCellRepository.findCellYellow();
	}
    @Transactional(readOnly = true)
	public List<ParchisCell> findCellGreen(){
		return parchisCellRepository.findCellGreen();
	}
    @Transactional(readOnly = true)
	public List<ParchisCell> findCellRed(){
		return parchisCellRepository.findCellRed();
	}
    @Transactional(readOnly = true)
	public List<ParchisCell> findCellBlue(){
		return parchisCellRepository.findCellBlue();
	}
}