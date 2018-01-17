package Weka;

import Main.MicroText;

import java.util.List;

public class WekaMachineLearning {

    private ProponentOponentClassifier proponentOponentClassifier;
    private IsClaimClassifier isClaimClassifier;
    private AttackSupportClassifier attackSupportClassifier;
    private RebutUndercutClassifier rebutUndercutClassifier;
    private TargetClassifier targetClassifier;

    public void learn(List<MicroText> microTexts, int testDataPercentage) {
        proponentOponentClassifier = new ProponentOponentClassifier();
        proponentOponentClassifier.run(microTexts, testDataPercentage);
        isClaimClassifier = new IsClaimClassifier();
        isClaimClassifier.run(microTexts, testDataPercentage);
        attackSupportClassifier = new AttackSupportClassifier();
        attackSupportClassifier.run(microTexts, testDataPercentage);
        rebutUndercutClassifier = new RebutUndercutClassifier();
        rebutUndercutClassifier.run(microTexts, testDataPercentage);
        targetClassifier = new TargetClassifier();
        targetClassifier.run(microTexts, testDataPercentage);

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
