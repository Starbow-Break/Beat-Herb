package store.beatherb.restapi.content.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="inorder")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "root_content_id")
    private Content rootContent;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @Setter
    private Content childContent;


    @Builder
    public InOrder(Content rootContent, Content childContent) {
        this.rootContent = rootContent;
    }
}
