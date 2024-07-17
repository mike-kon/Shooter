<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<body>
Topic:<input name="topic" type="text" size="50"/>
<button onclick="{
        let obj = document.getElementsByName('UUID')[0];
        obj.value = '10000000-1000-4000-8000-100000000000'.replace(/[018]/g, c =>
            (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16));
}">generate!</button>
<br>
Headers (key=value)
<br>
<textarea name="headers" cols="100" rows="15"></textarea>
<br>
Message:
<br>
<textarea name="messages" cols="100" rows="15"></textarea>
</body>
</html>