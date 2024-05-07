package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;



    @Builder
    public HashTag(String name) {
        this.name = name;
    }


    // 생성자, 게터, 세터 등 필요한 코드들...

    public void changeName(String name){
        this.name = name;
    }
}