<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
        <a class="images" href="https://bulma.io">
            <img src="/images/flat_manager_logo.png" width="160" height="160">
        </a>

        <a role="button" class="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
        </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item">
                Strona główna
            </a>

            <a class="navbar-item">
                Moje mieszkania
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
                    <a class="navbar-item">
                        Zgłoś problem
                    </a>
                </div>
            </div>
        </div>

        <div class="navbar-end">
            <div class="navbar-item">
                <div class="buttons">
                    <a class="button is-primary">
                        <strong>Zarejestruj się</strong>
                    </a>
                    <a class="button is-light">
                        Zaloguj się
                    </a>
                </div>
            </div>
        </div>
    </div>
</nav>
