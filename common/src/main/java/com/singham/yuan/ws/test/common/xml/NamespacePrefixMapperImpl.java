package com.singham.yuan.ws.test.common.xml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.springframework.stereotype.Component;

public class NamespacePrefixMapperImpl extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://www.yuan.singham.com/head".equals(namespaceUri) && !requirePrefix)
            return "";
        if ("http://www.yuan.singham.com/body".equals(namespaceUri) && !requirePrefix)
            return "";
        return "ns2";
    }

}
