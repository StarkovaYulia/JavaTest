# Лабораторная №2

### Практика, третья часть.

#### Цель:

- Научится работе с пагинацией в Spring Framework
- Научиться применять сортировку

#### Теория:

Полезные ссылки:
- Пагинация и сортировка  
  https://www.baeldung.com/spring-data-jpa-pagination-sorting

Сортировка, пагинация и спецификация на примере Книг и Авторов
```java
@Data
@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {
    @Id
    private String id;
    private String name;
    private Integer birthYear;
    public static AuthorEntity createAuthor(String name, Integer birthYear) {
        return new AuthorEntity(UUID.randomUUID().toString(), name, birthYear);
    }
}
```
```java
@Data
@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private Integer releaseYear;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    public static BookEntity createBook(String name, String description, Integer releaseYear, AuthorEntity author) {
        return new BookEntity(UUID.randomUUID().toString(), name, description, releaseYear, author);
    }
}
```
```java
    @Transactional(readOnly = true)
    public Page<BookDto> fetch(String authorName) {
        Sort sort = Sort.by(Sort.Order.desc("releaseYear")); // сортируем по дате выхода по убыванию
        var pageRequest = PageRequest.of(0, 3, sort); // фронт может присылать номер страницы (первая - 0) и кол-во записей на страние (тут 3)
        Specification<BookEntity> specification = (r, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (authorName != null) { // фильтруем по имени автора (через связь many-to-one)
                predicates.add(cb.like(r.get("author").get("name"), '%' + authorName + '%'));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };

        var entityPage = bookRepository.findAll(specification, pageRequest);
        var dtos = entityPage.getContent().stream() // как вариант - преобразование сразу в страницу dto
                .map(b -> new BookDto(b.getId(), b.getName(), b.getDescription(), b.getReleaseYear(), b.getAuthor().getName()))
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, entityPage.getPageable(), entityPage.getTotalElements());
    }
```

#### Практические задачи:

Расширить метод поиска задач по произвольному кол-ву полей
- Добавить произвольный набор полей сортировки, с указанием порядка (ASC / DESC)
- Сделать поиск страничным: в запросе должны появиться номер страницы и кол-во записей, а тело ответа должно соответствовать требованиям запроса и также иметь информацию о фактическом кол-ве записей на странице
