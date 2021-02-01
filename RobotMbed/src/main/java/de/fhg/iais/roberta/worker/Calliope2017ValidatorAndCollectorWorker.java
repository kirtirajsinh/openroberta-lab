package de.fhg.iais.roberta.worker;

import com.google.common.collect.ClassToInstanceMap;

import de.fhg.iais.roberta.bean.IProjectBean;
import de.fhg.iais.roberta.components.Project;
import de.fhg.iais.roberta.visitor.validate.AbstractValidatorAndCollectorVisitor;
import de.fhg.iais.roberta.visitor.validate.Calliope2017ValidatorAndCollectorVisitor;

public class Calliope2017ValidatorAndCollectorWorker extends AbstractValidatorAndCollectorWorker {

    @Override
    protected AbstractValidatorAndCollectorVisitor getVisitor(Project project, ClassToInstanceMap<IProjectBean.IBuilder<?>> beanBuilders) {
        return new Calliope2017ValidatorAndCollectorVisitor(project.getConfigurationAst(), beanBuilders);
    }
}
