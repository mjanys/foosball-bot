package cz.janys.foosballbot.admin;

import cz.janys.foosballbot.Constants;
import cz.janys.foosballbot.dto.JoinedPlayer;
import cz.janys.foosballbot.repository.PlayerRepository;
import cz.janys.foosballbot.util.MessageUtils;
import cz.janys.jabberbot.JabberMessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martin Janys
 */
@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private JabberMessageDispatcher dispatcher;

    @RequestMapping
    public String adminPage(Model model) {
        model.addAttribute("allPlayers", playerRepository.findAll());
        model.addAttribute("joinedPlayers", joinedPlayers());
        return "admin_page";
    }

    @SuppressWarnings("unchecked")
    private Collection<String> joinedPlayers() {
        Map<String, Object> conversation = dispatcher.getConversationMap(Constants.FOOSBALL_CONVERSATION_ID);
        Collection<JoinedPlayer> joinedPlayers = MessageUtils.getFoosballPlayers(conversation);
        if (!CollectionUtils.isEmpty(joinedPlayers))
            return  joinedPlayers
                    .stream()
                    .map(JoinedPlayer::getPlayer)
                    .collect(Collectors.toSet());
        else
            return Collections.emptyList();
    }
}
