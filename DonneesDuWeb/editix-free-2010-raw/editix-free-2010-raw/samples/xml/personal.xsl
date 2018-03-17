<?xml version="1.0" encoding="UTF-8"?>

<!-- New document created with EditiX at Fri Jan 08 12:02:08 CET 2010 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/">
		<html>
			<body>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="person">
		<h1>
			<xsl:value-of select="name/family"/>,<xsl:value-of select="name/given"/>
		</h1>
		Email : <xsl:value-of select="email"/>
		<hr/>
	</xsl:template>
</xsl:stylesheet>
