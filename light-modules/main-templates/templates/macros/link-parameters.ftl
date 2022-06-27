[#function getLinkParams item field=""]

    [#assign tmpLinkParameters = ""]
    [#if item['linkParameters']?has_content]
        [#assign tmpLinkParameters = item['linkParameters']!]
    [#elseif field?has_content && item[field + 'Parameters']?has_content]
        [#assign tmpLinkParameters = item[field + 'Parameters']!]
    [/#if]
    [#if tmpLinkParameters?has_content && !tmpLinkParameters?starts_with('#')]
        [#assign tmpLinkParameters = '#' + tmpLinkParameters]
    [/#if]

    [#return tmpLinkParameters]
[/#function]