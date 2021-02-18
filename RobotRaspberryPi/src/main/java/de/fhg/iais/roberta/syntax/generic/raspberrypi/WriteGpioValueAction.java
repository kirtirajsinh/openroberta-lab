package de.fhg.iais.roberta.syntax.generic.raspberrypi;

import java.util.List;

import de.fhg.iais.roberta.blockly.generated.Block;
import de.fhg.iais.roberta.blockly.generated.Field;
import de.fhg.iais.roberta.blockly.generated.Mutation;
import de.fhg.iais.roberta.factory.BlocklyDropdownFactory;
import de.fhg.iais.roberta.inter.mode.action.IBuzzerMode;
import de.fhg.iais.roberta.syntax.*;
import de.fhg.iais.roberta.syntax.action.Action;
import de.fhg.iais.roberta.transformer.AbstractJaxb2Ast;
import de.fhg.iais.roberta.transformer.Ast2JaxbHelper;
import de.fhg.iais.roberta.util.dbc.Assert;
import de.fhg.iais.roberta.visitor.IVisitor;
import de.fhg.iais.roberta.visitor.hardware.IRaspberryPiVisitor;
import de.fhg.iais.roberta.visitor.hardware.actor.IPinVisitor;

/**
 * This class represents the <b>mbedActions_write_to_pin</b> blocks from Blockly into the AST (abstract syntax tree). Object from this class will generate code
 * for reading values from a given pin.<br/>
 * <br>
 * <br>
 * To create an instance from this class use the method {@link #make(String, IBuzzerMode, BlocklyBlockProperties, BlocklyComment)}.<br>
 */
public class WriteGpioValueAction<V> extends Action<V> {

    private final String port;
    private final IBuzzerMode value;

    private WriteGpioValueAction(String port, IBuzzerMode value, BlocklyBlockProperties properties, BlocklyComment comment) {
        super(BlockTypeContainer.getByName("GPIO_WRITE_VALUE"), properties, comment);
        Assert.notNull(port);
        Assert.notNull(value);
        this.port = port;
        this.value = value;

        setReadOnly();
    }

    /**
     * Create object of the class {@link WriteGpioValueAction}.
     *
     * @param properties of the block (see {@link BlocklyBlockProperties}),
     * @param comment added from the user,
     * @return read only object of {@link WriteGpioValueAction}
     */
    public static <V> WriteGpioValueAction<V> make(String port, IBuzzerMode value, BlocklyBlockProperties properties, BlocklyComment comment) {
        return new WriteGpioValueAction<>(port, value, properties, comment);
    }

    /**
     * Transformation from JAXB object to corresponding AST object.
     *
     * @param block for transformation
     * @param helper class for making the transformation
     * @return corresponding AST object
     */
    public static <V> Phrase<V> jaxbToAst(Block block, AbstractJaxb2Ast<V> helper) {
        BlocklyDropdownFactory factory = helper.getDropdownFactory();
        List<Field> fields = helper.extractFields(block, (short) 3);

        String port = helper.extractField(fields, BlocklyConstants.ACTORPORT);
        String value = helper.extractField(fields, BlocklyConstants.VALUE);

        return WriteGpioValueAction
            .make(factory.sanitizePort(port), factory.getBuzzerMode(value), helper.extractBlockProperties(block), helper.extractComment(block));
    }

    public String getPort() {
        return this.port;
    }

    @Override
    public String toString() {
        return "PinWriteValueAction [" + this.port + ", " + this.value + "]";
    }

    @Override
    protected V acceptImpl(IVisitor<V> visitor) {
        return ((IRaspberryPiVisitor<V>) visitor).visitWriteGpioValueAction(this);
    }

    @Override
    public Block astToBlock() {
        Block jaxbDestination = new Block();

        Mutation mutation = new Mutation();
        mutation.setProtocol("DIGITAL");
        jaxbDestination.setMutation(mutation);

        Ast2JaxbHelper.setBasicProperties(this, jaxbDestination);
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.VALUE, this.value.toString());
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.ACTORPORT, this.port);
        Ast2JaxbHelper.addField(jaxbDestination, BlocklyConstants.MODE, "DIGITAL");
        return jaxbDestination;
    }
}
