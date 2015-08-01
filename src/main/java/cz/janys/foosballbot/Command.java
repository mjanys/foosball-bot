package cz.janys.foosballbot;

import java.util.Arrays;
import java.util.List;

/**
 * @author Martin Janys
 */
public enum Command {

    REGISTER("reg", "register"),
    UNREGISTER("unreg", "unregister"),
    FOOSBALL("f"),
    YES("y", "yes", "jj", "j"),
    NO("n", "no", "nn", "leave", "ee", "e"),
    STATUS("s", "status"),
    PLUS("+"),
    HELP("h", "help"),
    MESSAGE("m", "message"),
    BROADCAST("b", "broadcast");

    private List<String> command;

    Command(String ... command) {
        this.command = Arrays.asList(command);
    }

    public List<String> cmd() {
        return command;
    }
}
