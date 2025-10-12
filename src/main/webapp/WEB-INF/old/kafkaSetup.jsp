<%--
  Created by IntelliJ IDEA.
  User: MKononovich
  Date: 26.09.2025
  Time: 22:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<body>
<div>
    <h4>Настройка сервера Кафка</h4>

    <form:form method="post" action="/api/v1/createKafkaServer" >
        <table>
            <tr>
                <th>Параметр</th>
                <th>Значение</th>
            </tr>
            <tr>
                <td>Брокер</td>
                <td></td>
            </tr>
        </table>
        <input type="submit" value="ok">
    </form:form>
</div>
</body>
</html>
