public class Program {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        lexer.register(PascalParser.parser, "pascal");
        lexer.register(JavaParser.parser, "java");
        String pascalProgram = "var\n" +
                "    a, b, c, n, i: integer;\n" +
                "begin\n" +
                "    a := 1;\n" +
                "    b := 1;\n" +
                "    n := 25;\n" +
                "    write(a,' ',b,' ');\n" +
                "    for i := 3 to n do\n" +
                "    begin\n" +
                "        c := a + b;\n" +
                "        write(c,' ');\n" +
                "        a := b;\n" +
                "        b := c;\n" +
                "    end;\n" +
                "end.";
        String translatedProgram = lexer.generate("pascal", "java", pascalProgram);
        System.out.print(translatedProgram);
    }
}
