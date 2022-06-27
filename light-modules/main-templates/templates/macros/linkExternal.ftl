[#assign linkHref = (item[field + 'external']!) + linkParameters]
[#assign linkHrefOriginal = (item[field + 'external']!) + linkParameters]
[#if !linkHref?starts_with('http://') && !linkHref?starts_with('https://') && !linkHref?starts_with('/')]
    [#assign linkHref = "http://" + linkHref]
[/#if]
[#if linkHrefOriginal?starts_with("mailto")]
    [#assign linkHref = linkHrefOriginal]
[/#if]
[#if linkHref?starts_with('/')]
    [#assign linkHref = ctx.contextPath + linkHref]
[/#if]


[#assign linkText = item['title' + field]!item['title' + field?cap_first]!item[field + 'external']!item['titleLink']!]

[#if linkHref?starts_with('http://') || linkHref?starts_with('https://')]
    [#assign linkTarget = " target=\"_blank\""]
[/#if]

[#assign linkIcon = "${ctx.contextPath}/.resources/tele2/webresources/img/icons/24/external_link.svg"]