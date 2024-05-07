package store.beatherb.restapi.content.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ContentPopularityResponse {
    private List<ContentResponse> dailyVocal;
    private List<ContentResponse> weeklyVocal;
    private List<ContentResponse> monthlyVocal;
    private List<ContentResponse> dailyMelody;
    private List<ContentResponse> weeklyMelody;
    private List<ContentResponse> monthlyMelody;
    private List<ContentResponse> dailySoundtrack;
    private List<ContentResponse> weeklySoundtrack;
    private List<ContentResponse> monthlySoundtrack;

    @Builder
    public ContentPopularityResponse(List<ContentResponse> dailyVocal, List<ContentResponse> weeklyVocal, List<ContentResponse> monthlyVocal,
                                     List<ContentResponse> dailyMelody, List<ContentResponse> weeklyMelody, List<ContentResponse> monthlyMelody,
                                     List<ContentResponse> dailySoundtrack, List<ContentResponse> weeklySoundtrack, List<ContentResponse> monthlySoundtrack){
        this.dailyVocal = dailyVocal;
        this.weeklyVocal = weeklyVocal;
        this.monthlyVocal = monthlyVocal;
        this.dailyMelody = dailyMelody;
        this.weeklyMelody = weeklyMelody;
        this.monthlyMelody = monthlyMelody;
        this.dailySoundtrack = dailySoundtrack;
        this.weeklySoundtrack = weeklySoundtrack;
        this.monthlySoundtrack = monthlySoundtrack;
    }
}
