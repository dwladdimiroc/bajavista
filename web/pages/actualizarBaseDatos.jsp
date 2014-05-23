<%@page import="bajavista.Actualizar"%>
<%
    String file = request.getParameter("file");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

        <title>Bajavista</title>

       <link type="text/css" href="../css/bootstrap.min.css" rel="stylesheet">


    </head>

    <body>

        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="index.jsp">Buscar</a></li>
                        <li><a href="pages/actualizar.jsp">Actualizar</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <br><br><br>

        <div class="container">
            <div class="text-center">
                <h3 class="text-center">
                    <img src="img/logo.png" alt="" title="" border="0" />
                </h3>
                <% out.print(file);
                %>
                <h1>La base de datos se ha actualizado...</h1>   
            </div>
        </div>

    </body>
</html>
