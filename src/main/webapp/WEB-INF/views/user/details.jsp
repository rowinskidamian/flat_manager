<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - dane zalogowanego użytkownika</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
</head>
<body>
<section>
    <section class="navbar">
        <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
    </section>
</section>

<section>

    <div class="container is-fluid">

        <div class="columns is-flex is-vcentered is-centered">

            <div class="column is-three-quarters">

                <div class="notification has-text-centered is-light">
                    <strong>Dane zalogowanego użytkownika</strong>
                </div>

                <table class="table">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Szczegóły</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th>Login</th>
                        <td>${userData.login}</td>
                    </tr>
                    <tr>
                        <th>E-mail</th>
                        <td>${userData.email}</td>
                    </tr>
                    <tr>
                        <th>Imię</th>
                        <td>${userData.firstName}</td>
                    </tr>
                    <tr>
                        <th>Nazwisko</th>
                        <td>${userData.lastName}</td>
                    </tr>
                    <tr>
                        <th>Miasto</th>
                        <td>${userData.cityName}</td>
                    </tr>
                    <tr>
                        <th>Ulica</th>
                        <td>${userData.streetName}</td>
                    </tr>
                    <tr>
                        <th>Numer budynku</th>
                        <td>${userData.streetNumber}</td>
                    </tr>
                    <tr>
                        <th>Numer mieszkania</th>
                        <td>${userData.apartmentNumber}</td>
                    </tr>
                    </tbody>
                </table>

                <div id="actions" class="field">
                    <div class="field is-grouped">
                        <div class="control">
                            <button class="button is-light" onclick="history.go(-1)">
                                Powrót
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</section>

<footer class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

</body>
</html>
