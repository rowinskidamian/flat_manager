<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flat Manager - Twój program do zarządzania najmem mieszkań</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css">
    <script src="https://kit.fontawesome.com/a1834f9866.js" crossorigin="anonymous"></script>
</head>
<body>
<section>
    <section class="navbar">
        <jsp:include page="/WEB-INF/views/layout/nav.jsp"/>
    </section>
</section>

<section class="section">
    <div class="container is-fluid">

        <div class="columns is-vcentered">

            <div class="column is-three-quarters">
                <div class="container has-text-centered">
                    <h1 class="title">
                        Formularz Rejestracyjny
                    </h1>
                    <p class="subtitle">
                        Wpisz swoje dane, aby się zarejestrować.
                    </p>
                </div>

                <form:form modelAttribute="userData" action="/register" method="post">

                    <div class="field">
                        <label class="label">Nazwa użytkownika</label>
                        <div class="control">
                            <form:input path="login" cssClass="input"/>
                        </div>
                        <form:errors path="login" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Hasło</label>
                        <div class="control has-icons-left">
                            <form:password path="password" cssClass="input"/>
                            <span class="icon is-small is-left">
                        <i class="fas fa-lock"></i>
                    </span>
                        </div>
                        <form:errors path="password" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Powtórz Hasło</label>
                        <div class="control has-icons-left">
                            <form:password path="passwordCheck" cssClass="input"/>
                            <span class="icon is-small is-left">
                        <i class="fas fa-lock"></i>
                    </span>
                        </div>
                        <form:errors path="passwordCheck" cssClass="has-text-danger"/>
                    </div>


                    <div class="field">
                        <label class="label">Imię</label>
                        <div class="control has-icons-left has-icons-right">
                            <form:input path="firstName" cssClass="input"/>
                            <span class="icon is-small is-left">
                        <i class="fas fa-user"></i>
                    </span>
                        </div>
                        <form:errors path="firstName" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Nazwisko</label>
                        <div class="control has-icons-left has-icons-right">
                            <form:input path="lastName" cssClass="input"/>
                            <span class="icon is-small is-left">
                        <i class="fas fa-user"></i>
                    </span>
                        </div>
                        <form:errors path="lastName" cssClass="has-text-danger"/>
                    </div>

                    <div class="field">
                        <label class="label">Email</label>
                        <div class="control has-icons-left has-icons-right">
                            <form:input path="email" cssClass="input"/>
                            <span class="icon is-small is-left">
                                <i class="fas fa-envelope"></i>
                            </span>
                        </div>
                        <form:errors path="email" cssClass="has-text-danger"/>
                    </div>

                    <div id="adress-details">
                        <div class="notification is-primary">
                            <strong>Dane adresowe</strong>
                        </div>

                        <div class="block">

                            <div class="field">
                                <label class="label">Miasto</label>
                                <div class="control has-icons-left has-icons-right">
                                    <form:input path="cityName" cssClass="input"/>
                                    <span class="icon is-small is-left">
                                        <i class="fas fa-envelope"></i>
                                     </span>
                                </div>
                                <form:errors path="cityName" cssClass="has-text-danger"/>
                            </div>

                            <div class="field">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Ulica</th>
                                            <th>Numer mieszkania</th>
                                            <th>Numer domu</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="control has-icons-left">
                                                    <form:input path="streetName" cssClass="input"/>
                                                    <span class="icon is-small is-left">
                                                        <i class="fas fa-envelope"></i>
                                                    </span>
                                                </div>
                                                <form:errors path="streetName" cssClass="has-text-danger"/>
                                            </td>
                                            <td>
                                                <div class="control">
                                                    <input type="number" name="streetNumber" class="input">
<%--                                                <form:input path="streetNumber" cssClass="input"/>--%>
                                                </div>
                                                <form:errors path="streetNumber" cssClass="has-text-danger"/>
                                            </td>
                                            <td>
                                                <div class="control">
                                                    <input type="number" name="apartmentNumber" class="input">
                                                </div>
                                                <form:errors path="apartmentNumber" cssClass="has-text-danger"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>

                    </div>


                    <div class="field is-grouped">
                        <div class="control">
                            <button class="button is-link" type="submit">Wyślij</button>
                        </div>
                        <div class="control">
                            <button class="button is-link is-light" type="reset">Wyczyść</button>
                        </div>
                        <div class="control">
                            <button class="button is-link is-light" type="button" id="showAdress">Dodaj adres</button>
                        </div>
                    </div>
                    <input type="hidden" name="validAddress" id="validAddress" value="${validAddress}">
                    <sec:csrfInput/>
                </form:form>
            </div>
        </div>

    </div>

    <script src="/scripts/adress_in_registration.js" type="text/javascript"></script>

</section>
</body>
</html>