# Domain
> domain을 담을 패키지를 만들어준다. 도메인이란 게시글, 댓글 등 소프트웨어에 대한 요구사항 혹은 문제 영역을 일컫는다. ex) Post, Member 클래스 등 .. <br>
실제 DB의 테이블과 매칭될 클래스로, 보통 Entity 클래스라고도 한다. 즉, Entity 클래스를 기준으로 테이블이 생성되고 스키마가 변경된다. JPA를 통해 **DB 데이터에 작업할 경우 이 Entity 클래스의 수정을 통해 작업**할 수 있다.

<details>
<summary> domain.posts.Posts </summary>

``` java
package com.rommmu.book.springboot.domain.posts;

import com.rommmu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

```

</details>

## 어노테이션과 메서드
### `@Getter`
클래스 위에 붙이는 롬북의 어노데티션으로, 클래스 내 모든 필드의 Getter 메서드를 자동생성 해준다. `@Getter`는 선언된 모든 필드의 get 메서드를 생성해주는 어노테이션이다.
### `@NoArgsConstructor`
클래스 위에 붙이는 롬북의 어노데이션으로, 기본 생성자를 자동으로 추가해준다. ex) `public Posts() {}` 과 같은 효과이다.
### `@Entity`
클래스 위에 붙이는 JPA의 어노테이션으로, 테이블과 링크될 클래스임을 나타낸다.
### `@Id`
해당 테이블의 PK 필드를 나타낸다.
### `@GeneratedValue`
PK의 생성 규칙을 나타낸다. `GenerationType.IDENTITY` 옵션을 추가해야만 `auto_increment`가 된다. (MySql 기준)
### `@Column`
테이블의 컬럼을 나타내며 굳이 선언하지 않아도 해당 클래스의 필드는 모두 컬럼이 된다. <br>
이 어노테이션을 사용하는 이유는 기본값 외에 추가적인 변경이 필요한 옵션이 있을 경우 사용한다. ex) 문자열의 경우 VARCHAR(255)가 기본인데 사이즈를 500으로 늘리고 싶을 경우 ...
### `@Builder`
해당 클래스의 빌더 패턴 클래스를 생성한다. 생성자 상단애 선언하면 생성자에 포함된 필드만 빌더에 포함된다.

## Domain 의 특징
### 💡 Database 유의사항
`Entity`의 PKsms `Long` 타입의 `Auto_increment`를 사용하는 것이 좋다. 주민등록번호나 복합키 등은 유니크 키로 별도로 추가하는 것이 좋다.
### 💡 Setter 메서드
`Entity` 클래스는 절대 `Setter` 메서드를 만들지 않는다. 대신 해당 필드의 값 변경이 필요하다면 명확히 그 목적과 의도를 나타낼 수 있는 메서드를 추가해야한다. <br>
기본적으로는 **생성자를 통해 최종값을 채운 후 DB에 삽입**하며, 값 변경이 필요한 경우 **해당 이벤트에 맞는 public 메서드를 호출**하여 변경하도록 한다. <br>
생성자 대신 **`@Builder`를 통해 제공되는 빌더 클래스**를 사용하는데,  생성자 대신 `@Builder`를 사용하는 이유는 생성자의 경우 지금 채워야할 필드가 무엇인지 명확히 지정할 수 없기 때문이다.
```java
Example.builder()
    .a(a)
    .b(b)
    .build();
```
처럼 활용한다.

## Repository
> DB Layer 접근자로, JPA에서는 인터페이스로 생성한다.
<details>
<summary> domain.posts.PostsRepository </summary>

``` java
package com.rommmu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}

```

</details>

### Repository 생성방식
단순히 인터페이스를 생성한 후에 `JpaRepository<Entity 클래스, PK 타입>` 을 상속하면 기본적인 CRUD 메서드가 자동으로 생성된다. <br>
@Repository를 추가할 필요도 없지만, **Entity 클래스와 기본 Entity Repository는 함께 위치해야한다**. Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수 없다.

## Test Code
<details>
<summary> test directory / domain.posts.PostsRepository </summary>

``` java
package com.rommmu.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("rommmu")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2024, 1, 9, 0, 0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>> createdDate =" + posts.getCreatedDate()+ ", modifiedDate = " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}

```

</details>

### `@SpringBootTest`
별다른 설정없이 `@SpringBootTest`를 사용할 경우, H2 데이터베이스를 자동으로 실행해준다.
### `@After`
Junit에서 단위테스트가 끝날때마다 수행되는 메서드를 지정해준다. 보통 배포 전 전체 테스트를 수행할 때 테스트간 데이터의 침범을 막기위해 사용한다. 여러 테스트가 동시에 수행될 때 테스트용 데이터베이스 H2에 데이터가 남아있어 다음 테스트 실행시 테스트가 실패할 수 있다.
### `postsRepository.save()`
테이블 posts에 insert/update 쿼리를 실행한다. `id` 값이 있다면 `update`가, 없으면 `insert` 쿼리가 실행된다.
### `postsRepository.findAll()`
테이블 posts에 있는 모든 데이터를 조회해오는 메서드이다.
