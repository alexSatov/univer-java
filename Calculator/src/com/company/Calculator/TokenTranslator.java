package com.company.Calculator;

import com.company.Lexer.Token;
import com.company.Lexer.TokenType;

import java.util.ArrayList;

public class TokenTranslator {
    private ArrayList<Token> mathTokens;
    private ArrayList<Token> vectorTokens;
    private boolean isOpenScope;
    private int index;

    public ArrayList<Token> translateToMathTokens(ArrayList<Token> tokens) {
        init();
        for(index = 0; index < tokens.size(); index++){
            Token token = tokens.get(index);
            if (token.type == TokenType.WhiteSpace) continue;

            if (token.type == TokenType.VarName)
                token = new Token(TokenType.Imaginary, token.value);

            if (token.value.charAt(token.value.length() - 1) == '.') {
                token = tokens.get(index + 1).type == TokenType.VarName ?
                        new Token(TokenType.Imaginary, token.value + tokens.get(index + 1).value) :
                        new Token(TokenType.Number, token.value + tokens.get(index + 1).value);
                index++;
            }

            if (isVectorSymbol(tokens, token)) continue;

            if (isOpenScope)
                vectorTokens.add(token);
            else
                mathTokens.add(token);
        }
        mathTokens = parseNegativeOperators(mathTokens);
        return mathTokens;
    }

    private ArrayList<Token> parseNegativeOperators(ArrayList<Token> tokens){
        ArrayList<Token> parsedTokens = new ArrayList<>();
        int i = 0;
        if (tokens.get(0).value.equals("-")) {
            parsedTokens.add(new Token(TokenType.Number, "0"));
            parsedTokens.add(tokens.get(0));
            i++;
        }
        for(; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.value.equals("-") && tokens.get(i - 1).value.equals("(")) {
                parsedTokens.add(new Token(TokenType.Number, "0"));
                parsedTokens.add(token);
                continue;
            }
            parsedTokens.add(token);
        }
        return parsedTokens;
    }

    private boolean isVectorSymbol(ArrayList<Token> tokens, Token token){
        switch (token.value){
            case "(":
                isOpenScope = true;
                addAll(mathTokens, vectorTokens);
                vectorTokens.clear();
                vectorTokens.add(token);
                return true;
            case ")":
                isOpenScope = false;
                vectorTokens.add(token);
                addAll(mathTokens, vectorTokens);
                vectorTokens.clear();
                return true;
            case ",":
                readTokensBeforeCloseBracket(token, tokens);
                isOpenScope = false;
                String vectorStr = "";
                for(Token vectorToken : vectorTokens)
                    vectorStr += vectorToken.value;
                mathTokens.add(new Token(TokenType.Vector, vectorStr));
                vectorTokens.clear();
                return true;
        }
        return false;
    }

    private void readTokensBeforeCloseBracket(Token currentToken, ArrayList<Token> tokens){
        vectorTokens.add(currentToken);
        do {
            index++;
            currentToken = tokens.get(index);
            if (currentToken.value.equals("(")) readTokensBeforeCloseBracket(currentToken, tokens);
            if (currentToken.type != TokenType.WhiteSpace) vectorTokens.add(currentToken);
        } while (!currentToken.value.equals(")"));
    }

    private void init(){
        isOpenScope = false;
        mathTokens = new ArrayList<>();
        vectorTokens = new ArrayList<>();
    }

    private void addAll(ArrayList<Token> target, ArrayList<Token> source){
        for(Token mathToken : source)
            target.add(mathToken);
    }
}
