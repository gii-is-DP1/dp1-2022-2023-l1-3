package org.springframework.samples.xtreme.player;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.xtreme.player.Player;
import org.springframework.samples.xtreme.player.PlayerService;
import org.springframework.samples.xtreme.user.Authorities;
import org.springframework.samples.xtreme.user.AuthoritiesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path="/players")
public class PlayerController {

    private final PlayerService playerService;
    private final AuthoritiesService authoritiesService;


    private static final String VIEWS_FORM = "players/createPlayerForm";
    private static final String PLAYERS_LIST = "players/playersList";
    private static final String VIEW_GAMEHOME = "players/gameHome";
    private static final String CREATE_GAME = "players/createGame";


    @Autowired
    public PlayerController(PlayerService playerService,AuthoritiesService authoritiesService) {
        this.playerService = playerService;
        this.authoritiesService = authoritiesService;
    }

    @GetMapping
    public ModelAndView showPlayerList() {
        ModelAndView mav = new ModelAndView(PLAYERS_LIST);
        mav.addObject("players", this.playerService.getAllPlayers());
        return mav;
    }

    @GetMapping(path="/create")
    public ModelAndView viewForm(){
        ModelAndView mav = new ModelAndView(VIEWS_FORM);
/*
        Authorities a= new Authorities();
        a.setAuthority("player");

        Set<Authorities> cjto= new HashSet<Authorities>();
        cjto.add(a);

        Player p= new Player();
        p.getUser().setAuthorities(cjto);*/

        mav.addObject("player", new Player());
        return mav;
    }

    @PostMapping(path = "/create")
    public ModelAndView createPlayer(@Valid Player player, BindingResult res){
        ModelAndView mav = new ModelAndView("redirect:/");
        Player p = playerService.findByUsername(player.getUser().getUsername());
        if(res.hasErrors()){
            mav = new ModelAndView(VIEWS_FORM);
            mav.addObject("player", player);
        } 
        if(p!=null) {
            mav = new ModelAndView(VIEWS_FORM);
            mav.addObject("player", player);
            if(p!=null && p.getUser().getUsername().equals(player.getUser().getUsername())) {
                mav.addObject("message", "El nombre de usuario ya está registrado.");
            }
            if(p!=null && p.getEmail().equals(player.getEmail())) {
                mav.addObject("message", "El email de usuario ya está registrado");
            }
        } else{
            Authorities a= new Authorities();
            a.setId(player.getId());
            a.setAuthority("player");
            a.setUser(player.getUser());

            playerService.save(player);
            authoritiesService.saveAuthorities(a);

        }
        return mav;
    }

    // TODO: Checkear el tipo de authority del usuario logueado para mostrarle unos botones u otros en home
    // Mirar jsp createOwner de la carpeta user (ejemplo uso if)
    @GetMapping(path="/gameHome")
    public ModelAndView gameHome() {
        ModelAndView mav = new ModelAndView(VIEW_GAMEHOME);
        //mav.addObject("user", authentication.getAuthetication());
        return mav;
    }

    
    @GetMapping(path="/createGame")
    public ModelAndView createGame() {
        ModelAndView mav = new ModelAndView(CREATE_GAME);
       // mav.addObject("players", playerService.getAllPlayers());
        return mav;
    }
    

}
