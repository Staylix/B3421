<?xml version="1.0" encoding="UTF-8" ?>

<!-- New document created with EditiX at Fri Jan 08 12:20:58 CET 2010 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="my-page">
					<fo:region-body margin="1cm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="my-page">
				<fo:flow flow-name="xsl-region-body">
					<fo:block border-after-color="blue" border-after-width="1px" border-after-style="solid">Personals</fo:block>
					<xsl:apply-templates/>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="person">
		<fo:block>
			<xsl:value-of select="name/family"/>,<xsl:value-of select="name/given"/>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>
