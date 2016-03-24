#!/bin/bash

# BRANCHES=("step0-getting-started" "step1-user-completed-no-database" "step2-user-with-mvc-framework" "step3-user-completed-database" "step4-qna-getting-started" "step5-qna-with-ajax" "step6-qna-with-mvc-framework" "step7-self-check" "step8-self-check-completed" "step9-mvc-framework-completed" "step10-di-getting-started" "step11-1st-di-framework" "step12-2nd-di-framework" "step13-3rd-di-framework" "step14-di-framework-completed" "step15-spring-orm-framework")
BRANCHES=("step1-user-completed-no-database" "step2-user-with-mvc-framework" "step3-user-completed-database" "step4-qna-getting-started" "step5-qna-with-ajax" "step6-qna-with-mvc-framework" "step7-self-check" "step8-self-check-completed" "step9-mvc-framework-completed")
COMMIT_REVISION=cc52eef^..8e34bb5

for BRANCH_NAME in "${BRANCHES[@]}"; do
    echo $BRANCH_NAME
    git checkout $BRANCH_NAME
	git cherry-pick $COMMIT_REVISION
	git push origin $BRANCH_NAME 
done

echo "Finished."