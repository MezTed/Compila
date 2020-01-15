package compila ;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author Meziane Ted
 */

public class Lexical {
    
    private StringBuilder input = new StringBuilder();
    private Token token;
    private String lexema;
    private boolean exausthed = false;
    private String errorMessage = "";
    private Set<Character> WhiteSpaceCharacter = new HashSet<Character>();

    public Lexical(String filePath) {
        try (Stream<String> st = Files.lines(Paths.get(filePath))) {
            st.forEach(input::append); 
        } catch (IOException ex) {
            exausthed = true;
            errorMessage = "Could not read file: " + filePath;
            return;
        }
        /* tous les character qui doit etre supprimée */
        
        WhiteSpaceCharacter.add('\r');
        WhiteSpaceCharacter.add('\n');
        WhiteSpaceCharacter.add((char) 8);
        WhiteSpaceCharacter.add((char) 9);
        WhiteSpaceCharacter.add((char) 11);
        WhiteSpaceCharacter.add((char) 12);
        WhiteSpaceCharacter.add((char) 32);

        Next();
        
    }

    public void Next() {
        if (exausthed) {
            return;
        }

        if (input.length() == 0) { // cas d"space 
            exausthed = true;
            return;
        }
 
        IgnorerLesEspaces(); // supprimer tous les character genant

        if (TrouverleProchainToken()) { //appeler la fonction Next Token
            return;
        }
        
        exausthed = true; // cas derreur

        if (input.length() > 0) {
            errorMessage = "Unexpected symbol: '" + input.charAt(0) + "'";
        }
    }

    private void IgnorerLesEspaces() {
        int charsToDelete = 0;

        while (WhiteSpaceCharacter.contains(input.charAt(charsToDelete))) {
            charsToDelete++;
        }

        if (charsToDelete > 0) {
            input.delete(0, charsToDelete);
        }
    }

    private boolean TrouverleProchainToken() {
        for (Token t : Token.values()) {
            int end = t.FinduMatcher(input.toString());    //input stock tous le programme  
            
            if (end != -1) {
                token = t;
                lexema = input.substring(0, end); // comprendre la fonction substring exactement
                input.delete(0, end);
                return true;
            }
        }

        return false;
    }

    public Token TokenActuel() {
        return token;
    }

    public String LexemaActuel() {
        return lexema;
    }

    public boolean Succes() {
        return errorMessage.isEmpty();
    }

    public String Error() {
        return errorMessage;
    }

    public boolean Fatigue() {
        return exausthed;
    }
}
