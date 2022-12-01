package org.springframework.samples.xtreme.player;


import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.xtreme.friendship.Friendship;
import org.springframework.samples.xtreme.friendship.FriendshipService;
import org.springframework.samples.xtreme.friendship.FriendshipState;
import org.springframework.samples.xtreme.game.Game;
import org.springframework.samples.xtreme.game.GameService;
import org.springframework.samples.xtreme.player.Player;
import org.springframework.samples.xtreme.player.PlayerService;
import org.springframework.samples.xtreme.user.Authorities;
import org.springframework.samples.xtreme.user.AuthoritiesService;
import org.springframework.samples.xtreme.user.User;
import org.springframework.samples.xtreme.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping(path="/players")
public class PlayerController {

    
    private final PlayerService playerService;
    private final AuthoritiesService authoritiesService;
    private final FriendshipService friendshipService;
    private final UserService userService;

    private final PlayerValidator playerValidator;
    private final GameService gameService;



    private static final String VIEWS_FORM = "players/createPlayerForm";
    private static final String PLAYERS_LIST = "players/playersList";
    private static final String VIEW_GAMEHOME = "users/home";
    private static final String CREATE_GAME = "players/createGame";
    private static final String FRIENDS = "players/friends";
    private static final String PROFILE = "players/viewProfile";
    private static final String EDIT_PROFILE = "players/editProfile";
    private static final String CREATE_FRIENDSHIP = "players/createFriend";
    private static final String LIST_PLAYERS = "players/listNewFriends";


