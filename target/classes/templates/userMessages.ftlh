<#import "parts/common.ftlh" as c>

<@c.page>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Message editor
    </a>
    <#if isCurrentUser>
    <#include "parts/messageEdit.ftlh" />
    </#if>

    <#include "parts/messageList.ftlh" />

</@c.page>