package Weka;

import Main.*;
import java.util.List;

public class WekaMachineLearning {

    private ProponentOponentClassifier proponentOponentClassifier;
    private IsClaimClassifier isClaimClassifier;
    private AttackSupportClassifier attackSupportClassifier;
    private RebutUndercutClassifier rebutUndercutClassifier;
    private TargetClassifier targetClassifier;

    public WekaMachineLearning() {
        this.proponentOponentClassifier = new ProponentOponentClassifier();
        this.isClaimClassifier = new IsClaimClassifier();
        this.attackSupportClassifier = new AttackSupportClassifier();
        this.rebutUndercutClassifier = new RebutUndercutClassifier();
        this.targetClassifier = new TargetClassifier();
    }

    public void learn(List<MicroText> microTexts, int testDataPercentage) {
        proponentOponentClassifier.run(microTexts, testDataPercentage);
        isClaimClassifier.run(microTexts, testDataPercentage);
        attackSupportClassifier.run(microTexts, testDataPercentage);
        rebutUndercutClassifier.run(microTexts, testDataPercentage);
        targetClassifier.run(microTexts, testDataPercentage);
        Main.systemHasLearn = true;
//        StanceClassifier stanceClassifer = new StanceClassifier();
//        stanceClassifer.run(microTexts);
    }

    public MicroText decide(MicroText myMicroText) {

        proponentOponentClassifier.useClassifier(myMicroText);
        isClaimClassifier.useClassifier(myMicroText);
        attackSupportClassifier.useClassifier(myMicroText);
        rebutUndercutClassifier.useClassifier(myMicroText);
        targetClassifier.useClassifier(myMicroText);

        return myMicroText;
    }

}
