# npn.vita-soft

<h1>Тестовое задание. Регистрация и обработки пользовательских заявок.</h1>
<h3>Описание:</h3>

<ul>
<li>База данных PostgreSQL.</li>
<li>Обмен данных через JSON. Для добавления заявки формат JSON должен соответствовать 
{ "message" : "value", "header" : "value" }. Значения сообщения и заголовка не могут быть пустыми.</li>
<li>Используется базовая аутентификация http.</li>
<li>Краткое описание пакетов:<br>com.npn.vita.soft.configurations - пакет конфигурации сервера;
<br>com.npn.vita.soft.controllers - пакет контроллеров сервера;
<br>com.npn.vita.soft.model.request - пакет обеспечивающий работу с заявкой пользователя;
<br>com.npn.vita.soft.model.security - пакет обеспечивающий работу авторизации и прав доступа.
</li>
<li>Тут выполнены только интеграционные тесты, которые требуют соответсвенно настроенной и заполненной тестовой базы данных.
Для использования дампа требуется создать пользователей dbadmin, user1 назначить им пароли и группу test_users, либо изменить дамп базы.
<br>CREATE ROLE dbadmin WITH LOGIN NOSUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
<br>CREATE ROLE test_users WITH NOLOGIN NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
<br>CREATE ROLE user1 WITH LOGIN NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
<br>GRANT test_users TO user1;
</li>
<li>Пароль пользователя user1 - test, либо откорректировать application.properties</li>
<li>Дамп тестовой базы  - Файл vita_soft в корне проекта.</li>
</ul>
