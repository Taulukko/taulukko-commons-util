package com.taulukko.commons.util.httpserver;

public class RLTest implements RequestListenner
{

	public ListnnerResponse getResponse(String path)
	{
		ListnnerResponse ret = new ListnnerResponse();
		if (path.indexOf("jar://") > 0)
		{
			ret.setData(("File type jar [" + path + "] have captured!")
					.getBytes());
			ret.setIgnore(false);
			ret.setNotFound(false);
		}
		else if (path.indexOf("zip://") > 0)
		{
			ret.setData(("File type zip [" + path + "] have captured!")
					.getBytes());
			ret.setIgnore(false);
			ret.setNotFound(true);
		}
		else
		{
			ret.setIgnore(true);
			ret.setNotFound(true);
		}

		return ret;
	}

}
