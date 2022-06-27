
[#--<a class="menu-toggle rounded" href="#">--]
[#--    <i class="fas fa-bars"></i>--]
[#--</a>--]
[#--<nav id="sidebar-wrapper">--]
[#--    <ul class="sidebar-nav">--]
[#--        [#list components as component ]--]

[#--            [#assign componentId=component.id!]--]
[#--            [#if component.check?has_content]--]
[#--                <li class="sidebar-brand">--]
[#--                    <a class="js-scroll-trigger" href="#${componentId!}">${component.title!}</a>--]
[#--                </li>--]
[#--            [/#if]--]
[#--        [/#list]--]


[#--    </ul>--]
[#--</nav>--]
[#list components as component ]
    [@cms.component content=component /]
[/#list]
