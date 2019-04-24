package com.taulukko.commons.util.web;

import java.util.ArrayList;

public abstract class FilterCharacters
{
	public abstract byte[] getInvalidCharacters();
	
	public byte[] filter(byte[] content)
	{
		byte [] invalid = this.getInvalidCharacters();
		
		ArrayList<Byte> newContent = new ArrayList<>();
		
		for(int contContent =0; contContent < content.length;contContent++)
		{
			boolean isInvalid = false;
			for(int contInvalid =0; !isInvalid && contInvalid < invalid.length;contInvalid++)
			{
				isInvalid = content[contContent] == invalid[contInvalid];
			}
			if(!isInvalid)
			{
				newContent.add(content[contContent]);
			}
		}
		
		byte ret[] = new byte[newContent.size()];
		
		for(int cont = 0 ; cont< newContent.size();cont++)
		{
			ret[cont] = newContent.get(cont);
		}
		
		return ret;
	}
}
