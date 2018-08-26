public class WordReader
{
	private final String word;

	// TODO ������ 1: �������� �������� ignoreCase ������������������ � ��������
	public WordReader(String word)
	{
		super();
		this.word = word;
	}

	public Token tryReadToken(String input) 
	{
		if (input.startsWith(word))
			return new Token("kw", word);
		return null;
	}
}
