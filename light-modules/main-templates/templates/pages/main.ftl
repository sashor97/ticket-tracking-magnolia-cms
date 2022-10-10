<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/x-icon" href="${ctx.contextPath}/.resources/main-templates/webresources/images/high-priority-bug.svg">


    <title>Ticket tracking</title>
    <link href="${ctx.contextPath}/.resources/main-templates/webresources/css/style.css" rel="stylesheet">
    <link href="${ctx.contextPath}/.resources/main-templates/webresources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/.resources/main-templates/webresources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx.contextPath}/.resources/main-templates/webresources/vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
    <script src="${ctx.contextPath}/.resources/main-templates/webresources/vendor/jquery/jquery.min.js"></script>
    <script src="${ctx.contextPath}/.resources/main-templates/webresources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet" />

    <!-- Plugin JavaScript -->
    <script src="${ctx.contextPath}/.resources/main-templates/webresources/vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- jPList core js and css  -->
    <link href="${ctx.contextPath}/.resources/main-templates/webresources/dist/css/jplist.core.min.css" rel="stylesheet" type="text/css" />
    <script src="${ctx.contextPath}/.resources/main-templates/webresources/dist/js/jplist.core.min.js"></script>

    <!-- jplist pagination bundle -->
    <script src="${ctx.contextPath}/.resources/main-templates/webresources/dist/js/jplist.pagination-bundle.min.js"></script>
    <link href="${ctx.contextPath}/.resources/main-templates/webresources/dist/css/jplist.pagination-bundle.min.css" rel="stylesheet" type="text/css" />
    [@cms.page /]

</head>

<body id="page-top">
[#--<a class="menu-toggle rounded" href="#">--]
[#--    <i class="fas fa-bars"></i>--]
[#--</a>--]
[#--<nav id="sidebar-wrapper">--]
[#--    <ul class="sidebar-nav">--]
[#--        [#list components as component ]--]
[#--            [@cms.component content=component /]--]
[#--            [#assign componentId=content.id!]--]
[#--            <li class="sidebar-brand">--]
[#--                <a class="js-scroll-trigger" href="${componentId!}">Start Bootstrap</a>--]
[#--            </li>--]
[#--        [/#list]--]

[#--    </ul>--]
[#--</nav>--]
<!-- Navigation -->
[#--<a class="menu-toggle rounded" href="#">--]
[#--    <i class="fas fa-bars"></i>--]
[#--</a>--]
[#--<nav id="sidebar-wrapper">--]
[#--    <ul class="sidebar-nav">--]
[#--        <li class="sidebar-brand">--]
[#--            <a class="js-scroll-trigger" href="#page-top">Start Bootstrap</a>--]
[#--        </li>--]
[#--        <li class="sidebar-nav-item">--]
[#--            <a class="js-scroll-trigger" href="#page-top">Home</a>--]
[#--        </li>--]
[#--        <li class="sidebar-nav-item">--]
[#--            <a class="js-scroll-trigger" href="#about">About</a>--]
[#--        </li>--]
[#--        <li class="sidebar-nav-item">--]
[#--            <a class="js-scroll-trigger" href="#services">Services</a>--]
[#--        </li>--]
[#--        <li class="sidebar-nav-item">--]
[#--            <a class="js-scroll-trigger" href="#portfolio">Portfolio</a>--]
[#--        </li>--]
[#--        <li class="sidebar-nav-item">--]
[#--            <a class="js-scroll-trigger" href="#contact">Contact</a>--]
[#--        </li>--]
[#--    </ul>--]
[#--</nav>--]


<!-- Header -->
[@cms.area name = "navigation"/]
[@cms.area name = "main"/]
[@cms.area name = "loginArea"/]

<!-- About -->

<!-- Services -->

<!-- Callout -->

<!-- Portfolio -->

<!-- Call to Action -->

<!-- Map -->

<!-- Footer -->
<footer class="footer text-center" id="footer">
[#--    <div class="container">--]
[#--        <ul class="list-inline mb-5">--]
[#--            [@cms.area name="footerIcons"/]--]
[#--        </ul>--]
[#--        <p class="text-muted small mb-0">${cmsfn.decode(content).footerCopy!}</p>--]
[#--    </div>--]
</footer>

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded js-scroll-trigger" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Bootstrap core JavaScript -->

<!-- Custom scripts for this template -->

</body>

</html>