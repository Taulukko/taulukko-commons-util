package com.taulukko.commons.util.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

import org.apache.log4j.Logger;

public class EMin
{
	
	private static final char NEW_LINE = '\n';
	private static final char RETURN_LINE = '\r';
	private static final char BREAK_LINE_DEFAULT = RETURN_LINE;
	private static final boolean DEBUG = false;
	
	private Logger stdout = Logger.getRootLogger();


	private static final int EOF = -1;

	private PushbackInputStream _in;

	private ByteArrayOutputStream _out;

	private int _cursor;

	private int _line;

	private int _collumn;
	
	private OutputStream _return;
	
	private SCRIPT_TYPE _type = null;
	

	public enum SCRIPT_TYPE
	{
		ALL,
		CSS,
		JS,
		OFF
	}
 

	public EMin(InputStream in, OutputStream out, SCRIPT_TYPE type)
	{
		_in = new PushbackInputStream(in);
		_return = (DEBUG) ? System.out : out;
		_out = new ByteArrayOutputStream();
		_line = 0;
		_collumn = 0;
		_type = type;
	}

	/**
	 * get -- return the cursor character from stdin. 
	 */
	int get()
	{
		return _cursor;
	}

	/**
	 * Get the next character without getting it.
	 */
	int peek() throws IOException
	{
		int lookaheadChar = _in.read();
		_in.unread(lookaheadChar);
		if (lookaheadChar == RETURN_LINE)
		{
			lookaheadChar = BREAK_LINE_DEFAULT;
		}
		return lookaheadChar;
	}

	/**
	 * next -- get the next character 
	 */
	int next() throws IOException
	{

		_cursor = _in.read();

		if (_cursor == NEW_LINE)
		{
			_cursor = BREAK_LINE_DEFAULT;
			_line++;
			_collumn = 0;
		}
		else
		{
			if (_cursor == RETURN_LINE)
			{
				_cursor = BREAK_LINE_DEFAULT;
			}
			if( _cursor =='	' )
			{
				_cursor = ' ';
				
			}
			
			if (_cursor == 0 )
			{
				_cursor = EOF;
			}
			_collumn++;
			
		}

		if (_cursor >= ' ' || _cursor == BREAK_LINE_DEFAULT || _cursor == EOF)
		{
			return _cursor;
		}

		return ' ';
	}
	public Logger getStdout()
	{
		return stdout;
	}
	
	public void setStdout(Logger stdout)
	{
		this.stdout = stdout;
	}

	/**
	 * jsmin -- Copy the input to the output, deleting the characters which are
	 * insignificant to JavaScript. Comments will be removed. Tabs will be
	 * replaced with spaces. Carriage returns will be replaced with linefeeds.
	 * Most spaces and linefeeds will be removed.
	 */
	public void flush() throws IOException, UnterminatedRegExpLiteralException,
			UnterminatedCommentException, UnterminatedStringLiteralException
	{
		int lastSize=-1;
 		
		while(lastSize != _out.size())
		{
			lastSize= _out.size();
			_out.close();
			_out = new ByteArrayOutputStream();
			while (next() != EOF)
			{
				if (evaluateComment())
					continue;
				if (evaluateString())
					continue;
				if (evaluateSpaces())
					continue;
				if (evaluateBreakeLines())
					continue;
				if (_type == SCRIPT_TYPE.JS && evaluateRegularExpression())
					continue;
				if (get() != EOF && (_type!=SCRIPT_TYPE.CSS || get() != BREAK_LINE_DEFAULT))
				{				
					_out.write(get());
				}
				 
				debugFlush();
			}
			_out.flush();
			_in = new PushbackInputStream(new ByteArrayInputStream(_out.toByteArray()));
			_cursor = ' ';
			_collumn=0;
			_line = 0;
		}
		_in.close();
		_return.write(_out.toByteArray());
		_out.close();
		
		
	}

	private boolean evaluateBreakeLines() throws IOException
	{
		boolean exist = false;
		while ((get() == BREAK_LINE_DEFAULT ||  get() == ' ' ) && (peek() == BREAK_LINE_DEFAULT || peek() == ' ' ))
		{
			exist = true;
			next();
		}
		if (exist)
		{
			_out.write(BREAK_LINE_DEFAULT);
		}
		return exist;
	}

	private boolean evaluateRegularExpression() throws IOException,
			UnterminatedRegExpLiteralException
	{
		int scape = '/';

		try
		{
			if (tokentoToTokenRegularExpression(scape))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			throw new UnterminatedRegExpLiteralException(_line, _collumn);
		}

		return false;
	}

