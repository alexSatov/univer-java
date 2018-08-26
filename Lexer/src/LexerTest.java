import junit.framework.TestCase;

import java.util.ArrayList;

public class LexerTest extends TestCase {
    private Lexer lexer;

    @org.junit.Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        lexer.register(PascalParser.parser, "pascal");
        lexer.register(JavaParser.parser, "java");
    }

    public void testTokenParsing_SimpleJavaProgram() {
        String program = "{\nint a = 1;\n}";
        ArrayList<Token> result = lexer.getAllTokens(program, "java");
        assertEquals(12, result.size());
    }

    public void testTokenParsing_SimplePascalProgram() {
        String program = "var a: integer;\nbegin\na := 1;\nend.";
        ArrayList<Token> result = lexer.getAllTokens(program, "pascal");
        assertEquals(18, result.size());
    }

    public void testTokenParsing_ManyWhiteSpaces() {
        String program = " { \n int a   =   1   ; \n } ";
        ArrayList<Token> result = lexer.getAllTokens(program, "java");
        assertEquals(19, result.size());
    }

    public void testTokenParsing_PascalProgram() {
        String program = "var\n" +
                "  x,y: integer;\n" +
                "  min: integer;\n" +
                "begin\n" +
                "  write(\"Введите x и y: \");\n" +
                "  readln(x,y);\n" +
                "  if x < y then\n" +
                "    min := x\n" +
                "  else min := y;\n" +
                "  writeln(\"Минимум = \",min);\n" +
                "end.";
        ArrayList<Token> result = lexer.getAllTokens(program, "pascal");
        assertEquals(74, result.size());
    }

    public void testTranslateFromJavaToPascal_SimpleProgram() {
        String javaProgram = "public static void main(String[] args)\n{\nint a = 1;\n}";
        String pascalProgram = "var\n    a: integer;\nbegin\na := 1;\nend.";
        String translatedProgram = lexer.generate("java", "pascal", javaProgram);
        assertEquals(pascalProgram, translatedProgram);
    }

    public void testTranslateFromPascalToJava_SimpleProgram() {
        String javaProgram = "public static void main(String[] args)\n{\n    int a;\n    a = 1;\n}";
        String pascalProgram = "var\n    a: integer;\nbegin\n    a := 1;\nend.";
        String translatedProgram = lexer.generate("pascal", "java", pascalProgram);
        assertEquals(javaProgram, translatedProgram);
    }

    public void testTranslateFromPascalToJava_NormalProgram() {
        String pascalProgram = "var\n" +
                "    x,y: integer;\n" +
                "    min: integer;\n" +
                "begin\n" +
                "    write(\"Введите x и y: \");\n" +
                "    readln(x,y);\n" +
                "    if x < y then\n" +
                "        min := x\n" +
                "    else min := y;\n" +
                "    writeln(\"Минимум = \",min);\n" +
                "end.";
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int x, y, min;\n" +
                "    write(\"Введите x и y: \");\n" +
                "    readln(x,y);\n" +
                "    if (x < y)\n" +
                "        min = x\n" +
                "    else min = y;\n" +
                "    writeln(\"Минимум = \",min);\n" +
                "}";
        String translatedProgram = lexer.generate("pascal", "java", pascalProgram);
        assertEquals(javaProgram, translatedProgram);
    }

    public void testTranslateFromJavaToPascal_NormalProgram() {
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int x, y, min;\n" +
                "    write(\"Введите x и y: \");\n" +
                "    readln(x,y);\n" +
                "    if (x < y)\n" +
                "        min = x\n" +
                "    else min = y;\n" +
                "    writeln(\"Минимум = \",min);\n" +
                "}";
        String pascalProgram = "var\n" +
                "    x, y, min: integer;\n" +
                "begin\n" +
                "    write(\"Введите x и y: \");\n" +
                "    readln(x,y);\n" +
                "    if (x < y) then\n" +
                "        min := x\n" +
                "    else min := y;\n" +
                "    writeln(\"Минимум = \",min);\n" +
                "end.";
        String translatedProgram = lexer.generate("java", "pascal", javaProgram);
        assertEquals(pascalProgram, translatedProgram);
    }

    public void testTranslateFromPascalToJava_ProgramWithCycle() {
        String pascalProgram = "var\n" +
                "    a, b, c, n: integer;\n" +
                "begin\n" +
                "    a := 1;\n" +
                "    b := 1;\n" +
                "    n := 25;\n" +
                "    write(a,' ',b,' ');\n" +
                "    for var i := 3 to n do\n" +
                "    begin\n" +
                "        c := a + b;\n" +
                "        write(c,' ');\n" +
                "        a := b;\n" +
                "        b := c;\n" +
                "    end;\n" +
                "end.";
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int a, b, c, n;\n" +
                "    a = 1;\n" +
                "    b = 1;\n" +
                "    n = 25;\n" +
                "    write(a,' ',b,' ');\n" +
                "    for (i = 3; i < n; i++)\n" +
                "    {\n" +
                "        c = a + b;\n" +
                "        write(c,' ');\n" +
                "        a = b;\n" +
                "        b = c;\n" +
                "    }\n" +
                "}";
        String translatedProgram = lexer.generate("pascal", "java", pascalProgram);
        assertEquals(javaProgram, translatedProgram);
    }

    public void testTranslateFromJavaToPascal_ProgramWithCycle() {
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
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int a, b, c, n;\n" +
                "    a = 1;\n" +
                "    b = 1;\n" +
                "    n = 25;\n" +
                "    write(a,' ',b,' ');\n" +
                "    for (int i = 3; i < n; i++)\n" +
                "    {\n" +
                "        c = a + b;\n" +
                "        write(c,' ');\n" +
                "        a = b;\n" +
                "        b = c;\n" +
                "    }\n" +
                "}";
        String translatedProgram = lexer.generate("java", "pascal", javaProgram);
        assertEquals(pascalProgram, translatedProgram);
    }

    public void testTranslateFromPascalToJava_ProgramWithWhile() {
        String pascalProgram = "var\n" +
                "    n: integer;\n" +
                "begin\n" +
                "    n := 25;\n" +
                "    while n > 1 do\n" +
                "    begin\n" +
                "      write(n, ' ');\n" +
                "      n := n - 1;\n" +
                "    end;\n" +
                "end.";
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int n;\n" +
                "    n = 25;\n" +
                "    while (n > 1)\n" +
                "    {\n" +
                "      write(n, ' ');\n" +
                "      n = n - 1;\n" +
                "    }\n" +
                "}";
        String translatedProgram = lexer.generate("pascal", "java", pascalProgram);
        assertEquals(javaProgram, translatedProgram);
    }

    public void testTranslateFromJavaToPascal_ProgramWithWhile() {
        String pascalProgram = "var\n" +
                "    n: integer;\n" +
                "begin\n" +
                "    n := 25;\n" +
                "    while (n > 1) do\n" +
                "    begin\n" +
                "      write(n, ' ');\n" +
                "      n := n - 1;\n" +
                "    end;\n" +
                "end.";
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int n;\n" +
                "    n = 25;\n" +
                "    while (n > 1)\n" +
                "    {\n" +
                "      write(n, ' ');\n" +
                "      n = n - 1;\n" +
                "    }\n" +
                "}";
        String translatedProgram = lexer.generate("java", "pascal", javaProgram);
        assertEquals(pascalProgram, translatedProgram);
    }

    public void testTranslateFromPascalToJava_ProgramWithProcedure() {
        String pascalProgram = "var\n" +
                "    n: integer;\n" +
                "begin\n" +
                "    n := 25;\n" +
                "    while n > 1 do\n" +
                "    begin\n" +
                "      write(n, ' ');\n" +
                "      n := n - 1;\n" +
                "    end;\n" +
                "end.";
        String javaProgram = "public static void main(String[] args)\n" +
                "{\n" +
                "    int n;\n" +
                "    n = 25;\n" +
                "    while (n > 1)\n" +
                "    {\n" +
                "      write(n, ' ');\n" +
                "      n = n - 1;\n" +
                "    }\n" +
                "}";
        String translatedProgram = lexer.generate("pascal", "java", pascalProgram);
        assertEquals(javaProgram, translatedProgram);
    }
}