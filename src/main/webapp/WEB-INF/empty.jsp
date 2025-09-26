<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<body>
<script>
    function generateUUID() {
        let obj = document.getElementsByName('aaaa')[0];
        obj.value = uuidv4();
    }

    function uuidv4() {
        return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
            (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16)
        );
    }

</script>

<form:form modelAttribute="EmptyModelDto" method="post">
<input name="aaaa" type="text" size="50"/>
<button onclick="{
        let obj = document.getElementsByName('aaaa')[0];
        obj.value = '10000000-1000-4000-8000-100000000000'.replace(/[018]/g, c =>
            (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16));
}">generate!</button>

<textarea name="bbbb" cols="100" rows="15"></textarea>
</form:form>
</body>
</html>