package com.singham.yuan.ws.test.common.xml;

public class NamespacePrefixMapperImpl {

    private static final String[] EMPTY_STRING = new String[0];

    public String[] getPreDeclaredNamespaceUris() {
        return EMPTY_STRING;
    }

    public String[] getPreDeclaredNamespaceUris2() {
        return EMPTY_STRING;
    }

    public String[] getContextualNamespaceDecls() {
        return EMPTY_STRING;
    }

    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://www.yuan.singham.com/head".equals(namespaceUri) && !requirePrefix)
            return "";
        if ("http://www.yuan.singham.com/body".equals(namespaceUri) && !requirePrefix)
            return "";
        return "ns2";
    }

}
