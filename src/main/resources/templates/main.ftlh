<#import "parts/common.ftlh" as c>

<@c.page>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>

    <#include "parts/messageEdit.ftlh" />
    <#include "parts/messageList.ftlh" />

</@c.page>