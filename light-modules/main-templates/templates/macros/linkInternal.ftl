[#assign linkHref = ""]
[#assign linkText = ""]
[#assign linkTarget = ""]
[#assign title = ""]
[#assign abstract = ""]
[#assign description = ""]
[#assign imageId = ""]
[#assign teaserImageId = ""]
[#assign color = ""]
[#assign dateFrom = ""]
[#assign dateTo = ""]
[#assign category = ""]
[#assign categoryName = ""]
[#assign template = ""]

[#if item[field + 'internal']?has_content]
    [#assign contentPage = cmsfn.nodeById(item[field + 'internal']!"")!""]
    [#if contentPage?has_content]
        [#assign contentPageCm = cmsfn.asContentMap(contentPage!)!]

        [#assign linkHref = (cmsfn.link(contentPage)!) + linkParameters]
        [#assign linkText = item['title' + field]!item['title' + field?cap_first]!item['titleLink']!contentPageCm.title!contentPageCm!i18n['link.label']!]


        [#-- load page properties --]
        [#assign contentPage = cmsfn.asContentMap(contentPage!)!]
        [#if !title?has_content]
            [#assign title = contentPage.title!contentPage.headerTitle!]
        [/#if]
        [#if !abstract?has_content]
            [#assign abstract = contentPage.abstract!]
        [/#if]
        [#if !description?has_content]
            [#assign description = contentPage.description!]
        [/#if]
        [#if !imageId?has_content]
            [#assign imageId = contentPage.headerPresentationImage!contentPage.image!]
        [/#if]
        [#if !teaserImageId?has_content]
            [#assign teaserImageId = contentPage.teaserImage!]
        [/#if]

        [#if !category?has_content]
            [#assign category = contentPage.category!]

            [#if category?has_content]
                [#assign catNode = cmsfn.nodeById(category!, "category")!]
                [#if catNode?has_content]
                    [#assign catDetails = cmsfn.asContentMap(catNode!)!]
                    [#assign categoryName = catDetails.displayName!catDetails.name!]
                [/#if]
            [/#if]

        [/#if]
        [#assign template = contentPage['mgnl:template']!]
    [/#if]
[/#if]