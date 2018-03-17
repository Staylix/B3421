<?xml version="1.0" encoding="UTF-8" ?>

<!-- New document created with EditiX at Fri Jan 15 11:19:00 CET 2010 -->

<!-- Test ok -->


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="person" mode="modeTest">
		<xsl:param name="test"></xsl:param>
		<h1>
			<xsl:value-of select="name/family"/>,<xsl:value-of select="name/given"/>
		</h1>
		Email 2 : <xsl:value-of select="email"/>
		<hr/>
	</xsl:template>
</xsl:stylesheet>
