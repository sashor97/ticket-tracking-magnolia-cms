[#include "/main-templates/templates/functions/assetFunctions.ftl"]

[#assign linkHref = ""]
[#assign linkText = ""]
[#assign linkTarget = ""]

[#assign downloadLink = ctx.assetId!item[field + linkType]!""]
[#if downloadLink?has_content]
    [#assign lang = cmsfn.language()!""]
    [#assign asset = damfn.getAsset(downloadLink)!""]
    [#if asset?has_content]
        [#assign identifier = asset.getItemKey().getAssetId()!""]
        [#assign icon = getIconBasedOnMimeType(asset.getMimeType())!]
        [#assign assetNode = cmsfn.nodeById(identifier,"dam")!""]
        [#assign linkHref = (damfn.getAssetLink(downloadLink)!) + linkParameters]
        [#assign linkTarget = " target=\"_blank\" "]
        [#assign linkText = getAssetTitle(asset,item['title' + field]!item['title' + field?cap_first]!)!i18n['download.label']!""]

        [#assign linkIcon = "${ctx.contextPath}/.resources/tele2/webresources/img/icons/24/file.svg"]
    [/#if]
[/#if]