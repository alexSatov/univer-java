import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Translator {
    private HashMap<TokenType, String[]> langFromTokensDict;
    private HashMap<TokenType, String[]> langToTokensDict;
    private LanguageParser langFromParser;
    private ILanguage langFrom;
    private ILanguage langTo;

    public Translator(ILanguage langFrom, ILanguage langTo, LanguageParser langFromParser) {
        this.langFrom = langFrom;
        this.langTo = langTo;
        langFromTokensDict = getTokensDict(langFrom);
        langToTokensDict = getTokensDict(langTo);
        this.langFromParser = langFromParser;
    }

    public String translate(String program) {
        StringBuilder result = new StringBuilder();
        ArrayList<Token> langFromTokens = langFromParser.parseProgram(program);
        int startProgramIndex = getStartProgramIndex(langFromTokens) + 2;
        int endProgramIndex = langFromTokens.size() - 1;

        result.append(langTo.getStartString());
        result.append(langTo.getVarInitBlock(exchangeVarTypes(langFrom.getAllVariables(langFromTokens))));
        result.append(langTo.getExtraString());
        for (int i = startProgramIndex; i < endProgramIndex; i++){
            Token token = langFromTokens.get(i);
            if (token.type.equals(TokenType.VarType)) {
                if (langFromTokens.get(i + 4).type.equals(TokenType.MathOperator))
                    i++;
                else {
                    i = getNextLineBreakIndex(i, langFromTokens);
                    int blIndex = result.lastIndexOf("\n");
                    result.delete(blIndex + 1, result.length());
                }

                continue;
            }
            if (token.value.equals("if")){
                result.append(langTo.getIfBlock(langFrom.getIfCondition(langFromTokens, i)));
                i = getNextLineBreakIndex(i, langFromTokens);
                continue;
            }
            if (token.value.equals("for")){
                result.append(langTo.getForBlock(langFrom.getRange(langFromTokens, i)));
                i = getNextLineBreakIndex(i, langFromTokens);
                continue;
            }
            if (token.value.equals("while")){
                result.append(langTo.getWhileBlock(langFrom.getWhileCondition(langFromTokens, i)));
                i = getNextLineBreakIndex(i, langFromTokens);
                continue;
            }
            result.append(exchangeTokenValue(token));
        }
        result.append(langTo.getEndString());
        return result.toString();
    }

    private int getNextLineBreakIndex(int currentIndex, ArrayList<Token> tokens){
        Token token = tokens.get(currentIndex);
        while (!token.value.equals("\n")){
            currentIndex++;
            token = tokens.get(currentIndex);
        }
        return currentIndex;
    }

    private String exchangeTokenValue(Token token){
        try {
            int index = getIndex(langFromTokensDict.get(token.type), token.value);
            return langToTokensDict.get(token.type)[index];
        }
        catch (NullPointerException e) {
            return token.value;
        }
    }

    private HashMap<String, ArrayList<String>> exchangeVarTypes(HashMap<String, ArrayList<String>> varsFrom){
        HashMap<String, ArrayList<String>> varsTo = new HashMap<>();
        Set<String> varTypes = varsFrom.keySet();
        for(String varType : varTypes){
            varsTo.put(exchangeTokenValue(new Token(TokenType.VarType, varType)), varsFrom.get(varType));
        }
        return varsTo;
    }

    private int getStartProgramIndex(ArrayList<Token> tokens){
        int index = 0;
        while (!tokens.get(index).type.equals(TokenType.Bracket))
            index++;
        return index;
    }

    private int getIndex(String[] array, String value){
        for (int i = 0; i < array.length; i++){
            if (array[i].equals(value))
                return i;
        }
        return -1;
    }

    private HashMap<TokenType, String[]> getTokensDict(ILanguage lang) {
        HashMap<TokenType, String[]> dict = new HashMap<>();
        dict.put(TokenType.Keyword, lang.getKeyWords());
        dict.put(TokenType.MathOperator, lang.getMathOperators());
        dict.put(TokenType.LogicOperator, lang.getLogicOperators());
        dict.put(TokenType.Bracket, lang.getBrackets());
        dict.put(TokenType.VarType, lang.getVarTypes());
        dict.put(TokenType.SpecSymbol, lang.getSpecSymbols());
        return dict;
    }
}
