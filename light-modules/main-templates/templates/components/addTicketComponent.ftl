[#assign title = content.title!]
[#assign button = content.button!]
[#assign selectPriorities = content.addPrioriteties!]
[#include "/main-templates/templates/macros/link-macro.ftl"]
[#assign componentID = content.id!]


[#--[#assign asset2 = damfn.getAssetLink(cmsfn.children(imageAsset)[0].linkField) !/]--]
<div class="container text-right mt-5">
            <a class="btn btn-primary btn-xl js-scroll-trigger text-white" id="addTicketButton" data-toggle="modal" data-target="#addTicketModal">${button!}</a>
</div>
<div class="modal" id="addTicketModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">${title}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addTicketForm" action="" method="post" >
                    <div class="form-group">
                        <label class="col-form-label" for="addTicketTitle">Enter ticket title</label>
                        <input class="form-control" type="text" name="title" id="addTicketTitle"/>

                    </div>
                    <div class="form-group">
                        <label class="col-form-label" for="addTicketDescription">Enter ticket description</label>
                        <textarea class="form-control" name="description" id="addTicketDescription" cols="40" rows="4"></textarea>
                    </div>
                    <p>

                    </p>
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Select priority
                        </button>
                        <ul class="dropdown-menu" id="dropDownPriority" aria-labelledby="dropdownMenuButton">
                            [#if selectPriorities?has_content]
                                [#list selectPriorities as priority]
                                    <li><a class="dropdown-item text-uppercase">${priority!}</a></li>
                                [/#list]
                            [/#if]
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Add ticket in board</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $(".dropdown-menu li a").click(function(){

            $("#dropdownMenuButton").text($(this).text());
            $("#dropdownMenuButton").val($(this).text());

        });
        $("#addTicketForm").submit(function (e) {

            e.preventDefault(); // avoid to execute the actual submit of the form.

            var form = $(this);
            console.log('form', form);
            console.log('formSez',form.serialize())
            let ticket={}
            ticket['title']=e.target.title.value;
            ticket['description']=e.target.description.value;
            ticket['priority']=$('#dropdownMenuButton').text();
            ticket['completed']=false;
            console.log(JSON.stringify(ticket));


            $.ajax({
                type: "POST",
                url: "${ctx.contextPath}/.rest/tickets/add/ticket",
                data: JSON.stringify(ticket),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                complete: function(data){
                    window.location.reload();
                }
            });

        });
    });
</script>