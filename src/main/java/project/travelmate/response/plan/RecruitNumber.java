package project.travelmate.response.plan;

import lombok.Getter;
import project.travelmate.domain.Plan;

@Getter
public class RecruitNumber {
    private String recruitManNumber;
    private String recruitWomanNumber;
    private String recruitEtcNumber;

    public RecruitNumber(Plan plan) {
        String[] requireRecruitMember = plan.getRequireRecruitMember().split("/");
        String[] currentRecruitMember = plan.getCurrentRecruitMember().split("/");

        this.recruitManNumber =
                stringMinus(requireRecruitMember[0], currentRecruitMember[0]) + "/" + requireRecruitMember[0];
        this.recruitWomanNumber =
                stringMinus(requireRecruitMember[1], currentRecruitMember[1]) + "/" + requireRecruitMember[1];
        this.recruitEtcNumber =
                stringMinus(requireRecruitMember[2], currentRecruitMember[2]) + "/" + requireRecruitMember[2];

    }
    private String stringMinus(String a, String b) {
        int c = Integer.parseInt(a) - Integer.parseInt(b);
        return Integer.toString(c);
    }
}
