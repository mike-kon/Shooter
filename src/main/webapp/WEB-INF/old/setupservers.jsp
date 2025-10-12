<%--
  Created by IntelliJ IDEA.
  User: MKononovich
  Date: 25.09.2025
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<body>
<h3>Настройка серверов</h3>
<div class="stand">
    <div class="standtop">
        <input type="button" value="добавить" onclick="askServerType()"/>
        <input type="button" value="удалить"/>
    </div>
    <table>
        <tr>
            <td>
                <div class="standleft">
                    <select size="10" onclick="getJsp('getSetupServer',this,'#setupServer')">
                        <c:forEach items="${serversList}" var="item">
                            <option>"${item}</option>
                        </c:forEach>
                    </select>
                </div>
            </td>
            <td>
                <div class="standbody" id="setupServer">
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
