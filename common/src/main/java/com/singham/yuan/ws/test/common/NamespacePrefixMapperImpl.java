package com.singham.yuan.ws.test.common;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import org.springframework.stereotype.Component;

@Component
public class NamespacePrefixMapperImpl extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://www.singham.yuan.com/header".equals(namespaceUri) && !requirePrefix)
            return "";
        if ("http://www.singham.yuan.com/body".equals(namespaceUri) && !requirePrefix)
            return "";
        return "ns";
    }

}
