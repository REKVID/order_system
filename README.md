# Система для оптовой компании


*   Компания занимается оптовой продажей различных товаров из определенного спектра и отслеживает финансовую сторону своей работы.
*   Для каждого товара указываются цена, справочная информация и значение признака наличия или отсутствия доставки.
*   В компанию обращаются заказчики (юридические лица), о каждом из которых известны наименование, адрес, номер телефона, контактное лицо.
*   По каждой сделке составляется документ, в котором фиксируются заказчик, купленные им товары, количество каждого купленного товара и дата покупки.
*   Доставка разных товаров может производиться разными способами, различными по цене и скорости.
*   Способы доставки каждого товара предопределены.
*   Ведется учет выбранных клиентами видов доставок при заключении сделок (с фиксацией стоимостей доставок).

---

Исполняемый `.jar` файл и SQL-скрипт для создания базы данных находятся в разделе **Releases** на странице репозитория.

---
### Некоторые пояснения к реализации

**1. Расчет стоимости доставки**

Так как компания занимается оптовой продажей, а методы доставки для каждого товара предопределены и различаются по цене и скорости, была выбрана следующая модель:
Цена доставки находится в связующей таблице `available_delivery_methods` (Доступные методы доставки) и определяется уникальной парой "товар" + "метод доставки". Это позволяет задавать разную стоимость для доставки разных товаров одним и тем же способом (например, доставка палеты кирпечей и коробки паралона одним и тем же самолетом будет стоить по-разному).

Итоговая стоимость доставки по одной позиции в заказе рассчитывается по формуле:
`[Количество единиц товара] * [Цена доставки одной единицы этого товара выбранным методом]`

**2. Ролевая система**

В системе реализованы две основные роли:
*   **Клиент:** Может просматривать каталог товаров, заключать сделки и редактировать данные своего профиля.
*   **Работник:** Имеет доступ к управлению всей информацией в базе данных (товары, методы доставки, доступные методы доставки для каждого товара), за исключением ролей и пользователей. Также работник может просматривать информацию по всем документам сделок, заключенным клиентами.