    @Autowired
    public PlayerController(PlayerService playerService,AuthoritiesService authoritiesService,
    FriendshipService friendshipService, PlayerValidator playerValidator, GameService gameService,
    UserService userService) {
        this.playerService = playerService;
        this.authoritiesService = authoritiesService;
        this.friendshipService=friendshipService;
        this.playerValidator=playerValidator;
        this.gameService=gameService;
        this.userService=userService;
    }

    
    @InitBinder("player")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(playerValidator);
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
        mav.addObject("player", new Player());
        return mav;
    }

    @PostMapping(path = "/create")
    public ModelAndView createPlayer(@Valid @ModelAttribute("player") Player player, BindingResult res){
        ModelAndView mav = new ModelAndView("redirect:/");

        if(res.hasErrors()){
            mav = new ModelAndView(VIEWS_FORM);
            mav.addObject("player", player);
        } else{
            playerService.save(player);
        }
        return mav;
    }
    
    @GetMapping(path="/createGame")
    public ModelAndView createGame() {
        ModelAndView mav = new ModelAndView(CREATE_GAME);
        mav.addObject("game",new Game());
      
        return mav;
    }

    @PostMapping(path = "/createGame")
    public ModelAndView createGame(@Valid Game game, BindingResult res){
        ModelAndView mav = new ModelAndView("redirect:/lobby");
       
        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails=null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
            Player player = playerService.findByUsername(userDetails.getUsername());
            game.setCreatorPlayer(player);
          }

        if(res.hasErrors()||game.getGameName()==null||game.getCreatorPlayer()==null){
            mav = new ModelAndView(CREATE_GAME);
            mav.addObject("game", game);
            
        }else{
            gameService.save(game);
        }
        return mav;
    }
    
    @GetMapping(path = "/friends")
    public ModelAndView friends(){
        ModelAndView mav= new ModelAndView(FRIENDS);

        // obtener el usuario actualmente logueado
        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails=null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
            Player p= playerService.findByUsername(userDetails.getUsername());

            Friendship fs= new Friendship();
            fs.setPlayer1(p);
            fs.setFriendshipState(FriendshipState.PENDING);
            mav.addObject("friendship", fs);

            Collection<Friendship> col = friendshipService.getAcceptedFriendshipsByUsername(userDetails.getUsername());
            List<Player> amigosByUsername = col.stream().filter(x->x.getPlayer1().equals(p)).map(x->x.getPlayer2()).collect(Collectors.toList());    
            List<Player> amigosByUsername2 = col.stream().filter(x->x.getPlayer2().equals(p)).map(x->x.getPlayer1()).collect(Collectors.toList());    
            amigosByUsername.addAll(amigosByUsername2);
            mav.addObject("myfriends", amigosByUsername);

            Collection<Friendship> colPending = friendshipService.getPendingFriendshipsByUsername(userDetails.getUsername());
            List<Player> amigosByUsernamePending = colPending.stream().filter(x->x.getPlayer1().equals(p)).map(x->x.getPlayer2()).collect(Collectors.toList());    
            List<Player> amigosByUsernamePending2 = colPending.stream().filter(x->x.getPlayer2().equals(p)).map(x->x.getPlayer1()).collect(Collectors.toList());    
            amigosByUsernamePending.addAll(amigosByUsernamePending2);
            mav.addObject("myfriendsPending", amigosByUsernamePending);
           
        }
        return mav;
    }

    @GetMapping(path="/friends/sendFriendship")
    public ModelAndView viewPlayers(){
        ModelAndView mav = new ModelAndView(LIST_PLAYERS);

                // obtener el usuario actualmente logueado
                Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                UserDetails userDetails=null;
                if (principal instanceof UserDetails) {
                    userDetails = (UserDetails) principal;
                    Player p= playerService.findByUsername(userDetails.getUsername());

                    List<Player> jugadores=  playerService.getAllPlayers();

                    Collection<Friendship> misAmigos= friendshipService.getFriendshipsByUsername(userDetails.getUsername());
                    List<Player> amigos1 = misAmigos.stream().filter(x->x.getPlayer1().equals(p)).map(x->x.getPlayer2()).collect(Collectors.toList());    
                    List<Player> amigos2 = misAmigos.stream().filter(x->x.getPlayer2().equals(p)).map(x->x.getPlayer1()).collect(Collectors.toList());    
                    amigos1.addAll(amigos2);
                    
                    jugadores.removeAll(amigos1);
                    jugadores.remove(p);
                     mav.addObject("players", jugadores);
            
                }   


        return mav;
    }

    @GetMapping(path = "/friends/{username}")
    public ModelAndView createFriendship(@PathVariable String username){
       ModelAndView mav= new ModelAndView(CREATE_FRIENDSHIP);
       Player player = playerService.findByUsername(username);
       Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       UserDetails userDetails=null;
       if (principal instanceof UserDetails) {
           userDetails = (UserDetails) principal;
           System.out.println("---Nombre actual del usuario logueado: "+userDetails.getUsername());
           }
       if(userDetails!= null){
          mav.addObject("player", player);
          mav.addObject("frienship", new Friendship());
       }


        return mav;
    }

    @PostMapping(path="/friends/{username}")
    public ModelAndView editProfilePost(@Valid @ModelAttribute("frindship") Friendship friendship, BindingResult res, @PathVariable("username") String username){
        ModelAndView mav = new ModelAndView("redirect:/"+FRIENDS);

        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String usuarioActual = userDetails.getUsername();

        Player player1 = playerService.findByUsername(usuarioActual);
        Player player2 = this.playerService.findByUsername(username);


        if(player1 != player2){
            friendship.setPlayer1(player1);
            friendship.setPlayer2(player2);

            Collection<Friendship> amigos = friendshipService.getFriendshipsByUsername(player1.getUser().getUsername());
            Boolean existFrienship= amigos.stream().anyMatch(x->x.getPlayer1().equals(player2) || x.getPlayer2().equals(player2));
            
            if(!existFrienship){
            friendshipService.saveFriendship(friendship);
            }
        }
            return mav;
    }
    

    @GetMapping(path="/{username}")
    public ModelAndView showProfile(@PathVariable String username){
        ModelAndView mav = new ModelAndView(PROFILE);
        Player player = this.playerService.findByUsername(username);
        mav.addObject("isBanned", player.getUser().isEnabled());
        mav.addObject("player", player);
        // obtener el usuario actualmente logueado
        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails=null;
        Boolean esAdmin = false;
        Boolean esUserEqual = false;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
            esAdmin=userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin"));
            esUserEqual = userDetails.getUsername().equals(username);
        }
        
        mav.addObject("esAdmin", esAdmin);
        mav.addObject("esUserEqual", esUserEqual);
        return mav;
    }

    @PostMapping(path="/{username}")
    public ModelAndView showProfilePost(@RequestParam String enabled, @PathVariable("username") String username){
        ModelAndView mav = new ModelAndView("redirect:/players/"+ username);
        
        User user = this.playerService.findByUsername(username).getUser();
        System.out.println("--- el usuario "+ user.getUsername()+" esta: "+ enabled);

       if(enabled.equals("activado")){
            user.setEnabled(true);
        }else{
            user.setEnabled(false);
        }
        this.userService.save(user);
        
        return mav;
    }
    
    @GetMapping(path="/{username}/edit")
    public ModelAndView editProfile(@PathVariable("username") String username){
        ModelAndView mav = new ModelAndView(EDIT_PROFILE);
        Player player = this.playerService.findByUsername(username);
        // obtener el usuario actualmente logueado
        Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails=null;
        Boolean esUserEqual = false;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
            esUserEqual = userDetails.getUsername().equals(username);
        }
        mav.addObject("esUserEqual", esUserEqual);
        System.out.println(esUserEqual);
        mav.addObject("player", player);
        return mav;
    }

    @PostMapping(path="/{username}/edit")
    public ModelAndView editProfilePost(@Valid @ModelAttribute("player") Player updatePlayer, BindingResult res, @PathVariable("username") String username){
        ModelAndView mav = new ModelAndView("redirect:/players/{username}");
        
        Player player=this.playerService.findByUsername(username);

        player.setPicProfile(updatePlayer.getPicProfile());
        player.setEmail(updatePlayer.getEmail());
        player.setFirstName(updatePlayer.getFirstName());
        player.setOnline(true);
        player.setLastName(updatePlayer.getLastName());
        player.getUser().setPassword(updatePlayer.getUser().getPassword());
        //player.getUser().setUsername(updatePlayer.getUser().getUsername());

        this.playerService.save(player);

        return mav;
    
    }
    
}
