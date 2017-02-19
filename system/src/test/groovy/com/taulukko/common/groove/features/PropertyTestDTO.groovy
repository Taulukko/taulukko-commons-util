package com.taulukko.common.groove.features;

public class PropertyTestDTO {

	def int age=0;
	private String name="J.D.";
	
	private def output="";

 
	def getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
		this.output = "setName";
	}
	
	def getOutput()
	{
		return this.output;
	}
}
