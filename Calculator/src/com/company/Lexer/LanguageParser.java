package com.company.Lexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class LanguageParser {
    private ILanguage language;
    private HashMap<TokenType, HashSet<String>> tokensDict = new HashMap<>();
    private String cache = "";
    private String program = "";
    private int currentIndex = 0;
    private boolean stringProcessed = false;
    private ArrayList<Token> result = new ArrayList<>();

    public LanguageParser(ILanguage language){
        this.language = language;
        initTokensDict();
    }

    public ArrayList<Token> parseProgram(String programText){
        program = programText;

        while (currentIndex < program.length()) {
            String symbol = Character.toString(program.charAt(currentIndex));
            processSymbol(symbol);
            currentIndex++;
        }

        processCache(" ");
        ArrayList<Token> tokenArray = new ArrayList<>();
        tokenArray.addAll(result);
        clean();
        return tokenArray;
    }

    protected void initTokensDict() {
        tokensDict.put(TokenType.Keyword, arrayToHashSet(language.getKeyWords()));
        tokensDict.put(TokenType.MathOperator, arrayToHashSet(language.getMathOperators()));
        tokensDict.put(TokenType.LogicOperator, arrayToHashSet(language.getLogicOperators()));
        tokensDict.put(TokenType.Bracket, arrayToHashSet(language.getBrackets()));
        tokensDict.put(TokenType.VarType, arrayToHashSet(language.getVarTypes()));
        tokensDict.put(TokenType.SpecSymbol, arrayToHashSet(language.getSpecSymbols()));
    }

    private HashSet<String> arrayToHashSet(String[] array)
    {
        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll(hashSet, array);
        return hashSet;
    }

    private void clean(){
        currentIndex = 0;
        result.clear();
        cache = "";
        program = "";
    }

    private void processSymbol(String symbol) {
        if (!stringProcessed) {
            if (symbol.equals(" "))
                processWhiteSpace(symbol);
            else {
                checkCacheOnWhiteSpaces();
                if (symbol.equals("\"") || symbol.equals("\'")) stringProcessed = true;
                else if (isSign(symbol)) {
                    processSign(symbol);
                    return;
                }
            }
        }
        cache += symbol;
        if ((symbol.equals("\"") || symbol.equals("\'")) && cache.length() > 1) {
            result.add(new Token(TokenType.String, cache));
            cache = "";
            stringProcessed = false;
        }
    }

    private boolean isSign(String symbol){
        return isLangToken(TokenType.SpecSymbol, symbol)
                || isLangToken(TokenType.MathOperator, symbol)
                || isLangToken(TokenType.LogicOperator, symbol);
    }

    private void checkCacheOnWhiteSpaces(){
        if (cache.startsWith(" ")) {
            result.add(new Token(TokenType.WhiteSpace, cache));
            cache = "";
        }
    }

    private void processSign(String sign) {
        TokenType type = tryGetTokenType(cache + sign);
        if (type != null && !cache.equals("")) {
            result.add(new Token(type, cache + sign));
            cache = "";
            return;
        }

        char nextChar =  currentIndex + 1 < program.length() ? program.charAt(currentIndex + 1) : ' ';
        String possibleSign = sign + nextChar;
        if (isSign(possibleSign)) {
            sign = possibleSign;
            currentIndex += 1;
        }

        processCache(sign);
        TokenType signType =  tryGetTokenType(sign);
        result.add(new Token(signType, sign));
    }

    private void processWhiteSpace(String whiteSpace) {
        if (cache.startsWith(whiteSpace)) return;
        processCache(whiteSpace);
    }

    private void processCache(String symbol) {
        if (!cache.equals("")) {
            TokenType type = tryGetTokenType(cache);

            if (type != null)
                result.add(new Token(type, cache));
            else
                switch (symbol){
                    case "(": result.add(new Token(TokenType.MethodName, cache));
                        break;
                    default: result.add(new Token(TokenType.VarName, cache));
                        break;
                }

            cache = "";
        }
    }

    private TokenType tryGetTokenType(String string) {
        if (isLangToken(TokenType.Keyword, string)) return TokenType.Keyword;
        if (isLangToken(TokenType.VarType, string)) return TokenType.VarType;
        if (isLangToken(TokenType.Bracket, string)) return TokenType.Bracket;
        if (isLangToken(TokenType.MathOperator, string)) return TokenType.MathOperator;
        if (isLangToken(TokenType.LogicOperator, string)) return TokenType.LogicOperator;
        if (isLangToken(TokenType.SpecSymbol, string)) return TokenType.SpecSymbol;
        if (isNumber(string)) return TokenType.Number;
        return null;
    }

    private boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isLangToken(TokenType type, String string) {
        return tokensDict.get(type).contains(string);
    }
}
