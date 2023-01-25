<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http //www.w3.org/1999/xsl/transform version 2.0" >
    <xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
    <xsl:template match="/">id,courseName,instructor,courseDuration,courseTime,location
        <xsl:for-each select="//row">
            <xsl:value-of select="concat(id,',',CourseName,',',Instructor,',',CourseDuration,',',CourseTime,',',Location,'&#xA;')"/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

