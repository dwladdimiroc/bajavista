<%@page import="java.io.File"%>
<%@page import="bajavista.IndiceInvertido"%>

<%
    String indexES = "/home/daniel/Documentos/Programación/Java/Tecnologías de la web/Bajavista/index/IndexFile";
    //String indexNONES = "/home/daniel/Documentos/Programación/Java/Tecnologías de la web/Bajavista/index/IndexFileNONES";
    File file = new File(indexES);

    if (file.isDirectory()) {
        if (file.list().length <= 0) {
            IndiceInvertido indexInv = new IndiceInvertido(indexES);
            indexInv.crearIndiceInvertido();
            System.out.print("Index create");
        }
    }
//    IndiceInvertido indexInv = new IndiceInvertido(indexES);
//    indexInv.crearIndiceInvertido();
//    System.out.println("Index create");
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

        <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">


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
                        <li><a href="#">Actualizar</a></li>
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
                <form id="search" action="pages/busqueda.jsp" method="get">
                    <input title="Ingrese una búsqueda" type="text" name="buscador" id="buscador" pattern="[a-zA-Z0-9]+" required /><br/>
                    <input value="Consultar" type="submit" />
                </form>
                Seleccione tipo de preferencia en la búsqueda:
                <select name="preferencia" id="preferencia" form="search">
                    <option value="tiempo">Tiempo</option>
                    <option value="influencia">Influencia</option>
                    <option value="objetividad">Objetividad</option>
                </select> 
            </div>
        </div>

    </body>
</html>
