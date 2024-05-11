# Описание задания

<a name="components"></a>
## Компоненты системы
Система состоит из 5 компонентов:

- [x] [Система закупки сырья](#raw_system)
- [x] [Система приготовления продукции](#preparing_system)
- [x] [Система заказов](#order_system)
- [ ] [Система доставки](#delivery_system)
- [ ] [Система истории](#history_system)


Пекарня реализует продукцию следующего формата:
- Пирожки:
  - С мясом
  - С капустой
  - Пироги
  - С рыбой
  - С ягодой
  - Напитки
  - Чай
  - Кофе

Для приготовления продукции используется сырье:
- Тесто
- Мясо
- Капуста
- Рыба
- Ягоды
- Листовой чай
- Зерна кофе

<a name="raw_system"></a>
## Система закупки сырья - [_к оглавлению_](#components)
Данная система отвечает за предоставление сырья по запросу. На вход подается объект, который содержит в себе поля:
- Тип сырья
- Количество

У каждого типа сырья есть свое выделенное время закупки (не зависит от кол-ва запрашиваемых единиц):
- Тесто - 1 секунда
- Мясо - 2 секунды
- Капуста - 2 секунды
- Рыба - 3 секунды
- Ягоды - 5 секунд
- Листовой чай - 2 секунды
- Зерна кофе - 2 секунды
  
Для того, чтобы система имитировала процесс закупки, используется остановка программы на выделенное время

<a name="preparing_system"></a>
## Система приготовления продукции - [_к оглавлению_](#components)
На вход системе подаётся рецепт, состоящий из перечисленных ингредиентов для конкретного блюда.
Рецепты выглядят следующим образом:
- Пирожок с мясом = тесто + мясо
- Пирожок с капустой = тесто + капуста
- Пирог с рыбой = 2 порции теста + рыба
- Пирог с ягодой = 2 порции теста + 2 порции ягод
- Чай = 1 единица листового чая
- Кофе = 2 единицы зерен кофе

Система приготовления должна учитывать количество сырья из хранилища. После выполнения запросов на получение нового сырья, оно сохраняется в хранилище. Система приготовления имеет внутри себя 10 объектов печи, которые будут имитировать работу. 
У каждой печи есть своя выделенная вместимость (считается, что для печи пирожок и большой пирог равнозначны с точки зрения вместимости):
- 2 большие печи, вмещающие 10 единиц
- 3 средние печи, вмещающие 5 единиц
- 5 маленьких печей, вмещающих по 2 единицы

Время приготовления каждого рецепта:
- Пирожок с мясом - 1 секунда
- Пирожок с капустой - 1 секунда
- Пирог с рыбой - 2 секунды
- Пирог с ягодой - 2 секунды

Чай и кофе готовятся в отдельных объектах - машины для приготовления чая и кофе:
- Чай готовится 3 секунды
- Кофе готовится 4 секунды

<a name="order_system"></a>
## Система заказов - [_к оглавлению_](#components)
Прием заказов осуществляется посредством генерации рандомных комбинаций блюд.
Объект заказа состоит из:
- Выбранный продукт
- Количество
- Дата-время создания
- Тип доставки
  - Курьер - после создания заказа он передается в сервис доставки
  - У кассы - отдельно в лог пишется запись "Приятного аппетита" после приготовления заказа
- Город доставки

1. Каждый заказ содержит не более 6 позиций (примеры: {5 пирожков, 1 чай} или {2 пирожок, 2 пирог, 2 кофе} или {3 чая, 3 кофе}). При этом скорость создания
нового заказа - 3 секунды.
2. Вся логика реализована посредством шедулера.

Созданные заказы добавляются в очередь заказов.

<a name="delivery_system"></a>
## Система доставки - [_к оглавлению_](#components)
На вход системе подается заказ с адресом. На текущий момент система может работать только в некоторых городах:
- Москва
- Санкт-Петербург
- Новосибирск
- Екатеринбург

Если в запросе был указан другой город, то выбрасывается ошибка и в системе истории регистрируется дополнительная запись “Заказ не может быть доставлен”.
Для каждого города есть следующие типы курьеров:
- 2 на автомобиле
- 3 на велосипедах
- 5 пеших

У каждого типа есть своя вместимость:
- В каждый автомобиль можно поместить 20 единиц продукции
- В каждый велосипед можно поместить 10 единиц продукции
- Пеший курьер может взять только 5 единиц продукции

Скорость доставки курьеров:
- Автомобиль - 1 секунда
- Велосипед - 2 секунды
- Пешком - 3 секунды

Соответственно, при запросе, система имитирует процесс доставки, используя остановку программы на выделенное время. Каждый курьер будет работать в отдельном потоке, для повышения пропускной способности.

<a name="history_system"></a>
## Система истории - [_к оглавлению_](#components)
На вход передается объект события. Система внутри может оперировать следующими типами событий:
- Событие заказа сырья
- Событие заказа клиента
- Событие доставки (завершенной)

Реализация хранилища для системы истории:
- Хранение в БД PostgreSQL - настроить репозиторий для сохранения данных по заказу.

Событие заказа сырья должно содержать:
- Уникальный id заказа
- Тип сырья
- Количество сырья
- Дату-время заказа

Событие заказа клиента должно содержать:
- Уникальный id заказа
- Данные заказа (вся продукция)
- Время приготовления (за сколько времени был приготовлен заказ)
- Дату-время заказа
- Флаг, была ли выполнена доставка курьером (true, если да, false в остальных случаях)

Событие доставки должно содержать:
- Уникальный id доставки
- Данные заказа (вся продукция)
- Время, за которое была выполнена доставка
- Тип курьера