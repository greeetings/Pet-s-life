<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name :</label>
            <div class="col-sm-6">
                <input type="text" placeholder="Enter name" class="form-control" name="username"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password:</label>
            <div class="col-sm-6">
                <input type="password" placeholder="Enter password" class="form-control" name="password"/>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />


        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else >Sign in</#if></button>
        <div><#if !isRegisterForm><a href="/registration">Add new user</a></#if></div>

    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Sign out</button>
    </form>
</#macro>