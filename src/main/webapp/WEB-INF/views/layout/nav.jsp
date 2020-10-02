<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="images" href="/">
            <img src="/images/flat_manager_logo.png" width="160" height="160">
        </a>

        <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false"
           data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
            <sec:authorize access="isAuthenticated()">
                <a class="navbar-item" href="/property">
                    Mieszkania
                </a>

                <a class="navbar-item" href="/room">
                    Pokoje
                </a>

                <a class="navbar-item" href="/tenant">
                    Najemcy
                </a>

                <a class="navbar-item" href="/payment">
                    Płatności
                </a>

                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link">
                        Rozwiń
                    </a>

                    <div class="navbar-dropdown">
                        <a class="navbar-item">
                            O nas
                        </a>
                        <a class="navbar-item">
                            Kontakt
                        </a>
                        <hr class="navbar-divider">
                        <a class="navbar-item" href="/user/details">
                            Twoje konto
                        </a>
                    </div>
                </div>
            </sec:authorize>

        </div>

        <div class="navbar-end">
            <div class="navbar-item">

                <div class="buttons">
                    <sec:authorize access="isAnonymous()">
                        <a class="button is-primary is-info" href="/register">
                            <strong>Zarejestruj się</strong>
                        </a>
                        <a class="button is-light" href="/login">
                            Zaloguj się
                        </a>
                    </sec:authorize>

                    <sec:authorize access="isAuthenticated()">
                        <a class="button is-white" href="/user/details">Zalogowany &nbsp;
                            <strong> ${userFirstName} </strong>
                        </a>
                        <a class="button is-primary is-info" href="/logout-confirm">
                            <strong>Wyloguj się</strong>
                        </a>
                    </sec:authorize>
                </div>

            </div>
        </div>
    </div>
</nav>

