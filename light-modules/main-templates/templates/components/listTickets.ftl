[#assign tickets = todofn.getTicketNodes()! /]
[#assign ticketsCM = cmsfn.asContentMapList(tickets!)! /]

[#if ticketsCM?has_content&&ticketsCM?size>0]
    <div class="container">
        <div id="demo" class="box jplist" style="margin: 20px 0 50px 0">

            <!-- ios button: show/hide panel -->
            <div class="jplist-ios-button">
                <i class="fa fa-sort"></i>
                jPList Actions
            </div>

            <!-- panel -->
            <div class="jplist-panel box panel-top">

                <div
                        class="jplist-label"
                        data-type="Page {current} of {pages}"
                        data-control-type="pagination-info"
                        data-control-name="paging"
                        data-control-action="paging">
                </div>

                <div
                        class="jplist-pagination"
                        data-control-type="pagination"
                        data-control-name="paging"
                        data-control-action="paging"
                        data-items-per-page="3">
                </div>

            </div>

            <!-- data -->
            <div class="list box text-shadow">
                [#list ticketsCM as ticket]
                    <!-- item 1 -->
                    <div class="list-item box">

                        <!-- img -->
                        <div class="img">
                            [#if ticket.ticketPriority?has_content&&ticket.ticketPriority=="LOW"]
                                <img src="${ctx.contextPath}/.resources/main-templates/webresources/images/low-priority-bug.svg"></img>
                            [#elseif ticket.ticketPriority?has_content&&ticket.ticketPriority=="MEDIUM"]
                                <img src="${ctx.contextPath}/.resources/main-templates/webresources/images/medium-priority-bug.svg"></img>
                            [#else]
                                <img src="${ctx.contextPath}/.resources/main-templates/webresources/images/high-priority-bug.svg"></img>
                            [/#if]
                        </div>

                        [#assign dateTicket=cmsfn.metaData(ticket, "mgnl:created")!]

                        <div class="block font">
                            <p class="title font-weight-bold">${ticket.ticketTitle!}</p>
                            <p class="desc">${ticket.ticketDescription!}</p>
                            <p class="theme text-uppercase font-weight-bold">
                                [#if ticket.ticketPriority?has_content&&ticket.ticketPriority=="LOW"]
                                    <span class="text-primary">Priority: ${ticket.ticketPriority!}</span>
                                [#elseif ticket.ticketPriority?has_content&&ticket.ticketPriority=="MEDIUM"]
                                    <span class="text-warning">Priority: ${ticket.ticketPriority!}</span>
                                [#else]
                                    <span class="text-danger">Priority: ${ticket.ticketPriority!}</span>
                                [/#if]
                            </p>
                            <p class="ticketUuidParagraph"><input class="ticketUuid" type="hidden"
                                                                  value="${ticket.@uuid}"/>
                            </p>
                            <p class="date"> ${dateTicket?substring(0,10)!}</p>
                            <p id="actionsTicket">
                                <button onclick="changeStatusTicket(this)" data-method="post"
                                        data-api="${ctx.contextPath}/.rest/tickets/edit/complete/status?uuid=${ticket.@uuid}" [#if ticket.ticketCompleted?has_content&&ticket.ticketCompleted] style="display: none;" [/#if]
                                        class="markAsCompleted btn btn-primary">Mark as complete
                                </button>
                                <button onclick="changeStatusTicket(this)" data-method="delete"
                                        data-api="${ctx.contextPath}/.rest/tickets/delete?uuid=${ticket.@uuid}" [#if ticket.ticketCompleted?has_content&&!ticket.ticketCompleted] style="display: none;" [/#if]
                                        class="removeFromDashboard btn btn-danger">Remove from dashboard
                                </button>
                            </p>
                        </div>
                    </div>
                [/#list]

            </div>

            <!-- no results -->
            [#if ticketsCM?size==0]
                <div class="box jplist-no-results text-shadow align-center">
                    <p>No results found</p>
                </div>
            [/#if]

            <!-- ios button: show/hide panel -->
            <div class="jplist-ios-button">
                <i class="fa fa-sort"></i>
                jPList Actions
            </div>

            <!-- panel -->
            <div class="jplist-panel box panel-bottom">

                <div
                        class="jplist-label"
                        data-type="{start} - {end} of {all}"
                        data-control-type="pagination-info"
                        data-control-name="paging"
                        data-control-action="paging">
                </div>

                <div
                        class="jplist-pagination"
                        data-control-type="pagination"
                        data-control-name="paging"
                        data-control-action="paging"
                        data-items-per-page="5"
                        data-control-animate-to-top="true">
                </div>
            </div>
        </div>
    </div>
[/#if]
<script>


    $('#demo').jplist({
        itemsBox: '.list'
        , itemPath: '.list-item'
        , panelPath: '.jplist-panel'
    });


    function changeStatusTicket(param) {
        let urlApi = $(param).attr('data-api');
        let method = $(param).attr('data-method');

        $.ajax({
            type: method,
            url: urlApi,
            contentType: "application/json; charset=utf-8",
            dataType: "json",

            complete: function (data) {
                window.location.reload();
            }
        });
    }
</script>

