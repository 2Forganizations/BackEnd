package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class PlanNotFoundException extends CustomException {

    public PlanNotFoundException() {
        super(ExceptionCodeConst.PLAN_NOT_FOUND_CODE);
    }
}