	private void debugFlush() throws IOException
	{
		if (DEBUG)
		{
			_out.flush();
			_return.write(_out.toByteArray());
			_return.flush();
		}
	}

	private boolean evaluateSpaces() throws IOException
	{
		boolean hasSpace = false;
		while ((get() == ' ' || get() == '	')
				&& (peek() == ' ' || peek() == '	'))
		{
			hasSpace = true;
			next();
		}
		if (hasSpace)
		{
			_out.write(' ');
		}
		return hasSpace;
	}

	private boolean evaluateComment() throws IOException,
			UnterminatedCommentException
	{
		if (get() == '/' && peek() == '/')
		{
			//find new line
			while (get() != BREAK_LINE_DEFAULT && get()!= EOF)
			{
				next();
			}
			return true;
		}
		if (get() == '/' && peek() == '*')
		{
			//find new line
			while (get() != '*' || peek() != '/')
			{
				if (next() == EOF)
				{
					throw new UnterminatedCommentException(_line, _collumn);
				}
			}
			next();
			return true;
		}
		return false;
	}

	private boolean evaluateString() throws IOException,
			UnterminatedStringLiteralException
	{
		int scape = '\'';

		try
		{
			if (tokentoToTokenString(scape))
			{
				return true;
			}

			scape = '\"';

			if (tokentoToTokenString(scape))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			throw new UnterminatedStringLiteralException(_line, _collumn);
		}
		return false;
	}

	private boolean tokentoToTokenRegularExpression(int token)
			throws IOException, Exception
	{

		boolean escape = false;

		if (get() == token)
		{
			_out.write(get());
			debugFlush();
			//find string end
			while (peek() != token)
			{
				escape = !escape && peek() == '\\';

				if (next() == EOF)
				{
					throw new Exception();
				}

				if (get() == BREAK_LINE_DEFAULT)
				{
					return false;
					//throw new Exception();
				}

				_out.write(get());
				debugFlush();
				if (escape && peek() == token)
				{
					//no  o fim, e sim um sinal para escapar					 
					_out.write(next());
					continue;
				}

			}
			next();
			_out.write(get());
			debugFlush();
			if (get() == 'g' || get() == 'i')
			{
				next();
				_out.write(get());
				debugFlush();
			}
			return true;
		}
		return false;
	}

	private boolean tokentoToTokenString(int token) throws IOException,
			Exception
	{

		boolean escape = false;

		if (get() == token)
		{
			_out.write(get());
			debugFlush();
			//find string end
			while (peek() != token)
			{
				escape = !escape && peek() == '\\';

				if (next() == EOF)
				{
					throw new Exception();
				}

				if (escape && peek() == BREAK_LINE_DEFAULT)
				{
					//escape de linha e no um terminador
					next();
					next();
					continue;
				}

				if (get() == BREAK_LINE_DEFAULT)
				{
					// string inacabaa
					throw new Exception();
				}

				_out.write(get());
				debugFlush();
				if (escape && peek() == token)
				{
					/*no  o fim, e sim um sinal para escapar
					 CORRECAO (analisa previous!= '\\' ): Agora ele tambm verifica se no  \\' que no caso no seria ento um escape 
					 */
					_out.write(next());
					continue;
				}

			}
			next();
			_out.write(get());
			debugFlush();
			return true;
		}
		return false;
	}

	static class UnterminatedCommentException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3875915996338279938L;

		/**
		 * 
		 */
 
		public UnterminatedCommentException(int line, int column)
		{
			super("Unterminated comment at line " + line + " and column "
					+ column);
		}
	}

	static class UnterminatedStringLiteralException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4364304491399243908L;

		public UnterminatedStringLiteralException(int line, int column)
		{
			super("Unterminated string literal at line " + line
					+ " and column " + column);
		}
	}

	static class UnterminatedRegExpLiteralException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -966531537851811049L;

		public UnterminatedRegExpLiteralException(int line, int column)
		{
			super("Unterminated regular expression at line " + line
					+ " and column " + column);
		}
	}

	public static void main(String arg[])
	{
		try
		{
			EMin jsmin = new EMin(new FileInputStream(
					"~/temp/prototype.js"), System.out,SCRIPT_TYPE.JS);
			jsmin.flush();
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (UnterminatedRegExpLiteralException e)
		{
			e.printStackTrace();
		}
		catch (UnterminatedCommentException e)
		{
			e.printStackTrace();
		}
		catch (UnterminatedStringLiteralException e)
		{
			e.printStackTrace();
		}

	}

}
