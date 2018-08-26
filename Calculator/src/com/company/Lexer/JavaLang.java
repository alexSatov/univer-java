package com.company.Lexer;

import java.util.*;

public class JavaLang implements ILanguage {
    private static String[] keyWords = new String[]{"void", "if", "else", "for", "while", "public", "static"};
    private static String[] mathOperators = new String[]{ "+", "-", "*", "/", "=", "/", "%", "++", "--"};
    private static String[] logicOperators = new String[]{ "<", ">", "==", "!=", ">=", "<=", "&&", "||", "!", "&", "|"};
    private static String[] brackets = new String[]{ "{", "}"};
    private static String[] varTypes = new String[]{ "int", "double", "String", "char", "boolean"};
    private static String[] specSymbols = new String[]{ "(", ")", "[", "]", ";", ":", ",", ".", "\n", "\t"};

    public static ILanguage language = new JavaLang();

    private JavaLang() {}

    @Override
    public String[] getKeyWords() {
        return keyWords;
    }

    @Override
    public String[] getMathOperators() {
        return mathOperators;
    }

    @Override
    public String[] getLogicOperators() {
        return logicOperators;
    }

    @Override
    public String[] getBrackets() {
        return brackets;
    }

    @Override
    public  String[] getVarTypes() {
        return varTypes;
    }

    @Override
    public String[] getSpecSymbols() {
        return specSymbols;
    }

    @Override
    public String getVarInitBlock(HashMap<String, ArrayList<String>> variables) {
        StringBuilder result = new StringBuilder();
        Set<String> varTypes = variables.keySet();

        for(String varType : varTypes){
            result.append(varType).append(" ");
            ArrayList<String> vars = variables.get(varType);
            for(int i = 0; i < vars.size(); i++){
                if(i != vars.size() - 1)
                    result.append(vars.get(i)).append(", ");
                else
                    result.append(vars.get(i)).append(";\n");
            }
        }

        result.insert(0, "    ");
        return result.toString();
    }

    @Override
    public HashMap<String, ArrayList<String>> getAllVariables(ArrayList<Token> tokens) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        String currentVarType = "";
        for(Token token : tokens) {
            if (currentVarType.equals("String") && token.value.equals("args"))
                continue;
            if (token.type.equals(TokenType.VarType))
                currentVarType = token.value;
            if (token.type.equals(TokenType.VarName)) {
                if (!result.containsKey(currentVarType))
                    result.put(currentVarType, new ArrayList<>());
                if (!varInitialized(result.values(), token.value))
                    result.get(currentVarType).add(token.value);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Token> getIfCondition(ArrayList<Token> tokens, int condIndex) {
        ArrayList<Token> condition = new ArrayList<>();
        condIndex++;

        while (!tokens.get(condIndex).value.equals("\n")) {
            if (!tokens.get(condIndex).type.equals(TokenType.WhiteSpace))
                condition.add(tokens.get(condIndex));
            condIndex++;
        }

        return condition;
    }

    @Override
    public ArrayList<Token> getWhileCondition(ArrayList<Token> tokens, int condIndex){
        return getIfCondition(tokens, condIndex);
    }

    @Override
    public String getIfBlock(ArrayList<Token> condition) {
        StringBuilder block = new StringBuilder();
        for(Token token : condition){
            block.append(token.value);
            block.append(" ");
        }
        block.deleteCharAt(block.length() - 1);
        if (!condition.get(0).value.equals("(")){
            block.insert(0, "(");
            block.append(")");
        }
        block.insert(0, "if ");
        block.append("\n");
        return block.toString();
    }

    @Override
    public String getWhileBlock(ArrayList<Token> condition) {
        StringBuilder block = new StringBuilder();
        for(Token token : condition){
            block.append(token.value);
            block.append(" ");
        }
        block.deleteCharAt(block.length() - 1);
        if (!condition.get(0).value.equals("(")){
            block.insert(0, "(");
            block.append(")");
        }
        block.insert(0, "while ");
        block.append("\n");
        return block.toString();
    }

    @Override
    public String getStartString() {
        return "public static void main(String[] args)\n{\n";
    }

    @Override
    public String getExtraString() {
        return "";
    }

    @Override
    public String getEndString() {
        return "}";
    }

    @Override
    public String[] getRange(ArrayList<Token> tokens, int forIndex) {
        String[] range = new String[3];
        while (!tokens.get(forIndex).value.equals("\n")){
            Token token = tokens.get(forIndex);
            if (token.type.equals(TokenType.VarName) && range[0] == null)
                range[0] = token.value;
            if (token.value.equals("=")){
                range[1] = tokens.get(forIndex + 2).value;
                forIndex++;
            }
            if (token.value.equals("<")){
                range[2] = tokens.get(forIndex + 2).value;
                forIndex++;
            }
            forIndex++;
        }
        return range;
    }

    @Override
    public String getForBlock(String[] range) {
        return "for (int " + range[0] + " = " + range[1] + "; "
                + range[0] + " < " + range[2] + "; " + range[0] + "++)\n";
    }

    private boolean varInitialized(Collection<ArrayList<String>> vars, String var){
        for(ArrayList<String> currentVars : vars)
            if(currentVars.contains(var))
                return true;
        return false;
    }
}
