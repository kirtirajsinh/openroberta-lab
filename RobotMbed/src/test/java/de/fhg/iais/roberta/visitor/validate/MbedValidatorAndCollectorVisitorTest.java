package de.fhg.iais.roberta.visitor.validate;

import com.google.common.collect.ClassToInstanceMap;
import de.fhg.iais.roberta.bean.IProjectBean;
import de.fhg.iais.roberta.bean.UsedHardwareBean;
import de.fhg.iais.roberta.components.ConfigurationAst;
import de.fhg.iais.roberta.components.UsedActor;
import de.fhg.iais.roberta.syntax.SC;
import de.fhg.iais.roberta.syntax.action.mbed.BothMotorsStopAction;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

public class MbedValidatorAndCollectorVisitorTest {

    private MbedValidatorAndCollectorVisitor mbedValidatorAndCollectorVisitor;
    private final UsedHardwareBean.Builder usedHardwareBean;

    public MbedValidatorAndCollectorVisitorTest() {
        ConfigurationAst brickConfiguration = Mockito.mock(ConfigurationAst.class);
        ClassToInstanceMap<IProjectBean.IBuilder<?>> classToInstanceMap = Mockito.mock(ClassToInstanceMap.class);
        usedHardwareBean = new UsedHardwareBean.Builder();
        Mockito.when(classToInstanceMap.getInstance(UsedHardwareBean.Builder.class)).thenReturn(usedHardwareBean);
        this.mbedValidatorAndCollectorVisitor = new MbedValidatorAndCollectorVisitor(brickConfiguration, classToInstanceMap);
    }

    @Test
    public void testBothMotorsStop() {
        BothMotorsStopAction<Void> voidBothMotorsStopAction = BothMotorsStopAction.make(null, null);
        mbedValidatorAndCollectorVisitor.visitBothMotorsStopAction(voidBothMotorsStopAction);
        Assertions.assertThat(usedHardwareBean.build().getUsedActors()).allMatch(usedActor -> usedActor.getType().equals(SC.CALLIBOT));
    }
}