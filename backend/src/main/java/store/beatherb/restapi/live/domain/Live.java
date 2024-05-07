package store.beatherb.restapi.live.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import store.beatherb.restapi.content.domain.Creator;
import store.beatherb.restapi.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "live")
public class Live {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne
    @JoinColumn(name="owner_id")
    private Member member;

    @Column(name="title")
    private String title;

    @Column(name="created_at")
    private LocalDateTime createdAt;


    @Column(name="live_describe")
    private String describe;

    @OneToMany(mappedBy = "live", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Guest> guestList;

    @OneToMany(mappedBy = "live", cascade =  CascadeType.ALL,orphanRemoval = true)
    private List<LiveContent> liveContentList;

    @Builder
    public Live(Member member, String title, LocalDateTime createdAt, String describe, List<Guest> guestList,List<LiveContent> liveContentList) {
        this.member = member;
        this.title = title;
        this.createdAt = createdAt;
        this.describe = describe;
        this.guestList = guestList;

        this.liveContentList = liveContentList;
        if(this.guestList !=null){
            for (Guest g: this.guestList){
                g.setLive(this);
            }
        }
        if(this.liveContentList != null){
            for(LiveContent l: this.liveContentList){
                l.setLive(this);
            }
        }


    }
}
