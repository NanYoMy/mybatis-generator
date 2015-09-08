package org.mybatis.generator.plugins.myplugin;

import java.util.List;

import org.apache.log4j.Logger;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.api.dom.xml.Element;

public class MysqlSplitingTablePlugin extends PluginAdapterEnhance {

	private Logger log = Logger.getLogger(this.getClass());

	private String tableName = "tableName";

	public MysqlSplitingTablePlugin() {
		log.debug("initialized");
	}

	/**
	 * 在查询条件类中添加offset与limitCount字段,在查询的类中添加字段
	 */
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {

		Field tableName = new Field("tableName",
				PrimitiveTypeWrapper.getStringInstance());
		// 默认设置为当前的table名字
		tableName.setInitializationString("\""
				+ introspectedTable.getTableConfiguration().getTableName()
				+ "\"");
		tableName.setVisibility(JavaVisibility.PROTECTED);
		addField(topLevelClass, introspectedTable, tableName);

		return super.modelExampleClassGenerated(topLevelClass,
				introspectedTable);
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		
		
		Field tableName = new Field("tableName",
				PrimitiveTypeWrapper.getStringInstance());
		// 默认设置为当前的table名字
		tableName.setInitializationString("\""
				+ introspectedTable.getTableConfiguration().getTableName()
				+ "\"");
		tableName.setVisibility(JavaVisibility.PROTECTED);
		addField(topLevelClass, introspectedTable, tableName);
		
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}

	/**
	 * 在xml的SelectByExample的SQL语句添加limit
	 */
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {

		resetSelectXmlElementTableName(element);
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element,
				introspectedTable);
	}

	// @Override
	// public boolean
	// sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
	// IntrospectedTable introspectedTable) {
	//
	// }
	//
	// @Override
	// public boolean sqlMapInsertElementGenerated(XmlElement
	// element,IntrospectedTable introspectedTable) {
	// reSetInsertXmlElementTableName(element);
	// return super.sqlMapInsertElementGenerated(element, introspectedTable);
	// }

	/**
	 * @return
	 */
	private void resetSelectXmlElementTableName(XmlElement element) {
		List<Element> elements = element.getElements();
		// elements的[3]个元素
		TextElement subSentence = new TextElement("from ${" + tableName + "}");
		elements.set(3, subSentence);
	}

	private void resetInsertXmlElementTableName(XmlElement element) {

		List<Element> elements = element.getElements();
		// elements的[3]个元素
		TextElement subSentence = new TextElement("from ${" + tableName + "}");

	}

}
