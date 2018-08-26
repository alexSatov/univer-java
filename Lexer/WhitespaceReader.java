public class WhitespaceReader 
{
	/**
	 * ������ � ������ ������ ������������ ���������� ���������� ��������.
	 * 
	 * @return ���������� ����������� ������� ������.
	 */
	public Token tryReadToken(String input) 
	{
		int i = 0;
		int len = input.length();
		while (i < len && Character.isWhitespace(input.charAt(i)))
			i++;
		return new Token("ws", input.substring(0, i));
	}
}
