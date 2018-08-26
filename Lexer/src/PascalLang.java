import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PascalLang implements ILanguage {
    private static String[] keyWords = new String[]{"procedure", "if", "else", "for", "while",
                                                    "then", "do", "var", "of", "to"};
    private static String[] mathOperators = new String[]{ "+", "-", "*", "/", ":=", "div", "mod", "++", "--"};
    private static String[] logicOperators = new String[]{ "<", ">", "=", "<>", ">=", "<=", "and", "or", "not"};
    private static String[] brackets = new String[]{ "begin", "end;", "end."};
    private static String[] varTypes = new String[]{ "integer", "double", "string", "char", "boolean", "array"};
    private static String[] specSymbols = new String[]{ "(", ")", "[", "]", ";", ":", ",", ".", "\n", "\t"};

    public static ILanguage language = new PascalLang();

    private PascalLang() {}

    @Override
    public String[] getKeyWords() { return keyWords; }

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
        result.append("var\n");
        Set<String> varTypes = variables.keySet();
        for(String varType : varTypes){
            ArrayList<String> vars = variables.get(varType);
            result.append("    ");
            for(int i = 0; i < vars.size(); i++){
                if(i != vars.size() - 1)
                    result.append(vars.get(i)).append(", ");
                else
                    result.append(vars.get(i)).append(": ");
            }
            result.append(varType).append(";\n");
        }
        return result.toString();
    }

    @Override
    public HashMap<String, ArrayList<String>> getAllVariables(ArrayList<Token> tokens) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        ArrayList<String> vars = new ArrayList<>();
        for(int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.value.equals("var")){
                while (!token.value.equals("begin")) {
                    if(token.type.equals(TokenType.VarName))
                        vars.add(token.value);
                    if(token.type.equals(TokenType.VarType))

                    if (!result.containsKey(token.value)){
                        result.put(token.value, new ArrayList<>(vars));
                        vars.clear();
                    }
                    else
                        result.get(token.value).addAll(vars);

                    i++;
                    token = tokens.get(i);
                }
                break;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Token> getIfCondition(ArrayList<Token> tokens, int condIndex) {
        ArrayList<Token> condition = new ArrayList<>();
        condIndex++;

        while (!tokens.get(condIndex).value.equals("then")) {
            if (!tokens.get(condIndex).type.equals(TokenType.WhiteSpace))
                condition.add(tokens.get(condIndex));
            condIndex++;
        }

        return condition;
    }

    @Override
    public ArrayList<Token> getWhileCondition(ArrayList<Token> tokens, int condIndex) {
        ArrayList<Token> condition = new ArrayList<>();
        condIndex++;

        while (!tokens.get(condIndex).value.equals("do")) {
            if (!tokens.get(condIndex).type.equals(TokenType.WhiteSpace))
                condition.add(tokens.get(condIndex));
            condIndex++;
        }

        return condition;
    }

    @Override
    public String getIfBlock(ArrayList<Token> condition) {
        StringBuilder block = new StringBuilder();
        condition.add(new Token(TokenType.WhiteSpace, " "));
        for(int i = 0; i < condition.size() - 1; i++){
            block.append(condition.get(i).value);
            if (!condition.get(i).value.equals("(") && !condition.get(i + 1).value.equals(")"))
                block.append(" ");
        }
        block.insert(0, "if ");
        block.append("then\n");
        return block.toString();
    }

    @Override
    public String getWhileBlock(ArrayList<Token> condition) {
        StringBuilder block = new StringBuilder();
        condition.add(new Token(TokenType.WhiteSpace, " "));
        for(int i = 0; i < condition.size() - 1; i++){
            block.append(condition.get(i).value);
            if (!condition.get(i).value.equals("(") && !condition.get(i + 1).value.equals(")"))
                block.append(" ");
        }
        block.insert(0, "while ");
        block.append("do\n");
        return block.toString();
    }

    @Override
    public String getStartString() {
        return "";
    }

    @Override
    public String getExtraString() {
        return "begin\n";
    }

    @Override
    public String getEndString() {
        return "end.";
    }

    @Override
    public String[] getRange(ArrayList<Token> tokens, int forIndex) {
        String[] range = new String[3];
        while (!tokens.get(forIndex).value.equals("\n")){
            Token token = tokens.get(forIndex);
            if (token.type.equals(TokenType.VarName) && range[0] == null)
                range[0] = token.value;
            if (token.value.equals(":=")){
                range[1] = tokens.get(forIndex + 2).value;
                forIndex++;
            }
            if (token.value.equals("to")){
                range[2] = tokens.get(forIndex + 2).value;
                forIndex++;
            }
            forIndex++;
        }
        return range;
    }

    @Override
    public String getForBlock(String[] range) {
        return "for " + range[0] + " := " + range[1] + " to " + range[2] + " do\n";
    }
}
