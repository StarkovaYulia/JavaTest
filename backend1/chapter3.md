# Лабораторная №1

### Практика, третья часть.

#### Цель:

- Научится миграции, через flyway
- Научится определениям связей между сущностями
- Научится валидации сущностей
- Научится работе с аннотацией @Transactional
- Научится работе с репозиториями сущностей

#### Теория:

- Связи в JPA   
  https://devcolibri.com/%D0%BA%D0%B0%D0%BA-%D1%81%D0%B2%D1%8F%D0%B7%D0%B0%D1%82%D1%8C-entity-%D0%B2-jpa/   
  https://www.baeldung.com/hibernate-one-to-many
- Миграции через flyway   
  https://www.baeldung.com/database-migrations-with-flyway   
  https://habr.com/ru/company/otus/blog/506788/
- Транзакции   
  https://habr.com/ru/post/532000/   
  https://www.baeldung.com/transaction-configuration-with-jpa-and-spring    
  https://medium.com/@kirill.sereda/%D1%82%D1%80%D0%B0%D0%BD%D0%B7%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D0%B2-spring-framework-a7ec509df6d2
- Фильтрация/поиск в JPA репозиториях   
  https://www.baeldung.com/spring-data-derived-queries
- Валидация   
  https://www.baeldung.com/spring-boot-bean-validation
  https://habr.com/ru/post/536612/

#### Пара слов о целях третей части:
Откажемся от auto-ddl hibernate и сделаем правильные миграции с использованием flyway, научимся связывать друг с другом 
сущности, покажем, как просто можно строить поисковые запросы в репозиториях сущностей и научимся валидировать поля перед
записью.

#### Практические задачи:

0. Используем наработки с прошлой части
1. Создать классы сущностей:
  - Задача
    - Идентификатор
    - Дата создания
    - Дата редактирования
    - Заголовок (Не пустой, не null)
    - Описание
    - Пользователь создатель (many-to-one)
    - Пользователь исполнитель (many-to-one)
    - Приоритет
    - Проект (many-to-one)
    - Временная оценка задачи
  - Проект
    - Идентификатор
    - Дата создания
    - Дата редактирования
    - Наименование (Не пустой, не null)
    - Описание (Не менее 10 символов, не пустой, не null)
  - Комментарий
    - Идентификатор
    - Дата создания
    - Дата редактирования
    - Пользователь (many-to-one)
    - Задача (many-to-many)
    - Текст комментария (Не пустой, не null)
2. Связать сущности через аннотации @ManyToOne и @ManyToMany (отмечены в скобках в п.1)
3. Добавить валидацию к полям сущностей (отмечены в скобках в п.1)
4. Создать сервисы, репозитории для новых сущностей, в каждом сервисе реализовать метод создания сущности и метод
   получения сущности по id. Реализовать эндпоинты для создания и для получения сущностей.
5. Реализовать в сервисе и репозитории сущности "Задача" 2 метода. Первый — это поиск всех задач по проекту (List),
   второй — поиск всех задач, где исполнитель — переданный пользователь (List).
6. Повесить на методы @Transactional аннотацию, обработать исключения (например, пользователь по id не найден)
7. Реализовать миграции через flyway и выставить auto-ddl hibernate значение в "validate"

#### Примеры решения конкретных задач:

[Реализация logback](misc/logback.xml)

*Реализация связей*

```
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;
    
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookEntity> books;
```
*Реализация исключения*

```
public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
```

#### Факультативное чтение:

- Этапы иницилизации контекста     
  https://habr.com/ru/post/222579/