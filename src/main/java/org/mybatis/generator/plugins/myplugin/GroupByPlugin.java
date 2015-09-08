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
	 * 在查询条件类中添加column
	 */
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		
		Field column = new Field("groupByColumn", PrimitiveTypeWrapper.getStringInstance());
		column.setInitializationString("null");
		column.setVisibility(JavaVisibility.PROTECTED);
		addField(topLevelClass, introspectedTable, column);
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * 在xml的SelectByExample的SQL语句添加group
	 */
	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		element.getElements().add(createGroupByXmlElement());
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}

	/**
	 * 在xml的SelectByExampleWithoutBLOBs的SQL语句添group
	 */
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		element.getElements().add(createGroupByXmlElement());
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	
	/**
	 * 创建group的xmlElement
	 * 
	 * @return
	 */
	private XmlElement createGroupByXmlElement() {
		XmlElement xmlElement = new XmlElement("if");
		xmlElement.addAttribute(new Attribute("test", "groupByColumn != null"));
		xmlElement.addElement(new TextElement("group by #{groupByColumn}"));
		return xmlElement; 
	}
}
