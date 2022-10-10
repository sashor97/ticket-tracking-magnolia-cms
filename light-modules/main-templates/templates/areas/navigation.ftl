[#assign rootPage = navfn.rootPage(content)!]
[#assign navPages = navfn.navItems(rootPage!)!]

[#assign logoId = content.logo!]
[#if logoId?has_content]
    [#assign logoAsset = damfn.getAsset(logoId!)!]
[/#if]
<nav class="nav navbar-nav">
    <div class="container-sm">
        <div class="nav-wrapper d-flex align-items-center">
            <div id = "logoNav" class="logo-wrapper img-fluid">
                <a href="${cmsfn.link(rootPage!)!}"> <img src="[#if logoAsset?has_content &&logoAsset.link??]${logoAsset.link!} [#else] ${ctx.contextPath!}/.resources/main-templates/webresources/images/logo.png"[/#if] alt="logo"></a>
            </div>
            <ul id ="navigation" style="margin-top: 10px" class="menu-wrapper d-flex flex-grow-1 flex-column flex-md-row fw-bold">
                [#if navPages?has_content && navPages?size > 0]
                    [#list navPages as navPage]
                        [#if navPage.hideInMenu?? && !navPage.hideInMenu && navPage.navigationMenuLabel?? && navPage.navigationMenuLabel != ""]
                            <li class="me-5 navList"><a href="${cmsfn.link(navPage!)!}">${navPage.navigationMenuLabel!}</a></li>
                     [/#if]
                    [/#list]
                [/#if]
            </ul>
            <div class="nav-icons-wrapper d-flex align-items-center" id="nav-icons" style="margin-top: 10px">
[#--                <img src="${ctx.contextPath}/.resources/leona/webresources/img/nav-search.png" alt="Search" style="height: 70px; transform: scale(0.9, 0.9);" class="mx-4"/>--]
                [#-- Build language navigation. --]
                [#assign localizedLinks = cmsfn.localizedLinks()!]
                [#if localizedLinks?has_content]
                    [#assign languages = localizedLinks?keys]
                    <ul class="list-unstyled d-inline-flex flex-column flex-lg-row" style="position: relative">
                        [#list languages as lang]
                            [#assign current = cmsfn.isCurrentLocale(lang)!]
                        [#-- Use "compress" to put "li" and "a" on one line to prevent white spaces. --]
                            <li class="m-1" style="width: 35px">[@compress single_line=true]
                                [#-- Current locale should not be linked. --]
                                    [#if !current]<a href="${localizedLinks[lang]!'#'}">[/#if]
                                    [#if lang == "en"]
                                        <img src="${ctx.contextPath}/.resources/main-templates/webresources/images/eng-flag.png" height="32px" width="32px" alt="English" class=""/>
                                    [#elseif lang == "de"]
                                        <img src="${ctx.contextPath}/.resources/main-templates/webresources/images/de-flag.png" height="32px" width="32px" alt="German" class=""/>
                                    [/#if]
                                    [#if !current]</a>[/#if]
                                [/@compress]</li>
                        [/#list]
                    </ul>
                [/#if]
            </div>
        </div>
        <nav class="jmenu">
            <label for="menu-btn" class="jm-menu-btn jm-icon-menu"></label>
            <input type="checkbox" id="menu-btn" class="jm-menu-btn">
            <ul class="jm-collapse">
                [#if navPages?has_content && navPages?size > 0]
                    [#list navPages as navPage]
                        [#if navPage.navigationMenuLabel?? && navPage.navigationMenuLabel != "" && navPage.hideInMenu?? && !navPage.hideInMenu]
                            <li><a href="${cmsfn.link(navPage!)!}">${navPage.navigationMenuLabel!}</a></li>
                        [/#if]
                    [/#list]
                [/#if]
            </ul>
        </nav>
    </div>
</nav>


<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        const width = window.innerWidth;
        if (width < 821) {
            const element = document.getElementById("navigation");
            element.classList.add("d-none");
            const logo = document.getElementById("logoNav");
            logo.setAttribute("style","width:100px");
        //    var ul = document.getElementById("")

        }
    });
</script>
<style>
    @media only screen
    and (min-device-width: 768px)
    and (max-device-width: 1024px)
    and (-webkit-min-device-pixel-ratio: 1) {
    #logoNav{
        width: 35px!important;
        height: 35px!important;
    }
    }
    @media only screen
    and (min-device-width: 250px)
    and (max-device-width: 650px){
        .nav-icons-wrapper{
            margin-left: 65%;
        }
    }
    @media only screen
    and (min-device-width: 651px)
    and (max-device-width: 769px){
        .nav-icons-wrapper{
            margin-left: 5%;
        }
    }
    .jmenu{background:#252525;box-shadow:1px 1px 3px 0 rgba(0,0,0,.5);box-sizing:border-box;line-height:1}input.jm-menu-btn,input[type=checkbox].jm-menu-btn~.jm-collapse{display:none}input[type=checkbox]:checked.jm-menu-btn~.jm-collapse{display:block}label.jm-menu-btn{color:#959595;cursor:pointer;display:block;padding:16px 32px}label.jm-menu-btn:hover{color:#fff}.jmenu li,.jmenu ul{list-style:none;margin:0;padding:0}.jmenu a{color:#959595;display:inline-block;padding:16px 32px;text-decoration:none}.jm-dropdown:hover a,.jmenu a:hover{color:#fff}.jmenu ul ul{display:none}.jm-dropdown:hover ul{display:block}.jm-dropdown ul{background:#fff;padding:0}.jm-dropdown ul a,.jm-dropdown:hover ul a{color:#0072bc}.jm-dropdown ul a:hover,.jm-dropdown:hover ul a:hover{color:#000}.jm-dropdown ul ul{border-bottom:1px #ccc solid;border-top:1px #ccc solid;box-shadow:none;margin-bottom:16px;max-width:100%;position:relative}.jm-icon-dropdown{border:solid #959595;border-width:0 2px 2px 0;display:inline-block;margin:0 0 3px 8px;padding:3px;transform:rotate(45deg)}li:hover .jm-icon-dropdown{border-color:#fff}.jm-icon-menu::before{content:'\2630'}@media (min-width:768px){.jmenu li{display:none}.jmenu a{padding:16px}.jm-dropdown{position:relative}.jm-dropdown li a{display:block;padding:8px 16px;white-space:nowrap}.jm-dropdown ul{box-shadow:1px 1px 3px 0 rgba(0,0,0,.5);padding:8px 0;position:absolute;min-width:100%}input.jm-menu-btn,label.jm-menu-btn{display:none}.jm-collapse,input[type=checkbox].jm-menu-btn~.jm-collapse{display:block}}

</style>
