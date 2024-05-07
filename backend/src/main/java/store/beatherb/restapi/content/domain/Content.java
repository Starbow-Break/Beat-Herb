package store.beatherb.restapi.content.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;
import store.beatherb.restapi.member.domain.Member;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO : 추후 복합 등록자 추가.

    @JsonIgnore
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL,orphanRemoval = true)

    private List<Creator> creatorList;
    @NotNull
    private String title;

    @ManyToOne
    @NotNull
    @JoinColumn(name="content_writer_id")
    private Member writer;

    @ManyToOne
    @JoinColumn(name="content_type_id")
//    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @JsonIgnore
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ContentHashTag> contentHashTagList;


    @Column(name="lyrics")
    private String lyrics;

    @Column(name="cotent_describe")
    private String describe;

    @Column(name="hit")
    @Setter
    private int hit;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Setter
    boolean processed;

    @OneToMany(mappedBy = "childContent", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    List<InOrder> inOrderList;

    @OneToMany(mappedBy = "rootContent", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    List<InOrder> outOrderList;

    private String image;

    @OneToMany(mappedBy = "content",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    List<Comment> commentList;


    @Builder
    public Content(String title, Member writer, ContentType contentType,List<InOrder> outOrderList, List<InOrder> inOrderList, List<Creator> creatorList, String lyrics, String describe,List<ContentHashTag> contentHashTagList, int hit, LocalDateTime createdAt,String image,List<Comment> commentList) {
        this.title = title;
        this.writer = writer;
        this.contentType = contentType;
        this.creatorList = creatorList;
        this.lyrics = lyrics;
        this.describe = describe;
        this.hit = hit;
        this.createdAt = createdAt;
        this.inOrderList = inOrderList;
        this.processed = false;
        this.contentHashTagList = contentHashTagList;
        this.image = image;
        this.outOrderList = outOrderList;
        this.commentList = commentList;

        if(this.creatorList !=null){
            for (Creator c: this.creatorList){
                c.setContent(this);
            }
        }
        if(this.inOrderList != null){
            for( InOrder i:  this.inOrderList){
                i.setChildContent(this);
            }
        }
        if(this.contentHashTagList != null){
            for(ContentHashTag c : this.contentHashTagList){
                c.setContent(this);
            }
        }
    }
}
