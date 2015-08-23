package org.mybatis.generator.plugins.myplugin;

import org.apache.log4j.Logger;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class GroupByPlugin extends PluginAdapterEnhance {


	private Logger log = Logger.getLogger(this.getClass());

	public GroupByPlugin() {
		log.debug("initialized");
	}

	/**
	 * 在查询条件类中添加offset与limitCount字段
	 */
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Field isGroupBy = new Field("groupBy", PrimitiveTypeWrapper.getBooleanInstance());
		isGroupBy.setInitializationString("false");
		isGroupBy.setVisibility(JavaVisibility.PROTECTED);
		addField(topLevelClass, introspectedTable, isGroupBy);

		Field column = new Field("column", PrimitiveTypeWrapper.getStringInstance());
		column.setInitializationString("null");
		column.setVisibility(JavaVisibility.PROTECTED);
		addField(topLevelClass, introspectedTable, column);

		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * 在xml的SelectByExample的SQL语句添加limit
	 */
	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		element.getElements().add(createPaginationXmlElement());
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}

	/**
	 * 在xml的SelectByExampleWithoutBLOBs的SQL语句添加limit
	 */
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		element.getElements().add(createPaginationXmlElement());
		
		System.out.println(element.toString());
		
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	
	/**
	 * 创建limit的xmlElement
	 * 
	 * @return
	 */
	private XmlElement createPaginationXmlElement() {
		XmlElement xmlElement = new XmlElement("if");
		xmlElement.addAttribute(new Attribute("test", "groupBy == true"));
		xmlElement.addElement(new TextElement("groupby #{column}"));
		return xmlElement; 
	}
}
