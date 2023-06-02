package project.travelmate.response.plan;

import lombok.Getter;
import project.travelmate.domain.Plan;

@Getter
public class RecruitNumber {
    private String recruitManNumber;
    private String recruitWomanNumber;
    private String recruitEtcNumber;

    public RecruitNumber(Plan plan) {
        String[] requireMember = plan.getRequireRecruitMember().split("/");
        String[] currentMember = plan.getCurrentRecruitMember().split("/");

        this.recruitManNumber =
                requireMember[0].equals("0") ? null : stringMinus(requireMember[0], currentMember[0]) + "/" + requireMember[0];
        this.recruitWomanNumber =
                requireMember[1].equals("0") ? null : stringMinus(requireMember[1], currentMember[1]) + "/" + requireMember[1];
        this.recruitEtcNumber =
                requireMember[2].equals("0") ? null : stringMinus(requireMember[2], currentMember[2]) + "/" + requireMember[2];

    }
    private String stringMinus(String a, String b) {
        int c = Integer.parseInt(a) - Integer.parseInt(b);
        return Integer.toString(c);
    }
}
