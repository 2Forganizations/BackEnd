package project.travelmate.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.travelmate.domain.Plan;
import project.travelmate.domain.enums.Category;
import project.travelmate.domain.enums.Gender;
import project.travelmate.request.PlanSearchRequest;
import project.travelmate.response.plan.CardPlanResponse;

import java.util.List;

import static project.travelmate.domain.QPlan.plan;
import static project.travelmate.domain.QPlanMember.planMember;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CardPlanResponse> search(PlanSearchRequest planSearchRequest, Pageable pageable) {
        List<Plan> fetch = queryFactory
                .selectFrom(plan)
                .join(plan.planMembers, planMember)
                .fetchJoin()
                .where(
                        eqTitle(planSearchRequest.getTitle()),
                        eqCategory(planSearchRequest.getCategory()),
                        betweenAge(planSearchRequest.getMinAge(), planSearchRequest.getMaxAge()),
                        eqGender(planSearchRequest.getGender())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .orderBy(plan.id.desc())
                .fetch();
        return afterTreatment(fetch);
    }

    private BooleanExpression betweenAge(Integer minAge, Integer maxAge) {
        if (minAge == null && maxAge == null) {
            return null;
        }
        if (minAge == null) {
//            return plan.minAge.goe(maxAge).or(plan.maxAge.loe(maxAge));
            return plan.minAge.goe(maxAge);
        }
        if (maxAge == null) {
            return plan.maxAge.loe(minAge);
        }
        return plan.minAge.goe(maxAge).and(plan.maxAge.loe(minAge));
    }

    private BooleanExpression eqCategory(Category searchCategory) {
        return searchCategory == null ? null : plan.category.eq(searchCategory);
    }

    private BooleanExpression eqTitle(String searchTitle){
        return searchTitle == null ? null : plan.title.contains(searchTitle);
    }

    private BooleanExpression eqGender(Gender searchGender){
        if (searchGender == null) {
            return null;
        }
        if (searchGender.equals(Gender.FEMALE)) {
            return plan.requireRecruitMember.charAt(2).ne('0')
                    .or(plan.requireRecruitMember.charAt(4).ne('0'));
        }
        if (searchGender.equals(Gender.MALE)) {
            return plan.requireRecruitMember.charAt(0).ne('0')
                    .or(plan.requireRecruitMember.charAt(4).ne('0'));
        }
        return null;
    }

    private Page<CardPlanResponse> afterTreatment(List<Plan> entities){
        PageImpl<Plan> page = new PageImpl<>(entities);
        Page<CardPlanResponse> map = page.map(plan -> new CardPlanResponse(plan));
        return map;
    }
}
