# Лабораторная №1

### Практика, вторая часть.

#### Цель:

- Научится базовой работе со Spring-Boot: создавать проекты и запускать приложение
- Научится базовой работе с REST контроллерами
- Научится работе с Spring Data JPA
- Научится работать с PostgreSQL, подключаться к нему из IDE, подключать приложение к БД
- Научится работе с jpa-сущностями и spring-data репозиториями
- Связать слой контроллера с сервисным слоем и сохранить/получить сущность

#### Теория:

- Enum  
  https://javarush.ru/groups/posts/1963-kak-ispoljhzovatjh-klass-enum
- Spring Boot  
  https://habr.com/ru/post/435144/  
- Spring Data      
  Простая статья на русском: https://habr.com/ru/post/435114/  
  Документация Spring-Boot: https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.jpa-and-spring-data
- Установка PostgreSQL  
  Инструкция для Windows: https://1cloud.ru/help/windows/ustanovka-postgresql-na-windows-server  
  Инструкция для Ubuntu: https://www.postgresql.org/download/linux/ubuntu/
- YML и properties файлы    
  https://habr.com/ru/company/otus/blog/576910/
- Описание настроек Spring-Boot  
  https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html  

#### Пара слов о целях второй части:
Учимся промышленной разработке, поднимаем веб-приложение. Основная работа с web заложена на вторую
лабораторную, а в текущей мы рассматриваем только базовые веб-интерфейсы, условно, для тестирования своих наработок.
Основной упор сделан на работу с БД и ORM, а конкретно, на создание сущностей, маппинг их на таблички в БД и
сохранение/получение их через веб интерфейс

#### Практические задачи:

1. Создать Spring-Boot приложение и проверить, что оно запускается
2. Установить (или поднять докером) PostgreSQL, создать пользователя и БД
3. Настроить application.yml приложения, указать credentials для подключения к БД, указать порт, на котором развернется
   web-сервер
4. Реализовать контроллер с двумя методами, один POST для создания сущности, второй GET для получения сущности
5. Создать класс User с полями:
    - Идентификатор
    - Дата создания
    - Дата редактирования
    - ФИО
    - Емейл/логин
    - Пароль (хэш)
    - Роль (пользователь / администратор), enum
6. Сделать класс из п.5 - сущностью и замаппить его поля на поля в БД с помощью аннотаций JPA (название полей придумать самим)
7. Создать интерфейс-репозиторий (Spring Data) для выше созданной сущности
8. Указать в application.yml параметр, который отвечает за генерацию ddl (hibernate) - ```spring.jpa.hibernate.ddl-auto: update```
9. Запустить приложение, убедится, что в БД создались необходимые таблички
10. Связать контроллеры с сервисным слоем, POST метод должен принимать все поля, кроме идентификатора и в прямом виде, без
    обработки полей, записывать в БД. GET метод, соответственно, должен получать эти данные и отдавать клиенту
    (все поля, включая идентификатор)
11. Сделать ещё один эндпоинт (POST), который по вызову, будем брать из ресурсов проекта csv файл с пользователями и
    загружает их в бд

#### Примеры решения конкретных задач:

*Реализация контроллера*

```
@RestController // аннотация контроллера
@RequestMapping("/book") // роутинг
@RequiredArgsConstructor // конструктор для final полей класса, генерируемый с помощью Lombok
public class BookController {

    private final BookService bookService; // Сервис для работы с сущностями

    @PostMapping // Определение HTTP-метода и роутинг
    public BookDto save(@RequestBody CreateUpdateBookDto createUpdateBookDto) {
        return bookService.save(createUpdateBookDto);
    }
}
```

*Реализация сущности*

```
@Entity // Определение сущности
@Table(name = "books") // Определение таблицы в БД
@Data // Аннотация, включающая в себя геттеры, сеттеры и т.д.
@NoArgsConstructor // Конструктор без аргументов
@AllArgsConstructor // Конструктор с аргументами для заполнение всех полей, которые есть в классе
public class BookEntity {

    @Id // Определение идентификатора
    @Column(name = "id") // колонка в таблице БД, name - имя колонки в таблице PostgreSQL
    private String uuid;

    @Enumerated(EnumType.STRING) // Определение, каким образом будет писаться поле в БД 
    @Column // колонка в таблице БД
    private Genre genre;
}
```

*Реализация репозитория*

```
public interface BookRepository extends CrudRepository<BookEntity, String> {
}
```

*Пример application.yml*

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/spring_db # урл подключения к БД
    username: hits # имя пользователя СУБД
    password: hits # пароль пользователя СУБД
    driver-class-name: org.postgresql.Driver # класс драйвера для подключения к БД
  jpa:
    hibernate:
      ddl-auto: create-drop

server:
  port: 8191 # порт, который будет занят веб-приложением
```
