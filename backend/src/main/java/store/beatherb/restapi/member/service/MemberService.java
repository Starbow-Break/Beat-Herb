package store.beatherb.restapi.member.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.follow.domain.FollowRepository;
import store.beatherb.restapi.global.auth.exception.AuthErrorCode;
import store.beatherb.restapi.global.auth.exception.AuthException;
import store.beatherb.restapi.member.domain.Verify;
import store.beatherb.restapi.member.domain.VerifyRepository;
import store.beatherb.restapi.global.auth.dto.response.VerifyTokenResponse;
import store.beatherb.restapi.global.auth.service.AuthService;
import store.beatherb.restapi.global.mail.service.MailService;
import store.beatherb.restapi.global.mail.vo.MailVo;
import store.beatherb.restapi.global.validate.Email;
import store.beatherb.restapi.member.domain.Member;
import store.beatherb.restapi.member.domain.MemberRepository;

import store.beatherb.restapi.member.dto.MemberDTO;
import store.beatherb.restapi.member.dto.request.SignInRequest;
import store.beatherb.restapi.member.dto.request.SignUpRequest;
import store.beatherb.restapi.member.dto.response.MemberDetailResponse;
import store.beatherb.restapi.member.dto.response.MemberSearchResponse;
import store.beatherb.restapi.member.exception.MemberErrorCode;
import store.beatherb.restapi.member.exception.MemberException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final MailService mailService;
    private final AuthService authService;
    private final VerifyRepository verifyRepository;

    private final String IMAGE_DIRECTORY;

    private final FollowRepository followRepository;

    public MemberService(MemberRepository memberRepository,
                         MailService mailService,
                         AuthService authService,
                         VerifyRepository verifyRepository,
                         @Value("${resource.directory.profile.image}") String IMAGE_DIRECTORY,
                         FollowRepository followRepository) {
        this.memberRepository = memberRepository;
        this.mailService = mailService;
        this.authService = authService;
        this.verifyRepository = verifyRepository;
        this.IMAGE_DIRECTORY = IMAGE_DIRECTORY;
        this.followRepository = followRepository;
    }

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        Email.validate(signUpRequest.getEmail());

        //1. 이메일 valid 검사

        String[] adjectives = {
                "가냘픈", "가는", "가엾은", "가파른", "같은", "거센", "거친", "검은", "게으른", "고달픈",
                "고른", "고마운", "고운", "고픈", "곧은", "괜찮은", "구석진", "굳은", "굵은", "귀여운",
                "그런", "그른", "그리운", "기다란", "기쁜", "긴", "깊은", "깎아지른", "깨끗한", "나쁜",
                "나은", "난데없는", "날랜", "날카로운", "낮은", "너그러운", "너른", "널따란", "넓은", "네모난",
                "노란", "높은", "누런", "눅은", "느닷없는", "느린", "늦은", "다른", "더러운", "더운",
                "덜된", "동그란", "돼먹잖은", "된", "둥그런", "둥근", "뒤늦은", "드문", "딱한", "때늦은",
                "뛰어난", "뜨거운", "막다른", "많은", "매운", "먼", "멋진", "메마른", "메스꺼운", "모난",
                "못난", "못된", "못생긴", "무거운", "무딘", "무른", "무서운", "미끄러운", "미운", "바람직한",
                "반가운", "밝은", "밤늦은", "보드라운", "보람찬", "부드러운", "부른", "붉은", "비싼", "빠른",
                "빨간", "뻘건", "뼈저린", "뽀얀", "뿌연", "새로운", "서툰", "섣부른", "설운", "성가신",
                "센", "수줍은", "쉬운", "스스러운", "슬픈", "쌀쌀맞은", "쏜살같은",
                "쓰디쓴", "쓰린", "쓴", "아닌", "아름다운", "아쉬운", "아픈", "안된", "안쓰러운",
                "안타까운", "않은", "알맞은", "약빠른", "약은", "얇은", "얕은", "어두운", "어려운", "어린",
                "언짢은", "엄청난", "없는", "여문", "열띤", "예쁜", "올바른", "옳은", "외로운", "우스운",
                "의심스런", "이른", "익은", "있는", "작은", "잘난", "잘빠진", "잘생긴", "재미있는", "적은",
                "젊은", "점잖은", "조그만", "좁은", "좋은", "줄기찬", "즐거운", "지나친", "지혜로운",
                "질긴", "짓궂은", "짙은", "짠", "짧은", "케케묵은", "큰", "탐스러운", "턱없는", "푸른",
                "한결같은", "흐린", "희망찬", "흰", "힘겨운"
        };

        String[] animals = {
                "고양이", "강아지", "거북이", "토끼", "뱀", "사자", "호랑이", "표범", "치타", "하이에나",
                "기린", "코끼리", "코뿔소", "하마", "악어", "펭귄", "부엉이", "올빼미", "곰", "돼지",
                "소", "닭", "독수리", "타조", "고릴라", "오랑우탄", "침팬지", "원숭이", "코알라", "캥거루",
                "고래", "상어", "칠면조", "직박구리", "쥐", "청설모", "메추라기", "앵무새", "삵", "스라소니",
                "판다", "오소리", "오리", "거위", "백조", "두루미", "고슴도치", "두더지", "아홀로틀", "맹꽁이",
                "너구리", "개구리", "두꺼비", "카멜레온", "이구아나", "노루", "제비", "까치", "고라니", "수달",
                "당나귀", "순록", "염소", "공작", "바다표범", "들소", "박쥐", "참새", "물개", "바다사자",
                "살모사", "구렁이", "얼룩말", "산양", "멧돼지", "카피바라", "도롱뇽", "북극곰", "퓨마", "",
                "미어캣", "코요테", "라마", "딱따구리", "기러기", "비둘기", "스컹크", "돌고래", "까마귀", "매",
                "낙타", "여우", "사슴", "늑대", "재규어", "알파카", "양", "다람쥐", "담비"
        };

        Random random = new Random();

        // 랜덤하게 형용사와 동물 선택
        String randomAdjective = adjectives[random.nextInt(adjectives.length)];
        String randomAnimal = animals[random.nextInt(animals.length)];


        memberRepository.findByEmail(signUpRequest.getEmail())
                .ifPresent(member -> {
                    throw new MemberException(MemberErrorCode.EMAIL_EXIST);
                });

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .nickname(randomAdjective + " " + randomAnimal)
                .build();
        memberRepository.save(
                member
        );

        Verify verify = Verify.builder()
                .member(member)
                .build();
        verifyRepository.save(verify);
        //이메일 인증 보내기
        MailVo mailVo = MailVo.builder()
                .address(signUpRequest.getEmail())
                .uuid(verify.getUuid())
                .build();

        mailService.authMailSend(mailVo);
        log.info("메일인증 발송!");
    }

    public void signIn(SignInRequest signInRequest) {
        Member member = memberRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(
                        () -> {
                            return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                        }
                );
        Verify verify = member.getVerify().orElseGet( //token 이 없다면 생성 후 반환
                () -> {
                    Verify a = Verify.builder().member(member).build();
                    verifyRepository.save(a);
                    return a;
                }

        );

        log.info("token = [{}] ", verify.getUuid());
        //이메일 인증 보내기
        MailVo mailVo = MailVo.builder()
                .address(member.getEmail())
                .uuid(verify.getUuid())
                .build();
        mailService.authMailSend(mailVo);
    }


    @Transactional
    public VerifyTokenResponse verify(String token) {
        // uuid 정보가 table에 존재하는지 확인
        Verify verify = verifyRepository.findByUuid(token).orElseThrow(
                () -> {
                    return new AuthException(AuthErrorCode.INVALID_TOKEN);
                }
        );

        Member member = verify.getMember().orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.TOKEN_IS_NOT_VALID);
                }
        );
        verifyRepository.delete(verify);

        return authService.generateVerifyTokenResponse(member.getId());
    }

    public Resource getImage(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );
        String fileName = member.getImage();
        if (fileName == null) {
            fileName = "noimage.jpeg";
        }
        String filePath = IMAGE_DIRECTORY + "/" + fileName;
        log.info("FILE NAME = [{}]", filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            file = new File(IMAGE_DIRECTORY + "/noimage.jpeg");
        }

        try {
            Resource resource = new FileSystemResource(file);
            return resource;
        } catch (Exception e) {
            throw new MemberException(MemberErrorCode.IMAGE_PROCESSING_ERROR);
        }
    }

    @Transactional
    public MemberDetailResponse detailMemberById(MemberDTO memberDTO,Long id) {
        log.info("Member DTO = [{}] ", memberDTO);
        Member member = memberRepository.findById(id).orElseThrow(
                () -> {
                    return new MemberException(MemberErrorCode.MEMBER_FIND_ERROR);
                }
        );

        boolean follow = false;
        if(memberDTO == null){
            follow = followRepository.findByMemberIdAndFollowMemberId(member.getId(),id).isPresent();
        }
        return MemberDetailResponse.toDto(member,follow);

    }

    public List<MemberSearchResponse> findByNickName(String nickname) {
        List<Member> memberList = memberRepository.findByNicknameContains(nickname);

        List<MemberSearchResponse> memberSearchResponseList = new ArrayList<>();
        for(Member member:memberList){
            memberSearchResponseList.add(MemberSearchResponse.toDto(member));
        }
        return memberSearchResponseList;
    }
}
