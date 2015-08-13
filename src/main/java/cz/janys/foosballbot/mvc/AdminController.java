package cz.janys.foosballbot.mvc;

import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.dto.JoinedPlayer;
import cz.janys.foosballbot.entity.PlayerEntity;
import cz.janys.foosballbot.repository.PlayerRepository;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.JabberMessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martin Janys
 */
@Controller
@RequestMapping("/")
public class AdminController {

    @Value("${foosball.size}")
    private Integer foosballSize;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private JabberMessageDispatcher dispatcher;

    @ModelAttribute("allPlayers")
    public List<PlayerEntity> allPlayersModel() {
        return playerRepository.findAll();
    }
    @ModelAttribute("joinedPlayers")
    public Collection<String> joinedPlayersModel() {
        return joinedPlayers();
    }
    @ModelAttribute("foosballSize")
    public int foosballSizeModel() {
        return foosballSize;
    }

    @RequestMapping
    public String adminPage(Model model) {
        return "admin_page";
    }

    @RequestMapping(value = "/addPlayer", method = RequestMethod.POST)
    public void addPlayer(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam("player") String player) throws IOException {

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setJabber(player);

        playerRepository.save(playerEntity);

        response.sendRedirect("/");
    }

    @RequestMapping("/unregisterPlayer")
    public void unregisterPlayer(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("id") Long playerId) throws IOException {
        playerRepository.delete(playerId);
        response.sendRedirect("/");
    }

    @SuppressWarnings("unchecked")
    private Collection<String> joinedPlayers() {
        Map<String, Object> conversation = dispatcher.getConversationMap(Constants.FOOSBALL_CONVERSATION_ID);
        Collection<JoinedPlayer> joinedPlayers = MessageUtils.getFoosballPlayers(conversation);
        if (!CollectionUtils.isEmpty(joinedPlayers)) {
            return joinedPlayers
                    .stream()
                    .map(JoinedPlayer::getPlayer)
                    .collect(Collectors.toSet());
        }
        else {
            return Collections.emptyList();
        }
    }
}
