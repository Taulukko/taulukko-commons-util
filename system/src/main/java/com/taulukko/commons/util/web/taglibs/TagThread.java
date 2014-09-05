	package com.taulukko.commons.util.web.taglibs;

public class TagThread extends TagLibBase
{
	private String _name;

	private int _time;

	public TagThread(String functionName, int iTime)
	{		
		_name = functionName;	
		 _time = iTime;
	}

	
	public String printBeginTag()
	{
		return "<script>";		

	}

	public String printIntoTag()
	{
		String sIntoTag = "var timerID" + _name +  " = null;\n";
		sIntoTag += "var timerRunning" + _name + "  = false;\n";
		sIntoTag += "function startClock" + _name + "()\n";
		sIntoTag += "{\n";
		sIntoTag += "   stopClock" + _name +  "();\n";
		sIntoTag += "   timeRemaining" +_name + "();\n";
		sIntoTag += "}\n\n";
		sIntoTag += "function stopClock" + _name + "()\n";
		sIntoTag += "{\n";
		sIntoTag += "   if (timerRunning" + _name + ")\n";
		sIntoTag += "   {\n";
		sIntoTag += "      clearTimeout" + _name + "(timerID" + _name + ");\n";
		sIntoTag += "   }\n";
		sIntoTag += "   timerRunning" + _name + " = false;\n";
		sIntoTag += "}\n\n";
		sIntoTag += "function timeRemaining" + _name + "()\n";
		sIntoTag += "{\n";
		sIntoTag += "   "+_name+"();\n";
		sIntoTag += "   timerID = setTimeout(\"timeRemaining" + _name + "()\", " + _time +  ");\n";
		sIntoTag += "   timerRunning" + _name + " = true;\n";
		sIntoTag += "}\n\n";
		return sIntoTag;
	}

	public String printEndTag()
	{
		return "startClock" + _name + "(); \n</script>";
	}	
}
