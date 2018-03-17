<?xml version="1.0" encoding="UTF-8"?>

<!-- New document created with EditiX at Tue Mar 13 16:22:25 CET 2018 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html"/>
	
	<xsl:template match="/"> 
		<html> 
			<head> 
				<title> 
				Pays du monde 
				</title> 
			</head> 
			 
			<body style="background-color:white;">  
				<h1>Les pays du monde</h1> 
				Mise en forme par : Safia et Grégoire (B3421)
				<xsl:apply-templates select="//metadonnees"/>
				<h1>Pays par ordre alphabétique</h1>
				<table width="100%" border="3" align="center">
					<tr>
						<th>N°</th>
						<th>Nom</th>
						<th>Capitale</th>
						<th>Continent<br></br>Sous-continent</th>
						<th>Voisins</th>
						<th>Coordonnées</th>
						<th>Drapeau</th>
					</tr>
					
					<xsl:for-each select="//country">
						<xsl:sort select="name/common" order="ascending"/>
						<xsl:apply-templates select="."/>
					</xsl:for-each>
				</table>
			</body> 
		</html> 
	</xsl:template>
	
	<xsl:template match="metadonnees">
 		<p style="text-align:center; color:blue;">
		Objectif : <xsl:value-of select="objectif"/>
 		</p><hr/>
	</xsl:template>
	
	<xsl:template match="country">

						<tr>
							<td>
								<xsl:value-of select="position()"/></td>
							<td>	
								<a style="color:green;">
								<xsl:value-of select="name/common"/></a>
								(<xsl:value-of select="name/official"/>)
							</td>
							<td>
								<xsl:value-of select="capital"/>
							</td>
							<td>
								<xsl:value-of select="infosRegion/region"/><br/>
								<xsl:value-of select="infosRegion/subregion"/>
							</td>
							<td>
								<xsl:value-of select="borders"/>
							</td>
							<td>
								Latitude : 
								<xsl:value-of select="coordinates/@lat"/>
								<br></br>Longitude : 
								<xsl:value-of select="coordinates/@long"/>
								
							</td>
							<td>
								
								<img src="{concat('http://www.geonames.org/flags/x/',concat(translate(codes/cca2, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'.gif'))}" alt="" height="40" width="60"/>
							</td>
						</tr>
		
	</xsl:template>
	

</xsl:stylesheet>


