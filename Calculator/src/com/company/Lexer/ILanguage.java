package com.company.Lexer;

import java.util.ArrayList;
import java.util.HashMap;

interface ILanguage {
    String[] getKeyWords();
    String[] getMathOperators();
    String[] getLogicOperators();
    String[] getBrackets();
    String[] getVarTypes();
    String[] getSpecSymbols();

    String getVarInitBlock(HashMap<String, ArrayList<String>> variables);
    HashMap<String, ArrayList<String>> getAllVariables(ArrayList<Token> tokens);
    ArrayList<Token> getIfCondition(ArrayList<Token> tokens, int condIndex);
    ArrayList<Token> getWhileCondition(ArrayList<Token> tokens, int condIndex);
    String getIfBlock(ArrayList<Token> condition);
    String getWhileBlock(ArrayList<Token> condition);
    String getStartString();
    String getExtraString();
    String getEndString();
    String[] getRange(ArrayList<Token> tokens, int forIndex);
    String getForBlock(String[] range);
}
