taulukko-commons-util
=====================
 
Java lib Taulukko Commons Util.

To use Tauluko Maven Mirror, add in your settings.xml

	<mirror>
			<id>Central Internal</id>
			<url>http://repository.taulukko.com:8080/repository/internal</url>
			<mirrorOf>central</mirrorOf>
	</mirror> 
	
Import and deploy
====================

Add in your maven ( settings.xml ) the reposotory below:

  <mirrors>	
	<mirror>
			<id>Central Internal</id>
			<url>http://repository.taulukko.com.br:8080/repository/internal</url>
			<mirrorOf>*</mirrorOf>
	</mirror>	
  </mirrors> 