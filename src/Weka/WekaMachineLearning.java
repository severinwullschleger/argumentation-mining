package Weka;

import GUI.GUI;
import Main.MicroText;

import java.util.ArrayList;
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
        proponentOponentClassifier.run(microTexts, testDataPercentage, "Proponent-Opponent Classifier");
        isClaimClassifier.run(microTexts, testDataPercentage, "Is-Claim Classifier");
        attackSupportClassifier.run(microTexts, testDataPercentage, "Attack Support Classifier");
        rebutUndercutClassifier.run(microTexts, testDataPercentage, "Rebut Undercut Classifier");
        targetClassifier.run(microTexts, testDataPercentage, "Target Classifier");
        List<String> notif = new ArrayList<>();
        notif.add("The Learning process has been completed");
        GUI.getGUI().runClassifierInfoLabel.setText("");
        GUI.showNotification(notif);
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
