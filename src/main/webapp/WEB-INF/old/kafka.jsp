<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html xml:lang="ru">
<body>
Bootsrtap server <input name="bootstrap" type="text" size="50"/>
<br>
Topic:<input name="topic" type="text" size="50"/>
<br>
Headers (key=value, key &lt;tab &gt; or json)
<br>
<textarea name="headers" cols="100" rows="15"></textarea>
<br>
Message:
<br>
<textarea name="messages" cols="100" rows="15"></textarea>
</body>
</html>