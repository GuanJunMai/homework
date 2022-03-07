package com.lagou.config;

import com.lagou.enumer.SqlMethodEnum;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            putMappedStatement(namespace, element, SqlMethodEnum.SELECT);
        }
        List<Element> updateList = rootElement.selectNodes("//update");
        for (Element element : updateList) {
            putMappedStatement(namespace, element, SqlMethodEnum.UPDATE);
        }
        List<Element> deleteList = rootElement.selectNodes("//delete");
        for (Element element : deleteList) {
            putMappedStatement(namespace, element, SqlMethodEnum.DELETE);
        }
    }

    private void putMappedStatement(String namespace, Element element, SqlMethodEnum sqlMethodEnum) {
        String id = element.attributeValue("id");
        String resultType = element.attributeValue("resultType");
        String paramterType = element.attributeValue("paramterType");
        String sqlText = element.getTextTrim();
        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParamterType(paramterType);
        mappedStatement.setSql(sqlText);
        String key = namespace + "." + id;
        mappedStatement.setSqlMethodEnum(sqlMethodEnum);
        configuration.getMappedStatementMap().put(key, mappedStatement);
    }
}
