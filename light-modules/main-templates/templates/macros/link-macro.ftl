[#include "/main-templates/templates/macros/link-parameters.ftl"]
[#macro assignLinkParameters item field]

    [#if field?has_content]
        [#assign linkType = item[field]!]
    [#else]
        [#assign linkType = item["link"]!]
    [/#if]

    [#assign linkParameters = getLinkParams(item, field)!""]

    [#assign linkIcon = ""]
    
    [#if linkType=="internal"]
        [#include "linkInternal.ftl"]
    [#elseif linkType=="external"]
        [#include "linkExternal.ftl"]
    [#elseif linkType=="download"]
        [#include "linkDownload.ftl"]
    [/#if]

[/#macro]
