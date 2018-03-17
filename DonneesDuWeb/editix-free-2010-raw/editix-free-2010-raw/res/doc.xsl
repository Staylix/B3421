<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/TR/xhtml1/strict" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:param name="test1"/>
	<xsl:template match="/editix">
		<html>
			<body>
				<h2>EditiX Menus</h2>
				<xsl:apply-templates/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="menuBar">
		<xsl:for-each select="menu">
			<h3>
				<xsl:value-of select="ui/@label"/>
				<xsl:value-of select="$test1"/>
			</h3>
			<table border="1" width="90%">
				<tr bgcolor="#FFCC66">
					<th width="40%">Item</th>
					<th width="60%">Meaning</th>
				</tr>
				<xsl:for-each select="item">
					<tr bgcolor="#FFFFCC">
						<td width="40%">
							<xsl:value-of select="ui/@label"/>
						</td>
						<td width="60%">
							<xsl:value-of select="ui/@help"/>
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
