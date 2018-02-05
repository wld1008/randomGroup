<%--
  Created by IntelliJ IDEA.
  User: lidan.wu
  Date: 2018/2/2 0002
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>随机抽取组长</title>
</head>
<body>
<form action="/randomLeaders" method="get">
  <table>
    <tr>
      <td>
        您的姓名：
      </td>
      <td>
        <input id ="userName" name ="userName" type="text" >
      </td>
      <td>
        <input type="submit" value="点击随机抽取"  >
      </td>

    </tr>
  </table>
</form>
</body>
</html>
