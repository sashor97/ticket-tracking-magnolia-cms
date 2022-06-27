[#assign title = content.title!]
[#assign subtitle = content.subtitle!]
[#--[#assign button = content.button!]--]
[#assign imageAsset = content.image!]
[#--[#assign myAsset = damfn.getAssetLink(imageAsset)!]--]
[#include "/main-templates/templates/macros/link-macro.ftl"]
[#--[#assign componentID = content.id!]--]
[#--[#assign myAsset = damfn.getAssetLink(imageAsset)!]--]


[#--[#assign asset2 = damfn.getAssetLink(cmsfn.children(imageAsset)[0].linkField) !/]--]




<header class="masthead d-flex" style="background: linear-gradient(90deg,rgba(255,255,255,.1) 0,rgba(255,255,255,.1) 100%),url(${damfn.getAssetLink(imageAsset)!})">
    <div class="container text-center my-auto">
        <h1 class="mb-1">${title!"To-Do Application"}</h1>
        <h3 class="mb-5">
            <em>${subtitle!"To-Do Application"}</em>
        </h3>
[#--        <img src="${myAsset}" alt="img"/>--]
        [@assignLinkParameters content "linkType"/]
[#--        <a class="btn btn-primary btn-xl js-scroll-trigger" href="${linkHref!}">${button!}</a>--]
    </div>
    <div class="overlay"></div>
</header>
