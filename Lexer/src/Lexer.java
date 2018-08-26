import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
    private HashMap<String, LanguageParser> parsers = new HashMap<>();

    public void register(LanguageParser parser, String language) {
        parsers.put(language, parser);
    }

    public String generate(String fromLang, String toLang, String program) {
        Translator translator = new Translator(parsers.get(fromLang).language,
                parsers.get(toLang).language, parsers.get(fromLang));
        return translator.translate(program);
    }

    public void unregister(String language) {
        try {
            parsers.remove(language);
        }
        catch (NullPointerException e){
            throw new IllegalArgumentException("Lexer haven't parser for this language");
        }
    }

    public ArrayList<Token> getAllTokens(String program, String language) {
        try {
            return parsers.get(language).parseProgram(program);
        }
        catch (NullPointerException e){
            throw new IllegalArgumentException("Lexer haven't parser for this language");
        }
    }
}
